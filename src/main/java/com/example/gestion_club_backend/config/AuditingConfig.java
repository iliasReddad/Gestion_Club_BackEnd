package com.example.gestion_club_backend.config;

import com.example.gestion_club_backend.security.UserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

	@Bean
	public AuditorAware<Long> auditorProvider() {
		//implementation below
		return new SpringSecurityAuditAwareImpl();

	}
}
//Spring Data enables you track who modified an entity and when, with just a few annotations.
// When combined with Spring Security, you can set this metadata based on the active user.


class SpringSecurityAuditAwareImpl implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
			return Optional.empty();
		}

		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		return Optional.ofNullable(userPrincipal.getId());
	}
}
