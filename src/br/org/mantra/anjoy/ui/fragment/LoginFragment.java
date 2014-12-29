package br.org.mantra.anjoy.ui.fragment;

import android.content.Intent;
import br.org.mantra.anjoy.controller.SessionController;
import br.org.mantra.anjoy.listener.ProgressListener;
import br.org.mantra.anjoy.model.USER;
import br.org.mantra.anjoy.session.ControlledSession;

public class LoginFragment extends ControlledFragment{

	private Class<?> mActivityToStartAfterAuthentication;


	@Override
	protected void beforeBindViews() {		
		super.beforeBindViews();
		mController = (SessionController)
				ControlledSession.getInstance().get(ControlledSession.SESSION_CONTROLLER);
	}

	protected void doLogin(LoginFragment loginFragment, ProgressListener progressListener){		
		((SessionController)mController).onLogin(loginFragment,progressListener);		
	}

	protected void onSuccessfullAuthentication(USER loggedUser){
		ControlledSession.getInstance().set(ControlledSession.CURRENT_LOGGED_USER, loggedUser);
		Intent i = new Intent(mActivity, getActivityToStartAfterAuthentication());		
		startActivity(i);
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


}
