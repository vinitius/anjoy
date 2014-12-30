package br.org.mantra.anjoy.ui.fragment;

import android.content.Intent;
import br.org.mantra.anjoy.controller.GreatController;
import br.org.mantra.anjoy.controller.SessionController;
import br.org.mantra.anjoy.handler.GreatErrorHandler;
import br.org.mantra.anjoy.listener.ProgressListener;
import br.org.mantra.anjoy.model.USER;
import br.org.mantra.anjoy.session.ControlledSession;
import br.org.mantra.anjoy.ui.adapter.GreatAdapter;

public class LoginFragment extends ControlledFragment{

	private Class<?> mActivityToStartAfterAuthentication;

	protected void doLogin(LoginFragment loginFragment, ProgressListener progressListener,String...loginData){		
		((SessionController)getController()).onLogin(loginFragment,progressListener,loginData);		
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

	@Override
	protected GreatController onSetController() {		
		return (SessionController)
				ControlledSession.getInstance().get(ControlledSession.SESSION_CONTROLLER);
	}

	@Override
	protected GreatAdapter onSetAdapter() {		
		return null;
	}

	@Override
	protected GreatErrorHandler onSetErrorHandler() {		
		return null;
	}


}
