package ws.springws.socket;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ws.springws.mqtt.MqttPublisher;
import ws.springws.mqtt.MqttTopic;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppMessageHandler extends TextWebSocketHandler {
    private final MqttPublisher mqttPublisher;

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        log.info(message.getPayload());
        if (message.getPayload().equals("SHOOT_COMMAND")) {
            mqttPublisher.send(message.getPayload(), MqttTopic.SHOOT);
        }
        else {
            mqttPublisher.send(message.getPayload(), MqttTopic.CONTROL);
        }
    }
}
