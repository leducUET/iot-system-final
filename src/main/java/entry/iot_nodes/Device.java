package entry.iot_nodes;

import common.enums.SensorType;
import common.enums.Status;
import common.enums.Topic;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import utils.SystemDevice;

public class Device extends SystemDevice {
    protected MqttClient client = null;
    protected String name;
    protected String topic = Topic.IOT_NODE_DEVICE_DATA.getTopic();
    protected final String[] topicsSubscriber = Topic.IOT_CONTROL.getTopic().split(",");
    protected int qos;
    protected int timeInterval;
    protected Status status;

    public Device(String brokerString, String clientId, String username, String password) {
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
            System.out.println("device: " + this.client);
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
    public void subscribe() {};
    public void setCallback() {};
}
