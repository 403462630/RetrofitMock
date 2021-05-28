package fc.retrofit.mock.library;

public class RetrofitData {
    private String baseUrl;
    private String xApiKey;

    public RetrofitData(String baseUrl, String xApiKey) {
        this.baseUrl = baseUrl;
        this.xApiKey = xApiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getxApiKey() {
        return xApiKey;
    }
}
