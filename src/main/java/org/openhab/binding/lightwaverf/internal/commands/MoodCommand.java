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
package org.openhab.binding.lightwaverf.internal.commands;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.types.State;
import org.openhab.binding.lightwaverf.internal.LightwaverfConnectMessageException;
import org.openhab.binding.lightwaverf.internal.utilities.AbstractLWCommand;
import org.openhab.binding.lightwaverf.internal.utilities.GeneralMessageId;
import org.openhab.binding.lightwaverf.internal.utilities.LWType;
import org.openhab.binding.lightwaverf.internal.utilities.MessageId;
import org.openhab.binding.lightwaverf.internal.utilities.MessageType;

/**
 * 
 * @author Neil Renaud - Initial contribution
 * @author David Murton - Since OH 2.x
 * 
 */
@NonNullByDefault
public class MoodCommand extends AbstractLWCommand implements RoomMessage {

    private static final Pattern REG_EXP = Pattern.compile(".*?([0-9]{1,3}),!R([0-9])FmP([0-9]{1,2}).*\\s*");
    private static final String MOOD_FUNCTION = "m";

    private final MessageId messageId;
    private final String roomId;
    private final int mood;

    /**
     * Commands are like: 100,!R2D3FdP1 (Lowest Brightness) 101,!R2D3FdP32 (High
     * brightness)
     */

    public MoodCommand(int messageId, String roomId, int mood) {
        this.roomId = roomId;
        this.mood = mood;
        this.messageId = new GeneralMessageId(messageId);
    }

    public MoodCommand(String message) throws LightwaverfConnectMessageException {
        try {
            Matcher matcher = REG_EXP.matcher(message);
            matcher.matches();
            this.messageId = new GeneralMessageId(Integer.valueOf(matcher.group(1)));
            this.roomId = matcher.group(2);
            this.mood = Integer.valueOf(matcher.group(3));
        } catch (Exception e) {
            throw new LightwaverfConnectMessageException("Error converting Dimming message: " + message, e);
        }
    }

    @Override
    public @Nullable String getCommandString() {
        return getMessageString(messageId, roomId, MOOD_FUNCTION, mood);
    }

    @Override
    public String getRoomId() {
        return roomId;
    }

    @Override
    public @Nullable State getState(@Nullable LWType type) {
        switch (type) {
            case MOOD:
                return new DecimalType(Integer.toString(mood));
            default:
                return null;
        }
    }

    @Override
    public @Nullable MessageId getMessageId() {
        return messageId;
    }

    @Override
    public boolean equals(@Nullable Object that) {
        if (that instanceof MoodCommand) {
            return Objects.equals(this.messageId, ((MoodCommand) that).messageId)
                    && Objects.equals(this.roomId, ((MoodCommand) that).roomId)
                    && Objects.equals(this.mood, ((MoodCommand) that).mood);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, roomId, mood);
    }

    @Override
    public @Nullable MessageType getMessageType() {
        return MessageType.ROOM;
    }

    public static boolean matches(String message) {
        return message.contains("FmP");
    }
}
