package fc.example.retrofitmock;

import fc.retrofit.mock.library.RetrofitMock;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ZhiHuApi {

    @GET("api/4/news/latest")
    Call<ResponseBody> getLatestNew1();

    /**
     * 使用默认的配置，如果没有配置，则调用原服务（即跟 getLatestNew1 效果一样）
     * @return
     */
    @RetrofitMock(
            baseUrl = "https://541c2043-0192-4d97-abcc-851c4f719b53.mock.pstmn.io/"
    )
    @GET("api/4/news/latest")
    Call<ResponseBody> getLatestNews2();

    @ARetrofitMock
    @GET("api/4/news/latest")
    Call<ResponseBody> getLatestNews3();

    @BRetrofitMock
    @GET("api/4/news/latest")
    Call<ResponseBody> getLatestNews4();
}
