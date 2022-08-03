package com.k1ng.doinggajigaji.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        // authentication 객체가 담겨있는 컨텍스트를 컨텍스트 홀더에서 꺼낸다.
        // authentication 에는 userName이 들어있고 파라미터로 email 을 준다고 했으니 email이 담겨있다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        if (authentication != null) {
            userId = authentication.getName();
        }

        return Optional.of(userId);
    }
}
