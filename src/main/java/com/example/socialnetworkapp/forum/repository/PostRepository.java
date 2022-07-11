package com.example.socialnetworkapp.forum.repository;

import com.example.socialnetworkapp.forum.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    Page<Post> findAllByIsActiveTrueAndForum_Id(Long id, Pageable pageable);

    Page<Post> findAllByIsActiveTrue(Pageable pageable);

    Page<Post> findAllByIsActiveTrue(Specification<Post> spec, Pageable pageable);

    Optional<Post> findByIsActiveTrueAndId(Long id);

    Page<Post> findAllByIsActiveTrueAndCreatedBy(Object createdBy, Pageable pageable);

}
