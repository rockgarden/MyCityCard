package com.eastcom.mycitycard;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.nfc.tech.IsoDep;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.eastcom.mycitycard.services.AppService;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

/**
 * Created by rockgarden on 15/11/2.
 */
public class MycardApplication extends Application {
    private String textData ="default";

    //public static LinkedList<ActivityBase> activityList = new LinkedList<ActivityBase>();
    // public static String AID = "A0000005980000051200000000000001"; //
    // 给个默认的AID用作非接圈存时候用
    public static String AID = "A00000059807560001";
    public static String READAID = "";
    // public static String READAID = "4144442E41505032";
    // public static String READAID = "315041592E5359532E4444463031";

    public static boolean isLogin = false;
    private IsoDep isoDep;
    private String testValue;

    public static boolean channelOnUse = true;

    // 正在进行请求标志位
    public static boolean isRequestOnWay = false;

    // @date 2015-08-20
    // 处理进度框显示
    public static Handler progressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //ProgressFragment.showMsg((String) msg.obj);
        }
    };

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public IsoDep getIsoDep() {
        return isoDep;
    }

    public void setIsoDep(IsoDep isoDep) {
        this.isoDep = isoDep;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Thread.setDefaultUncaughtExceptionHandler((Thread.UncaughtExceptionHandler) this);
        startService(new Intent(this, AppService.class));
        configUniversalImageLoader();
    }

    private void configUniversalImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(480, 800)
                        // max width, max height，即保存的每个缓存文件的最大长宽
                .discCacheExtraOptions(480, 800, null)
                        // Can slow ImageLoader, use it carefully (Better don't use it)
                .threadPoolSize(3)
                        // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                        // You can pass your own memory cache
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        // 将保存的时候的URI名称用MD5加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                        // 缓存的文件数量
                .discCache(
                        new UnlimitedDiskCache(new File(Environment
                                .getExternalStorageDirectory()
                                + "/myCard/imgCache")))
                        // 自定义缓存路径
                .defaultDisplayImageOptions(getDisplayOptions())
                .imageDownloader(
                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);
    }

    private DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数旋转/翻转
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
                        // .delayBeforeLoading(int delayInMillis)//int
                        // delayInMillis为你设置的下载前的延迟时间
                        // 设置图片加入缓存前，对bitmap进行设置
                        // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .build();
        return options;
    }

//    public static void exit() {
//        for (ActivityBase activity : activityList) {
//            activity.finish();
//        }
//        activityList.clear();
//        System.exit(0);
//    }
//
//    public static void clearUserData(Context context) {
//        CacheHelper cacheHelper = CacheHelper.getInstance(context);
//        cacheHelper.clear();
//    }
//
//    @Override
//    public void uncaughtException(Thread thread, Throwable ex) {
//        ZLog log = ZLog.getInstance();
//        ex.printStackTrace();
//        log.writeLog();
//        System.exit(0);
//    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public String getTextData() {
        return textData;
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return super.getApplicationInfo();
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
