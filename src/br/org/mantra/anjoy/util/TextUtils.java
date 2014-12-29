package br.org.mantra.anjoy.util;


public final class TextUtils {
	
	public static String getNameAndSecondName(String completeName){
		
		
		int whiteSpaceCount = 0;
		int index =1;
		for (char c : completeName.toCharArray()) {
		    if (c == ' ') {
		         whiteSpaceCount++;
		    }		    
		    
		    if (whiteSpaceCount == 2)
		    	return completeName.substring(0,index);
		    
		    index++;
		    
		   
		    
		}
		return completeName;
	}
	
	

}
