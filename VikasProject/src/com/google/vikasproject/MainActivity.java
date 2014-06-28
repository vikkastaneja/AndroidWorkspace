package com.google.vikasproject;

import java.io.FileInputStream;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {

	private String mainActivity = "Main Activity";
	private MediaPlayer mediaPlayer = null;
	@SuppressWarnings("unused")
	private FileInputStream fileStream = null;
	
	@SuppressWarnings("unused")
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
	
	public void onLocationChanged(Location location)
	{
	    if (location != null)
	    {
	        System.out.println("La localizacion es: (lon: "
	                + location.getLongitude() + ", lat: "
	                + location.getLatitude() + ")");
	        
	    }
	    else
	    {
	        System.out.println("La location es nula");
	    }

	}
	
	private void getLocation() {
		try {
			LocationManager locMgr = (LocationManager)getSystemService(LOCATION_SERVICE);
			if (!locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				Toast.makeText(this, "Unable to take location", 5000).show();
				finish();
			}
			
			locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
			Location location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			Log.i(mainActivity, "=====> Last known location: " + location.toString());
		} catch (Exception ex) {
			Log.e(mainActivity, "Location failed: " + ex.getMessage());
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(mainActivity, "OnCreate");
		super.onCreate(savedInstanceState);
		this.getLocation();
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onStart() {
		Log.v(mainActivity, "OnStart");
		//this.playMusicFromWeb("http://freedownloads.last.fm/download/458250264/The%2BDevil%2527s%2BGotta%2527%2BEarn.mp3");
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

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}
