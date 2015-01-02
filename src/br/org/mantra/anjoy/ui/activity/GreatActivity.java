package br.org.mantra.anjoy.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import de.keyboardsurfer.android.widget.crouton.Crouton;

public abstract class GreatActivity extends FragmentActivity {

	protected abstract int getRootViewContainer();

	public void replaceFragment(int containerId,Fragment fragment,boolean addToBackStack) {

		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().
				add(containerId, fragment);

		if (addToBackStack) fragmentTransaction.addToBackStack(null);

		fragmentTransaction.commitAllowingStateLoss();


	}

	public void renderView(int containerId,Fragment fragment,boolean shouldGoBack){
		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().
				add(containerId, fragment);

		if (shouldGoBack) fragmentTransaction.addToBackStack(null);

		fragmentTransaction.commitAllowingStateLoss();

	}

	public void renderView(Fragment fragment,boolean shouldGoBack){
		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().
				add(getRootViewContainer(), fragment);

		if (shouldGoBack) fragmentTransaction.addToBackStack(null);

		fragmentTransaction.commitAllowingStateLoss();

	}

	public void renderAndReplaceView(int containerId,Fragment fragment,boolean shouldGoBack){
		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().
				replace(containerId, fragment);

		if (shouldGoBack) fragmentTransaction.addToBackStack(null);

		fragmentTransaction.commitAllowingStateLoss();

	}

	public void renderAndReplaceView(Fragment fragment,boolean shouldGoBack){
		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().
				replace(getRootViewContainer(), fragment);

		if (shouldGoBack) fragmentTransaction.addToBackStack(null);

		fragmentTransaction.commitAllowingStateLoss();

	}


	public void removeView(Fragment fragment){
		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().remove(fragment);
		fragmentTransaction.commit();

	}

	public void goHome(){
		try{
			getSupportFragmentManager().popBackStack(
					getSupportFragmentManager().getBackStackEntryAt(1).getId(),FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}catch (Exception e ){
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		Crouton.cancelAllCroutons();
		super.onDestroy();
	}

}
