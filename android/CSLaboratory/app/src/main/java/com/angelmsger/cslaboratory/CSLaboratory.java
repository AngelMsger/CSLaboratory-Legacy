package com.angelmsger.cslaboratory;

import android.app.Application;

import com.angelmsger.cslaboratory.dao.DaoMaster;
import com.angelmsger.cslaboratory.dao.DaoSession;
import com.bumptech.glide.Glide;

import org.greenrobot.greendao.database.Database;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class CSLaboratory extends Application {
    public ExecutorService mExecutorService;

    public Cache mHttpCache;
    public OkHttpClient mHttpClient;

    // 为 Api 请求加入 Auth 认证，同时必要信息将直接写入本地数据库，而不是在此处使用 HttpClient 进行缓存
    public OkHttpClient mApiClient;

    // 本地数据库会话
    public DaoSession mDaoSession;

    // 用来存储全局对象
    public final Map<String, Object> mGlobal = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        // Http 缓存大小 16 MiB
        final int cacheSize = 16777216;
        // 请求超时时间 6 S
        final int timeout = 16;
        mExecutorService = Executors.newCachedThreadPool();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();

        // 使用 Android 推荐的 getCacheDir() 作为缓存目录，此目录安全受系统保护
        mHttpCache = new Cache(getCacheDir(), cacheSize);
        mHttpClient = new OkHttpClient.Builder()
                .cache(mHttpCache)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .build();

        mApiClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic("AngelMsger", "password");
                System.out.println(credential);
                return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
            }
        }).build();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // 当运行内存不足时释放 Glide 图片库内存缓存
        if (level >= TRIM_MEMORY_RUNNING_MODERATE) {
            Glide.get(this).clearMemory();
        }
    }
}
