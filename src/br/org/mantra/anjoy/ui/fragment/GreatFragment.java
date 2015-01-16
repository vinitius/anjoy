package br.org.mantra.anjoy.ui.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.org.mantra.anjoy.ui.activity.GreatActivity;

public abstract class GreatFragment extends Fragment {

	protected GreatActivity mActivity; 
	private View mViewToInflate;	
	private String mTagToGoBack;
	private HashMap<String, Integer> mViewTagHash;


	protected abstract void beforeBindViews(Bundle savedInstance);
	protected abstract void afterBindViews();
	protected abstract void onSetListeners();
	protected abstract int getLayoutToBeInflated();


	@Override
	public void onAttach(Activity activity) {	
		super.onAttach(activity);
		if (activity instanceof GreatActivity){
			mActivity = (GreatActivity)activity;
			mActivity.setCurrentFragmentTag(getTag());
			mActivity.hideView(getTagToGoBack());
		}


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.beforeBindViews(savedInstanceState);
		this.mViewToInflate = inflater.inflate(getLayoutToBeInflated(), container,false);
		this.doWhenFragmentIsReady();
		return getRootView();
	}

	protected void doWhenFragmentIsReady(){				
		this.mountViewHashTag();
		this.bindViews();			
		this.afterBindViews();
		this.onSetListeners();
		this.getRootView().bringToFront();
		this.getRootView().requestLayout();

	}

	@Override
	public void onDetach() {	
		super.onDetach();		
		mActivity.showView(getTagToGoBack());
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
		for(Field field : fields){
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

	public View getRootView(){
		return mViewToInflate;
	}
	public String getTagToGoBack() {
		return mTagToGoBack;
	}
	public void setTagToGoBack(String tag) {
		this.mTagToGoBack = tag;
	}




}
