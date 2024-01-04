package common.payload_interfaces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GatewayPayload {
    private String sensors;
    private String devices;

    @JsonCreator
    public GatewayPayload(
            @JsonProperty("sensors") String sensors,
            @JsonProperty("devices") String devices) {
        this.sensors = sensors;
        this.devices = devices;
    }


    @Override
    public String toString() {
        return String.format("{\"sensors\": %s, \"devices\": %s}", this.sensors, this.devices);
    }

    public void setSensors(String sensors) {
        this.sensors = sensors;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }
}
