package br.org.mantra.anjoy.session;

import java.lang.reflect.Field;
import java.util.HashMap;

import android.app.Application;
import br.org.mantra.anjoy.preference.AnjoyPreferences;

public class ControlledSession extends Application{

	private static ControlledSession sSession;
	private static HashMap<String, Object> sSessionData;
	public static final String REST_CLIENT_HOST = "REST_CLIENT_HOST";
	public static final String REST_CLIENT_BASE_URL = "REST_CLIENT_BASE_URL";
	public static final String APP_PREFERENCES = "APP_PREFERENCES";
	public static final String SESSION_CONTROLLER = "SESSION_CONTROLLER";
	public static final String CURRENT_LOGGED_USER = "CURRENT_LOGGED_USER";




	public static ControlledSession getInstance(){	
		return sSession;
	}

	private void collectSessionData(){
		sSessionData = new HashMap<String, Object>();
		Field[] allFields = this.getClass().getDeclaredFields();
		for(Field field:allFields){
			if (!field.getName().equals("sSession") 
					&&
					!field.getName().equals("sSessionData")){
				sSessionData.put(field.getName(),null);
			}

		}		
		set(APP_PREFERENCES, new AnjoyPreferences(this));		
	}

	public Object get(String field){
		return sSessionData.get(field);		
	}

	public void set(String field, Object value){
		sSessionData.put(field, value);
	}
	
	public AnjoyPreferences getPreferences(){
		return (AnjoyPreferences)get(APP_PREFERENCES);
	}


	@Override
	public void onCreate() {	
		super.onCreate();
		sSession = this;
		collectSessionData();
	}

}
