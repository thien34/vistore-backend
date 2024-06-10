package com.example.back_end.infrastructure.exception;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = APPLICATION_JSON_VALUE, examples = @ExampleObject(name = "Handle exception when the data invalid. (@RequestBody, @RequestParam, @PathVariable)", summary = "Handle Bad Request", value = """
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
        if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errorMessages = fieldErrors.stream()
                    .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
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
}
