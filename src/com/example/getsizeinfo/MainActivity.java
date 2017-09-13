package com.example.getsizeinfo;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {
	private EditText et_packname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_packname = (EditText) findViewById(R.id.et_packname);
	}

	public void click(View view){
		// mPm.getPackageSizeInfo(mCurComputingSizePkg, mStatsObserver);
		PackageManager pm = getPackageManager();
		try {
			Method method = PackageManager.class.getDeclaredMethod("getPackageSizeInfo", String.class,IPackageStatsObserver.class);
			method.invoke(pm, et_packname.getText().toString().trim(),new MyObserver());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//08-30 15:50:20.475: I/System.out(3124): cache:12.00 KB  缓存
	//08-30 15:50:20.475: I/System.out(3124): data:508 KB     数据
	//08-30 15:50:20.475: I/System.out(3124): code:2.76 MB    应用
	private class MyObserver extends IPackageStatsObserver.Stub{
		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			long cache = pStats.cacheSize;
			long data = pStats.dataSize;
			long code = pStats.codeSize;
			System.out.println("cache:"+Formatter.formatFileSize(getApplicationContext(), cache));
			System.out.println("data:"+Formatter.formatFileSize(getApplicationContext(), data));
			System.out.println("code:"+Formatter.formatFileSize(getApplicationContext(), code));
			
		}
	}
	
	

}
