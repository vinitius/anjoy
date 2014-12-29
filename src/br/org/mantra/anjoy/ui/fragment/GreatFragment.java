package br.org.mantra.anjoy.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.org.mantra.anjoy.ui.activity.GreatActivity;

public class GreatFragment extends Fragment {

	protected GreatActivity mActivity; 
	protected View mViewToInflate;
	private HashMap<Integer, Class<?>> mViewHash;

	@Override
	public void onAttach(Activity activity) {	
		super.onAttach(activity);
		if (activity instanceof GreatActivity)
			mActivity = (GreatActivity)activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		doWhenFragmentIsReady();
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	protected void doWhenFragmentIsReady(){
		this.beforeBindViews();
		this.onBindViews();
		this.afterBindViews();		
	}

	protected void beforeBindViews(){
		this.mountViewHash();
	}

	protected void onBindViews(){}

	protected void afterBindViews(){
		this.onSetListeners();
	}

	protected void onSetListeners(){}

	protected <T> T bindTo(int viewIdInResource, Class<T> cls){		
		return cls.cast(mViewToInflate.findViewById(viewIdInResource));		
	}

	@SuppressWarnings("unchecked")
	protected <T> T bindTo(int viewIdInResource){		
		return (T)mViewHash.get(viewIdInResource).cast(mViewToInflate.findViewById(viewIdInResource));		
	}


	@SuppressLint("UseSparseArrays") 
	private void mountViewHash(){ 

		if (mViewHash == null)
			mViewHash = new HashMap<Integer, Class<?>>();

		List<View> visited = new ArrayList<View>();
		List<View> unvisited = new ArrayList<View>();
		unvisited.add(mViewToInflate);
		while (!unvisited.isEmpty()) {
			View child = unvisited.remove(0);
			visited.add(child);
			mViewHash.put(child.getId(), child.getClass());
			if (!(child instanceof ViewGroup)) continue;
			ViewGroup group = (ViewGroup) child;
			final int childCount = group.getChildCount();
			for (int i=0; i<childCount; i++) unvisited.add(group.getChildAt(i));
		}




	}




}
