package com.hageli.playerservice.service;

import com.hageli.playerservice.dto.PlayerRequestDTO;
import com.hageli.playerservice.dto.PlayerResponseDTO;
import com.hageli.playerservice.exception.EmailAlreadyExistsException;
import com.hageli.playerservice.mapper.PlayerMapper;
import com.hageli.playerservice.model.Player;
import com.hageli.playerservice.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<PlayerResponseDTO> getPlayers() {
        List<Player> players = playerRepository.findAll();
        return players.stream().map(PlayerMapper::toDTO).toList();
    }

    public PlayerResponseDTO createPlayer(PlayerRequestDTO playerRequestDTO) {
        if(playerRepository.existsByEmail(playerRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already in use");
        }
        Player newPlayer = playerRepository.save(PlayerMapper.toModel(playerRequestDTO));
        return PlayerMapper.toDTO(newPlayer);
    }
}
