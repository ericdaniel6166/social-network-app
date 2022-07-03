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
import com.example.socialnetworkapp.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public Page<ForumDTO> findAll(Pageable pageable) throws ResourceNotFoundException {
        Page<Forum> forumPage = forumRepository.findAll(pageable);
        List<ForumDTO> forumDTOList = forumPage.stream()
                .map(forum -> modelMapper.map(forum, ForumDTO.class))
                .collect(Collectors.toList());
        PageImpl<ForumDTO> forumDTOPage = new PageImpl<>(forumDTOList, pageable, forumPage.getTotalElements());
        CommonUtils.checkPageNotEmpty(forumDTOPage);
        return forumDTOPage;
    }

    @Override
    @Transactional
    public SimpleResponseDTO create(ForumDTO forumDTO) throws SocialNetworkAppException {
        Forum forum = modelMapper.map(forumDTO, Forum.class);
        forum.setAppUser(userService.getCurrentUser());
        forum = saveAndFlush(forum);
        MasterMessage masterMessage = masterMessageService.findByMessageCode(MasterMessageCode.CREATE_SUCCESS);
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        String title = CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getTitle()),
                Constants.FORUM.toUpperCase()
        );
        simpleResponseDTO.setTitle(title);
        String message = CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getMessage()),
                Constants.FORUM.toLowerCase(),
                forum.getName()
        );
        simpleResponseDTO.setMessage(message);
        return simpleResponseDTO;
    }
}