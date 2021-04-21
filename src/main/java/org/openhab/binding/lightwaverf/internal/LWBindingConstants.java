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
package org.openhab.binding.lightwaverf.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link lightwaverfBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author David Murton - Initial contribution
 */

@NonNullByDefault
public class LWBindingConstants {

    private static final String BINDING_ID = "lightwaverf";

    // List of all Thing Type UIDs
    // Smart Series
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_ACCOUNT = new ThingTypeUID(BINDING_ID, "lightwaverfaccount");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_HUB = new ThingTypeUID(BINDING_ID, "h21");
    public static final ThingTypeUID THING_TYPE_THERMOSTAT = new ThingTypeUID(BINDING_ID, "t11");
    public static final ThingTypeUID THING_TYPE_TRV = new ThingTypeUID(BINDING_ID, "t12");
    public static final ThingTypeUID THING_TYPE_MAGNETIC_GEN1 = new ThingTypeUID(BINDING_ID, "m11");
    public static final ThingTypeUID THING_TYPE_EMONITOR_GEN1 = new ThingTypeUID(BINDING_ID, "e11");
    public static final ThingTypeUID THING_TYPE_SSOCKET_GEN1 = new ThingTypeUID(BINDING_ID, "s11");
    public static final ThingTypeUID THING_TYPE_DSOCKET_GEN1 = new ThingTypeUID(BINDING_ID, "s12");
    public static final ThingTypeUID THING_TYPE_SSOCKET_GEN2 = new ThingTypeUID(BINDING_ID, "s21");
    public static final ThingTypeUID THING_TYPE_DSOCKET_GEN2 = new ThingTypeUID(BINDING_ID, "s22");
    public static final ThingTypeUID THING_TYPE_SDIMMER_GEN1 = new ThingTypeUID(BINDING_ID, "d11");
    public static final ThingTypeUID THING_TYPE_SDIMMER_GEN2 = new ThingTypeUID(BINDING_ID, "d21");
    public static final ThingTypeUID THING_TYPE_DDIMMER_GEN2 = new ThingTypeUID(BINDING_ID, "d22");
    public static final ThingTypeUID THING_TYPE_TDIMMER_GEN2 = new ThingTypeUID(BINDING_ID, "d23");
    public static final ThingTypeUID THING_TYPE_QDIMMER_GEN2 = new ThingTypeUID(BINDING_ID, "d24");
    public static final ThingTypeUID THING_TYPE_TRELAY_GEN1 = new ThingTypeUID(BINDING_ID, "r13");
    public static final ThingTypeUID THING_TYPE_UNKNOWNDEVICE = new ThingTypeUID(BINDING_ID, "device");
    // Connect Series
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_1HUB = new ThingTypeUID(BINDING_ID, "1bridge");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_1LINK = new ThingTypeUID(BINDING_ID, "1link");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_1SWITCH = new ThingTypeUID(BINDING_ID, "1switch");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_1DIMMER = new ThingTypeUID(BINDING_ID, "1dimmer");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_1TRV = new ThingTypeUID(BINDING_ID, "1trv");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_1EM = new ThingTypeUID(BINDING_ID, "1em");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_1RELAY = new ThingTypeUID(BINDING_ID, "1relay");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_1MOOD = new ThingTypeUID(BINDING_ID, "1mood");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_1ALLOFF = new ThingTypeUID(BINDING_ID, "1alloff");

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPE_UIDS = Collections.unmodifiableSet(Stream.of(
            // Smart Series
            THING_TYPE_LIGHTWAVE_ACCOUNT, THING_TYPE_LIGHTWAVE_HUB, THING_TYPE_THERMOSTAT, THING_TYPE_TRV,
            THING_TYPE_MAGNETIC_GEN1, THING_TYPE_SSOCKET_GEN1, THING_TYPE_DSOCKET_GEN1, THING_TYPE_SSOCKET_GEN2,
            THING_TYPE_DSOCKET_GEN2, THING_TYPE_SDIMMER_GEN1, THING_TYPE_SDIMMER_GEN2, THING_TYPE_DDIMMER_GEN2,
            THING_TYPE_TDIMMER_GEN2, THING_TYPE_QDIMMER_GEN2, THING_TYPE_EMONITOR_GEN1, THING_TYPE_TRELAY_GEN1,
            THING_TYPE_UNKNOWNDEVICE,
            // Connect Series
            THING_TYPE_LIGHTWAVE_1HUB, THING_TYPE_LIGHTWAVE_1SWITCH, THING_TYPE_LIGHTWAVE_1DIMMER,
            THING_TYPE_LIGHTWAVE_1TRV, THING_TYPE_LIGHTWAVE_1EM, THING_TYPE_LIGHTWAVE_1RELAY,
            THING_TYPE_LIGHTWAVE_1MOOD, THING_TYPE_LIGHTWAVE_1ALLOFF).collect(Collectors.toSet()));

