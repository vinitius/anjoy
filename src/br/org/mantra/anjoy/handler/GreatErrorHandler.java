package br.org.mantra.anjoy.handler;

import android.content.Context;
import android.os.Bundle;

public abstract class GreatErrorHandler {
	
	public final static String ERROR_TITLE_BUNDLE_KEY = "ERROR_TITLE";
	public final static String ERROR_DESCRIPTION_BUNDLE_KEY = "ERROR_DESCRIPTION";
	public final static String ERROR_SOLUTION_BUNDLE_KEY = "ERROR_SOLUTION";
	public final static String ERROR_CAUSE_BUNDLE_KEY = "ERROR_CAUSE";

	public abstract Bundle handleError(Context context,int errorCode, Exception exception, String message);


}
