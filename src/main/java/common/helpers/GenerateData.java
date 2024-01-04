package common.helpers;


import common.enums.Status;
import common.payload_interfaces.BulbPayload;
import common.payload_interfaces.GatewayPayload;
import common.payload_interfaces.HumidityAndTemperature;
import common.payload_interfaces.Light;

import java.util.Map;
import java.util.Random;

public class GenerateData {
    static double baseHumidity = 50;
    static double baseTemperature = 27;
    static int baseLight = 50;
    static double initBulbPower = 40;

    public static HumidityAndTemperature generateHumidityAndTemperature() {
        Random random = new Random();

        // [-0.5; 0.5]
        double humidityFluctuationRange = random.nextDouble() - 0.5;
        double humidity = baseHumidity + humidityFluctuationRange;

        double temperatureFluctuationRange = random.nextDouble() - 0.5;
        double temperature = baseTemperature + temperatureFluctuationRange;

        baseHumidity = humidity;
        baseTemperature = temperature;

        return new HumidityAndTemperature(humidity, temperature);
    }

    public static Light generateLight() {
        Random random = new Random();

        // [-3; 3]
        int lightFluctuationRange = random.nextInt(7) - 3;
        int lightIntensityLux = lightFluctuationRange + baseLight;

        baseLight = lightIntensityLux;

        return new Light(lightIntensityLux);
    }

    public static GatewayPayload generateGatewayData(Map<String, byte[]> sensorMap, Map<String, byte[]> deviceMap) {
        StringBuilder sensorString = new StringBuilder();
        for (Map.Entry<String, byte[]> entry : sensorMap.entrySet()) {
            String key = entry.getKey();
            byte[] value = entry.getValue();
            sensorString.append("\"").append(key).append("\"").append(":").append(new String(value)).append(",");
        }
        if (sensorString.length() > 0) {
            sensorString.deleteCharAt(sensorString.length() - 1);
        }
        sensorString.insert(0,"{");
        sensorString.append("}");

        StringBuilder deviceString = new StringBuilder();
        for (Map.Entry<String, byte[]> entry : deviceMap.entrySet()) {
            String key = entry.getKey();
            byte[] value = entry.getValue();
            deviceString.append("\"").append(key).append("\"").append(":").append(new String(value)).append(",");
        }
        if (deviceString.length() > 0) {
            deviceString.deleteCharAt(deviceString.length() - 1);
        }
        deviceString.insert(0,"{");
        deviceString.append("}");

        return new GatewayPayload(sensorString.toString(), deviceString.toString());
    }

    public static BulbPayload generateBulbPayload(Status status) {
        if (status == Status.OFF) return new BulbPayload(0);
        Random random = new Random();
        double powerRange = random.nextDouble() * 6 - 3;
        double power = initBulbPower + powerRange;
        return new BulbPayload(power);
    }
}
