package br.org.mantra.anjoy.controller;

import br.org.mantra.anjoy.async.task.GreatAsyncTask;
import br.org.mantra.anjoy.listener.ParseListener;

public class GreatController implements ParseListener {



	public void doWebRequest(GreatAsyncTask task, String...params){
		if (params != null)
			task.execute(params);
		else
			task.execute();		
	}

	// To be implemented by each child
	@Override
	public Object onDataReceivedToParse(int operationCode, String data) {

		return null;
	}

}
