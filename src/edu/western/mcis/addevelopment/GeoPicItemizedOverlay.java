package edu.western.mcis.addevelopment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class GeoPicItemizedOverlay extends ItemizedOverlay {

  private static final int DIALOG_ID_TAP = 0;
  private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
  private Context mContext;

  public GeoPicItemizedOverlay(Drawable defaultMarker, Context context) {
    super(boundCenterBottom(defaultMarker));
    mContext = context;
    // TODO Auto-generated constructor stub
  }

  protected boolean onTap(int index) {
    // OverlayItem item = mOverlays.get(index);
    // AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
    // dialog.setTitle(item.getTitle());
    // dialog.setMessage(item.getSnippet());
    // dialog.show();

    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    // set alertDialog specific attributes 
    builder.setTitle("GeoPic Image");// get from strings
    builder.setIcon(R.drawable.androidmarker); // change to image
//    builder.setMessage("Are you sure you want to exit?"); // change message
    builder.setCancelable(false);
    
    // set the text and actions for the summary view buttons (limit 3)
    builder.setPositiveButton("View More!", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        Intent intent = new Intent(mContext, GeoPicViewOverlay.class);
        mContext.startActivity(intent);
      }
    });
    builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        Intent intent = new Intent(mContext, GeoPicActivityEdit.class);
        mContext.startActivity(intent);
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();
      }
    });

    // get the inflater for the body of the alert layout
    LayoutInflater inflater = (LayoutInflater) mContext
        .getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    
    // inflate the layout and store it for later
    View layout = inflater.inflate(R.layout.dialog_overlay_tap,
        (ViewGroup) ((Activity) mContext).findViewById(R.id.layout_root));

    // add values for the summary view of the GeoPic
    TextView text = (TextView) layout.findViewById(R.id.dialog_overlay_tap_text);
    text.setText("Hello, this is a custom dialog!");// get from strings
    
    
    // set the layout from the dialog xml file
    builder.setView(layout);
    
    // create the alert dialog and show it!
    AlertDialog alert = builder.create();
    alert.show();
    System.out.println("Tap!");

    // Intent intent = new Intent(mContext, GeoPicViewOverlay.class);
    // mContext.startActivity(intent);

    return true;
  }

  protected void onPrepareDialog(int id, Dialog dialog) {

  }

  public void addOverlay(OverlayItem overlay) {
    mOverlays.add(overlay);
    populate();
  }

  @Override
  protected OverlayItem createItem(int i) {
    return mOverlays.get(i);
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return mOverlays.size();
  }
  
  public void changeMarker(Drawable marker){
    
  
  }

}
