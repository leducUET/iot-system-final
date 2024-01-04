package common.payload_interfaces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BulbPayload {

    private final double power;

    @JsonCreator
    public BulbPayload(
            @JsonProperty("power") double power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return String.format("{\"power\": %.2f}", this.power);
    }
}
