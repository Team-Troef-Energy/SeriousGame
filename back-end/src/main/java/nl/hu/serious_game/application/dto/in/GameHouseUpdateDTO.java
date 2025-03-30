package nl.hu.serious_game.application.dto.in;

public record GameHouseUpdateDTO(
        long id,
        int batteries,
        int solarpanels
) {}
