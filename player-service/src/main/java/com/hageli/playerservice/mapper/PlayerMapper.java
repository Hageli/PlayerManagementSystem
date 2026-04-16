package com.hageli.playerservice.mapper;

import com.hageli.playerservice.dto.PlayerRequestDTO;
import com.hageli.playerservice.dto.PlayerResponseDTO;
import com.hageli.playerservice.model.Player;

import java.time.LocalDate;

public class PlayerMapper {
    public static PlayerResponseDTO toDTO(Player player) {
        PlayerResponseDTO playerDTO = new PlayerResponseDTO();
        playerDTO.setId(player.getId().toString());
        playerDTO.setName(player.getName());
        playerDTO.setEmail(player.getEmail());
        playerDTO.setDateOfBirth(player.getDateOfBirth().toString());
        playerDTO.setGoals(player.getGoals().toString());
        playerDTO.setAssists(player.getAssists().toString());
        playerDTO.setPosition(player.getPosition());
        playerDTO.setJerseyNumber(player.getJerseyNumber().toString());
        playerDTO.setTeam(player.getTeam());
        return playerDTO;
    }

    public static Player toModel(PlayerRequestDTO playerRequestDTO) {
        Player player = new Player();
        player.setName(playerRequestDTO.getName());
        player.setEmail(playerRequestDTO.getEmail());
        player.setDateOfBirth(LocalDate.parse(playerRequestDTO.getDateOfBirth()));
        player.setGoals(Integer.parseInt(playerRequestDTO.getGoals()));
        player.setAssists(Integer.parseInt(playerRequestDTO.getAssists()));
        player.setPosition(playerRequestDTO.getPosition());
        player.setJerseyNumber(Integer.parseInt(playerRequestDTO.getJerseyNumber()));
        player.setRegisteredDate(LocalDate.now());
        player.setTeam(playerRequestDTO.getTeam());
        return player;
    }
}