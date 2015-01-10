package br.org.mantra.anjoy.ui.fragment;

import android.os.Bundle;
import br.org.mantra.anjoy.handler.GenericErrorHandler;
import br.org.mantra.anjoy.handler.GreatErrorHandler;
import br.org.mantra.anjoy.listener.AsyncResultListener;
import br.org.mantra.anjoy.util.MessageUtils;



public abstract class ControlledFragment<A,C> extends GreatFragment implements AsyncResultListener {

	private GreatErrorHandler mErrorHandler;
	private C mController;
	private A mAdapter;

	protected abstract C onSetController();
	protected abstract A onSetAdapter();
	protected abstract GreatErrorHandler onSetErrorHandler();

	@Override
	protected void beforeBindViews(Bundle savedInstance) {			
		mController = onSetController();

	}

	@Override
	protected void afterBindViews() {			
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
	public C getController() {
		return mController;
	}
	public A getAdapter() {
		return mAdapter;
	}
	public GreatErrorHandler getErrorHandler() {
		return mErrorHandler;
	}


}
