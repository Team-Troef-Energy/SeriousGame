package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor // for unit tests
public class LevelHouse {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private DayProfile dayProfile;
    @Setter
    private HouseOptions houseOptions;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @Setter // being set by LevelTransformer's constructor.
    private LevelTransformer transformer;

    public LevelHouse(DayProfile dayProfile, HouseOptions houseOptions) {
        this.dayProfile = dayProfile;
        this.houseOptions = houseOptions;
    }
}
