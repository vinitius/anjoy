package br.org.mantra.anjoy.ui.fragment;

import android.content.Intent;
import br.org.mantra.anjoy.controller.GreatController;
import br.org.mantra.anjoy.controller.SessionController;
import br.org.mantra.anjoy.handler.GreatErrorHandler;
import br.org.mantra.anjoy.model.USER;
import br.org.mantra.anjoy.session.ControlledSession;
import br.org.mantra.anjoy.ui.adapter.GreatAdapter;

public abstract class LoginFragment extends ControlledFragment{

	private Class<?> mActivityToStartAfterAuthentication;

	protected void doLogin(USER user){
		getControlledSession().set(ControlledSession.SESSION_CONTROLLER, getSessionController());
		getSessionController().loadModel(user);
		getSessionController().onLogin();		
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

	protected abstract SessionController getSessionController();
	protected abstract ControlledSession getControlledSession();
	
	@Override
	protected GreatController onSetController() {	
		return null;
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
