package common.helpers;

import entry.iot_gateway.Gateway;
import org.eclipse.paho.client.mqttv3.MqttException;

public class GatewayHandle implements Runnable {
    private final Gateway gateway;

    public GatewayHandle(Gateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void run() {
        this.gateway.connectBroker();
        this.gateway.subscribe();
        this.gateway.setCallback();
        try {
            this.gateway.publishData();
        } catch (MqttException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
