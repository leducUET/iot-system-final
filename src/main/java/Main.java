import common.enums.ENVKey;
import common.helpers.DeviceHandle;
import common.helpers.GatewayHandle;
import common.helpers.SensorHandle;
import entry.iot_gateway.Gateway;
import entry.iot_nodes.Bulb;
import entry.iot_nodes.HumidityAndTemperatureSensor;
import entry.iot_nodes.LightSensor;
import entry.iot_nodes.Sensor;
import utils.ENVUtils;

public class Main {
    public static void main(String[] args) {
        String brokerString = ENVUtils.get(ENVKey.BROKER_STRING.toString());

        String gatewayPassword = ENVUtils.get(ENVKey. IOT_GATEWAY_PASSWORD.toString());
        String gatewayUsername = ENVUtils.get(ENVKey. IOT_GATEWAY_USERNAME.toString());
        String gatewayClientIDs = ENVUtils.get(ENVKey. IOT_GATEWAY_CLIENT_ID.toString());

        String iotNodePassword = ENVUtils.get(ENVKey.IOT_NODE_PASSWORD.toString());
        String[] iotNodeUsername = ENVUtils.get(ENVKey.IOT_NODE_USERNAME.toString()).split(",");
        String[] iotNodeClientIDs = ENVUtils.get(ENVKey.IOT_NODE_CLIENT_ID.toString()).split(",");


        System.out.println("Iot System Main");

        Gateway gateway = new Gateway(brokerString, gatewayClientIDs, gatewayUsername, gatewayPassword);
        Thread gateThread = new Thread(new GatewayHandle(gateway));
        gateThread.start();

        Sensor humidityAndTemperatureSensor1 = new HumidityAndTemperatureSensor(brokerString, iotNodeClientIDs[0], iotNodeUsername[0], iotNodePassword);
        Thread thread1 = new Thread(new SensorHandle(humidityAndTemperatureSensor1));
        thread1.start();

        Sensor lightSensor1 = new LightSensor(brokerString, iotNodeClientIDs[1], iotNodeUsername[1], iotNodePassword);
        Thread thread2 = new Thread(new SensorHandle(lightSensor1));
        thread2.start();

        Bulb bulb = new Bulb(brokerString, iotNodeClientIDs[2], iotNodeUsername[2], iotNodePassword);
        Thread thread3 = new Thread(new DeviceHandle(bulb));
        thread3.start();
    }
}
