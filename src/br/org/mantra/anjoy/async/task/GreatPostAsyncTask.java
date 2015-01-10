package br.org.mantra.anjoy.async.task;

import br.org.mantra.anjoy.connection.RestClient.RequestMethod;
import br.org.mantra.anjoy.listener.AsyncResultListener;
import br.org.mantra.anjoy.listener.ParseListener;
import br.org.mantra.anjoy.listener.ProgressListener;

public abstract class GreatPostAsyncTask extends GreatAsyncTask{



	public GreatPostAsyncTask(ProgressListener progressListener,
			AsyncResultListener asyncResultListener,
			ParseListener parseListener, int operationCode) {
		super(progressListener, asyncResultListener, parseListener, operationCode);

	}

	@Override
	protected RequestMethod getRequestMethod() {		
		return RequestMethod.POST;
	}

	@Override
	protected boolean shouldIgnoreExecutionParams() {		
		return true;
	}

	@Override
	protected String doInBackground(String... params) {

		StringBuffer buffer = new StringBuffer();
		for(String s:params){
			buffer.append(s);			
		}		
		mRestClient.addPostParams(buffer.toString());		
		return super.doInBackground(params);
	}

}
