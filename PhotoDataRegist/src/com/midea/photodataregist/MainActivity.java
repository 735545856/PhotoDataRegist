package com.midea.photodataregist;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.*;
import org.opencv.highgui.Highgui;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifReader;
import com.drew.metadata.exif.ExifIFD0Directory;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//0.init OpenCv
		
		

		
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			//picture process
			//1. load a picture
			//Mat picture = Highgui.imread("/sdcard/Pictures/");	
			//File f= new File("/sdcard/Pictures/20161211_143650.jpg");
			File f= new File("/sdcard/DCIM/Camera/20170116_190456.jpg");
			
			//2. read the picture's message
			Metadata metadata;
			String data= null; 
				try {
					metadata = ImageMetadataReader.readMetadata(f);
					Directory directory = metadata.getDirectory(ExifIFD0Directory.class);
					data= directory.getString(ExifIFD0Directory.TAG_DATETIME);
			        System.out.println(data);  
			        
			       
				} catch (ImageProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("ljp", ""+e);
				} 										
				
				Mat picture = Highgui.imread("/sdcard/DCIM/Camera/20170116_190456.jpg");
				Point pt = new Point(picture.width()-600, picture.height()-50);
				Scalar colortext = new Scalar(29,155,205);  //萝卜黄
				Core.putText(picture,data,pt,Core.FONT_HERSHEY_DUPLEX,1.5,colortext,2); // 输出结果文字到图像上
				Highgui.imwrite( "/sdcard/1.jpg", picture);
				Toast.makeText(MainActivity.this, "处理完毕！", Toast.LENGTH_LONG).show();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11, getApplicationContext(), mBaseLoaderCallback)){
			Log.e("ljp", "opencv加载成功！！！");
		};
		 
		}
	
	
	private BaseLoaderCallback mBaseLoaderCallback = new BaseLoaderCallback(this) {
		 
		@Override
		public void onManagerConnected(int status) {
		// TODO Auto-generated method stub
		switch (status){
		case BaseLoaderCallback.SUCCESS:
		break;
		default:
		super.onManagerConnected(status);
		break;
		}
		} 
		};
		
}
