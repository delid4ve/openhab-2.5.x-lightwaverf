/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.lightwaverf.internal.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.HSBType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.PercentType;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.ThingStatusInfo;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.eclipse.smarthome.core.types.State;
import org.openhab.binding.lightwaverf.internal.config.LightwaverfSmartDeviceConfig;
import org.openhab.binding.lightwaverf.internal.dto.LightwaverfSmartItem;
import org.openhab.binding.lightwaverf.internal.dto.LightwaverfSmartPayload;
import org.openhab.binding.lightwaverf.internal.dto.LightwaverfSmartRequest;
import org.openhab.binding.lightwaverf.internal.dto.api.LightwaverfSmartDevices;
import org.openhab.binding.lightwaverf.internal.dto.api.LightwaverfSmartFeatureSets;
import org.openhab.binding.lightwaverf.internal.dto.api.LightwaverfSmartFeatures;
import org.openhab.binding.lightwaverf.internal.listeners.LightwaverfSmartDeviceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link LightwaverfSmartDeviceHandler} class handles gen2 lightwave devices
 * 
 *
 * @author David Murton - Initial contribution
 */

@NonNullByDefault
public class LightwaverfSmartDeviceHandler extends BaseThingHandler implements LightwaverfSmartDeviceListener {

    private final Logger logger = LoggerFactory.getLogger(LightwaverfSmartDeviceHandler.class);
    private @Nullable LightwaverfSmartAccountHandler account;
    private Map<String, String> channels = new HashMap<String, String>();
    private String deviceid = "";
    private Double electricityCost = 0.0;

    public LightwaverfSmartDeviceHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        Bridge bridge = getBridge();
        if (bridge == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "Bridge Not set");
        } else {
            LightwaverfSmartAccountHandler account = (LightwaverfSmartAccountHandler) bridge.getHandler();
            if (account != null) {
                this.account = account;
                this.electricityCost = account.getElectricityCost();
                LightwaverfSmartDeviceConfig config = this.getConfigAs(LightwaverfSmartDeviceConfig.class);
                this.deviceid = config.deviceid;
                LightwaverfSmartDevices device = account.getDevice(deviceid);
                if (device == null) {
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                            "Please check the deviceid as the data couldnt be retrieved");
                    return;
                } else {
                    // Create a channel map and add the features to the featuremap for received messages
                    List<LightwaverfSmartFeatureSets> featureSets = device.getFeatureSets();
                    for (int i = 0; i < featureSets.size(); i++) {
                        List<LightwaverfSmartFeatures> features = featureSets.get(i).getFeatures();
                        for (int j = 0; j < features.size(); j++) {
                            String channel = (i + 1) + "#" + features.get(j).getType();
                            String featureid = features.get(j).getFeatureId();
                            logger.trace("Adding Channel {} with featureid {} to map for device {}", channel, featureid,
                                    deviceid);
                            account.addFeature(featureid, deviceid);
                            channels.putIfAbsent(channel, featureid);
                        }
                    }
                    setProperties(device);
                    account.addDeviceListener(deviceid, this);
                }
                if (bridge.getStatus() == ThingStatus.ONLINE) {
                    updateStatus(ThingStatus.ONLINE);
                } else {
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE);
                }
            }
        }
    }

    @Override
    public void bridgeStatusChanged(ThingStatusInfo bridgeStatusInfo) {
        logger.debug("bridgeStatusChanged {} for thing {}", bridgeStatusInfo, getThing().getUID());
        if (bridgeStatusInfo.getStatus() == ThingStatus.ONLINE) {
            updateStatus(ThingStatus.ONLINE);
            for (int j = 0; j < this.getThing().getChannels().size(); j++) {
                handleCommand(this.getThing().getChannels().get(j).getUID(), RefreshType.REFRESH);
            }
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE);
        }
    }

    @Override
    public void dispose() {
        logger.debug("LightwaveRF - Running dispose()");
        LightwaverfSmartAccountHandler account = this.account;
        if (account != null) {
            account.removeDeviceListener(deviceid);
            account = null;
        }
    }

    private void setProperties(LightwaverfSmartDevices device) {
        Map<String, String> properties = editProperties();
        Integer channelSize = device.getFeatureSets().size();
        properties.clear();
        properties.put("deviceid", device.getDeviceId());
        properties.put("Name", device.getName());
        properties.put("Device", device.getDevice());
        properties.put("Type", device.getType());
        properties.put("Description", device.getDesc());
        properties.put("Product", device.getProduct());
        properties.put("Product Code", device.getProductCode());
        properties.put("Category", device.getCat());
        properties.put("Generation", device.getGen().toString());
        properties.put("Channels", channelSize.toString());
        updateProperties(properties);
    }

    private long getState(String channel, String command) {
        switch (channel) {
            case "energyReset":
            case "voltageReset":
                return 0;
            case "switch":
            case "diagnostics":
            case "outletInUse":
            case "protection":
            case "identify":
            case "reset":
            case "upgrade":
            case "heatState":
            case "callForHeat":
            case "bulbSetup":
            case "dimSetup":
            case "valveSetup":
            case "threeWayRelay":
                if (command == "ON") {
                    return 1;
                } else {
                    return 0;
                }
            case "rgbColor":
                if (command.contains(",")) {
                    HSBType hsb = new HSBType(command);
                    int hue = Integer.parseInt(hsb.getHue().toString());
                    int brightness = Integer.parseInt(hsb.getBrightness().toString());
                    if (brightness > 100) {
                        brightness = 100;
                    }
                    if (hue >= 0 && hue < 50) {
                        hue = 0;
                    } else if (hue >= 40 && hue < 75) {
                        hue = 75;
                    } else if (hue >= 75 && hue < 160) {
                        hue = 120;
                    } else if (hue >= 160 && hue < 260) {
                        hue = 220;
                    } else if (hue >= 260 && hue < 325) {
                        hue = 275;
                    } else if (hue >= 325) {
                        hue = 0;
                    }
                    PercentType brightness1 = new PercentType(brightness);
                    DecimalType hue1 = new DecimalType(hue);
                    PercentType saturation = new PercentType(100);
                    HSBType h = new HSBType(hue1, saturation, brightness1);
                    PercentType redp = h.getRed();
                    PercentType greenp = h.getGreen();
                    PercentType bluep = h.getBlue();
                    int redr = (int) (redp.doubleValue() * 255 / 100);
                    int greenr = (int) (greenp.doubleValue() * 255 / 100);
                    int bluer = (int) (bluep.doubleValue() * 255 / 100);
                    long d = (redr * 65536 + greenr * 256 + bluer);
                    return d;
                } else {
                    logger.warn("LightwaveRF - Brightness Is Not Supported For the RGB Colour Channel");
                    return -1;
                }
            case "timeZone":
            case "locationLongitude":
            case "locationLatitude":
            case "dimLevel":
            case "valveLevel":
                return (long) (Float.parseFloat(command));
            case "temperature":
            case "targetTemperature":
                return (long) (Float.parseFloat(command) * 10);
            default:
                return -1;
        }
    }

    private void resetCommand(ChannelUID channelUID, Command command) {
        if (command.toString().toLowerCase().equals("on")) {
            if (channelUID.getIdWithoutGroup().equals("energyReset")) {
                String featureid = channels.get(channelUID.getGroupId() + "#" + "energy");
                if (featureid != null) {
                    setStatus("feature", "write", "request", 0L, featureid);
                } else {
                    logger.error("Command {} for device {} returned a null featureid and couldnt be sent",
                            command.toString(), this.deviceid);
                }
            } else if (channelUID.getIdWithoutGroup().equals("voltageReset")) {
                String featureid = channels.get(channelUID.getGroupId() + "#" + "voltage");
                if (featureid != null) {
                    setStatus("feature", "write", "request", 0L, featureid);
                } else {
                    logger.error("Command {} for device {} returned a null featureid and couldnt be sent",
                            command.toString(), this.deviceid);
                }
            }
            updateState(channelUID.getId().toString(), OnOffType.OFF);
        }
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (command instanceof RefreshType) {
            String featureid = channels.get(channelUID.getId());
            if (featureid != null) {
                logger.trace("Device {} is requesting an update for {}", deviceid, channelUID.getId());
                setStatus("feature", "read", "request", null, featureid);
            }
            return;
        } else {
            String channelid = channelUID.getIdWithoutGroup();
            if (channelid.equals("energyReset") || channelid.equals("voltageReset")) {
                resetCommand(channelUID, command);
            } else {
                String featureid = channels.get(channelUID.getId());
                if (featureid != null) {
                    long value = getState(channelUID.getIdWithoutGroup(), command.toString());
                    setStatus("feature", "write", "request", value, featureid);
                } else {
                    logger.error("Command {} for device {} returned a null featureid and couldnt be sent",
                            command.toString(), this.deviceid);
                }
            }
        }
    }

    private void setStatus(String _class, String operation, String direction, @Nullable Long value, String id) {
        LightwaverfSmartItem item = new LightwaverfSmartItem();
        LightwaverfSmartPayload payload = new LightwaverfSmartPayload();
        // Random random = new Random();
        payload.setFeatureId(id);
        if (value != null) {
            payload.setValue(value);
        }
        item.setPayload(payload);
        LightwaverfSmartRequest command = new LightwaverfSmartRequest(_class, operation, direction, item);
        if (payload.getFeatureId() == null) {
            logger.error("Payload was emtpy from device {}, not sending message {} - {} - {}", deviceid, id, _class,
                    operation);
        } else {
            LightwaverfSmartAccountHandler account = this.account;
            if (account != null && account.isConnected()) {
                account.sendDeviceCommand(command);
            } else {
                logger.error("Could not set status for device {} as the account is disconnected", deviceid);
            }
        }
    }

    @Override
    public void updateChannel(String channelId, State state) {
        updateState(channelId, state);
        logger.debug("Device {} Updated Channel {}", deviceid, channelId);
        if (channelId.contains("power")) {
            Double value = Double.valueOf(state.toString());
            updateState(channelId + "Cost", new DecimalType((value / 1000) * electricityCost));
        }
        if (channelId.contains("energy")) {
            Double value = Double.valueOf(state.toString());
            updateState(channelId + "Cost", new DecimalType(value * electricityCost));
        }
    }
}
