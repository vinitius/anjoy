package br.org.mantra.anjoy.util;

import android.util.Log;

public final class LogUtils {

	public final static String ANJOY_LOG_TAG = "###Anjoy###";

	public final static void logInfoForAnjoy(String messageToBeLogged){
		Log.i(ANJOY_LOG_TAG, messageToBeLogged);
	}

	public final static void logErrorForAnjoy(String messageToBeLogged){
		Log.e(ANJOY_LOG_TAG, messageToBeLogged);
	}


}
