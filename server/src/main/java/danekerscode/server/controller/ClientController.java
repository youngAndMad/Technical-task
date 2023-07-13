package danekerscode.server.controller;

import danekerscode.server.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    ResponseEntity<?> register(
            @RequestParam("chat-id") Integer id
    ) {
        return ResponseEntity.status(201).body(clientService.register(id));
    }

    @GetMapping("{id}")
    ResponseEntity<?> findById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateLastActionTime(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(clientService.updateLastActionTime(id));
    }
}
