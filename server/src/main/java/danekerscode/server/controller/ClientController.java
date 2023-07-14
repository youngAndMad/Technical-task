package danekerscode.server.controller;

import danekerscode.server.payload.Location;
import danekerscode.server.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    ResponseEntity<?> register(
            @RequestParam("chat-id") Integer id) {
        return ResponseEntity.status(201).body(clientService.register(id));
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateLastActionTime(
            @PathVariable Integer id) {
        return ResponseEntity.ok(clientService.updateLastActionTime(id));
    }

    @PostMapping("location")
    ResponseEntity<?> setVisited(
            @RequestParam("id") Integer id,
            @RequestParam("longitude") Integer longitude,
            @RequestParam("latitude") Integer latitude) {
        System.out.println("hello");
        return ResponseEntity.accepted().body(clientService.setVisited(id, latitude, longitude));
    }

}
