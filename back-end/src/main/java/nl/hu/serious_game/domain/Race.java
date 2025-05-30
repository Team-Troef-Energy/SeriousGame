package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Race {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Column
    private String name;

    @Column
    private String userEmail;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "race")
    private List<LevelTemplate> levels;

    public Race(String name, String userEmail, List<LevelTemplate> levels) {
        this.name = name;
        this.userEmail = userEmail;
        this.levels = levels;
    }
}
