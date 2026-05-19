package couponmarketplace.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = org.springframework.http.HttpStatus.BAD_REQUEST)
public class ResellerPriceTooLowException extends RuntimeException {

    public ResellerPriceTooLowException(String message) {
        super(message);
    }
}
