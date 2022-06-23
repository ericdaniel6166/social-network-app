package com.example.socialnetworkapp.repository;

import com.example.socialnetworkapp.model.MasterErrorMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterErrorMessageRepository extends JpaRepository<MasterErrorMessage, Long> {

    Optional<MasterErrorMessage> findByErrorCode(String errorCode);

}
