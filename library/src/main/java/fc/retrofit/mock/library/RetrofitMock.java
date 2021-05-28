package fc.retrofit.mock.library;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RetrofitMock {
    String baseUrl() default "";
    String xApiKey() default "";
}
