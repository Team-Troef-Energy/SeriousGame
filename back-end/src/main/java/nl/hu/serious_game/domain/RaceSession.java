package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.hu.serious_game.application.dto.out.RaceSessionUserDTO;

import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "raceSession")
    private List<RaceSessionUser> users;

    public RaceSession(Race race, String joinCode) {
        this.race = race;
        this.joinCode = joinCode;
    }
}
