package entry.iot_nodes;

import common.enums.SensorType;
import common.enums.Status;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import utils.SystemDevice;

public class Sensor extends SystemDevice {
    protected MqttClient client = null;
    protected SensorType sensorType = null;
    protected String topic;
    protected int qos;
    protected int timeInterval;
    protected Status status;

    public Sensor(String brokerString, String clientId, String username, String password) {
        super(brokerString, clientId, username, password);
    }

    public void connectBroker() {
        try {
            this.client = new MqttClient(this.brokerString, this.clientId, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(this.username);
            connOpts.setPassword(this.password.toCharArray());
            connOpts.setCleanSession(true);
            int connectTimeout = 60;
            connOpts.setConnectionTimeout(connectTimeout);
            int keepAliceInterval = 60;
            connOpts.setKeepAliveInterval(keepAliceInterval);

            this.client.connect(connOpts);
            System.out.println("sensor: " + this.client);
        } catch (MqttException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void disconnectAndClose() {
        try {
            this.client.disconnect();
            this.client.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publishData() throws MqttException {};
}
