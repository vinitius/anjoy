package br.org.mantra.anjoy.async.task;

import br.org.mantra.anjoy.connection.RestClient.RequestMethod;
import br.org.mantra.anjoy.listener.AsyncResultListener;
import br.org.mantra.anjoy.listener.ParseListener;
import br.org.mantra.anjoy.listener.ProgressListener;

public abstract class GreatGetAsyncTask extends GreatAsyncTask{


	public GreatGetAsyncTask(ProgressListener progressListener,
			AsyncResultListener asyncResultListener,
			ParseListener parseListener, int operationCode) {
		super(progressListener, asyncResultListener, parseListener, operationCode);
		
	}

	@Override
	protected RequestMethod getRequestMethod() {		
		return RequestMethod.GET;
	}

	@Override
	protected boolean shouldIgnoreExecutionParams() {		
		return false;
	}

}
