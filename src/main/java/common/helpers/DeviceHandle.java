package common.helpers;

import entry.iot_nodes.Device;
import org.eclipse.paho.client.mqttv3.MqttException;

public class DeviceHandle implements Runnable {
    private final Device device;

    public DeviceHandle(Device device) {
        this.device = device;
    }

    @Override
    public void run() {
        this.device.connectBroker();
        this.device.subscribe();
        this.device.setCallback();
        try {
            this.device.publishData();
        } catch (MqttException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
