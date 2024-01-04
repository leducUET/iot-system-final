package common.payload_interfaces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Light {

    private int lightIntensityLux;

    @JsonCreator
    public Light(
            @JsonProperty("lightIntensityLux") int lightIntensityLux) {
        this.lightIntensityLux = lightIntensityLux;
    }

    // Getters and setters
    public void setLightIntensityLux(int lightIntensityLux) {
        this.lightIntensityLux = lightIntensityLux;
    }

    public int getLightIntensityLux() {
        return lightIntensityLux;
    }

    @Override
    public String toString() {
        return String.format("{\"lightIntensityLux\": %d}", this.lightIntensityLux);
    }
}
