package nl.hu.serious_game.presentation;

import nl.hu.serious_game.application.dto.in.CreateRaceDTO;
import nl.hu.serious_game.application.dto.out.LevelTemplateDTO;
import nl.hu.serious_game.application.dto.out.RaceDTO;
import nl.hu.serious_game.data.RaceRepository;
import nl.hu.serious_game.domain.Race;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/races")
public class RaceController {
    private final Logger logger = LoggerFactory.getLogger(RaceController.class);
    private final RaceRepository raceRepository;

    @Autowired
    public RaceController(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @PostMapping
    public ResponseEntity<RaceDTO> create(@Validated @RequestBody CreateRaceDTO createRaceDTO) {
        Race race = new Race(createRaceDTO.name(), createRaceDTO.userEmail(), List.of());
        race = this.raceRepository.save(race);
        return ResponseEntity.ok(RaceDTO.fromEntity(race));
    }

    @GetMapping("/by-email")
    public ResponseEntity<RaceDTO> getByEmail(@RequestParam String email) {
        Optional<Race> race = this.raceRepository.findByUserEmail(email);
        if (race.isPresent()) {
            return ResponseEntity.ok(RaceDTO.fromEntity(race.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceDTO> getById(@PathVariable Long id) {
        Optional<Race> race = this.raceRepository.findById(id);
        if (race.isPresent()) {
            return ResponseEntity.ok(RaceDTO.fromEntity(race.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRace(@PathVariable Long id) {
        if (this.raceRepository.findById(id).isPresent()) {
            this.raceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<RaceDTO> updateName(@PathVariable Long id, @RequestParam String name) {
        Optional<Race> raceOptional = this.raceRepository.findById(id);
        if (raceOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Race race = raceOptional.get();
        race.setName(name);
        this.raceRepository.save(race);

        return ResponseEntity.ok(RaceDTO.fromEntity(race));
    }

    @GetMapping("/{id}/is-email")
    public ResponseEntity<?> checkIsEmail(@PathVariable Long id, @RequestParam String email) {
        Optional<Race> raceOptional = this.raceRepository.findById(id);
        if (raceOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(raceOptional.get().getUserEmail().equals(email));
        }
    }
}
