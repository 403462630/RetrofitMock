## RetrofitMock

顾名思义，此`Library`的作用是快速`mock retrofit`接口，可以结合`PostMan`的`mock servers`服务，在开发阶段 只需要定义好数据格式和字段，就可以不需要等后端接口直接开发业务代码，并进行简单的调试

### 用法

##### 1. 添加依赖

在项目根目录下的build.gradle文件，添加如下配置

```

buildscript {
    repositories {
    	...
	maven { url 'https://jitpack.io' }
    }
    
    dependencies {
    	...
        classpath 'com.github.403462630.RetrofitMock:plugin:1.0.0'
    }
}


```

然后在app/build.gradle下使用plugin

```
// 使用插件(只需要在app中添加，library module中不需要添加)
apply plugin: 'retrofitMock'

// 配置默认的baseUrl和xApiKey(postman mock server私有api专用)
// 可以不用配置，在使用的时候再指定
retrofitMock {
    onlyDebug true // 默认值，只有在debug模式才起作用
    // postman（或自己搭建的mock服务）的host
    baseUrl="https://541c2043-0192-4d97-abcc-851c4f719b53.mock.pstmn.io/"
    // postman的private api key（public api 不需要配置xApiKey）
    xApiKey="PMAK-60af85af16eef400422dc3f8-c682c75f5880082a1fad8ab737e301aee9"
}
```

最后添加依赖

```
dependencies {
    implementation 'com.github.403462630.RetrofitMock:library:1.0.0'
}
```

##### 2. 使用 RetrofitMock 注解

在使用`retrofit`原来的方式定义完`api`后，只需要加上`@RetrofitMock`注解即可

```
/**
 * 使用默认的配置，如果没有配置，则调用原服务
 */
@RetrofitMock
@GET("api/4/news/latest")
Call<ResponseBody> getLatestNews1();

/**
 * 自定义配置，可以配置baseUrl、xApiKey、headers
 */
@RetrofitMock(
    baseUrl = "https://541c2043-0192-4d97-abcc-851c4f719b53.mock.pstmn.io/"
)
@GET("api/4/news/latest")
Call<ResponseBody> getLatestNews2();

```

由于在开发过程中每个人`postman` 的 `mock server` 的 `baseUrl`等配置不一样，所以可以通过`@RetrofitMock`预先定义好每个人的配置

```

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RetrofitMock(
        baseUrl = "https://541c2043-0192-4d97-abcc-851c4f719b53.mock.pstmn.io/"
)
public @interface ARetrofitMock {
}


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

```

然后直接使用定义好配置的注解即可

```
@ARetrofitMock
@GET("api/4/news/latest")
Call<ResponseBody> getLatestNews3();

@BRetrofitMock
@GET("api/4/news/latest")
Call<ResponseBody> getLatestNews4();
```

##### 3. 其他

- `@RetrofitMock`默认只会在Debug模式下起作用，你可以通过修改onlyDebug的值，改变这种行为
- 可以在`local.properties`中添加`retrofitMock.disablePlugin=false`，全面禁用RetrofitMock插件
- `postmain`的mock server用法非常简单，请自行查阅文档

##### 4. 混淆

默认不需要你添加任何混淆规则（library会自动导入混淆规则），如果没起作用(可能是gradle版本过低)，则自己添加如下混淆规则

```
-keep @fc.retrofit.mock.library.** class *
-keepclassmembers class retrofit2.Retrofit$Builder {
 *;
}
```
