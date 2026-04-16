package com.hageli.playerservice.controller;

import com.hageli.playerservice.dto.PlayerRequestDTO;
import com.hageli.playerservice.dto.PlayerResponseDTO;
import com.hageli.playerservice.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
