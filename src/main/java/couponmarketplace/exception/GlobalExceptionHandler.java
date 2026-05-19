package couponmarketplace.exception;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import couponmarketplace.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode("PRODUCT_NOT_FOUND");
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadySoldException.class)
    public ResponseEntity<ErrorResponse> handleProductAlreadySold(ProductAlreadySoldException e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode("PRODUCT_ALREADY_SOLD");
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResellerPriceTooLowException.class)
    public ResponseEntity<ErrorResponse> handleResellerPriceTooLow(ResellerPriceTooLowException e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode("RESELLER_PRICE_TOO_LOW");
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode("UNAUTHORIZED");
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationFailure(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ErrorResponse error = new ErrorResponse();
        error.setErrorCode("INVALID_REQUEST");
        error.setMessage(message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJson(HttpMessageNotReadableException e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode("INVALID_JSON");
        error.setMessage("Request body is malformed or contains invalid values");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode("DATA_INTEGRITY_VIOLATION");
        String msg = e.getMostSpecificCause() != null ? e.getMostSpecificCause().getMessage() : e.getMessage();
        error.setMessage(msg);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode("INVALID_REQUEST");
        error.setMessage(e.getReason() != null ? e.getReason() : "Invalid request");
        return new ResponseEntity<>(error, e.getStatusCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode("INVALID_REQUEST");
        error.setMessage(e.getMessage() != null ? e.getMessage() : "Invalid request");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
