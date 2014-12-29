package br.org.mantra.anjoy.controller;

import br.org.mantra.anjoy.listener.AsyncResultListener;
import br.org.mantra.anjoy.listener.ProgressListener;
import br.org.mantra.anjoy.model.USER;
import br.org.mantra.anjoy.ui.fragment.LoginFragment;

public abstract class SessionController extends GreatController{

	public abstract USER onLogin(LoginFragment loginFragment, ProgressListener progressListener);
	public abstract void onLogout(AsyncResultListener resultListener, ProgressListener progressListener);
	public abstract void onRestoreSession();
	public abstract void onClearSession();






}
