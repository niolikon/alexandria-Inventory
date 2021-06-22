package org.niolikon.alexandria.inventory.system;

import org.niolikon.alexandria.inventory.system.exceptions.EntityDuplicationException;
import org.niolikon.alexandria.inventory.system.exceptions.EntityNotFoundException;
import org.niolikon.alexandria.inventory.system.exceptions.EntityNotProcessableException;
import org.niolikon.alexandria.inventory.system.exceptions.OperationNotAcceptableException;
import org.niolikon.alexandria.inventory.system.web.ApiErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
        return prepareRestException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityDuplicationException.class})
    public ResponseEntity<Object> handleDuplicationException(Exception ex, WebRequest request) {
        return prepareRestException(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({EntityNotProcessableException.class})
    public ResponseEntity<Object> handleNotProcessableException(Exception ex, WebRequest request) {
        return prepareRestException(ex, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({OperationNotAcceptableException.class})
    public ResponseEntity<Object> handleOperationNotAllowedException(Exception ex, WebRequest request) {
        return prepareRestException(ex, request, HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return prepareRestException(ex, request, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> prepareRestException(Exception ex, WebRequest request, HttpStatus status) {
        logger.info(ex.getClass().getName());
        String error = ex.getLocalizedMessage();
        if (ex.getCause() != null) {
            error += String.format(". %s", ex.getCause().getMessage());
        } else if (! StringUtils.hasText(error)) {
            error = "message not available";
        }
        final ApiErrorResponse response = ApiErrorResponse.valueOf(status.value(), getPath(request), error, ex.getClass().getSimpleName());
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request)
                .getRequest()
                .getRequestURI();
    }
}
