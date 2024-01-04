package entry.iot_nodes;

import com.google.gson.Gson;
import common.enums.Controller;
import common.enums.Status;
import common.enums.Topic;
import common.helpers.GenerateData;
import common.payload_interfaces.ControlPayload;
import common.payload_interfaces.DataPayload;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Objects;

public class Bulb extends Device {
    private static int instanceCount = 0;
    private final String localTopic;
    private boolean forceStatus = false;
    public Bulb(String brokerString, String clientId, String username, String password) {
        super(brokerString, clientId, username, password);
        this.timeInterval = 1000;
        this.qos = 0;
        this.name = "BULB";
        this.status = Status.OFF;
        instanceCount ++;
        this.localTopic = this.topic + "/" + this.name + "_" + instanceCount;
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
            System.out.println("bulb: " + this.client);
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
                String message = new String(mqttMessage.getPayload());
                Gson gson = new Gson();
                ControlPayload controlPayload = gson.fromJson(message, ControlPayload.class);
                String command = controlPayload.getCommand();
                String to = controlPayload.getTo();
                String from = controlPayload.getFrom();
                if (!Objects.equals(to, name + "_" + instanceCount)) return;
                switch (command){
                    case "ON":
                        if (Objects.equals(from, Controller.CUSTOMER.getController())) {
                            status = Status.ON;
                        } else if (!forceStatus) {
                            status = Status.ON;
                        }
                        break;
                    case "OFF":
                        if (Objects.equals(from, Controller.CUSTOMER.getController())) {
                            status = Status.OFF;
                        } else if (!forceStatus) {
                            status = Status.OFF;
                        }
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
                String data = GenerateData.generateBulbPayload(this.status).toString();
                String payload = new DataPayload(this.name + "_" + instanceCount, this.status, data).toString();
                MqttMessage message = new MqttMessage(payload.getBytes());
                message.setQos(0);
                this.client.publish(this.localTopic, message);
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
