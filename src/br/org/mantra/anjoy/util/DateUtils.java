package br.org.mantra.anjoy.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.widget.TextView;


@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
public final class DateUtils {


	public static void setDate(TextView txtDate){
		SimpleDateFormat mDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
		String mStringDate="";
		mStringDate=mDateFormat.format(Calendar.getInstance().getTime());	
		txtDate.setText(mStringDate.toUpperCase(Locale.getDefault()));
	}

}
