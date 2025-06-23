package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.serious_game.application.dto.out.RaceSessionUserDTO;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.Date;
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Race race;

    @Column
    private String joinCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "raceSession")
    private List<RaceSessionUser> users;

    @Column
    @Setter
    private Instant expiration;

    public RaceSession(Race race, String joinCode, Instant expiration) {
        this.race = race;
        this.joinCode = joinCode;
        this.expiration = expiration;
    }
}
