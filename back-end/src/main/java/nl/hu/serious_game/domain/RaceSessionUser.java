package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RaceSessionUser {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String username;

    @ManyToOne(cascade = CascadeType.ALL)
    private RaceSession raceSession;

    @Column
    private String token;

    public RaceSessionUser(RaceSession raceSession, String username, String token) {
        this.raceSession = raceSession;
        this.username = username;
        this.token = token;
    }
}
