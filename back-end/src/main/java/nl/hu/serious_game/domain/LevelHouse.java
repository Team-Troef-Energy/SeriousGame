package nl.hu.serious_game.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class LevelHouse {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private DayProfile dayProfile;
    @Setter
    private HouseOptions houseOptions;

    public LevelHouse(DayProfile dayProfile, HouseOptions houseOptions) {
        this.dayProfile = dayProfile;
        this.houseOptions = houseOptions;
    }
}
