package com.kissoft.kcym.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApp extends Application {
	// private
	private static final String TAG = MyApp.class.getSimpleName();
	private static PreferenceHelper setting = null;
	private static final String YIMENG_PREFERENCES_SETTING = "setting";
	private static final String GUIDE_FLAG = "guide_flag";

	public final static int SCANNIN_GREQUEST_CODE = 1;
	public final static int FILECHOOSER_RESULTCODE = 2;
	public final static int SPEECH_RECONGNITION_CODE = 3;
	
	public static final String LEE_APK_NAME = "LeeWebShell.apk";
	public static final String CHECK_UPDATE_ACTION = "com.steven.leewebshell.ACRION.ApkUpdate_dia";
//	public static final String updateServer = "http://192.168.1.220/san/update_apk.html";
//	public static final String updateServer = "http://www.91yimeng.cn/update/update_apk.html";
	public static final String updateServer = "http:192.168.1.71:8081/UpdService.do";

	public static final String baseServer = "http://192.168.1.2:8081/appService.do";
	public static int version_code;
	public static String version_name;


	@Override
	public void onCreate() {
		setting = new PreferenceHelper(YIMENG_PREFERENCES_SETTING);
		
		try {
			MyApp.version_code = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
			MyApp.version_name = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		initImageLoader(getApplicationContext());
		
		super.onCreate();
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public static void setGuideFlag(int flag) {
		setting.putInt(GUIDE_FLAG, flag);
	}

	public static int getGuideFlag() {
		return setting.getInt(GUIDE_FLAG, -1);
	}
	
    public static String getDebugStr() {
    	return setting.getString("lee_debug_str", "");
    }
    
    public static void setDebugStr(String city_code) {
    	setting.putString("lee_debug_str", city_code);
    }
	
	private class PreferenceHelper {
		SharedPreferences preferences;
		
		public PreferenceHelper(String whichPreferences) {
			preferences = getSharedPreferences(whichPreferences, MODE_MULTI_PROCESS);
		}
		
		private int getInt(String Key, int defValue){
			int value = preferences.getInt(Key, defValue);
			return value;
		}
		
		private String getString(String Key, String defValue){
			String value = preferences.getString(Key, defValue);
			return value;
		}
		
		private void putInt(String Key, int value){
			Editor editor = preferences.edit();
			editor.putInt(Key, value);
			editor.commit();
			return;
		}
		
		private void putString(String Key, String value){
			Editor editor = preferences.edit();
			editor.putString(Key, value);
			editor.commit();
			return;
		}
		
		private boolean contains(String key){
			return preferences.contains(key);
		}
	}
	
	
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.MAX_PRIORITY).denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}
}
