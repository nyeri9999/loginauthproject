package com.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    // auditing 기능을 한다. 엔티티 데이터가 생성/수정될 때, 일자를 자동으로 기록하고 관리한다.
}