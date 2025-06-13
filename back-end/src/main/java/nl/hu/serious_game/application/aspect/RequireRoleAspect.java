package nl.hu.serious_game.application.aspect;

import nl.hu.serious_game.application.UserService;
import nl.hu.serious_game.domain.User;
import nl.hu.serious_game.domain.UserRole;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequireRoleAspect {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(RequireRoleAspect.class);

    public RequireRoleAspect(UserService userService) {
        this.userService = userService;
    }

    @Before(value = "@annotation(RequireRole)")
    public void evaluateRole(JoinPoint joinPoint) {
        UserRole givenRole;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            givenRole = UserRole.ANONYMOUS;
        } else {
            User user = userService.getUser(authentication);
            givenRole = user.role();
        }

        // https://www.lenar.io/aspectj-get-annotation-parameters/
        UserRole requiredRole = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RequireRole.class).role();
        if (!UserRole.allowAccess(requiredRole, givenRole)) {
            logger.warn("Blocking request that does not meet required role");
            throw new AccessDeniedException("Request requires role %s, %s given".formatted(requiredRole.getKey(), givenRole.getKey()));
        }
    }
}
