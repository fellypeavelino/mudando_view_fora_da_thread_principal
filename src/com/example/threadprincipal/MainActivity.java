package com.example.threadprincipal;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpConnection;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {
	private Handler handler = new Handler();//para alterar uma view por meio de outra thread que não seja a principal
	@Override
	protected void onCreate(Bundle savedInstanceState) {//Thread principal
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void download(View v){//para acessar a internet é necessario criar outra thread
		new Thread(){
			public void run(){
				try {
					URL url = new URL("http://www.apple.com/apple-touch-icon.png");
					HttpURLConnection connection;
					connection = (HttpURLConnection) url.openConnection();
					connection.setDoInput(true);
					connection.connect();
					InputStream input = connection.getInputStream();
					final Bitmap imagem = BitmapFactory.decodeStream(input);
					Log.i("baixo","imagem");
					final ImageView iv = (ImageView)findViewById(R.id.imageView1);
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							iv.setImageBitmap(imagem);
						}
					});
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}.start();
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
