package me.ocean.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.ocean.springbootdeveloper.domain.RefreshToken;
import me.ocean.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService
{
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshtoken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
