package fc.example.retrofitmock;

import fc.retrofit.mock.library.RetrofitMock;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RetrofitMock(
        baseUrl = "https://541c2043-0192-4d97-abcc-851c4f719b53.mock.pstmn.io/"
)
public @interface ARetrofitMock {
}
