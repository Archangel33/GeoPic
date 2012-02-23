package edu.western.mcis.addevelopment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GeoPicActivity extends MapActivity {
  private static final String LOG_TAG = null;
  private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
  private static final int GALLERY_PICKER_ACTIVITY_REQUEST_CODE = 1;
  private static final int EDIT_PIC_ACTIVITY_REQUEST_CODE = 2;
  private static Uri imageUri;
  public static List<Overlay> mapOverlays;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    

    MapView mapView = (MapView) findViewById(R.id.mapview1);
    mapView.setBuiltInZoomControls(true);
    mapView.setSatellite(true);

    mapOverlays = mapView.getOverlays();
    Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
    GeoPicItemizedOverlay itemizedoverlay = new GeoPicItemizedOverlay(drawable, GeoPicActivity.this);

    GeoPoint point = new GeoPoint(19240000, -99120000);
    OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");

    GeoPoint point2 = new GeoPoint(35410000, 139460000);
    OverlayItem overlayitem2 = new OverlayItem(point2, "Sekai, konichiwa!", "I'm in Japan!");

    itemizedoverlay.addOverlay(overlayitem);
    itemizedoverlay.addOverlay(overlayitem2);
    mapOverlays.add(itemizedoverlay);

    location();

  }

  public void location() {

    // Acquire a reference to the system Location Manager
    LocationManager locationManager = (LocationManager) this
        .getSystemService(Context.LOCATION_SERVICE);

    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
      public void onLocationChanged(Location location) {
        // Called when a new location is found by the network location provider.
        makeUseOfNewLocation(location);
      }

      private void makeUseOfNewLocation(Location location) {
        // TODO Auto-generated method stub
        System.out.println("New Location!");
        

      }

      public void onStatusChanged(String provider, int status, Bundle extras) {
      }

      public void onProviderEnabled(String provider) {
      }

      public void onProviderDisabled(String provider) {
      }
    };

    // get the location Provider you wish to use .NETWORK_PROVIDER or
    // .GPS_PROVIDER
    String locationProvider = LocationManager.GPS_PROVIDER;

    // get a cached location for quick initial location
     Location lastKnownLocation =
     locationManager.getLastKnownLocation(locationProvider);

    // Register the listener with the Location Manager to receive location
    // updates
    locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
    
    // once you get the info you need stop looking for updates (saves better)
    // Remove the listener you previously added
    // locationManager.removeUpdates(locationListener);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
    case R.id.new_pic:
      newPic();
      return true;
    case R.id.gallery:
      showGallery();
      return true;
    default:
      return super.onOptionsItemSelected(item);
    }
  }

  private void showGallery() {
    // TODO Auto-generated method stub
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");
    startActivityForResult(intent, GALLERY_PICKER_ACTIVITY_REQUEST_CODE);

  }
  
  private void newPic() {
    // get date time of when photo was taken
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    //get current date time with Calendar()
    Calendar cal = Calendar.getInstance();
    String dateTime = dateFormat.format(cal.getTime());
    // define the file-name to save photo taken by Camera activity
    String fileName = dateTime + ".jpg";
    // create parameters for Intent with filename
    ContentValues values = new ContentValues();
    values.put(MediaStore.Images.Media.TITLE, fileName);
    values.put(MediaStore.Images.Media.DATE_TAKEN, cal.getTimeInMillis());
    values.put(MediaStore.Images.Media.LATITUDE, 1);
    values.put(MediaStore.Images.Media.LONGITUDE, 1);
    
    imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    // create new Intent
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
     // use imageUri here to access the image
        
        System.out.println(imageUri);
        
        System.out.println("pic!");
        // add location to picture here
        // add orientation to picture here
        
        Intent intent = new Intent(GeoPicActivity.this, GeoPicActivityEdit.class);
        intent.putExtra("imageUri", imageUri.toString());
        startActivity(intent);
        
        
      } else if (resultCode == RESULT_CANCELED) {
        Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT);
      } else {
        Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT);
      }
    } else if (requestCode == GALLERY_PICKER_ACTIVITY_REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
        // need to figure out how to get access to image data or what to do once
        // image has been selected.
        System.out.println("gallery finished");

        Intent intent = new Intent(GeoPicActivity.this, GeoPicViewOverlay.class);
        startActivity(intent);

      } else if (resultCode == RESULT_CANCELED) {
        Toast.makeText(this, "No picture selected!", Toast.LENGTH_SHORT);
      } else {
        Toast.makeText(this, "No picture selected!", Toast.LENGTH_SHORT);
      }
    } else if (requestCode == EDIT_PIC_ACTIVITY_REQUEST_CODE){
      if (resultCode == RESULT_OK) {
        System.out.println("edit finished");
        Intent intent = new Intent(GeoPicActivity.this, GeoPicViewOverlay.class);
        startActivity(intent);
        
      } else if (resultCode == RESULT_CANCELED) {
        Toast.makeText(this, "Pic not saved!", Toast.LENGTH_SHORT);
      } else {
        Toast.makeText(this, "Pic not saved!", Toast.LENGTH_SHORT);
      }
    }

  }

  @Override
  protected boolean isRouteDisplayed() {
    // TODO Auto-generated method stub
    return false;
  }

}