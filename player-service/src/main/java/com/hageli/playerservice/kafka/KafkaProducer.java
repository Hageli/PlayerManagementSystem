package com.hageli.playerservice.kafka;

import com.hageli.playerservice.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import player.events.PlayerEvent;

@Service
public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Player player) {
        PlayerEvent event = PlayerEvent.newBuilder()
                .setPlayerId(player.getId().toString())
                .setName(player.getName())
                .setEmail(player.getName())
                .setEventType("PLAYER_CREATED")
                .build();
        try {
            kafkaTemplate.send("player", event.toByteArray());

        } catch (Exception e) {
            log.error("Error sending PatientCreated event: {}", event);
        }
    }

}
