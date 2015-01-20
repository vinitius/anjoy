package br.org.mantra.anjoy.imageloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;
import br.org.mantra.anjoy.R;
import br.org.mantra.anjoy.util.ImageUtils;

public class ImageLoader {


	// Initialize MemoryCache
	MemoryCache memoryCache = new MemoryCache();

	FileCache fileCache;

	//Create Map (collection) to store image and image url in key value pair
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(
			new WeakHashMap<ImageView, String>());


	ExecutorService executorService;

	//handler to display images in UI thread
	Handler handler = new Handler();

	public ImageLoader(Context context){

		fileCache = new FileCache(context);

		// Creates a thread pool that reuses a fixed number of
		// threads operating off a shared unbounded queue.
		//executorService=Executors.newFixedThreadPool(5);
		executorService=Executors.newFixedThreadPool(20);
		this.stub_id = R.drawable.stub;


	}

	// default image show in list (Before online image download)
	int stub_id= R.drawable.stub;

	int requiredSize = 0;

	public boolean shouldGetRoundCorner = false;


	public String displayImageFromGallery(Uri data, ImageView imageView, Activity activity){
		Bitmap bitmap;
		Uri selectedImage = data;
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = activity.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();

		ExifInterface exif = null;
		try {
			exif = new ExifInterface(picturePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		File f = new File(picturePath);

		int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

		if (rotation != 0f) {
			bitmap = rotate(decodeFile(f),exifToDegrees(rotation));
		}else{
			bitmap = decodeFile(f);
		}



		imageView.setImageBitmap(bitmap);

		return picturePath;
	}


	public Boolean displayImageFromGallery(String path, ImageView imageView){
		Bitmap bitmap;
		String picturePath = path;

		ExifInterface exif = null;
		try {
			exif = new ExifInterface(picturePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		File f = new File(picturePath);

		int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

		if (rotation != 0f) {
			bitmap = rotate(decodeFile(f),exifToDegrees(rotation));
		}else{
			bitmap = decodeFile(f);
		}

		imageView.setImageBitmap(bitmap);

		if(bitmap!=null)
			return true;
		else
			return false;


	}


	public void displayImage(String url, ImageView imageView, int stub_id)
	{

		if (stub_id != 0)
			this.stub_id = stub_id;

		//Store image and url in Map
		imageViews.put(imageView, url);

		//Check image is stored in MemoryCache Map or not (MemoryCache.java)
		Bitmap bitmap = memoryCache.get(url);

		if(bitmap!=null){
			// if image is stored in MemoryCache Map then
			// Show image in listview row
			if (!shouldGetRoundCorner)
				imageView.setImageBitmap(bitmap);
			else
				imageView.setImageBitmap(ImageUtils.getRoundedCornerBitmap(bitmap,200));
		}
		else
		{
			//queue Photo to download from url
			queuePhoto(url, imageView);

			//Before downloading image show default image
			imageView.setImageResource(stub_id);
		}
	}

	public void displayImage(String url, ImageView imageView, int stub_id,int requiredSize)
	{

		if (stub_id != 0)
			this.stub_id = stub_id;

		this.requiredSize = requiredSize;

		//Store image and url in Map
		imageViews.put(imageView, url);

		//Check image is stored in MemoryCache Map or not (MemoryCache.java)
		Bitmap bitmap = memoryCache.get(url);

		if(bitmap!=null){
			// if image is stored in MemoryCache Map then
			// Show image in listview row
			imageView.setImageBitmap(bitmap);
		}
		else
		{
			//queue Photo to download from url
			queuePhoto(url, imageView);

			//Before downloading image show default image
			imageView.setImageResource(stub_id);
		}
	}



	private void queuePhoto(String url, ImageView imageView)
	{
		// Store image and url in PhotoToLoad object
		PhotoToLoad p = new PhotoToLoad(url, imageView);

		// pass PhotoToLoad object to PhotosLoader runnable class
		// and submit PhotosLoader runnable to executers to run runnable
		// Submits a PhotosLoader runnable task for execution

		executorService.submit(new PhotosLoader(p));
	}

	//Task for the queue
	private class PhotoToLoad
	{
		public String url;
		public ImageView imageView;
		public PhotoToLoad(String u, ImageView i){
			url=u;
			imageView=i;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad){
			this.photoToLoad=photoToLoad;
		}

		@Override
		public void run() {
			try{
				//Check if image already downloaded
				if(imageViewReused(photoToLoad))
					return;
				// download image from web url
				Bitmap bmp = getBitmap(photoToLoad.url);

				// set image data in Memory Cache
				memoryCache.put(photoToLoad.url, bmp);

				if(imageViewReused(photoToLoad))
					return;

				// Get bitmap to display
				BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);

				// Causes the Runnable bd (BitmapDisplayer) to be added to the message queue.
				// The runnable will be run on the thread to which this handler is attached.
				// BitmapDisplayer run method will call
				handler.post(bd);

			}catch(Throwable th){
				th.printStackTrace();
			}
		}
	}

	private Bitmap getBitmap(String url)
	{
		File f=fileCache.getFile(url);

		//from SD cache
		//CHECK : if trying to decode file which not exist in cache return null
		Bitmap b = decodeFile(f);
		if(b!=null)
			return b;

		// Download image file from web
		try {

			Bitmap bitmap=null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is=conn.getInputStream();

			// Constructs a new FileOutputStream that writes to file
			// if file not exist then it will create file
			OutputStream os = new FileOutputStream(f);

			// See Utils class CopyStream method
			// It will each pixel from input stream and
			// write pixels to output stream (file)
			Utils.CopyStream(is, os);

			os.close();
			conn.disconnect();

			//Now file created and going to resize file with defined height
			// Decodes image and scales it to reduce memory consumption
			bitmap = decodeFile(f);

			return bitmap;

		} catch (Throwable ex){
			ex.printStackTrace();
			if(ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return null;
		}
	}

	private Bitmap decodeFile(File f){

		try {

			//Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			FileInputStream stream1=new FileInputStream(f);
			BitmapFactory.decodeStream(stream1,null,o);
			stream1.close();

			//Find the correct scale value. It should be the power of 2.

			int scale=1;

			if (this.requiredSize > 0){

				// Set width/height of recreated image
				final int REQUIRED_SIZE=requiredSize;            

				int width_tmp=o.outWidth, height_tmp=o.outHeight;
				//int scale=1;
				while(true){
					if(width_tmp/2 < REQUIRED_SIZE || height_tmp/2 < REQUIRED_SIZE)
						break;
					width_tmp/=2;
					height_tmp/=2;
					scale*=2;
				}

			}

			//decode with current scale values
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			//o2.inSampleSize=scale;
			o2.inSampleSize=scale;
			FileInputStream stream2=new FileInputStream(f);
			Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();
			return bitmap;



		} catch (FileNotFoundException e) {
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	boolean imageViewReused(PhotoToLoad photoToLoad){

		String tag=imageViews.get(photoToLoad.imageView);
		//Check url is already exist in imageViews MAP
		if(tag==null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	//Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable
	{
		Bitmap bitmap;
		PhotoToLoad photoToLoad;
		public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}
		public void run()
		{
			if(imageViewReused(photoToLoad))
				return;

			// Show bitmap on UI
			if(bitmap!=null){
				if (!shouldGetRoundCorner)
					photoToLoad.imageView.setImageBitmap(bitmap);
				else
					photoToLoad.imageView.setImageBitmap(ImageUtils.getRoundedCornerBitmap(bitmap, 200));
			}
			else
				photoToLoad.imageView.setImageResource(stub_id);
		}
	}

	public void clearCache() {
		//Clear cache directory downloaded images and stored data in maps
		memoryCache.clear();
		fileCache.clear();
	}


	private static Bitmap rotate(Bitmap src, float degree) {
		// create new matrix
		Matrix matrix = new Matrix();
		// setup rotation degree
		matrix.postRotate(degree);

		// return new bitmap rotated using matrix
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
	}

	private static int exifToDegrees(int exifOrientation) {
		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
		return 0;
	}


	@SuppressLint("DefaultLocale") 
	public String getBase64FromBitmap(Bitmap bitmap, String extension){

		//Bitmap.CompressFormat.PNG 1
		//Bitmap.CompressFormat.JPEG 0
		extension = extension.toLowerCase();

		if (extension.equals("jpg"))
			extension = "jpeg";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.valueOf(extension.toUpperCase()), 100, baos);
		byte[] array = baos.toByteArray();

		return Base64.encodeToString(array,Base64.DEFAULT);

	}


	public Bitmap getBitmapFromPath(String path){
		Bitmap bitmap;
		String picturePath = path;

		ExifInterface exif = null;
		try {
			exif = new ExifInterface(picturePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		File f = new File(picturePath);

		int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

		if (rotation != 0f) {
			bitmap = rotate(decodeFile(f),exifToDegrees(rotation));
		}else{
			bitmap = decodeFile(f);
		}

		return bitmap;
	}

}
