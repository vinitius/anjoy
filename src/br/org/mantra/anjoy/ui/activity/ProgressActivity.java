package br.org.mantra.anjoy.ui.activity;

import android.support.v4.app.FragmentTransaction;
import br.org.mantra.anjoy.listener.ProgressListener;
import br.org.mantra.anjoy.ui.dialog.ProgressDialogFragment;


public abstract class ProgressActivity extends GreatActivity implements ProgressListener {
	
	ProgressDialogFragment mProgressDialog;

	@Override
	public void onProgressStarted() {
		
		if (mProgressDialog == null || mProgressDialog.isVisible()) {
			mProgressDialog = new ProgressDialogFragment();
			mProgressDialog.show(getSupportFragmentManager(), "");

		}

		
	}

	@Override
	public void onProgressFinished() {
		if (mProgressDialog != null && mProgressDialog.getDialog()!=null) {
			final FragmentTransaction fragmentTrans = getSupportFragmentManager()
					.beginTransaction();
			fragmentTrans.remove(mProgressDialog).commitAllowingStateLoss();
			mProgressDialog = null;
		}
		
	}
	
	

}
