package br.org.mantra.anjoy.util;

import android.view.View;
import android.view.animation.Animation;
import android.widget.ListView;

public class ViewUtils {

	public static void animateListViewItem(Animation anim,ListView listItems,int position){

		int wantedPosition = position;
		int firstPosition = listItems.getFirstVisiblePosition() - listItems.getHeaderViewsCount(); // This is the same as child #0
		int wantedChild = wantedPosition - firstPosition;		
		View wantedView = listItems.getChildAt(wantedChild);
		wantedView.startAnimation(anim);




	}

}
