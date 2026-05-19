package couponmarketplace.dto;

public class CreateResellerResponse {

    private String apiToken;

    // for serialization/deserialization
    public CreateResellerResponse() {
    }

    public CreateResellerResponse(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }
}
