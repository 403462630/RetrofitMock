package fc.example.retrofitmock;

import fc.retrofit.mock.library.RetrofitMock;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ZhiHuApi {

    @GET("api/4/news/latest")
    Call<ResponseBody> getLatestNews();

    @RetrofitMock
    @GET("api/4/news/latest")
    Call<ResponseBody> getLatestNews2();
}
