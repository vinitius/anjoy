package br.org.mantra.anjoy.ui.fragment;

import android.os.Bundle;
import br.org.mantra.anjoy.controller.GreatController;
import br.org.mantra.anjoy.handler.GenericErrorHandler;
import br.org.mantra.anjoy.handler.GreatErrorHandler;
import br.org.mantra.anjoy.listener.AsyncResultListener;
import br.org.mantra.anjoy.ui.adapter.GreatAdapter;
import br.org.mantra.anjoy.util.MessageUtils;



public abstract class ControlledFragment extends GreatFragment implements AsyncResultListener {

	private GreatErrorHandler mErrorHandler;
	private GreatController mController;
	private GreatAdapter mAdapter;

	protected abstract GreatController onSetController();
	protected abstract GreatAdapter onSetAdapter();
	protected abstract GreatErrorHandler onSetErrorHandler();

	@Override
	protected void afterBindViews() {	
		super.afterBindViews();
		mController = onSetController();
		mAdapter = onSetAdapter();
		mErrorHandler = onSetErrorHandler() != null
				? onSetErrorHandler() : new GenericErrorHandler();




	}







	// To be implemented by each child
	@Override
	public void onSuccessfullyDataReceived(int operationCode, Object result) {

	}

	@Override
	public void onErrorCaught(int errorCode, Exception error, String message) {
		Bundle errorPrompt = getErrorHandler().handleError(mActivity, errorCode, error, message);
		MessageUtils.showAlertCrouton(mActivity, 
				errorPrompt.getString(GreatErrorHandler.ERROR_TITLE_BUNDLE_KEY)
				+"\n"+
				errorPrompt.getString(GreatErrorHandler.ERROR_DESCRIPTION_BUNDLE_KEY));
	}
	public GreatController getController() {
		return mController;
	}
	public GreatAdapter getAdapter() {
		return mAdapter;
	}
	public GreatErrorHandler getErrorHandler() {
		return mErrorHandler;
	}


}
