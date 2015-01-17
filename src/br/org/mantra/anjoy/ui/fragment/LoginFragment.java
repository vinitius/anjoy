package br.org.mantra.anjoy.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import br.org.mantra.anjoy.controller.SessionController;
import br.org.mantra.anjoy.handler.GreatErrorHandler;
import br.org.mantra.anjoy.model.USER;
import br.org.mantra.anjoy.session.ControlledSession;

public abstract class LoginFragment extends ControlledFragment<Void,SessionController>{

	private Class<?> mActivityToStartAfterAuthentication;
	private SessionController mSessionController;

	protected void doLogin(USER user){
		getControlledSession().set(ControlledSession.SESSION_CONTROLLER, getSessionController());
		mSessionController.loadModel(user);
		mSessionController.onLogin();		
	}

	@Override
	protected void beforeBindViews(Bundle savedInstance) {	
		mSessionController = getSessionController();
		super.beforeBindViews(savedInstance);

	}

	protected void onSuccessfullAuthentication(USER loggedUser){
		ControlledSession.getInstance().set(ControlledSession.SESSION_CONTROLLER, getController());
		ControlledSession.getInstance().set(ControlledSession.CURRENT_LOGGED_USER, loggedUser);
		Intent i = new Intent(mActivity, getActivityToStartAfterAuthentication());		
		startActivity(i);
		mActivity.finish();
	}

	public Class<?> getActivityToStartAfterAuthentication() {
		return mActivityToStartAfterAuthentication;
	}

	public void setActivityToStartAfterAuthentication(
			Class<?> mActivityToStartAfterAuthentication) {
		this.mActivityToStartAfterAuthentication = mActivityToStartAfterAuthentication;
	}

	@Override
	public void onSuccessfullyDataReceived(int operationCode, Object result) {	
		super.onSuccessfullyDataReceived(operationCode, result);
		onSuccessfullAuthentication((USER)result);
	}

	protected abstract SessionController getSessionController();
	protected abstract ControlledSession getControlledSession();

	@Override
	protected SessionController onSetController() {	
		return mSessionController;
	}

	@Override
	protected Void onSetAdapter() {	
		return null;
	}

	@Override
	protected GreatErrorHandler onSetErrorHandler() {	
		return null;
	}


}
