package br.org.mantra.anjoy.preference;

import br.org.mantra.anjoy.util.LogUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AnjoyPreferences {

	private final SharedPreferences preferences;
	public static final String USER_INFO = "USER_INFO";




	public AnjoyPreferences(Context context) {
		this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public void savePreference(String key, Object value) {
		SharedPreferences.Editor editor = this.preferences.edit();
		if (value.getClass() == Boolean.class) {
			editor.putBoolean(key, (Boolean) value);
		} else if (value.getClass() == String.class) {
			editor.putString(key, (String) value);
		} else if (value.getClass() == Integer.class) {
			editor.putInt(key, (Integer) value);
		} else if (value.getClass() == Long.class) {
			editor.putLong(key, (Long) value);
		} else if (value.getClass() == Float.class) {
			editor.putFloat(key, (Float) value);
		}else{
			LogUtils.logInfoForAnjoy("Saving Preferences:"
					+ " Type not supported -> "+value.getClass().getSimpleName()+
					" -> Ignored");
		}

		editor.commit();
	}

	public void removePreference(String key) {
		SharedPreferences.Editor editor = this.preferences.edit();
		editor.remove(key);
		editor.commit();
	}

	public void clearPreferences() {
		SharedPreferences.Editor editor = this.preferences.edit();
		editor.clear();
		editor.commit();
	}

	public boolean isUserInfoStored(){
		return this.preferences.getBoolean(USER_INFO, false);
	}

	public void confirmUserInfoStored(){
		this.savePreference(USER_INFO, true);
	}

}
