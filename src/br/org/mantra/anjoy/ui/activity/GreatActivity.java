package br.org.mantra.anjoy.ui.activity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import br.org.mantra.anjoy.ui.fragment.GreatFragment;
import de.keyboardsurfer.android.widget.crouton.Crouton;

public abstract class GreatActivity extends FragmentActivity {


	private View mViewToInflate;	
	private HashMap<String, Integer> mViewTagHash;	
	private String mCurrentFragmentTag;
	private int[] mFragmentTransitionAnimations = {0,0}; 

	protected abstract void beforeBindViews(Bundle savedInstance);
	protected abstract void afterBindViews();
	protected abstract void onSetListeners();
	protected abstract int getLayoutToBeInflated();	
	protected abstract int getRootViewContainer();



	protected void doWhenActivityIsReady(){
		mountViewHashTag();
		bindViews();
		afterBindViews();
		onSetListeners();

	}

	@Override
	protected void onCreate(Bundle arg0) {			
		super.onCreate(arg0);
		beforeBindViews(arg0);		
		setContentView(getLayoutToBeInflated());
		mViewToInflate = this.findViewById(android.R.id.content);
		doWhenActivityIsReady();

	}

	public void replaceFragment(int containerId,Fragment fragment,boolean addToBackStack) {

		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().
				add(containerId, fragment);

		if (addToBackStack) fragmentTransaction.addToBackStack(null);

		fragmentTransaction.commitAllowingStateLoss();


	}

	public void renderView(int containerId,Fragment fragment,boolean shouldGoBack){
		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().setCustomAnimations(getFragmentTransitionAnimations()[0],
						getFragmentTransitionAnimations()[1]).
						add(containerId, fragment,fragment.getClass().getSimpleName());

		if (shouldGoBack){ 
			fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
			((GreatFragment)fragment).setTagToGoBack(getCurrentFragmentTag());
		}


		fragmentTransaction.commit();	


	}

	public void renderView(Fragment fragment,boolean shouldGoBack){	

		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().setCustomAnimations(getFragmentTransitionAnimations()[0],
						getFragmentTransitionAnimations()[1]).
						add(getRootViewContainer(),fragment,fragment.getClass().getSimpleName());

		if (shouldGoBack){ 
			fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
			((GreatFragment)fragment).setTagToGoBack(getCurrentFragmentTag());	
		}




		fragmentTransaction.commit();








	}

	public void renderAndReplaceView(int containerId,Fragment fragment,boolean shouldGoBack){
		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().setCustomAnimations(getFragmentTransitionAnimations()[0],
						getFragmentTransitionAnimations()[1]).
						replace(containerId, fragment,fragment.getClass().getSimpleName());

		if (shouldGoBack) fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());


		fragmentTransaction.commitAllowingStateLoss();

	}

	public void renderAndReplaceView(Fragment fragment,boolean shouldGoBack){
		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().setCustomAnimations(getFragmentTransitionAnimations()[0],
						getFragmentTransitionAnimations()[1]).
						replace(getRootViewContainer(), fragment,fragment.getClass().getSimpleName());


		if (shouldGoBack) fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());



		fragmentTransaction.commitAllowingStateLoss();


	}


	public void removeView(Fragment fragment){
		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().remove(fragment);
		fragmentTransaction.commit();

	}

	public void hideView(String tag){
		Fragment fragmentToHide = getSupportFragmentManager().findFragmentByTag(tag);
		if (fragmentToHide != null){
			FragmentTransaction fragmentTransaction = 
					getSupportFragmentManager().beginTransaction().hide(fragmentToHide);
			fragmentTransaction.commit();
		}

	}

	public void showView(String tag){		
		Fragment fragmentToShow = getSupportFragmentManager().findFragmentByTag(tag);
		if (fragmentToShow != null){			
			FragmentTransaction fragmentTransaction = 
					getSupportFragmentManager().beginTransaction().show(fragmentToShow);
			fragmentTransaction.commit();
			setCurrentFragmentTag(fragmentToShow.getTag());
		}

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



	private void mountViewHashTag(){


		if (mViewTagHash == null)
			mViewTagHash = new HashMap<String, Integer>();

		List<View> visited = new ArrayList<View>();
		List<View> unvisited = new ArrayList<View>();
		unvisited.add(mViewToInflate);
		while (!unvisited.isEmpty()) {
			View child = unvisited.remove(0);
			visited.add(child);
			if (child.getTag() != null)
				mViewTagHash.put(child.getTag().toString(), child.getId());
			if (!(child instanceof ViewGroup)) continue;
			ViewGroup group = (ViewGroup) child;
			final int childCount = group.getChildCount();
			for (int i=0; i<childCount; i++) unvisited.add(group.getChildAt(i));
		}

	}

	private void bindViews(){		
		Field[] fields = getClass().getDeclaredFields();
		Field[] parentFields = getClass().getSuperclass().getDeclaredFields();
		List<Field> fieldList =  new ArrayList<Field>();
		for(Field field: fields){
			fieldList.add(field);
		}
		for(Field field: parentFields){
			fieldList.add(field);
		}
		for(Field field : fieldList){			
			field.setAccessible(true);
			Class<?> fieldClass = field.getType();	
			Integer viewId = mViewTagHash.get(field.getName());
			if (viewId != null){
				try {			
					field.set(this,fieldClass.cast(mViewToInflate.findViewById(viewId)));
				} catch (IllegalAccessException e) {				
					e.printStackTrace();
				} catch (IllegalArgumentException e) {				
					e.printStackTrace();
				}
			}

		}

	}
	public String getCurrentFragmentTag() {
		return mCurrentFragmentTag;
	}
	public void setCurrentFragmentTag(String mCurrentFragmentTag) {
		this.mCurrentFragmentTag = mCurrentFragmentTag;
	}
	public int[] getFragmentTransitionAnimations() {
		return mFragmentTransitionAnimations;
	}
	public void setFragmentTransitionAnimations(
			int[] mFragmentTransitionAnimations) {
		this.mFragmentTransitionAnimations = mFragmentTransitionAnimations;
	}

	public void restartSelf(){
		Intent i = new Intent(this, this.getClass());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();

	}


}
