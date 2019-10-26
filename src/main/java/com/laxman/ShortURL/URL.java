package com.laxman.ShortURL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="shortnertable")
public class URL {
	
	URL(){};
	
	URL(String URL, String ShortURL){
		this.ShortURL=ShortURL;
		this.URL=URL;
	}
	
	@Id
	@Column(name="URL")
	private String URL;
	
	@Column(name="ShortURL")
	private String ShortURL;

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getShortURL() {
		return ShortURL;
	}

	public void setShortURL(String shortURL) {
		ShortURL = shortURL;
	}

	@Override
	public String toString() {
		return "URL [URL=" + URL + ", ShortURL=" + ShortURL + "]";
	}
	
}
