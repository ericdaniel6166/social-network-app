package com.example.socialnetworkapp.repository;

import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.model.MasterMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterMessageRepository extends JpaRepository<MasterMessage, Long> {

    Optional<MasterMessage> findByMessageCode(MasterMessageCode messageCode);

}
