package com.example.socialnetworkapp.forum.repository;

import com.example.socialnetworkapp.forum.model.AppComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<AppComment, Long>, JpaSpecificationExecutor<AppComment> {

    Page<AppComment> findAllByPost_IdAndIsActiveTrue(Long id, Pageable pageable);

    Page<AppComment> findAllByUsernameAndIsActiveTrue(String username, Pageable pageable); //(Long id, Pageable pageable);

    Page<AppComment> findAllByIsActiveTrue(Pageable pageable);

    Optional<AppComment> findByIdAndIsActiveTrue(Long id);


}
