package com.example.socialnetworkapp.utils;

import com.example.socialnetworkapp.auth.enums.ErrorMessageEnum;
import com.example.socialnetworkapp.auth.enums.RoleEnum;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
public final class CommonUtils {

    public static String maskEmail(String email) {
        return email.replaceAll(Constants.REGEX_MASKING_EMAIL, Constants.CHARACTER_ASTERISK);
    }

    public static String formatString(String string, Object... objects) throws SocialNetworkAppException {
        String formattedString;
        try {
            formattedString = String.format(string, objects);
        } catch (Exception e) {
            log.error("Error format string, string: {}, arguments: {}", string, Arrays.asList(objects), e);
            throw new SocialNetworkAppException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), getRootCauseMessage(e), null);
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
     * ROLE_MODERATOR can search inactive resource (isActive = false)
     * By default, all search queries are added isActive = True
     *
     * @param search
     * @return Specification
     */
    public static Specification<?> buildSpecification(String search) {
        log.debug("Build specification, search: {}", search);
        if (StringUtils.containsIgnoreCase(search, Constants.IS_ACTIVE_FALSE)) {
            if (!hasAuthority(RoleEnum.ROLE_MODERATOR)) {
                log.error(ErrorMessageEnum.ERROR_MESSAGE_NOT_HAVE_PERMISSION_SEARCH_INACTIVE_RESOURCE.getErrorMessage());
                throw new AccessDeniedException(ErrorMessageEnum.ERROR_MESSAGE_NOT_HAVE_PERMISSION_SEARCH_INACTIVE_RESOURCE.getErrorMessage());
            }
        } else if (!StringUtils.containsIgnoreCase(search, Constants.IS_ACTIVE_TRUE)) {
            search += Constants.SEMICOLON + Constants.IS_ACTIVE_TRUE;
        }
        Set<ComparisonOperator> operators = buildComparisonOperators();
        Node rootNode = new RSQLParser(operators).parse(search);
        return rootNode.accept(new CustomRsqlVisitor<>());
    }

    public static boolean hasAuthority(RoleEnum roleEnumCompare) {
        List<String> scope = getScope();
        if (scope == null){
            return false;
        }
        RoleEnum roleEnum;
        for (String roleName : scope) {
            roleEnum = RoleEnum.valueOf(roleName);
            if (roleEnum.compareTo(roleEnumCompare) >= 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAuthority(RoleEnum roleEnum, RoleEnum roleEnumCompare) {
        return roleEnum.compareTo(roleEnumCompare) >= 0;
    }


    private static Set<ComparisonOperator> buildComparisonOperators() {
        Set<ComparisonOperator> operators = RSQLOperators.defaultOperators();
        operators.add(CustomRSQLOperators.BOOLEAN);
        return operators;
    }


    private static Jwt getJwt() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            return (Jwt) authentication.getPrincipal();
        }
        return null;

    }

    private static List<String> getScope() {
        Jwt principal = getJwt();
        if (principal != null) {
            return (List<String>) principal.getClaims().get(Constants.SCOPE);
        }
        return null;
    }

    public static Collection<? extends GrantedAuthority> getAuthorities() {
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities();
        }
        return null;

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

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
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
