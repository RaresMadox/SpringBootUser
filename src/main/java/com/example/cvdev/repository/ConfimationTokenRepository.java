package com.example.cvdev.repository;

import com.example.cvdev.model.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConfimationTokenRepository extends CrudRepository<ConfirmationToken,Long> {

    Optional<ConfirmationToken> findConfirmationTokenByConfirmationToken(String token);

}
