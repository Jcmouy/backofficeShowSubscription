package com.uydevs.backoffice.repository.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uydevs.backoffice.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
