package common.enums;

public enum Topic {
    IOT_NODE_SENSOR_DATA("iot/data/sensor"),
    IOT_NODE_DEVICE_DATA("iot/data/device"),
    IOT_CONTROL("iot/control"),
    IOT_DATA("iot/data/#"),
    IOT_GATEWAY_DATA("iot/data/gateway"),
    ;

    private final String topic;

    Topic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
