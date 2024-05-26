package ws.springws.socket;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;

@Slf4j
@Component
public class NovaMessageHandler extends BinaryWebSocketHandler {
    @Override
    protected void handleBinaryMessage(@NonNull WebSocketSession session, BinaryMessage message) throws IOException {
        log.info("Received frame");
        session.sendMessage(new TextMessage("Frame received"));
    }
}