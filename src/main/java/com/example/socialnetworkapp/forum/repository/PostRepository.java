package com.example.socialnetworkapp.forum.repository;

import com.example.socialnetworkapp.forum.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    Page<Post> findAllByForum_IdAndIsActiveTrue(Long id, Pageable pageable);

    Page<Post> findAllByIsActiveTrue(Pageable pageable);

    Optional<Post> findByIdAndIsActiveTrue(Long id);

    Page<Post> findAllByUsernameAndIsActiveTrue(String username, Pageable pageable);

}
