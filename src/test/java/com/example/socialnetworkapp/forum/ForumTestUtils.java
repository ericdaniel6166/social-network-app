package com.example.socialnetworkapp.forum;

import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.forum.dto.CommentDTO;
import com.example.socialnetworkapp.forum.dto.ForumDTO;
import com.example.socialnetworkapp.forum.dto.PostDTO;
import com.example.socialnetworkapp.forum.model.AppComment;
import com.example.socialnetworkapp.forum.model.Forum;
import com.example.socialnetworkapp.forum.model.Post;

public class ForumTestUtils {


    public static final String CONTENT = "content";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static CommentDTO buildCommentDTO() {
        CommentDTO commentDTO = new CommentDTO();
        AppComment appComment = buildAppComment();
        commentDTO.setContent(appComment.getContent());
        commentDTO.setPostId(appComment.getPost().getId());
        commentDTO.setId(appComment.getId());
        return commentDTO;
    }

    public static PostDTO buildPostDTO() {
        PostDTO postDTO = new PostDTO();
        Post post = buildPost();
        postDTO.setContent(post.getContent());
        postDTO.setName(post.getName());
        postDTO.setId(post.getId());
        postDTO.setForumId(post.getForum().getId());
        return postDTO;
    }


    public static ForumDTO buildForumDTO() {
        ForumDTO forumDTO = new ForumDTO();
        Forum forum = buildForum();
        forumDTO.setId(forum.getId());
        forumDTO.setName(forum.getName());
        forumDTO.setDescription(forum.getDescription());
        return forumDTO;
    }

    public static AppComment buildAppComment() {
        AppComment appComment = new AppComment();
        appComment.setPost(buildPost());
        appComment.setAppUser(AuthTestUtils.buildAppUser());
        appComment.setIsActive(true);
        appComment.setContent(CONTENT);
        appComment.setId(1L);
        return appComment;
    }

    public static Post buildPost() {
        Post post = new Post();
        post.setId(1L);
        post.setIsActive(true);
        post.setForum(buildForum());
        post.setAppUser(AuthTestUtils.buildAppUser());
        post.setContent(CONTENT);
        post.setName(NAME);
        return post;
    }

    public static Forum buildForum() {
        Forum forum = new Forum();
        forum.setId(1L);
        forum.setIsActive(true);
        forum.setAppUser(AuthTestUtils.buildAppUser());
        forum.setDescription(DESCRIPTION);
        forum.setName(NAME);
        return forum;
    }
}
