package br.org.mantra.anjoy.model;

import java.io.Serializable;

import com.activeandroid.Model;

public class MODEL extends Model implements Serializable{

	private static final long serialVersionUID = 5895520094156314076L;
		
	
	private long identifier;

	public long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}



}
