package nl.hu.serious_game.presentation;

import nl.hu.serious_game.application.aspect.RequireRole;
import nl.hu.serious_game.application.dto.in.JoinRaceDTO;
import nl.hu.serious_game.application.dto.out.RaceSessionDTO;
import nl.hu.serious_game.application.dto.out.RaceSessionUserDTO;
import nl.hu.serious_game.data.RaceRepository;
import nl.hu.serious_game.data.RaceSessionRepository;
import nl.hu.serious_game.data.RaceSessionUserRepository;
import nl.hu.serious_game.domain.RaceSession;
import nl.hu.serious_game.domain.RaceSessionUser;
import nl.hu.serious_game.domain.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/racesession")
public class RaceSessionController {
    private final Logger logger = LoggerFactory.getLogger(RaceSessionController.class);
    private final RaceRepository raceRepository;
    private final RaceSessionRepository raceSessionRepository;
    private final RaceSessionUserRepository raceSessionUserRepository;

    public RaceSessionController(RaceRepository raceRepository, RaceSessionRepository raceSessionRepository, RaceSessionUserRepository raceSessionUserRepository) {
        this.raceRepository = raceRepository;
        this.raceSessionRepository = raceSessionRepository;
        this.raceSessionUserRepository = raceSessionUserRepository;
    }

    private static String generateAlphanumericCode(int length) {
        var characters = "ABCDEFGHJKMNPQRSTUVWXYZ23456789";
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(0, characters.length())));
        }
        return result.toString();
    }

    @PostMapping
    public ResponseEntity<RaceSessionDTO> createSession(@RequestParam("raceId") long raceId) {
        var raceOptional = this.raceRepository.findById(raceId);
        if (raceOptional.isEmpty()) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), "Race with ID %s is not found".formatted(raceId))).build();
        }

        String joinCode = generateAlphanumericCode(6);

        var session = this.raceSessionRepository.save(new RaceSession(
                raceOptional.get(),
                joinCode
        ));

        return ResponseEntity.ok(RaceSessionDTO.fromEntity(session));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceSessionDTO> getSession(@PathVariable("id") long sessionId) {
        var sessionOptional = this.raceSessionRepository.findById(sessionId);
        if (sessionOptional.isEmpty()) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), "RaceSession with ID %s is not found".formatted(sessionId))).build();
        }

        return ResponseEntity.ok(RaceSessionDTO.fromEntity(sessionOptional.get()));
    }

    @GetMapping("/by-joincode")
    public ResponseEntity<RaceSessionDTO> getSessionByJoinCode(@RequestParam("joincode") String joinCode) {
        var sessionOptional = this.raceSessionRepository.findByJoinCode(joinCode);
        if (sessionOptional.isEmpty()) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), "RaceSession with joincode %s is not found".formatted(joinCode))).build();
        }

        return ResponseEntity.ok(RaceSessionDTO.fromEntity(sessionOptional.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable("id") long sessionId) {
        var sessionOptional = this.raceSessionRepository.findById(sessionId);
        if (sessionOptional.isEmpty()) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), "RaceSession with ID %s is not found".formatted(sessionId))).build();
        }

        this.raceSessionRepository.delete(sessionOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/checkRaceId/{raceId}")
    public ResponseEntity<Boolean> checkIsFromSession(@PathVariable("id") long sessionId, @PathVariable("raceId") long raceId) {
        var sessionOptional = this.raceSessionRepository.findById(sessionId);
        if (sessionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(sessionOptional.get().getRace().getId().equals(raceId));
    }

    @PostMapping("/join")
    public ResponseEntity<RaceSessionUserDTO> joinSession(@Validated @RequestBody JoinRaceDTO joinRaceDTO) {
        Optional<RaceSession> raceSessionOptional = this.raceSessionRepository.findByJoinCode(joinRaceDTO.joinCode());
        if (raceSessionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RaceSession raceSession = raceSessionOptional.get();
        Random random = new Random();
        byte[] tokenData = new byte[20];
        random.nextBytes(tokenData);
        String token = Base64.getEncoder().encodeToString(tokenData);

        var sessionUser = new RaceSessionUser(raceSession, joinRaceDTO.username(), token);
        raceSessionUserRepository.save(sessionUser);

        return ResponseEntity.ok(RaceSessionUserDTO.fromEntity(sessionUser, true));
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leaveSession(@RequestParam("token") String token) {
        Optional<RaceSessionUser> raceSessionUserOptional = this.raceSessionUserRepository.findByToken(token);
        if (raceSessionUserOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        this.raceSessionUserRepository.delete(raceSessionUserOptional.get());

        return ResponseEntity.noContent().build();
    }
}
