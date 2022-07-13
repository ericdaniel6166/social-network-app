package com.example.socialnetworkapp.utils;

import com.example.socialnetworkapp.auth.enums.AppRoleName;
import com.example.socialnetworkapp.configuration.rsql.CustomRSQLOperators;
import com.example.socialnetworkapp.configuration.rsql.CustomRsqlVisitor;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLOperators;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Slf4j
public final class CommonUtils {

    private static final String REGEX_MASKING_EMAIL = "(?<=.)[^@](?=[^@]*?[^@]@)|(?:(?<=@.)|(?!^)\\G(?=[^@]*$)).(?=.*[^@]\\.)";

    private static final String CHARACTER_ASTERISK = "*";

    private static final String IS_ACTIVE_TRUE = "isActive=bool=true";
    private static final String IS_ACTIVE_FALSE = "isActive=bool=false";

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

    /***
     * Only Admin role can search item with isActive = False
     * By default, All search queries are added isActive = True
     *
     * @param search
     * @return
     */
    public static Specification<?> buildSpecification(String search) {
        log.debug("Build specification, search: {}", search);
        if (StringUtils.containsIgnoreCase(search, IS_ACTIVE_FALSE)) {
            Collection<GrantedAuthority> authorities = getAuthorities();
            if (authorities == null || !authorities.contains(AppRoleName.ROLE_ADMIN.name())) {
                log.error("Argument is inappropriate, {}", IS_ACTIVE_FALSE);
                throw new IllegalArgumentException("Argument is inappropriate, " + IS_ACTIVE_FALSE);
            }
        } else if (!StringUtils.containsIgnoreCase(search, IS_ACTIVE_TRUE)) {
            search += Constants.SEMICOLON + IS_ACTIVE_TRUE;
        }
        Set<ComparisonOperator> operators = RSQLOperators.defaultOperators();
        operators.add(CustomRSQLOperators.BOOLEAN);
        Node rootNode = new RSQLParser(operators).parse(search);
        return rootNode.accept(new CustomRsqlVisitor<>());
    }

    private static Jwt getJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            return (Jwt) authentication.getPrincipal();
        }
        return null;

    }

    public static Collection<GrantedAuthority> getAuthorities() {
        Jwt principal = getJwt();
        Collection<GrantedAuthority> authorities = null;
        if (principal != null) {
            authorities = (Collection<GrantedAuthority>) principal.getClaims().get("scope");
        }
        return authorities;

    }

    public static String getCurrentUsername() {
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

    public static String getRootCauseMessage(Exception e) {
        return ExceptionUtils.getRootCause(e).getMessage();
    }
}
