package br.com.alexandre.email.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alexandre.email.entities.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {

}
