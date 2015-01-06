package br.org.mantra.anjoy.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public abstract class ScreenUtils {


	public static void hideVirtualKeyboard(final View view) {

		if (view != null) {
			InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}


	public static void adjustWindowToPan(Activity activity) {

		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);		

	}

	public static void adjustWindowToResize(Activity activity) {

		if (activity != null) {
			activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		}
	}

	public static int getDipsFromPixel(Activity activity,float pixels)
	{
		// Get the screen's density scale
		final float scale = activity.getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	public static int getPixelsFromDips(Activity activity,float dips){
		Resources r = activity.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, r.getDisplayMetrics());
		return (int) px;

	}


}
