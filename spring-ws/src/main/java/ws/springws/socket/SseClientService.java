package ws.springws.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class SseClientService {
    private final WebClient webClient;

    public Flux<byte[]> getSse(String url) {
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(byte[].class);
    }
}
