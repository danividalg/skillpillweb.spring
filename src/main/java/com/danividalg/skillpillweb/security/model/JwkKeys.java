package com.danividalg.skillpillweb.security.model;

import java.io.Serializable;
import java.util.List;

public class JwkKeys implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<JwkKey> keys;

	public List<JwkKey> getKeys() {
		return keys;
	}
	public void setKeys(List<JwkKey> keys) {
		this.keys = keys;
	}
}
