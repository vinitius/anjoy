package br.org.mantra.anjoy.ui.fragment;

import android.os.Bundle;
import br.org.mantra.anjoy.controller.GreatController;
import br.org.mantra.anjoy.handler.GenericErrorHandler;
import br.org.mantra.anjoy.handler.GreatErrorHandler;
import br.org.mantra.anjoy.listener.AsyncResultListener;
import br.org.mantra.anjoy.ui.adapter.GreatAdapter;
import br.org.mantra.anjoy.util.MessageUtils;



public class ControlledFragment extends GreatFragment implements AsyncResultListener {

	protected GreatErrorHandler mErrorHandler = new GenericErrorHandler();
	protected GreatController mController;
	protected GreatAdapter mAdapter;


	// To be implemented by each child
	@Override
	public void onSuccessfullyDataReceived(int operationCode, Object result) {

	}

	@Override
	public void onErrorCaught(int errorCode, Exception error, String message) {
		Bundle errorPrompt = mErrorHandler.handleError(mActivity, errorCode, error, message);
		MessageUtils.showAlertCrouton(mActivity, 
				errorPrompt.getString(GreatErrorHandler.ERROR_TITLE_BUNDLE_KEY)
				+"\n"+
				errorPrompt.getString(GreatErrorHandler.ERROR_DESCRIPTION_BUNDLE_KEY));
	}


}
