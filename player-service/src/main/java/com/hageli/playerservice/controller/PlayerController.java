package com.hageli.playerservice.controller;

import com.hageli.playerservice.dto.PlayerRequestDTO;
import com.hageli.playerservice.dto.PlayerResponseDTO;
import com.hageli.playerservice.service.PlayerService;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerResponseDTO>> getPlayers() {
        List<PlayerResponseDTO> players = playerService.getPlayers();
        return ResponseEntity.ok().body(players);
    }

    @PostMapping
    public ResponseEntity<PlayerResponseDTO> createPlayer(@Valid @RequestBody PlayerRequestDTO playerRequestDTO) {
        PlayerResponseDTO playerResponseDTO = playerService.createPlayer(playerRequestDTO);
        return ResponseEntity.ok().body(playerResponseDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> updatePlayer(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PlayerRequestDTO playerRequestDTO) {
        PlayerResponseDTO playerResponseDTO = playerService.updatePlayer(id, playerRequestDTO);
        return ResponseEntity.ok().body(playerResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable UUID id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
