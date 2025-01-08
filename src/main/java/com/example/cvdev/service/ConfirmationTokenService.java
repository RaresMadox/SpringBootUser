package com.example.cvdev.service;

import com.example.cvdev.model.ConfirmationToken;
import com.example.cvdev.repository.ConfimationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfimationTokenRepository confirmationTokenRepository;

    void saveConfirmationToken(ConfirmationToken confirmationToken)
    {
        confirmationTokenRepository.save(confirmationToken);
    }

    void deleteConfimationToken(Long id)
    {
        confirmationTokenRepository.deleteById(id);
    }

    public Optional<ConfirmationToken> findConfirmationTokenByToken(String token) {

        return confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
    }

}
