package com.google.vikasproject;

import java.io.FileInputStream;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private String mainActivity = "Main Activity";
	private MediaPlayer mediaPlayer = null;
	private FileInputStream fileStream = null;
	
	private void playMusicFromWeb(final String url) {
		if (url == null || url.isEmpty()) {
			throw new NullPointerException("URL Passed is null");
		}
		
		try {
			Uri file = Uri.parse(url);
			mediaPlayer = MediaPlayer.create(this, file);
			mediaPlayer.start();
		} catch(Exception ex) {
			Log.e(mainActivity, ex.getMessage());
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(mainActivity, "OnCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onStart() {
		Log.v(mainActivity, "OnStart");
		this.playMusicFromWeb("http://freedownloads.last.fm/download/458250264/The%2BDevil%2527s%2BGotta%2527%2BEarn.mp3");
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		Log.v(mainActivity, "OnResume");
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.v(mainActivity, "OnPause");
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		Log.v(mainActivity, "OnStop");
		if (this.mediaPlayer != null) {
			if (this.mediaPlayer.isPlaying()) {
				this.mediaPlayer.stop();
			}
			
			this.mediaPlayer.release();
		}
		
		super.onStop();
	}
	
	@Override
	protected void onRestart() {
		Log.v(mainActivity, "OnRestart");
		super.onRestart();
	}
	
	@Override
	protected void onDestroy() {
		Log.v(mainActivity, "OnDestroy");
		
		super.onDestroy();
	}
}
