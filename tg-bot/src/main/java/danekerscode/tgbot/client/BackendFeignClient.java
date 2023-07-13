package danekerscode.tgbot.client;

import danekerscode.tgbot.payload.Client;
import feign.Response;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@FeignClient(name = "backend" , url = "http://localhost:8080/api/v1/client")
public interface BackendFeignClient {
    @PostMapping
    Client register(@RequestParam("chat-id") long chatId);

    @PutMapping("{id}")
    Client updateLastActionTime(@PathVariable Long id);
}
