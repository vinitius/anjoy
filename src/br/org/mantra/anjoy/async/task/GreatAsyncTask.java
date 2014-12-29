package br.org.mantra.anjoy.async.task;

import java.io.IOException;

import android.os.AsyncTask;
import br.org.mantra.anjoy.connection.RestClient;
import br.org.mantra.anjoy.listener.AsyncResultListener;
import br.org.mantra.anjoy.listener.ParseListener;
import br.org.mantra.anjoy.listener.ProgressListener;

public class GreatAsyncTask extends AsyncTask<String,Void,String> {

	private ProgressListener mProgressListener;
	private AsyncResultListener mResultListener;
	private ParseListener mParseListener;
	private int mOperationCode;
	protected RestClient mRestClient;

	public GreatAsyncTask(ProgressListener progressListener,
			AsyncResultListener asyncResultListener, 
			ParseListener parseListener,
			int operationCode){

		this.mProgressListener = progressListener;
		this.mResultListener = asyncResultListener;
		this.mParseListener = parseListener;
		this.mOperationCode = operationCode;
		this.mRestClient = new RestClient(); //may or may not be used


	}

	@Override
	protected void onPreExecute() {	
		super.onPreExecute();

		if (mProgressListener != null)
			mProgressListener.onProgressStarted();
	}

	@Override
	protected void onPostExecute(String result) {	
		super.onPostExecute(result);

		if (mProgressListener != null)
			mProgressListener.onProgressFinished();

		//Will only process data if someone is waiting for the result (ResultListener != null)
		if (mResultListener != null){
			if (mParseListener != null){				
				try{ //Trying to successfully parse the upcoming data
					Object parsedData = mParseListener.onDataReceivedToParse(mOperationCode, result);
					mResultListener.onSuccessfullyDataReceived(mOperationCode, parsedData);
				}catch(Exception error){ //Something went wrong during the parse
					mResultListener.onErrorCaught(Integer.valueOf(result),error, error.getMessage());
				}
			}else if (mParseListener == null && result != null){ //If no parser is set and the result is valid, it should be sent directly to whom is waiting 
				mResultListener.onSuccessfullyDataReceived(mOperationCode, result);
			}else{ // Something went wrong retrieving the data
				IOException error = new IOException();
				mResultListener.onErrorCaught(0,error,error.getMessage());
			}
		}
	}

	// To be implemented by each child
	@Override
	protected String doInBackground(String... params) {		
		return null;
	}




}
