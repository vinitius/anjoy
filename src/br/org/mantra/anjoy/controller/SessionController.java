package br.org.mantra.anjoy.controller;

import br.org.mantra.anjoy.listener.AsyncResultListener;
import br.org.mantra.anjoy.listener.ProgressListener;
import br.org.mantra.anjoy.model.MODEL;

public abstract class SessionController extends GreatController{

	public SessionController(ProgressListener progressListener,
			AsyncResultListener asyncResultListener, MODEL modelInstance) {
		super(progressListener, asyncResultListener, modelInstance);		
	}
	public abstract void onLogin();
	public abstract void onLogout();
	public abstract void onRestoreSession();
	public abstract void onClearSession();






}
