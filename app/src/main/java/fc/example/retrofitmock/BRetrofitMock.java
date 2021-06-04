package fc.example.retrofitmock;

import fc.retrofit.mock.library.RetrofitMock;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RetrofitMock(
        baseUrl = "https://aac73f0f-ee75-4188-aabc-b2d8be3f81e2.mock.pstmn.io/",
//        xApiKey = "PMAK-60af85af16eef400422dc3f8-c682c75f5880082a1fad8ab737e301aee9"
//        headers = "x-api-key: PMAK-60af85af16eef400422dc3f8-c682c75f5880082a1fad8ab737e301aee9"
        headers = {
                "x-api-key: PMAK-60af85af16eef400422dc3f8-c682c75f5880082a1fad8ab737e301aee9"
        }
)
public @interface BRetrofitMock {
}
