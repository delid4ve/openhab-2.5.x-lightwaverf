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
package org.openhab.binding.lightwaverf.internal.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author David Murton - Initial contribution
 */
public class LightwaverfSmartPing {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("class")
    @Expose
    private String classType;
    @SerializedName("operation")
    @Expose
    private String operation;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("senderId")
    @Expose
    private String senderId;
    @SerializedName("items")
    @Expose
    private List<LightwaverfSmartItem> items = new ArrayList<LightwaverfSmartItem>();
    @SerializedName("transactionId")
    @Expose
    private Integer transactionId;
    @SerializedName("error")
    @Expose
    private String error;

    public LightwaverfSmartPing(String token, String clientDeviceUuid) {
        this.classType = "device";
        this.operation = "read";
        this.direction = "request";
        this.version = 1;
        LightwaverfSmartPayload payload = new LightwaverfSmartPayload();
        payload.setToken(token);
        payload.setClientDeviceId(clientDeviceUuid);
        payload.setDeviceId(1);
        LightwaverfSmartItem item = new LightwaverfSmartItem();
        item.setItemId("0");
        item.setPayload(payload);
        items.add(item);
    }

    public Integer getVersion() {
        return version;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getDirection() {
        return direction;
    }

    public List<LightwaverfSmartItem> getItems() {
        return items;
    }

    public void setItems(List<LightwaverfSmartItem> items) {
        this.items = items;
    }

    public String getClass_() {
        return classType;
    }

    public String getOperation() {
        return operation;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
