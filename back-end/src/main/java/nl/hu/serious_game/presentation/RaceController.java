package nl.hu.serious_game.presentation;

import nl.hu.serious_game.application.dto.in.CreateRaceDTO;
import nl.hu.serious_game.application.dto.out.RaceDTO;
import nl.hu.serious_game.data.RaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    }

    @GetMapping("/by-email")
    public ResponseEntity<RaceDTO> getByEmail(@RequestParam String email) {

    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceDTO> getById(@PathVariable Long id) {

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RaceDTO> deleteRace(@PathVariable Long id) {

    }

    @PutMapping("/{id}/name")
    public ResponseEntity<RaceDTO> updateName(@PathVariable Long id, @RequestParam String name) {

    }

    @PutMapping("/{id}/is-email")
    public ResponseEntity<RaceDTO> updateName(@PathVariable Long id, @RequestParam String email) {

    }
}
