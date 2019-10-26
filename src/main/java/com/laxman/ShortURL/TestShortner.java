package com.laxman.ShortURL;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestShortner {
	
	public static void main(String args[]) throws IOException {
		ShortnerClass sc=new ShortnerClass();
		
		//String single_url="https://howtodoinjava.com/hibernate/hibernate-ehcache-configuration-tutorial/";
		
		//String short_url= sc.ShortnerMethod(single_url);
		
		List<String> l=sc.ShortnerMethod_File();
		
		for(String short_url:l) {
			System.out.println(short_url);
		}
	}
}
