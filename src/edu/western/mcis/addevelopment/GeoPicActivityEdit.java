package edu.western.mcis.addevelopment;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

public class GeoPicActivityEdit extends Activity {

  Bundle extras;
  Uri imageUri;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.edit_pic);

    try {
      extras = getIntent().getExtras();
      imageUri = Uri.parse(extras.getString("imageUri"));

      Bitmap pic = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

      ImageView image = (ImageView) findViewById(R.id.imageView1);

      image.setImageBitmap(pic);

    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NullPointerException e){
      e.printStackTrace();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.edit_menu, menu);
    return true;

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
    case R.id.save_pic:
      // save stuff and return to previous activity
      try{
        
        Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        GeoPicItemizedOverlay itemizedoverlay = new GeoPicItemizedOverlay(drawable, this);
        int lat = extras.getInt("lat");
        int lon = extras.getInt("lon");
        Uri contentUri = MediaStore.Images.Media.getContentUri(imageUri.toString());
        
        
        Cursor mCursor = MediaStore.Images.Media.query(getContentResolver(), contentUri, null);
        
        System.out.println(mCursor.toString());
        
        
        
        
        
        
        
        
        
        System.out.println(contentUri.toString());
        
        GeoPoint point = new GeoPoint(lat, lon);
        OverlayItem overlayitem = new OverlayItem(point, "" , "");

        itemizedoverlay.addOverlay(overlayitem);
        GeoPicActivity.mapOverlays.add(itemizedoverlay);
        
      }catch(Exception e){
        e.printStackTrace();
      }
      // create new overlay
      finish(); // may need to add setResult before this line to return
                // something
      return true;
    case R.id.cancel_edit_pic:
      // return to previous activity
      finish(); // may need to add setResult before this line to return
                // something
      return true;
    default:
      return super.onOptionsItemSelected(item);
    }
  }

}
