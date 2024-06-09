package ws.springws.mqtt;

import lombok.RequiredArgsConstructor;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@RequiredArgsConstructor
public class MqttPublisher {
    private final MessageChannel mqttOutboundChannel;

    public void send(String data, MqttTopic topic) {
        Message<String> message = MessageBuilder.withPayload(data)
                .setHeader(MqttHeaders.TOPIC, topic.getValue()).build();
        mqttOutboundChannel.send(message);
    }
}
