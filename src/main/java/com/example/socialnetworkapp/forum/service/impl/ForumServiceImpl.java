package com.example.socialnetworkapp.forum.service.impl;

import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.dto.ForumDTO;
import com.example.socialnetworkapp.forum.model.Forum;
import com.example.socialnetworkapp.forum.repository.ForumRepository;
import com.example.socialnetworkapp.forum.service.ForumService;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;

    private final ModelMapper modelMapper;

    private final UserService userService;

    private final MasterMessageService masterMessageService;

    @Override
    public Forum saveAndFlush(Forum forum) {
        return forumRepository.saveAndFlush(forum);
    }

    @Override
    public boolean existsById(Long id) {
        return forumRepository.existsById(id);
    }

    @Override
    public Forum findById(Long id) throws ResourceNotFoundException {
        log.debug("Find forum by id, id: {}", id);
        return forumRepository.findByIsActiveTrueAndId(id).orElseThrow(
                () -> new ResourceNotFoundException(Constants.FORUM + ", id:" + id));
    }

    @Override
    public ForumDTO getById(Long id) throws ResourceNotFoundException {
        return modelMapper.map(this.findById(id), ForumDTO.class);
    }

    @Override
    public Page<ForumDTO> getAll(Pageable pageable, String search) throws SocialNetworkAppException {
        Page<Forum> forumPage;
        if (StringUtils.isBlank(search)) {
            forumPage = forumRepository.findAllByIsActiveTrue(pageable);
        } else {
            Specification<Forum> spec = (Specification<Forum>) CommonUtils.buildSpecification(search);
            forumPage = forumRepository.findAll(spec, pageable);
        }
        List<ForumDTO> forumDTOList = forumPage.stream()
                .map(forum -> modelMapper.map(forum, ForumDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(forumDTOList, pageable, forumPage.getTotalElements());
    }

    @Override
    @Transactional
    public SimpleResponseDTO create(ForumDTO forumDTO) throws SocialNetworkAppException {
        log.debug("Create forum, forum name: {}", forumDTO.getName());
        Forum forum = modelMapper.map(forumDTO, Forum.class);
        forum.setAppUser(userService.getCurrentUser());
        forum.setIsActive(true);
        this.saveAndFlush(forum);
        MasterMessage masterMessage = masterMessageService.findByMessageCode(MasterMessageCode.CREATE_SUCCESS);
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        simpleResponseDTO.setTitle(CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getTitle()),
                Constants.FORUM.toUpperCase()
        ));
        simpleResponseDTO.setMessage(CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getMessage()),
                Constants.FORUM.toLowerCase(),
                forum.getName()
        ));
        return simpleResponseDTO;
    }
}
