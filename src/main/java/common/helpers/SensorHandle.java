package common.helpers;

import entry.iot_nodes.Sensor;
import org.eclipse.paho.client.mqttv3.MqttException;

public class SensorHandle implements Runnable {
    private final Sensor sensor;

    public SensorHandle(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public void run() {
        try {
            this.sensor.connectBroker();
            this.sensor.publishData();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
