package br.org.mantra.anjoy.ui.activity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import br.org.mantra.anjoy.R;

public abstract class SideMenuActivity extends ProgressActivity {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private FrameLayout mLeftMenuFrame;
	private FrameLayout mRightMenuFrame;

	public enum MENU_MODE{
		LEFT,
		RIGHT,
		BOTH
	}

	protected abstract MENU_MODE getMenuMode();



	@SuppressLint("NewApi") 
	private void bindMenu(){	

		if (getMenuMode() == MENU_MODE.RIGHT || getMenuMode() == MENU_MODE.BOTH){

			mRightMenuFrame.getLayoutParams().width = geRightMenuWidthInPx();
			mRightMenuFrame.requestLayout();
		}

		if (getMenuMode() == MENU_MODE.LEFT || getMenuMode() == MENU_MODE.BOTH){


			mLeftMenuFrame.getLayoutParams().width = getLeftMenuWidthInPx();
			mLeftMenuFrame.requestLayout();
			mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
					getLeftDrawerToggleResource(), 
					getLeftDrawerToggleTitleWhenOpen(), 
					getLeftDrawerToggleTitleWhenClose()) {

				public void onDrawerClosed(View view) {
					super.onDrawerClosed(view);						
					invalidateOptionsMenu(); 
				}


				public void onDrawerOpened(View drawerView) {
					super.onDrawerOpened(drawerView);
					mLeftMenuFrame.bringToFront();
					mLeftMenuFrame.requestLayout();
					invalidateOptionsMenu();	                    
				}


			};
			mDrawerLayout.setDrawerListener(mDrawerToggle);
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setHomeButtonEnabled(true);
		}







	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);


		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
		if(mDrawerToggle.onOptionsItemSelected(item))
			return true;
		return super.onOptionsItemSelected(item);
	}



	public DrawerLayout getDrawerLayout() {
		return mDrawerLayout;
	}

	public void setDrawerLayout(DrawerLayout mDrawerLayout) {
		this.mDrawerLayout = mDrawerLayout;
	}

	public int getLeftMenu() {
		return R.id.left_drawer;
	}

	public int getRightMenu() {
		return R.id.right_drawer;
	}

	public void openMenu(int gravity){
		if (mDrawerLayout.isDrawerOpen(gravity))
			mDrawerLayout.closeDrawer(gravity);
		else
			mDrawerLayout.openDrawer(gravity);

	}

	protected abstract int getLeftDrawerToggleResource();
	protected abstract int getLeftDrawerToggleTitleWhenOpen();
	protected abstract int getLeftDrawerToggleTitleWhenClose();
	protected abstract int getLeftMenuWidthInPx();
	protected abstract int geRightMenuWidthInPx();


	@Override
	protected int getRootViewContainer() {

		return R.id.view_main;
	}

	@Override
	protected int getLayoutToBeInflated() {
		if (getMenuMode() == MENU_MODE.LEFT)
			return R.layout.side_menu_activity_left;
		else if (getMenuMode() == MENU_MODE.RIGHT)
			return R.layout.side_menu_activity_right;
		else if (getMenuMode() == MENU_MODE.BOTH)
			return R.layout.side_menu_activity_both;	

		return R.layout.side_menu_activity_left;
	}

	@Override
	protected void afterBindViews() {		
		bindMenu();

	}




}
