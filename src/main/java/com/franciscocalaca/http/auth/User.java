package com.franciscocalaca.http.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

	private String login;
	
	private Map<String, Object> extra = new HashMap<>();
	
	private String password;
	
	private List<String> roles = new ArrayList<>();
	
	private String tenant;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}
}