    public static final Set<ThingTypeUID> DISCOVERABLE_THING_TYPE_UIDS = Collections.unmodifiableSet(Stream.of(
            // Smart Series
            THING_TYPE_LIGHTWAVE_HUB, THING_TYPE_THERMOSTAT, THING_TYPE_TRV, THING_TYPE_MAGNETIC_GEN1,
            THING_TYPE_SSOCKET_GEN1, THING_TYPE_DSOCKET_GEN1, THING_TYPE_SSOCKET_GEN2, THING_TYPE_DSOCKET_GEN2,
            THING_TYPE_SDIMMER_GEN1, THING_TYPE_SDIMMER_GEN2, THING_TYPE_DDIMMER_GEN2, THING_TYPE_TDIMMER_GEN2,
            THING_TYPE_QDIMMER_GEN2, THING_TYPE_EMONITOR_GEN1, THING_TYPE_TRELAY_GEN1, THING_TYPE_UNKNOWNDEVICE,
            // Connect Series
            THING_TYPE_LIGHTWAVE_1SWITCH, THING_TYPE_LIGHTWAVE_1DIMMER, THING_TYPE_LIGHTWAVE_1TRV,
            THING_TYPE_LIGHTWAVE_1EM, THING_TYPE_LIGHTWAVE_1RELAY, THING_TYPE_LIGHTWAVE_1MOOD,
            THING_TYPE_LIGHTWAVE_1ALLOFF).collect(Collectors.toSet()));

    // Map of product codes supported
    public static final Map<String, ThingTypeUID> createMap() { // thing type denotes number of channels
        Map<String, ThingTypeUID> deviceTypes = new HashMap<>();
        deviceTypes.put("L2", THING_TYPE_LIGHTWAVE_HUB);
        deviceTypes.put("LW931", THING_TYPE_MAGNETIC_GEN1);
        deviceTypes.put("LW600", THING_TYPE_EMONITOR_GEN1);
        deviceTypes.put("LW320", THING_TYPE_SSOCKET_GEN1);
        deviceTypes.put("LW380", THING_TYPE_SSOCKET_GEN1);
        deviceTypes.put("LW260", THING_TYPE_SSOCKET_GEN1);
        deviceTypes.put("LW381", THING_TYPE_DSOCKET_GEN1);
        deviceTypes.put("LW400", THING_TYPE_SDIMMER_GEN1);
        deviceTypes.put("L42", THING_TYPE_DSOCKET_GEN2);
        deviceTypes.put("L21", THING_TYPE_SDIMMER_GEN2);
        deviceTypes.put("L21MK2", THING_TYPE_SDIMMER_GEN2);
        deviceTypes.put("L22", THING_TYPE_DDIMMER_GEN2);
        deviceTypes.put("L22MK2", THING_TYPE_DDIMMER_GEN2);
        deviceTypes.put("L22EU", THING_TYPE_DDIMMER_GEN2);
        deviceTypes.put("L23", THING_TYPE_TDIMMER_GEN2);
        deviceTypes.put("L23MK2", THING_TYPE_TDIMMER_GEN2);
        deviceTypes.put("L24", THING_TYPE_QDIMMER_GEN2);
        deviceTypes.put("L24MK2", THING_TYPE_QDIMMER_GEN2);
        deviceTypes.put("LW921", THING_TYPE_THERMOSTAT);
        deviceTypes.put("LW922", THING_TYPE_TRV);
        deviceTypes.put("LW821", THING_TYPE_TRELAY_GEN1);
        return Collections.unmodifiableMap(deviceTypes);
    }

