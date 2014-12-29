package br.org.mantra.anjoy.model;

import java.lang.reflect.Field;

import br.org.mantra.anjoy.preference.AnjoyPreferences;
import br.org.mantra.anjoy.session.ControlledSession;
import br.org.mantra.anjoy.util.LogUtils;

public class USER extends POJO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2233789820671734704L;

	public void saveToPreferences(){
		AnjoyPreferences preferences = (AnjoyPreferences)
				ControlledSession.getInstance().get(ControlledSession.APP_PREFERENCES);

		for(Field field:this.getClass().getDeclaredFields()){
			field.setAccessible(true);
			Class<?> target = field.getType();
			Object fieldValue = null;
			try {
				fieldValue = target.newInstance();
			} catch (Exception e) {	
				LogUtils.logErrorForAnjoy("Error during saving user preferences...");
				e.printStackTrace();
			} 
			preferences.savePreference(field.getName(),fieldValue);

		}

		preferences.confirmUserInfoStored();

	}

}
