package br.org.mantra.anjoy.model;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

public class MODEL extends Model implements Serializable{

	public MODEL(){
		super();
	}

	private static final long serialVersionUID = 5895520094156314076L;

	@Column(name = "identifier")
	private long identifier;

	public long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}



}
