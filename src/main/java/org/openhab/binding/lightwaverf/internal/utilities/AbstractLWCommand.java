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
package org.openhab.binding.lightwaverf.internal.utilities;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * This abstract class represents a command that we can send to the LightwaveRF
 * wifi link it includes utility methods that are applicable for use by all
 * LightwaveRfCommands that extend it.
 * /**
 * 
 * @author Neil Renaud - Initial contribution
 * @author David Murton - Since OH 2.x
 * 
 */
@NonNullByDefault
public abstract class AbstractLWCommand implements LWCommand {

    public String getFunctionMessageString(MessageId messageId, String roomId, String function) {
        return messageId.getMessageIdString() + ",!R" + roomId + "F" + function + "\n";
    }

    public String getMessageString(MessageId messageId, String roomId, String deviceId, String function) {
        return messageId.getMessageIdString() + ",!R" + roomId + "D" + deviceId + "F" + function + "\n";
    }

    public String getMessageString(MessageId messageId, String roomId, String function) {
        return messageId.getMessageIdString() + ",!R" + roomId + "F" + function + "\n";
    }

    public String getMessageString(MessageId messageId, String roomId, String function, int parameter) {
        return messageId.getMessageIdString() + ",!R" + roomId + "F" + function + "P" + parameter + "\n";
    }

    public String getMessageString(MessageId messageId, String roomId, String deviceId, String function,
            int parameter) {
        return messageId.getMessageIdString() + ",!R" + roomId + "D" + deviceId + "F" + function + "P" + parameter
                + "\n";
    }

    public String getMessageString(MessageId messageId, String roomId, String deviceId, String function,
            double parameter) {
        return messageId.getMessageIdString() + ",!R" + roomId + "D" + deviceId + "F" + function + "P" + parameter
                + "\n";
    }

    public String getVersionString(MessageId messageId, String version) {
        return messageId.getMessageIdString() + ",?V=\"" + version + "\"\n";
    }

    public String getOkString(MessageId messageId) {
        return messageId.getMessageIdString() + ",OK\n";
    }

    public String getDeviceRegistrationMessageString(MessageId messageId, String function, String parameter) {
        return messageId.getMessageIdString() + ",!F" + function + "p" + parameter + "\n";
    }
}
