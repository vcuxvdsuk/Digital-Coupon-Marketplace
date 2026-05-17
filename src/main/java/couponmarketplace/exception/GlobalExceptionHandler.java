package couponmarketplace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}