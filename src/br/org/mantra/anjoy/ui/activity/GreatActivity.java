package br.org.mantra.anjoy.ui.activity;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class GreatActivity extends FragmentActivity {

	public void replaceFragment(int containerId,Fragment fragment,boolean addToBackStack) {

		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().
				replace(containerId, fragment);

		if (addToBackStack) fragmentTransaction.addToBackStack(null);

		fragmentTransaction.commitAllowingStateLoss();


	}


	public void removeFragment(Fragment fragment){
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
