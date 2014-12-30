package br.org.mantra.anjoy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public abstract class SplashScreenActivity extends GreatActivity{

	public abstract int getSplashResource();
	public abstract int getSplashDurationInMilliseconds();
	public abstract Class<?> getNextActivityToGoTo();

	@Override
	protected void onCreate(Bundle arg0) {	
		super.onCreate(arg0);

		setContentView(getSplashResource());	

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent i = new Intent(SplashScreenActivity.this, getNextActivityToGoTo());
				startActivity(i);
				finish();

			}
		}, getSplashDurationInMilliseconds());
	}

}
