package com.hageli.playerservice.service;

import com.hageli.playerservice.dto.PlayerRequestDTO;
import com.hageli.playerservice.dto.PlayerResponseDTO;
import com.hageli.playerservice.exception.EmailAlreadyExistsException;
import com.hageli.playerservice.exception.PlayerNotFoundException;
import com.hageli.playerservice.grpc.BillingServiceGrpcClient;
import com.hageli.playerservice.kafka.KafkaProducer;
import com.hageli.playerservice.mapper.PlayerMapper;
import com.hageli.playerservice.model.Player;
import com.hageli.playerservice.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;


    public PlayerService(PlayerRepository playerRepository, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.playerRepository = playerRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
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
        billingServiceGrpcClient.createBillingAccount(newPlayer.getId().toString(), newPlayer.getName(), newPlayer.getEmail());
        kafkaProducer.sendEvent(newPlayer);

        return PlayerMapper.toDTO(newPlayer);
    }


    public PlayerResponseDTO updatePlayer(UUID id, PlayerRequestDTO playerRequestDTO) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player not found with id: " + id));
        if(playerRepository.existsByEmailAndIdNot(playerRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("Email already in use");
        }

        player.setName(playerRequestDTO.getName());
        player.setEmail(playerRequestDTO.getEmail());
        player.setDateOfBirth(LocalDate.parse(playerRequestDTO.getDateOfBirth()));
        player.setGoals(Integer.parseInt(playerRequestDTO.getGoals()));
        player.setAssists(Integer.parseInt(playerRequestDTO.getAssists()));
        player.setPosition(playerRequestDTO.getPosition());
        player.setJerseyNumber(Integer.parseInt(playerRequestDTO.getJerseyNumber()));
        player.setRegisteredDate(LocalDate.now());
        player.setTeam(playerRequestDTO.getTeam());

        Player updatedPlayer = playerRepository.save(player);
        return PlayerMapper.toDTO(updatedPlayer);
    }

    public void deletePlayer(UUID id) {
        playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player not found with id: " + id));
        playerRepository.deleteById(id);
    }
}
