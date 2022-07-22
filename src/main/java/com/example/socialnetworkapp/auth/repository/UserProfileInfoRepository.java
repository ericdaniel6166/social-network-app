package com.example.socialnetworkapp.auth.repository;

import com.example.socialnetworkapp.auth.model.UserProfileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileInfoRepository extends JpaRepository<UserProfileInfo, Long> {

}
