package com.example.socialnetworkapp.forum.repository;

import com.example.socialnetworkapp.forum.model.AppComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<AppComment, Long>, JpaSpecificationExecutor<AppComment> {

    Page<AppComment> findAllByIsActiveTrueAndPost_Id(Long id, Pageable pageable);

    Page<AppComment> findAllByIsActiveTrue(Pageable pageable);

    Page<AppComment> findAllByIsActiveTrue(Specification<AppComment> spec, Pageable pageable);

    Page<AppComment> findAllByIsActiveTrueAndCreatedBy(Object createdBy, Pageable pageable);

    Optional<AppComment> findByIsActiveTrueAndId(Long id);
}
