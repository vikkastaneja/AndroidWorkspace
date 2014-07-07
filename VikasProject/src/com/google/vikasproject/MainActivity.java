package com.google.vikasproject;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {

	private String mainActivity = "Main Activity";
	private int request_code = 1;
	
	public void onLocationChanged(Location location)
	{
	    if (location != null)
	    {
	        System.out.println("La localizacion es: (lon: "
	                + location.getLongitude() + ", lat: "
	                + location.getLatitude() + ")");
	        updateText(location);
	        
	    }
	    else
	    {
	        System.out.println("La location es nula");
	    }

	}
	
	private void setTimer() {
		TimePicker tp = (TimePicker)findViewById(R.id.timePicker1);
		Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
        .putExtra(AlarmClock.EXTRA_HOUR, tp.getCurrentHour())
        .putExtra(AlarmClock.EXTRA_MINUTES, tp.getCurrentMinute());
		if (intent.resolveActivity(getPackageManager()) != null)
			startActivity(intent);
	}
	
	private void pluginTimer() {
		Button button = (Button)findViewById(R.id.button2);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setTimer();
				
			}
		});
	}
	
	private void getLocation() {
		try {
			LocationManager locMgr = (LocationManager)getSystemService(LOCATION_SERVICE);
			if (!locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				Toast.makeText(this, "Unable to take location", Toast.LENGTH_SHORT).show();
				return;
			}
			
			locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
			Location location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location != null) {
				updateText(location);
			}
			Log.i(mainActivity, "=====> Last known location: " + location.toString());
		} catch (Exception ex) {
			Log.e(mainActivity, "Location failed: " + ex.getMessage());
		}
	}
	
	private void updateText(Location locationInformation) {
		if (locationInformation == null) {
			Toast.makeText(this, "Location information is null", Toast.LENGTH_LONG).show();
			return;
		}
		
		TextView textView = (TextView)findViewById(R.id.textView1);
		StringBuilder toDisplay = new StringBuilder();
		if (Geocoder.isPresent()) {
			try {
				Geocoder geoCoder = new Geocoder(getApplicationContext());
				List<Address> list = geoCoder.getFromLocation(locationInformation.getLatitude(), locationInformation.getLongitude(), 1);
			    toDisplay.append("Current address: ");
			    toDisplay.append(list.get(0).getAddressLine(0) + "\n");
			    toDisplay.append(list.get(0).getAddressLine(1) + "\n");
			    toDisplay.append(list.get(0).getAddressLine(2));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			toDisplay.append("Longitude: " + locationInformation.getLongitude() 
					+ " Lattitude: " + locationInformation.getLatitude());
		}
		
		textView.setText(toDisplay);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(mainActivity, "OnCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	private void pluginDialButton() {
		Button button = (Button)findViewById(R.id.dialButton);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TextView numberTextView = (TextView)findViewById(R.id.editText1);
				String number = numberTextView.getText().toString();
				Uri numUri = Uri.parse("tel:" + number);
				Intent dial = new Intent(Intent.ACTION_DIAL, numUri);
				startActivity(dial);
			}
		});
	}
	
	@Override
	protected void onStart() {
		Log.v(mainActivity, "OnStart");
		pluginDialButton();
		Button button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			private MediaPlayer mediaPlayer = null;
			
			private void playMusicFromWeb(final String url) {
				if (url == null || url.isEmpty()) {
					throw new NullPointerException("URL Passed is null");
				}
				
				Button button = (Button)findViewById(R.id.button1);
				button.setClickable(false);
				try {
					if (mediaPlayer == null) {
						Uri file = Uri.parse(url);
						mediaPlayer = MediaPlayer.create(getApplicationContext(), file);
						
						mediaPlayer.start();
						button.setText("Stop playing song");
					} else {
						if (mediaPlayer.isPlaying())
							mediaPlayer.stop();
						
						mediaPlayer.release();
						mediaPlayer = null;
						button.setText("Start playing song");
					}
				} catch(Exception ex) {
					Log.e(mainActivity, ex.getMessage());
				} finally {
					button.setClickable(true);
				}
			}
			

			@Override
			public void onClick(View v) {
				this.playMusicFromWeb("http://sound18.mp3slash.net/indian/3idiots/3idiots01%28www.songs.pk%29.mp3");
				
			}
		});
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		Log.v(mainActivity, "OnResume");
		this.getLocation();
		this.pluginTimer();
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
//		if (this.mediaPlayer != null) {
//			if (this.mediaPlayer.isPlaying()) {
//				this.mediaPlayer.stop();
//			}
//			
//			this.mediaPlayer.release();
//		}
		
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
	
	public void onClickGoto(View view) {
		// To start the activity without worrying about result
		//startActivity(new Intent("com.google.vikasproject.SecondaryActivity1"));
		//startActivity(new Intent(this, SecondaryActivity1.class));
		
		// To capture the return value, use the following:
		startActivityForResult(new Intent(this, SecondaryActivity1.class), request_code );
	}
	
	public void onActivityResult(int requestCode, int resultcode, Intent data) {
		if (data != null) {
			Toast.makeText(getBaseContext(), data.getData().toString(), Toast.LENGTH_LONG).show();
		} else {
			// If back is pressed, no data is returned.
			Toast.makeText(getBaseContext(), "No data is returned", Toast.LENGTH_LONG).show();
		}
	}
}
