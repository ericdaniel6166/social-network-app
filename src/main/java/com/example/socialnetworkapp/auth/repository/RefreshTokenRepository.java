package com.example.socialnetworkapp.auth.repository;

import com.example.socialnetworkapp.auth.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenAndUsername(String token, String username);

    void deleteByToken(String token);


}
