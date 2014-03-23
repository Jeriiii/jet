/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.jet.models;

import org.springframework.web.multipart.MultipartFile; 

/**
 *
 * @author Petr Kukrál <p.kukral@kukral.eu>
 */
public class Subscriber {

	private String name;
	private String email;
	private Integer age;
	private Gender gender;
	private Frequency newsletterFrequency;
	private Boolean receiveNewsletter;
	//private byte[] file;
	private MultipartFile file; 
	
	public enum Frequency {
		HOURLY, DAILY, WEEKLY, MONTHLY, ANNUALLY
	}

	public enum Gender {
		MALE, FEMALE
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

//    public void setFile(byte[] file) {
//        this.file = file;
//    }
//
//    public byte[] getFile() {
//        return file;
//    }

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Frequency getNewsletterFrequency() {
		return newsletterFrequency;
	}

	public void setNewsletterFrequency(Frequency newsletterFrequency) {
		this.newsletterFrequency = newsletterFrequency;
	}

	public Boolean getReceiveNewsletter() {
		return receiveNewsletter;
	}

	public void setReceiveNewsletter(Boolean receiveNewsletter) {
		this.receiveNewsletter = receiveNewsletter;
	}

	@Override
	public String toString() {
		return "Subscriber [name=" + name + ", age=" + age + ", gender=" + gender
				+ ", newsletterFrequency=" + newsletterFrequency
				+ ", receiveNewsletter=" + receiveNewsletter + "]";
	}

}