package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Race {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String userEmail;

    @OneToMany
    private List<Race> race;
}
