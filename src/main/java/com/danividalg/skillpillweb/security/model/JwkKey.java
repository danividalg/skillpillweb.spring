package com.danividalg.skillpillweb.security.model;

import java.io.Serializable;

public class JwkKey implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String use;
	private String kty;
	private String kid;
	private String alg;
	private String n;
	private String e;
	
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getKty() {
		return kty;
	}
	public void setKty(String kty) {
		this.kty = kty;
	}
	public String getKid() {
		return kid;
	}
	public void setKid(String kid) {
		this.kid = kid;
	}
	public String getAlg() {
		return alg;
	}
	public void setAlg(String alg) {
		this.alg = alg;
	}
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n = n;
	}
	public String getE() {
		return e;
	}
	public void setE(String e) {
		this.e = e;
	}
}
