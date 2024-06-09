package ws.springws.socket;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class NovaMessageHandler extends BinaryWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();

    public NovaMessageHandler(SseClientService sseClientService) {
        var flux = sseClientService.getSse("http://192.168.43.3:81/stream");
        flux.doOnNext(lastFrame -> {
            sessions.forEach(session -> {
                try {
                    session.sendMessage(new BinaryMessage(lastFrame));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }).subscribe();
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        sessions.remove(session);
        log.info("Closing session {}", session.getId());
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        sessions.add(session);
        log.info("Opening session {}", session.getId());
    }

    @Override
    protected void handleBinaryMessage(@NonNull WebSocketSession session, @NonNull BinaryMessage message) throws IOException {
        log.info("Received frame");
        session.sendMessage(new TextMessage("Frame received"));
    }
}