package common.payload_interfaces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import common.enums.SensorType;
import common.enums.Status;

public class DataPayload {
    private String from;
    private Status status;
    private String data;

    @JsonCreator
    public DataPayload(
            @JsonProperty("from") String from,
            @JsonProperty("status") Status status,
            @JsonProperty("data") String data) {
        this.from = from;
        this.status = status;
        this.data = data;
    }


    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "{" +
                "\"from\":\"" + from + '"' +
                ", \"status\":\"" + status + '"' +
                ", \"data\":" + data +
                "}";
    }
}