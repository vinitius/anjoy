package br.org.mantra.anjoy.ui.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.org.mantra.anjoy.ui.activity.GreatActivity;

public class GreatAdapter extends BaseAdapter {

	protected GreatActivity mActivity;
	protected List<?> mItems;

	public GreatAdapter(GreatActivity context,List<?> collection){
		this.mActivity = context;
		this.mItems = collection;

	}


	@Override
	public int getCount() {		
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {		
		return 0;
	}


	// To be implemented by each child
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return null;
	}

}
