/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jet.models;

/**
 *
 * @author Petr Kukrál
 */
public class PomItemEntite {

	private int id;
	private String email;
	private String result;

	public String getEmail() {
	    return email;
	}

	public void setEmail(String email) {
	    this.email = email;
	}

	public String getResult() {
	    return result;
	}

	public void setResult(String result) {
	    this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
