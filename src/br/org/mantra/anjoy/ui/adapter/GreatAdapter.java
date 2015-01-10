package br.org.mantra.anjoy.ui.adapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.org.mantra.anjoy.ui.activity.GreatActivity;

public abstract class GreatAdapter<T> extends BaseAdapter {

	private GreatActivity mActivity;
	private List<T> mItems;
	private HashMap<String, Integer> mViewTagHash;
	private View mViewToInflate;	

	protected abstract int getLayoutToBeInflated();
	protected abstract void setValuesForItem(T item,int position,View convertView,ViewGroup parent);

	public GreatAdapter(GreatActivity context,List<T> collection){
		this.mActivity = context;
		this.mItems = collection;	
		this.mViewToInflate = getContext().getLayoutInflater().inflate(getLayoutToBeInflated(),null);
		this.mountViewHashTag();



	}


	@Override
	public int getCount() {		
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return (T)mItems.get(position);
	}

	@Override
	public long getItemId(int position) {		
		return 0;
	}


	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null){
			convertView = getContext().getLayoutInflater().inflate(getLayoutToBeInflated(), parent,false);
			bindViews(convertView);
		}

		HashMap<String, Object> viewHolder = (HashMap<String, Object>)convertView.getTag();
		for(Field field: getClass().getDeclaredFields()){
			if (mViewTagHash.containsKey(field.getName())){
				field.setAccessible(true);
				try {		
					field.set(this, viewHolder.get(field.getName()));
				} catch (IllegalAccessException e) {					
					e.printStackTrace();
				} catch (IllegalArgumentException e) {					
					e.printStackTrace();
				}
			}

		}
		setValuesForItem(getCollection().get(position), position,convertView,parent);

		return convertView;
	}


	public GreatActivity getContext() {
		return mActivity;
	}


	public void setContext(GreatActivity mActivity) {
		this.mActivity = mActivity;
	}


	public List<T> getCollection() {
		return mItems;
	}


	public void setCollection(List<T> mItems) {
		this.mItems = mItems;
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

	private void bindViews(View view){

		HashMap<String, Object> hash = new HashMap<String, Object>();
		Field[] fields = getClass().getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			Class<?> fieldClass = field.getType();	
			Integer viewId = mViewTagHash.get(field.getName());
			if (viewId != null){
				try {
					field.set(this,fieldClass.cast(view.findViewById(viewId)));
					hash.put(field.getName(), field.get(this));
				} catch (IllegalAccessException e) {				
					e.printStackTrace();
				} catch (IllegalArgumentException e) {				
					e.printStackTrace();
				}
			}

		}

		view.setTag(hash);


	}

	public View getRootView(){
		return mViewToInflate;
	}




}
