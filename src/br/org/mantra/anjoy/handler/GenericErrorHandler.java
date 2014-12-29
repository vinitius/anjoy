package br.org.mantra.anjoy.handler;

import android.content.Context;
import android.os.Bundle;
import br.org.mantra.anjoy.R;
import br.org.mantra.anjoy.util.ConnectionUtils.HTTPStatus;
import br.org.mantra.anjoy.util.LogUtils;


public class GenericErrorHandler extends GreatErrorHandler {

	@Override
	public Bundle handleError(Context context,int errorCode, Exception exception, String message) {

		Bundle bundle = new Bundle();
		String title;
		String description;
		//String cause;
		String solution;

		//handling connection errors
		if (errorCode != 0){
			if (errorCode == HTTPStatus.NOT_FOUND){
				title = context.getResources().getString(R.string.Connection_Error_Title);
				description = context.getResources().getString(R.string.Connection_Error_Description);
				solution = context.getResources().getString(R.string.Connection_Error_Solution);
			}else if (errorCode == HTTPStatus.DENIED){
				title = context.getResources().getString(R.string.Denied_Error_Title);
				description = context.getResources().getString(R.string.Denied_Error_Description);
				solution = context.getResources().getString(R.string.Denied_Error_Solution);
			}else if (errorCode == HTTPStatus.SERVER_ERROR){
				title = context.getResources().getString(R.string.Server_Error_Title);
				description = context.getResources().getString(R.string.Server_Error_Description);
				solution = context.getResources().getString(R.string.Server_Error_Solution);
			}else{
				title = context.getResources().getString(R.string.Unknown_Error_Title);
				description = context.getResources().getString(R.string.Unknown_Error_Description);
				solution = context.getResources().getString(R.string.Unknown_Error_Solution);
			}


		}else{
			title = context.getResources().getString(R.string.Connection_Error_Title);
			description = context.getResources().getString(R.string.Connection_Error_Description);
			solution = context.getResources().getString(R.string.Connection_Error_Solution);
		}

		bundle.putString(GreatErrorHandler.ERROR_TITLE_BUNDLE_KEY, title);
		//bundle.putString(GreatErrorHandler.ERROR_CAUSE_BUNDLE_KEY, cause);
		bundle.putString(GreatErrorHandler.ERROR_DESCRIPTION_BUNDLE_KEY, description);
		bundle.putString(GreatErrorHandler.ERROR_SOLUTION_BUNDLE_KEY, solution);

		LogUtils.logErrorForAnjoy("StackTrace:");
		exception.printStackTrace();
		LogUtils.logErrorForAnjoy("Message:");
		LogUtils.logErrorForAnjoy(message);
		//LogUtils.logInfoForAnjoy("Possible Cause: "+cause);
		LogUtils.logInfoForAnjoy("Possible Solution: "+solution);



		return bundle;
	}




}