    // Structure / Devices / Featuresets
    public static final String CHANNEL_NAME = "name";
    // Devices / Features
    public static final String CHANNEL_TYPE = "type";

    public static final String CHANNEL_ENERGY_RESET = "energyReset";
    public static final String CHANNEL_ENERGY_COST = "energyCost";
    public static final String CHANNEL_POWER_RESET = "powerReset";
    public static final String CHANNEL_POWER_COST = "powerCost";

    public static final String CHANNEL_THREEWAYRELAY = "threeWayRelay";
    public static final String CHANNEL_WINDOWPOSITION = "windowPosition";

    public static final String CHANNEL_VALVESETUP = "valveSetup";

    // List of all Channel ids (Sockets)
    public static final String CHANNEL_SWITCH = "switch";
    public static final String CHANNEL_OUTLET_IN_USE = "outletInUse";
    public static final String CHANNEL_PROTECTION = "protection";
    public static final String CHANNEL_POWER = "power";
    public static final String CHANNEL_POWERM = "powerm";
    public static final String CHANNEL_ENERGY = "energy";
    public static final String CHANNEL_IDENTIFY = "identify";
    public static final String CHANNEL_RESET = "reset";
    public static final String CHANNEL_UPGRADE = "upgrade";
    public static final String CHANNEL_DIAGNOSTICS = "diagnostics";
    public static final String CHANNEL_PERIOD_OF_BROADCAST = "periodOfBroadcast";
    public static final String CHANNEL_RGB_COLOR = "rgbColor";
    public static final String CHANNEL_VOLTAGE = "voltage";
    // List of all Channel ids (Structure)

    public static final String CHANNEL_GROUP_ID = "groupId";
    // List of all Channel ids (Devices)
    public static final String CHANNEL_SDEVICE_ID = "sdId";
    public static final String CHANNEL_DEVICE_ID = "deviceId";
    public static final String CHANNEL_PRODUCT_CODE = "productCode";
    public static final String CHANNEL_PRODUCT = "product";
    public static final String CHANNEL_DEVICE = "device";
    public static final String CHANNEL_DESC = "desc";

    public static final String CHANNEL_CAT = "cat";
    public static final String CHANNEL_GEN = "gen";
    // List of all Channel ids (Featuresets)
    public static final String CHANNEL_FEATURE_SET_ID = "featureSetId";
    // List of all Channel ids (Features)
    public static final String CHANNEL_FEATURE_ID = "featureId";
    public static final String CHANNEL_WRITABLE = "writable";

    public static final String CHANNEL_BATTERY_LEVEL = "batteryLevel";
    public static final String CHANNEL_RSSI = "rssi";
    public static final String CHANNEL_CALL_FOR_HEAT = "callForHeat";
    public static final String CHANNEL_TEMPERATURE = "temperature";
    public static final String CHANNEL_TARGET_TEMPERATURE = "targetTemperature";
    public static final String CHANNEL_VALVE_LEVEL = "valveLevel";
    public static final String CHANNEL_HEAT_STATE = "heatState";

    public static final String CHANNEL_DIM_LEVEL = "dimLevel";
    public static final String CHANNEL_DIM_SETUP = "dimSetup";
    public static final String CHANNEL_BULB_SETUP = "bulbSetup";

    public static final String CHANNEL_CURRENT_TIME = "currentTime";
    public static final String CHANNEL_BUTTON_PRESS = "buttonPress";
    public static final String CHANNEL_TIME = "time";
    public static final String CHANNEL_DATE = "date";
    public static final String CHANNEL_MONTH_ARRAY = "monthArray";
    public static final String CHANNEL_WEEKDAY_ARRAY = "wekdayArray";
    public static final String CHANNEL_LOCATION_LONGITUDE = "locationLongitude";
    public static final String CHANNEL_LOCATION_LATITUDE = "locationLatitude";
    public static final String CHANNEL_DUSK_TIME = "duskTime";
    public static final String CHANNEL_DAWN_TIME = "dawnTime";
    public static final String CHANNEL_DAY = "day";
    public static final String CHANNEL_MONTH = "month";
    public static final String CHANNEL_YEAR = "year";
    public static final String CHANNEL_WEEKDAY = "weekday";
    public static final String CHANNEL_TIME_ZONE = "timeZone";
}
