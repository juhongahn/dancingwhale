package com.k1ng.doinggajigaji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  //JPA의 Auditing 기능을 활서화시키는 어노테이션
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // 유저의 이메일을 반환해주는 getCurrentAuditor 메서드가 담긴 AuditorAwareImpl 를 빈으로 등록
        return new AuditorAwareImpl();
    }
}
