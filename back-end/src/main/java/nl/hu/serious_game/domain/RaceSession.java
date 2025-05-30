package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RaceSession {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Race race;

    @Column
    private String joinCode;

    public RaceSession(Race race, String joinCode) {
        this.race = race;
        this.joinCode = joinCode;
    }
}
