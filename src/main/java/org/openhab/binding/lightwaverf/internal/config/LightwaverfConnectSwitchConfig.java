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
package org.openhab.binding.lightwaverf.internal.config;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * The {@link LightwaverfConnectSwitchConfig} class defines the configuration for a
 * generation 1 connect series switch
 *
 * @author David Murton - Initial contribution
 */
@NonNullByDefault
public class LightwaverfConnectSwitchConfig {

    public String roomId = "";
    public String deviceId = "";

    @Override
    public String toString() {
        return "[roomid=" + roomId + ", deviceid=" + deviceId + "]";
    }
}
