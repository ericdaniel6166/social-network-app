package com.example.socialnetworkapp.repository;

import com.example.socialnetworkapp.model.AppComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<AppComment, Long>, JpaSpecificationExecutor<AppComment> {
}
