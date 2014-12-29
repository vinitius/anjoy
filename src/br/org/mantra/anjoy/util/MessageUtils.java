package br.org.mantra.anjoy.util;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public final class MessageUtils {

	public static void showToast(final Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static void showToast(final Context context,String title, String msg) {
		Toast.makeText(context, title+"\n"+msg, Toast.LENGTH_LONG).show();
	}

	public static void showAlertCrouton(Activity context,String message){
		Crouton.makeText(context, message, Style.ALERT).show();
	}

	public static void showInfoCrouton(Activity context,String message){
		Crouton.makeText(context, message, Style.INFO).show();
	}

	public static void showConfirmationCrouton(Activity context,String message){
		Crouton.makeText(context, message, Style.CONFIRM).show();
	}
}
