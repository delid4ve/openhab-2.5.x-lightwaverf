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

import static org.openhab.binding.lightwaverf.internal.LWBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.eclipse.smarthome.io.net.http.HttpClientFactory;
import org.eclipse.smarthome.io.net.http.WebSocketFactory;
import org.openhab.binding.lightwaverf.internal.handler.LightwaverfConnectAccountHandler;
import org.openhab.binding.lightwaverf.internal.handler.LightwaverfConnectTRVHandler;
import org.openhab.binding.lightwaverf.internal.handler.LightwaverfSmartAccountHandler;
import org.openhab.binding.lightwaverf.internal.handler.LightwaverfSmartDeviceHandler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The {@link lightwaverfHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author David Murton - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.lightwaverf", service = { ThingHandlerFactory.class, })

public class LWHandlerFactory extends BaseThingHandlerFactory {

    private final WebSocketClient webSocketClient;
    private final HttpClient httpClient;
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPE_UIDS.contains(thingTypeUID);
    }

    @Activate
    public LWHandlerFactory(final @Reference WebSocketFactory webSocketFactory,
            final @Reference HttpClientFactory httpClientFactory) {
        this.webSocketClient = webSocketFactory.getCommonWebSocketClient();
        this.httpClient = httpClientFactory.getCommonHttpClient();
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (THING_TYPE_LIGHTWAVE_ACCOUNT.equals(thingTypeUID)) {
            return new LightwaverfSmartAccountHandler((Bridge) thing, webSocketClient, httpClient, gson);
        } else if (THING_TYPE_LIGHTWAVE_1HUB.equals(thingTypeUID)) {
            return new LightwaverfConnectAccountHandler((Bridge) thing);
        } else if (THING_TYPE_LIGHTWAVE_1TRV.equals(thingTypeUID)) {
            return new LightwaverfConnectTRVHandler((Thing) thing);
        } else {
            return new LightwaverfSmartDeviceHandler(thing);
        }
    }
}
