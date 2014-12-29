package br.org.mantra.anjoy.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import br.org.mantra.anjoy.R;

public abstract class SideMenuActivity extends ProgressActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mMenuListLeft;
	private ListView mMenuListRight;

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

	private void bindMenu(){
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		mMenuListLeft = (ListView)findViewById(R.id.left_drawer);
		mMenuListRight = (ListView)findViewById(R.id.right_drawer);				

	}


	public DrawerLayout getDrawerLayout() {
		return mDrawerLayout;
	}

	public void setDrawerLayout(DrawerLayout mDrawerLayout) {
		this.mDrawerLayout = mDrawerLayout;
	}

	public ListView getLeftMenu() {
		return mMenuListLeft;
	}

	public ListView getRightMenu() {
		return mMenuListRight;
	}

	public void openMenu(int gravity){
		if (mDrawerLayout.isDrawerOpen(gravity))
			mDrawerLayout.closeDrawer(gravity);
		else
			mDrawerLayout.openDrawer(gravity);

	}

	public void replaceFragment(Fragment fragment,
			boolean addToBackStack) {

		FragmentTransaction fragmentTransaction = 
				getSupportFragmentManager().beginTransaction().
				replace(R.id.view_main, fragment);

		if (addToBackStack) fragmentTransaction.addToBackStack(null);

		fragmentTransaction.commitAllowingStateLoss();

	}


}
