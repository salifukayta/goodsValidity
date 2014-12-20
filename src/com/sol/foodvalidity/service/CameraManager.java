package com.sol.foodvalidity.service;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.sol.foodvalidity.activity.food.AbstractCameraActivity;
import com.sol.foodvalidity.utils.DateUtils;

public class CameraManager {

	private static final String APP_UNIQUE_PARAPHE = "EFBR_";
	private static CameraManager CURRENT_INSTANCE;
	// Activity result key for camera
	public static final int REQUEST_TAKE_PHOTO = 11111;

	private static String IMAGE_EXTENTION = ".jpg";
	private static String DEFAULT_CUSTOM_FORMATTER = "yyyyMMdd_HHmmss";
	private static String picturePath;
	
	public static CameraManager getInstance() {
		if (CURRENT_INSTANCE == null) {
			synchronized (CameraManager.class) {
				if (CURRENT_INSTANCE == null) {
					CURRENT_INSTANCE = new CameraManager();
				}
			}
		}		
		return CURRENT_INSTANCE;
	}
	
	static public void openCamera(AbstractCameraActivity currentActivity) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if (takePictureIntent.resolveActivity(currentActivity.getPackageManager()) != null) {
			// Create the File where the photo should go. 
			File photoFile = null; 
			try { 
				photoFile = createImageFile(); 
			} 
			catch (IOException ex) { 
				Toast.makeText(currentActivity.getApplicationContext(), currentActivity.getErrorCreatingFilePicture(), 
						Toast.LENGTH_SHORT).show(); 
			} 
			// Continue only if the File was successfully created 
			if (photoFile != null) { 
				Uri fileUri = Uri.fromFile(photoFile); 
				picturePath = fileUri.getPath(); 
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); 
				currentActivity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO); 
			}
//			Read more at http://www.airpair.com/android/taking-pictures-android-fragment-intents#kyd01XHq6OSF0L89.99
		}
	}
	
	/**
     * Creates the image file to which the image must be saved.
     * @return
     * @throws IOException
     */
    protected static File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = APP_UNIQUE_PARAPHE + DateUtils.custumDateFormatter(Calendar.getInstance(), DEFAULT_CUSTOM_FORMATTER);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                IMAGE_EXTENTION,         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        picturePath = "file:" + image.getAbsolutePath();
        return image;
    }
    
    /**
     * Add the picture to the photo gallery.
     * Must be called on all camera images or they will
     * disappear once taken.
     */
    protected void addPhotoToGallery(Activity currentActivity) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(picturePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        currentActivity.sendBroadcast(mediaScanIntent);
    }

	public static String getPicturePath() {
		return picturePath;
	}

	public static String getIMAGE_EXTENTION() {
		return IMAGE_EXTENTION;
	}

	public static void setIMAGE_EXTENTION(String iMAGE_EXTENTION) {
		IMAGE_EXTENTION = iMAGE_EXTENTION;
	}

	public static String getDEFAULT_CUSTOM_FORMATTER() {
		return DEFAULT_CUSTOM_FORMATTER;
	}

	public static void setDEFAULT_CUSTOM_FORMATTER(String dEFAULT_CUSTOM_FORMATTER) {
		DEFAULT_CUSTOM_FORMATTER = dEFAULT_CUSTOM_FORMATTER;
	}
    
}
