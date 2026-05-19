package couponmarketplace.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = org.springframework.http.HttpStatus.BAD_REQUEST)
public class ProductAlreadySoldException extends RuntimeException {

    public ProductAlreadySoldException(String message) {
        super(message);
    }
}
