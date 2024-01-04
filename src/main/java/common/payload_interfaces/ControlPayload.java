package common.payload_interfaces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ControlPayload {
    private String from;
    private String to;
    private String command;


    @JsonCreator
    public ControlPayload(
            @JsonProperty("from") String from,
            @JsonProperty("to") String to,
            @JsonProperty("command") String command) {
        this.from = from;
        this.to = to;
        this.command = command;
    }


    public void setCommand(String command) {
        this.command = command;
    }
    public String getCommand() {
        return command;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "{" +
                "\"from\":\"" + from + '"' +
                ", \"to\":\"" + to + '"' +
                ", \"command\":" + command +
                "}";
    }
}