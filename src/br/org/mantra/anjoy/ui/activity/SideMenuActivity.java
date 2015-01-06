package br.org.mantra.anjoy.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import br.org.mantra.anjoy.R;

public abstract class SideMenuActivity extends ProgressActivity {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	public enum MENU_MODE{
		LEFT,
		RIGHT,
		BOTH
	}

	protected abstract MENU_MODE getMenuMode();

	@Override
	protected void onCreate(Bundle arg0) {	
		super.onCreate(arg0);


		if (getMenuMode() == MENU_MODE.LEFT)
			setContentView(R.layout.side_menu_activity_left);
		else if (getMenuMode() == MENU_MODE.RIGHT)
			setContentView(R.layout.side_menu_activity_right);
		else if (getMenuMode() == MENU_MODE.BOTH)
			setContentView(R.layout.side_menu_activity_both);	



		bindMenu();

	}

	@SuppressLint("NewApi") 
	private void bindMenu(){
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

		if (getMenuMode() == MENU_MODE.LEFT || getMenuMode() == MENU_MODE.BOTH){

			findViewById(getLeftMenu()).getLayoutParams().width = getLeftMenuWidthInPx();
			findViewById(getLeftMenu()).requestLayout();

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
					invalidateOptionsMenu();	                    
				}


			};



			mDrawerLayout.setDrawerListener(mDrawerToggle);
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setHomeButtonEnabled(true);
		}



		if (getMenuMode() == MENU_MODE.RIGHT || getMenuMode() == MENU_MODE.BOTH){

			findViewById(getRightMenu()).getLayoutParams().width = geRightMenuWidthInPx();
			findViewById(getRightMenu()).requestLayout();
		}



	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		mDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return mDrawerToggle.onOptionsItemSelected(item);
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


}
