package org.abrar.storemanagement.exception;

import jakarta.persistence.OptimisticLockException;
import org.abrar.storemanagement.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
//        ApiError error = new ApiError(
//                false,
//                ex.getMessage(),
//                LocalDateTime.now()
//        );
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiError> handleValidationErrors(
//            MethodArgumentNotValidException ex) {
//
//        String errorMessage = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(err -> err.getField() + ": " + err.getDefaultMessage())
//                .findFirst()
//                .orElse("Validation failed");
//
//        ApiError apiError = new ApiError(
//                false,
//                errorMessage,
//                LocalDateTime.now()
//        );
//
//        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
//    public ResponseEntity<ApiError> handleOptimisticLock(
//            ObjectOptimisticLockingFailureException ex) {
//
//        ApiError error = new ApiError(
//                false,
//                "Product was modified by another request. Please retry.",
//                LocalDateTime.now()
//        );
//
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
//    }
//
//    @ExceptionHandler(OptimisticLockException.class)
//    public ResponseEntity<ApiError> handleOptimisticLock(
//            OptimisticLockException ex) {
//
//        ApiError error = new ApiError(
//                false,
//                "Stock was updated by another transaction. Please retry.",
//                LocalDateTime.now()
//        );
//
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
//    }
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
//        ApiError error = new ApiError(
//                false,
//                ex.getMessage(),
//                LocalDateTime.now()
//        );
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
//
//}
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VersionMismatchException.class)
    public String handleVersionMismatch(VersionMismatchException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error"; // error.jsp
    }

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error"; // refers to WEB-INF/views/error.jsp
    }
}