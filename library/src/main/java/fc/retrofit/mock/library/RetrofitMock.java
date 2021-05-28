package fc.retrofit.mock.library;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RetrofitMock {
    String baseUrl() default "";
    String xApiKey() default "";
}
