package fc.retrofit.mock.library;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

final class MockHeaderInterceptor implements Interceptor {

    private Map<String, String> headers;

    public MockHeaderInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (headers == null || headers.isEmpty()) {
            return chain.proceed(chain.request());
        } else {
            Request request = chain.request();
            Headers.Builder headerBuilder = request.headers().newBuilder();
            for (String key : headers.keySet()) {
                headerBuilder.add(key, headers.get(key));
            }
            return chain.proceed(request.newBuilder()
                    .headers(headerBuilder.build())
                    .build());
        }
    }
}
