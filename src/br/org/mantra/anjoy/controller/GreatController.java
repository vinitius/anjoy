package br.org.mantra.anjoy.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import br.org.mantra.anjoy.async.task.GreatAsyncTask;
import br.org.mantra.anjoy.listener.AsyncResultListener;
import br.org.mantra.anjoy.listener.ParseListener;
import br.org.mantra.anjoy.listener.ProgressListener;
import br.org.mantra.anjoy.model.MODEL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class GreatController<T> implements ParseListener {

	private ProgressListener mProgressListener;
	private AsyncResultListener mViewWaitingForAsyncResult;
	private MODEL mModel;
	private Gson mGSONParser;


	public GreatController() {

	}

	public void loadModel(MODEL modelInstance){
		this.mModel = modelInstance;

	}

	public  T getModel(){
		return (T)mModel;
	}

	public GreatController(ProgressListener progressListener, 
			AsyncResultListener asyncResultListener) {
		mGSONParser = new Gson();
		setProgressListener(progressListener);
		setViewWaitingForAsyncResult(asyncResultListener);

	}


	public void doWebRequest(int operationCode,Class<?> greatTaskClass,String...params){

		Constructor<?> constructor = null;
		try {
			constructor = greatTaskClass.getConstructor(ProgressListener.class,
					AsyncResultListener.class,
					ParseListener.class,int.class);
		} catch (NoSuchMethodException e) {			
			e.printStackTrace();
		}

		Object taskInstance = null;
		try {
			taskInstance = constructor.newInstance(getProgressListener(),
					getViewWaitingForAsyncResult(),
					this,operationCode);
		} catch (InstantiationException e) {			
			e.printStackTrace();
		} catch (IllegalAccessException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {			
			e.printStackTrace();
		} catch (InvocationTargetException e) {			
			e.printStackTrace();
		}

		((GreatAsyncTask)taskInstance).execute(params);




	}

	// To be implemented by each child
	@Override
	public Object onDataReceivedToParse(int operationCode, String data) throws Exception{

		return null;
	}

	public ProgressListener getProgressListener() {
		return mProgressListener;
	}

	public void setProgressListener(ProgressListener mProgressListener) {
		this.mProgressListener = mProgressListener;
	}

	public AsyncResultListener getViewWaitingForAsyncResult() {
		return mViewWaitingForAsyncResult;
	}

	public void setViewWaitingForAsyncResult(AsyncResultListener mViewWaitingForAsyncResult) {
		this.mViewWaitingForAsyncResult = mViewWaitingForAsyncResult;
	}

	public Gson getGSONParser() {
		return mGSONParser;
	}

	public void setGSONParserFromBuilder(GsonBuilder builder) {
		mGSONParser = builder.create();
	}

	public String getModelAsJSON(){
		return getGSONParser().toJson(getModel());
	}

	public MODEL getModelFromJSON(String json){
		return (MODEL)getGSONParser().fromJson(json, getModel().getClass());
	}


}
