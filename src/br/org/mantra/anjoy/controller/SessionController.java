package br.org.mantra.anjoy.controller;

import android.app.Activity;
import br.org.mantra.anjoy.listener.AsyncResultListener;
import br.org.mantra.anjoy.listener.ProgressListener;
import br.org.mantra.anjoy.model.USER;

public abstract class SessionController extends GreatController<USER>{

	public SessionController(){
		super(null, null);
	}

	public SessionController(ProgressListener progressListener,
			AsyncResultListener asyncResultListener) {
		super(progressListener, asyncResultListener);		
	}
	public abstract void onLogin();
	public abstract void onLogout();
	public abstract void onRestoreSession(Activity currentTask);
	public abstract void onClearSession();






}
