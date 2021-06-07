package fc.example.retrofitmock;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static Retrofit retrofit;
    private ZhiHuApi zhiHuApi;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_text);

        initRetrofit();
        zhiHuApi = retrofit.create(ZhiHuApi.class);
    }

    private void initRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://news-at.zhihu.com/")
                    .build();
        }
    }

    public void onRequest1(View view) {
        zhiHuApi.getLatestNew1().enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    textView.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    public void onMockRequest2(View view) {
        zhiHuApi.getLatestNews2().enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        textView.setText(response.body().string());
                    } else {
                        textView.setText(response.errorBody().string());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    public void onMockRequest3(View view) {
        zhiHuApi.getLatestNews3().enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        textView.setText(response.body().string());
                    } else {
                        textView.setText(response.errorBody().string());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    public void onMockRequest4(View view) {
        zhiHuApi.getLatestNews4().enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        textView.setText(response.body().string());
                    } else {
                        textView.setText(response.errorBody().string());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }
}
