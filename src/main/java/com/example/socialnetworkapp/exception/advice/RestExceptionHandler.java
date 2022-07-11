package com.example.socialnetworkapp.exception.advice;

import com.example.socialnetworkapp.configuration.OperationIdConfiguration;
import com.example.socialnetworkapp.dto.ErrorDetail;
import com.example.socialnetworkapp.dto.ErrorResponseDTO;
import com.example.socialnetworkapp.dto.ValidationErrorDetail;
import com.example.socialnetworkapp.enums.ErrorCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final OperationIdConfiguration operationIdConfiguration;

    private ResponseEntity<Object> buildResponseExceptionEntity(ErrorResponseDTO errorResponseDTO) {
        if (operationIdConfiguration != null) {
            errorResponseDTO.setOperationId(operationIdConfiguration.getOperationId());
        }

        return new ResponseEntity<>(errorResponseDTO, errorResponseDTO.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest) {
        log.error("Handle {}, error message: {}", e.getClass(), getRootCauseMessage(e), e);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.UNPROCESSABLE_ENTITY,
                ErrorCode.VALIDATION_ERROR.name(), null, httpServletRequest, null);

        List<ErrorDetail> errorDetails = e.getBindingResult().getAllErrors().stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> mapFieldErrorToErrorDetail((FieldError) error))
                .collect(Collectors.toList());

        errorResponseDTO.setErrorDetails(errorDetails);

        return buildResponseExceptionEntity(errorResponseDTO);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        log.error("Handle {}, error message: {}", e.getClass(), errorMessage, e);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getHttpStatus(),
                ErrorCode.VALIDATION_ERROR.name(), errorMessage, httpServletRequest, e.getErrorDetails());

        return buildResponseExceptionEntity(errorResponseDTO);
    }

    private ErrorDetail mapFieldErrorToErrorDetail(FieldError fieldError) {
        return new ValidationErrorDetail(fieldError.getObjectName(), fieldError.getField(),
                fieldError.getRejectedValue(), fieldError.getDefaultMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        log.error("Handle {}, error message: {}", e.getClass(), errorMessage, e);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getStatus(), e.getStatus().name(),
                errorMessage, httpServletRequest, null);
        return buildResponseExceptionEntity(errorResponseDTO);
    }

    @ExceptionHandler(SocialNetworkAppException.class)
    public ResponseEntity<Object> handleSocialNetworkAppException(SocialNetworkAppException e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        log.error("Handle {}, error message: {}", e.getClass(), errorMessage, e);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getHttpStatus(), e.getError(),
                errorMessage, httpServletRequest, e.getErrorDetails());

        return buildResponseExceptionEntity(errorResponseDTO);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        log.error("Handle {}, error message: {}", e.getClass(), errorMessage, e);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.NOT_FOUND, e.getError(),
                errorMessage, httpServletRequest, e.getErrorDetails());

        return buildResponseExceptionEntity(errorResponseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e, HttpServletRequest httpServletRequest) {
        log.error("Handle {}, error message: {}", e.getClass(), getRootCauseMessage(e), e);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), httpServletRequest, null);

        return buildResponseExceptionEntity(errorResponseDTO);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleBadCredentialsException(AuthenticationException e, HttpServletRequest httpServletRequest) {
        log.error("Handle {}, error message: {}", e.getClass(), getRootCauseMessage(e), e);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.name(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(), httpServletRequest, null);

        return buildResponseExceptionEntity(errorResponseDTO);
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class, NumberFormatException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        log.error("Handle {}, error message: {}", e.getClass(), errorMessage, e);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), httpServletRequest, null);

        return buildResponseExceptionEntity(errorResponseDTO);
    }

    private String getRootCauseMessage(Exception e) {
        return ExceptionUtils.getRootCause(e).getMessage();
    }


}
