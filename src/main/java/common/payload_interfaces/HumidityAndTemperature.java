package common.payload_interfaces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class HumidityAndTemperature {
    private double humidity;
    private double temperature;

    @JsonCreator
    public HumidityAndTemperature(
            @JsonProperty("humidity") double humidity,
            @JsonProperty("temperature") double temperature) {
        this.humidity = humidity;
        this.temperature = temperature;
    }

    // Getters and setters
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return String.format("{\"temperature\": %.2f, \"humidity\": %.2f}", this.temperature, this.humidity);
    }
}
