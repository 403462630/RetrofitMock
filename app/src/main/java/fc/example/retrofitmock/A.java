package fc.example.retrofitmock;

import android.util.Log;
import fc.retrofit.mock.library.MockRetrofitHelper;
import fc.retrofit.mock.library.RetrofitData;
import retrofit2.Retrofit;

import java.lang.reflect.Method;

public class A {
    public void test() {
        Log.i("TAG", "aaa");
    }

    public static void test1() {
        Log.i("TAG", "aaa");
    }

    public static void mockRetrofit(Retrofit retrofit, Method method) {
        retrofit = MockRetrofitHelper.wrapRetrofit(retrofit, method, new RetrofitData("aaa", "bbb"));
//        String tag = "TAG";
//        Log.i(tag, "aaa");
    }
}
