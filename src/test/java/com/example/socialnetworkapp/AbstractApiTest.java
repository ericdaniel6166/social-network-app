package com.example.socialnetworkapp;

import com.example.socialnetworkapp.auth.service.AccountService;
import com.example.socialnetworkapp.auth.service.AuthService;
import com.example.socialnetworkapp.configuration.AppConfiguration;
import com.example.socialnetworkapp.configuration.OperationIdConfiguration;
import com.example.socialnetworkapp.forum.service.CommentService;
import com.example.socialnetworkapp.forum.service.ForumService;
import com.example.socialnetworkapp.forum.service.PostService;
import com.example.socialnetworkapp.service.MasterErrorMessageService;
import com.example.socialnetworkapp.service.MasterMessageService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = OperationIdConfiguration.class)
@ComponentScan(value = {"com.example.socialnetworkapp.auth.controller",
        "com.example.socialnetworkapp.controller",
        "com.example.socialnetworkapp.forum.controller"
})
public abstract class AbstractApiTest {
    protected static final String UTF_8 = "UTF-8";

    @MockBean
    private AuthService authService;

    @MockBean
    private MasterMessageService masterMessageService;

    @MockBean
    private MasterErrorMessageService masterErrorMessageService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private PostService postService;

    @MockBean
    private ForumService forumService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AppConfiguration appConfiguration;


}
