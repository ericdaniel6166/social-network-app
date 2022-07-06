package com.example.socialnetworkapp.utils;

import com.example.socialnetworkapp.configuration.rsql.CustomRsqlVisitor;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;

@Slf4j
public final class CommonUtils {

    private static final String REGEX_MASKING_EMAIL = "(?<=.)[^@](?=[^@]*?[^@]@)|(?:(?<=@.)|(?!^)\\G(?=[^@]*$)).(?=.*[^@]\\.)";

    private static final String CHARACTER_ASTERISK = "*";

    public static String maskEmail(String email) {
        return email.replaceAll(REGEX_MASKING_EMAIL, CHARACTER_ASTERISK);
    }

    public static String formatString(String string, Object... objects) throws SocialNetworkAppException {
        String formattedString;
        try {
            formattedString = String.format(string, objects);
        } catch (Exception e) {
            log.error("Error format string, string: {}, arguments: {}", string, Arrays.asList(objects), e);
            throw new SocialNetworkAppException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return formattedString;
    }

    public static Pageable buildPageable(Integer page, Integer size, Sort.Direction direction, String[] properties) {
        log.debug("Build pageable, page: {}, size: {}, direction: {}, properties: {}", page, size, direction, properties);
        return PageRequest.of(page, size, direction, properties);
    }

    public static ResponseEntity<?> buildPageResponseEntity(Page<?> page) {
        if (page.getContent().isEmpty()) {
            log.debug("Page is empty, page number: {}, total pages: {}", page.getNumber(), page.getTotalPages());
            return new ResponseEntity<>(page, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    public static Specification<?> buildSpecification(String search) {
        log.debug("Build specification, search: {}", search);
        Node rootNode = new RSQLParser().parse(search);
        return rootNode.accept(new CustomRsqlVisitor<>());
    }

    public static Jwt getJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            return (Jwt) authentication.getPrincipal();
        }
        return null;

    }

    public static String getUsername() {
        Jwt principal = getJwt();
        if (principal != null) {
            if (StringUtils.isNotBlank(principal.getSubject())) {
                return principal.getSubject();
            }
        }
        return null;

    }


    public static String writeValueAsString(final Object object) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
