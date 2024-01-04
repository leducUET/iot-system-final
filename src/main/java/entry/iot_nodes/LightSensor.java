package entry.iot_nodes;

import common.enums.SensorType;
import common.enums.Status;
import common.enums.Topic;
import common.helpers.GenerateData;
import common.payload_interfaces.DataPayload;
import org.eclipse.paho.client.mqttv3.*;

public class LightSensor extends Sensor {
    private static int instanceCount = 0;
    private final String localTopic;

    public LightSensor(String brokerString, String clientId, String username, String password) {
        super(brokerString, clientId, username, password);
        this.sensorType = SensorType.LIGHT;
        this.timeInterval = 1000;
        this.topic = Topic.IOT_NODE_SENSOR_DATA.getTopic();
        this.qos = 0;
        this.status = Status.ACTIVE;
        instanceCount++;
        this.localTopic = this.topic + "/" + this.sensorType + "_" + instanceCount;
    }

    public void publishData() throws MqttException {
        try {
                while (true) {
                    String data = GenerateData.generateLight().toString();
                    String payload = new DataPayload(this.sensorType.toString() + instanceCount, this.status , data).toString();
                    MqttMessage message = new MqttMessage(payload.getBytes());
                    message.setQos(this.qos);
                    client.publish(this.localTopic, message);
                    Thread.sleep(this.timeInterval);
                }
        } catch (MqttException | InterruptedException e) {
            // disconnect
            this.client.disconnect();
            // close client
            this.client.close();

            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
