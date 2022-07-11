package com.example.socialnetworkapp.forum.repository;

import com.example.socialnetworkapp.forum.model.Forum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long>, JpaSpecificationExecutor<Forum> {

    Optional<Forum> findByIsActiveTrueAndId(Long id);

    Page<Forum> findAllByIsActiveTrue(Pageable pageable);

    Page<Forum> findAllByIsActiveTrue(Specification<Forum> spec, Pageable pageable);
}
