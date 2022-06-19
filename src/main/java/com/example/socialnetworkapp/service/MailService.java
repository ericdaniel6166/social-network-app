package com.example.socialnetworkapp.service;

import com.example.socialnetworkapp.dto.EmailDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;

public interface MailService {

    void sendMail(EmailDTO emailDTO) throws SocialNetworkAppException;
}
