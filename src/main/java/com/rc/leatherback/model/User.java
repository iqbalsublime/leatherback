package com.rc.leatherback.model;

public class User extends AbstractEntity {
	private static final long serialVersionUID = 3357474440893113325L;

	private String name;

	private String username;
	private String password;

	private Authorisation authorisation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Authorisation getAuthorisation() {
		return authorisation;
	}

	public void setAuthorisation(Authorisation authorisation) {
		this.authorisation = authorisation;
	}
}
