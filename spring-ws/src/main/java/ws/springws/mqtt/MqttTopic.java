package ws.springws.mqtt;

import lombok.Getter;

@Getter
public enum MqttTopic {
    CONTROL("control/topic"),
    SHOOT("shooting/topic");

    private final String value;

    MqttTopic(String value) {
        this.value = value;
    }
}
