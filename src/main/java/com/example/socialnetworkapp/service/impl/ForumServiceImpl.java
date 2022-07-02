package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.dto.ForumDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.model.Forum;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.repository.ForumRepository;
import com.example.socialnetworkapp.service.ForumService;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.service.UserService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.sun.deploy.ref.AppModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MasterMessageService masterMessageService;

    @Override
    public Forum saveAndFlush(Forum forum) {
        return forumRepository.saveAndFlush(forum);
    }

    @Override
    @Transactional
    public SimpleResponseDTO createForum(ForumDTO forumDTO) throws SocialNetworkAppException {
        Forum forum = modelMapper.map(forumDTO, Forum.class);
        forum.setAppUser(userService.getCurrentUser());
        forum = saveAndFlush(forum);
        MasterMessage masterMessage = masterMessageService.findByMessageCode(MasterMessageCode.CREATE_FORUM_SUCCESS);
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        simpleResponseDTO.setTitle(StringEscapeUtils.unescapeJava(masterMessage.getTitle()));
        String string = StringEscapeUtils.unescapeJava(masterMessage.getMessage());
        String message = CommonUtils.formatString(string, forum.getName());;
        simpleResponseDTO.setMessage(message);
        return simpleResponseDTO;
    }
}
