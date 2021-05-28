package fc.retrofit.mock.library;

import android.text.TextUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockRetrofitHelper {

    private volatile static Map<String, Retrofit> retrofitCache = new HashMap<>();

    public static Retrofit wrapRetrofit(Retrofit retrofit, Method method, RetrofitData data) {
        if (retrofit == null || method == null) return retrofit;
        RetrofitMock retrofitMock = getRetrofitMock(method);
        if (retrofitMock != null) {
            return buildRetrofit(retrofit, retrofitMock, data);
        } else {
            return retrofit;
        }
    }

    private static RetrofitMock getRetrofitMock(Method method) {
        RetrofitMock retrofitMock = method.getAnnotation(RetrofitMock.class);
        if (retrofitMock != null) {
            return retrofitMock;
        }
        Annotation[] annotations = method.getAnnotations();
        if (annotations != null && annotations.length > 0) {
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationClass = annotation.annotationType();
                retrofitMock = getRetrofitMock(annotationClass);
                if (retrofitMock != null) {
                    return retrofitMock;
                }
            }
        }
        return null;
    }

    private static RetrofitMock getRetrofitMock(Class<? extends Annotation> annotationClass) {
        if (annotationClass == Documented.class
                || annotationClass == Inherited.class
                || annotationClass == Target.class
                || annotationClass == Retention.class) {
            return null;
        }
        RetrofitMock retrofitMock = annotationClass.getAnnotation(RetrofitMock.class);
        if (retrofitMock != null) {
            return retrofitMock;
        } else {
            Annotation[] annotations = annotationClass.getAnnotations();
            if (annotations != null && annotations.length > 0) {
                for (Annotation annotation : annotations) {
                    retrofitMock = getRetrofitMock(annotation.annotationType());
                    if (retrofitMock != null) {
                        return retrofitMock;
                    }
                }
            }
            return null;
        }
    }

    private static Retrofit buildRetrofit(Retrofit retrofit, RetrofitMock retrofitMock, RetrofitData data) {
        String baseUrl = getBaseUrl(retrofitMock, data);
        Retrofit result = retrofitCache.get(baseUrl);
        if (result == null) {
            synchronized (MockRetrofitHelper.class) {
                if (retrofitCache.get(baseUrl) != null) {
                    return retrofitCache.get(baseUrl);
                }
                String xApiKey = getXApiKey(retrofitMock, data);
                Retrofit.Builder builder = retrofit.newBuilder().baseUrl(baseUrl);
                if (!TextUtils.isEmpty(xApiKey)) {
                    addHeaderInterception(builder, xApiKey);
                }
                result = builder.build();
                retrofitCache.put(baseUrl, result);
            }
        }
        return result;
    }

    private static void addHeaderInterception(Retrofit.Builder builder, String xApiKey) {
        try {
            Field field = builder.getClass().getDeclaredField("callFactory");
            field.setAccessible(true);
            Object callFactory = field.get(builder);
            if (callFactory instanceof OkHttpClient) {
                Map<String, String> headers = new HashMap();
                headers.put("x-api-key", xApiKey);
                OkHttpClient okHttpClient = ((OkHttpClient) callFactory).newBuilder()
                        .addInterceptor(new MockHeaderInterceptor(headers))
                        .build();
                builder.callFactory(okHttpClient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getBaseUrl(RetrofitMock retrofitMock, RetrofitData data) {
        String baseUrl = retrofitMock.baseUrl().trim();
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = data.getBaseUrl().trim();
        }
        return baseUrl;
    }

    private static String getXApiKey(RetrofitMock retrofitMock, RetrofitData data) {
        if (TextUtils.isEmpty(retrofitMock.baseUrl().trim())) { // 没有配置baseurl，则xApiKey不起作用
            return data.getxApiKey();
        } else {
            return retrofitMock.xApiKey();
        }
    }
}
