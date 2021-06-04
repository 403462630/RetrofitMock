package fc.retrofit.mock.library;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RetrofitMock {

    String baseUrl() default "";

    /**
     * PostMain 私有的 Api KEY
     */
    String xApiKey() default "";

    /**
     * 设置请求头信息，格式如下：
     * headers = "Cache-Control: max-age=640000"
     * 或
     * headers = {
     *     "Accept: application/vnd.github.v3.full+json",
     *     "User-Agent: Retrofit-Sample-App"
     * }
     */
    String[] headers() default {};
}
