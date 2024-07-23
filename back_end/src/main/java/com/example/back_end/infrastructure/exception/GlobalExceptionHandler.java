package com.example.back_end.infrastructure.exception;

import com.example.back_end.infrastructure.constant.ErrorCode;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Date;
import java.util.List;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle exception when validate data
     *
     * @param e
     * @param request
     * @return errorResponse
     */
    @ExceptionHandler({ConstraintViolationException.class, MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(BAD_REQUEST)
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = APPLICATION_JSON_VALUE, examples = @ExampleObject(name = "Handle exception when the data is invalid. (@RequestBody, @RequestParam, @PathVariable)", summary = "Handle Bad Request", value = """
            {
                 "timestamp": "2024-04-07T11:38:56.368+00:00",
                 "status": 400,
                 "path": "/api/v1/...",
                 "error": "Invalid Payload",
                 "message": "{data} must be not blank"
             }
            """))})})
    public ErrorResponse handleValidationException(Exception e, WebRequest request) {

        ErrorResponse.ErrorResponseBuilder builder = ErrorResponse.builder()
                .timestamp(new Date())
                .status(BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""));

        String message = e.getMessage();
        if (e instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errorMessages = fieldErrors.stream()
                    .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .toList();
            message = String.join(", ", errorMessages);
            builder.error("Invalid Payload").message(message);
        } else {
            builder.error("Invalid Data").message(message);
        }

        return builder.build();
    }

    /**
     * Handle exception when the request not found data
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Bad Request", content = {@Content(mediaType = APPLICATION_JSON_VALUE, examples = @ExampleObject(name = "404 Response", summary = "Handle exception when resource not found", value = """
            {
              "timestamp": "2023-10-19T06:07:35.321+00:00",
              "status": 404,
              "path": "/api/v1/...",
              "error": "Not Found",
              "message": "{data} not found"
            }
            """))})})
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {

        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(NOT_FOUND.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    /**
     * Handle exception when the data is conflicted
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(CONFLICT)
    @ApiResponses(value = {@ApiResponse(responseCode = "409", description = "Conflict", content = {@Content(mediaType = APPLICATION_JSON_VALUE, examples = @ExampleObject(name = "409 Response", summary = "Handle exception when input data is conflicted", value = """
            {
              "timestamp": "2023-10-19T06:07:35.321+00:00",
              "status": 409,
              "path": "/api/v1/...",
              "error": "Conflict",
              "message": "{data} exists, Please try again!"
            }
            """))})})
    public ErrorResponse handleDuplicateKeyException(InvalidDataException e, WebRequest request) {

        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(CONFLICT.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(CONFLICT.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    /**
     * Handle exception when internal server error
     *
     * @param e
     * @param request
     * @return error
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ApiResponses(value = {@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(mediaType = APPLICATION_JSON_VALUE, examples = @ExampleObject(name = "500 Response", summary = "Handle exception when internal server error", value = """
            {
              "timestamp": "2023-10-19T06:35:52.333+00:00",
              "status": 500,
              "path": "/api/v1/...",
              "error": "Internal Server Error",
              "message": "Connection timeout, please try again"
            }
            """))})})
    public ErrorResponse handleException(Exception e, WebRequest request) {

        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(INTERNAL_SERVER_ERROR.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ErrorResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.FORBIDDEN;
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ErrorResponse.builder()
                        .status(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    /*
     * No static resource - 404
     * */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNoResourceFoundException(NoResourceFoundException e) {
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(NOT_FOUND.value())
                .path(e.getResourcePath())
                .error(e.getMessage())
                .message(NOT_FOUND.getReasonPhrase())
                .build();
    }

    @ExceptionHandler(StoreException.class)
    public ResponseEntity<ErrorResponse> handleStoreException(StoreException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(new Date())
                .status(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(errorResponse);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = APPLICATION_JSON_VALUE, examples = @ExampleObject(name = "404 Response", summary = "Handle exception when type mismatch occurs", value = """
            {
              "timestamp": "2024-07-14T11:23:14.801+00:00",
              "status": 400,
              "path": "/api/admin/product-attributes/3bjbjjbjk",
              "error": "Bad Request",
              "message": "Resource not found due to type mismatch"
            }
            """))})})
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, WebRequest request) {
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error("Bad Request")
                .message(e.getMessage())
                .build();
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponses(value = {@ApiResponse(responseCode = "409", description = "Conflict", content = {@Content(mediaType = APPLICATION_JSON_VALUE, examples = @ExampleObject(name = "409 Response", summary = "Handle exception when data integrity is violated", value = """
            {
              "timestamp": "2024-07-14T11:23:14.801+00:00",
              "status": 409,
              "path": "/api/v1/...",
              "error": "Conflict",
              "message": "Data integrity violation occurred"
            }
            """))})})
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {

        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.CONFLICT.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message("Data integrity violation: " + e.getMessage())
                .build();
    }
    /**
     * Handle IllegalArgumentException.
     *
     * @param e the exception
     * @param request the web request
     * @return the error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", examples = @ExampleObject(name = "400 Response", summary = "Handle IllegalArgumentException", value = """
            {
              "timestamp": "2024-07-14T11:23:14.801+00:00",
              "status": 400,
              "path": "/api/v1/...",
              "error": "Bad Request",
              "message": "Invalid argument provided"
            }
            """))})})
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error("Bad Request")
                .message(e.getMessage())
                .build();
    }

}
