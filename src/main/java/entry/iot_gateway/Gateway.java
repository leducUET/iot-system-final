package entry.iot_gateway;

import com.google.gson.Gson;
import common.enums.Controller;
import common.enums.Topic;
import common.helpers.GenerateData;
import common.payload_interfaces.BulbPayload;
import common.payload_interfaces.ControlPayload;
import common.payload_interfaces.DataPayload;
import common.payload_interfaces.Light;
import entry.iot_nodes.Bulb;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import utils.SystemDevice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Gateway extends SystemDevice {
    private MqttClient client = null;
    private final String[] topicsSubscriber = Topic.IOT_DATA.getTopic().split(",");
    private final String topic = Topic.IOT_GATEWAY_DATA.getTopic();
    private final Map<String, byte[]> sensorMap;
    private final Map<String, byte[]> deviceMap;

    public Gateway(String brokerString, String clientId, String username, String password) {
        super(brokerString, clientId, username, password);
        this.sensorMap = new HashMap<>();
        this.deviceMap = new HashMap<>();
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
            System.out.println("gateway: " + this.client);
        } catch (MqttException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void subscribe() {
        try {
            this.client.subscribe(this.topicsSubscriber);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCallback() {
        this.client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.out.println("Connection lost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                String[] topicSplice = topic.split("/");
                switch (topicSplice[2]) {
                    case "sensor":
                        sensorMap.put(topicSplice[3], mqttMessage.getPayload());
                        break;
                    case "device":
                        deviceMap.put(topicSplice[3], mqttMessage.getPayload());
                        break;
                    default:
                        break;
                }
                
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    public void publishData() throws MqttException {
        try {
            while (true) {
                String payload = GenerateData.generateGatewayData(this.sensorMap, this.deviceMap).toString();
                MqttMessage message = new MqttMessage(payload.getBytes());
                message.setQos(0);
                this.client.publish(this.topic, message);
                Thread.sleep(1000);
            }
        } catch ( MqttException | InterruptedException  e) {
            // disconnect
            this.client.disconnect();
            // close client
            this.client.close();

            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
