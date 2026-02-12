package com.udd.back.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "com.udd.back.feature_auth.repository",
        "com.udd.back.feature_docs.repository"
})
public class JpaConfig {
}
