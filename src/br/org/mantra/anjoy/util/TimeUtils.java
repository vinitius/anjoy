package br.org.mantra.anjoy.util;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.widget.TextView;

public final class TimeUtils {

	private static String sTimeDivider;

	public static void setTime(final TextView txtTime,final Activity activity){

		sTimeDivider = (Calendar.getInstance().get(Calendar.MINUTE) < 10 )
				? ":0" : ":";														
		txtTime.setText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
				+sTimeDivider+Calendar.getInstance().get(Calendar.MINUTE));

		Timer timer = new Timer();		 
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {	

				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						sTimeDivider = (Calendar.getInstance().get(Calendar.MINUTE) < 10 )
								? ":0" : ":";														
						txtTime.setText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
								+sTimeDivider+Calendar.getInstance().get(Calendar.MINUTE));							
					}
				});

			}

		};
		timer.schedule(timerTask,1000,1000);   



	} 



}
