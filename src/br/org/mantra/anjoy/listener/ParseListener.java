package br.org.mantra.anjoy.listener;

public interface ParseListener {
	
	Object onDataReceivedToParse(int operationCode, String data) throws Exception;		
	

}
