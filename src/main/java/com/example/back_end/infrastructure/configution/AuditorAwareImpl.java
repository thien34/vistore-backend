package com.example.back_end.infrastructure.configution;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
@Primary
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.empty();
//        }
//        Customer userDetails = (Customer) authentication.getPrincipal();
//
//        return Optional.ofNullable(userDetails.getId());
        return Optional.ofNullable(1L);
    }

}