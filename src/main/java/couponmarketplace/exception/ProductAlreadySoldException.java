package couponmarketplace.exception;


public class ProductAlreadySoldException extends RuntimeException {
    public ProductAlreadySoldException(String message) {
        super(message);
    }
}