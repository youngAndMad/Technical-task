package danekerscode.tgbot.client;

import danekerscode.tgbot.payload.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "backend", url = "http://localhost:8080/api/v1/client")
public interface BackendFeignClient {
    @PostMapping
    Client register(
            @RequestParam("chat-id") long chatId
    );

    @PutMapping("{id}")
    Client updateLastActionTime(
            @PathVariable Long id
    );

    @PostMapping("location")
    Boolean setVisit(
            @RequestParam("id") Long id ,
            @RequestParam("latitude") Integer latitude ,
            @RequestParam("longitude") Integer longitude
    );

}
