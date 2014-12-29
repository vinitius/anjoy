package br.org.mantra.anjoy.listener;

public interface AsyncResultListener {

	void onSuccessfullyDataReceived(int operationCode,Object result);
	void onErrorCaught(int errorCode,Exception error, String message);

}
