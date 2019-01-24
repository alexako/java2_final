/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagement;

import java.util.UUID;

/**
 *
 * @author alex
 */
public class User {
	private String id;
	private String name;
	private String role;

	public User() {
	    this.id = UUID.randomUUID().toString();
	}

	public User(String name, String role) {
	    this.id = UUID.randomUUID().toString();
		this.name = name;
		this.role = role;
	}

	public User(String id, String name, boolean flag) {
	    this.id = id;
		this.name = name;
	}

	public User(String id, String name, String role) {
	    this.id = id;
		this.name = name;
		this.role = role;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
