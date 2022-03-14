package com.arcade.exception;

import com.arcade.dao.user.UserDaoImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class UsersRestExceptionHandler {

    private final static Logger logger = Logger.getLogger(UserDaoImplementation.class.getName());

    /**
     * This handler will catch only if a value for ID that isn't in the database is passed to the request
     * @param e Custom exception instance
     * @return A response with a 404 (not found) status
     */
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e) {
        return handleBaseException(e, HttpStatus.NOT_FOUND);
    }

    /**
     * This handler will catch every error (e.g., "testId" string inserted instead of an int value)
     * @param e Custom exception instance
     * @return A response with a 400 (bad request) status
     */
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(Exception e) {
        return handleBaseException(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<UserErrorResponse> handleBaseException(Exception e, HttpStatus httpStatus) {
        UserErrorResponse errorResponse = new UserErrorResponse(
                httpStatus.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
