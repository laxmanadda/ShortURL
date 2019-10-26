package com.laxman.ShortURL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.apache.commons.validator.routines.UrlValidator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.spi.ServiceException;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;


public class ShortnerClass {
	
	//My Localhost Mysql is accepting Varchar upto 3072 bytes (It's taking max 768 length)
	public static final int MAX_URL_Length=767;
	
	public String ShortnerMethod(String URL) {
		
		SessionFactory factory=null;
		Session session=null;
		
		try {
			
			UrlValidator validator=new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
			if(!validator.isValid(URL)) {
				return "Not a Valid URL";
			}
			
			if(URL.length()>MAX_URL_Length) {
				throw new MysqlDataTruncation("URL length shouldn't be more than 10", 0, false, false, 0, 0, 0);
			}
			
			factory=new Configuration().configure().addAnnotatedClass(URL.class).buildSessionFactory();
			session= factory.getCurrentSession();
			session.beginTransaction();
			
			URL url=session.get(URL.class, URL);
			
			if(url!=null) {
				return url.getShortURL();
			}else {
				String s="";
				List<URL> list;
				do {
					
					//String of Upper case and lowercase alphabets and numbers.
					String MixedString = "0123456789abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
					StringBuilder b = new StringBuilder(6); 
					
					//Came through Base 62 method were we find the hash of id of new URL entry in the database, but the
					//short string need not be of fixed length ie. here we took 6 character length
					// I didn't consider any expire date for a short url
					
					for (int i=0;i<6;i++){ 
						int index = (int)(MixedString.length() * Math.random()); 
						b.append(MixedString.charAt(index));
						s=b.toString();
					} 
					
					String duplicate_short = "from URL u where u.ShortURL = :shortURL";
					
					//Checking whether created short url is already there in the adtabase or not
					list=session.createQuery(duplicate_short).setParameter("shortURL", s).list();
					
					
				}while(list.size()!=0);
				
				URL u=new URL(URL,s);
				//Saving the original and short url in the database.
				session.save(u);
				
				return s;
			}	
			
		}catch (MysqlDataTruncation m) {
			
			//Exception when url length is more than 767
			return m.getMessage();
		}catch (ServiceException s) {
			
			//Exception when database connection error occurs
			System.out.println("didn't connect");
			return s.getMessage();
		}finally {
			if(session!=null) {
				
				//Closing the session if not null
				session.getTransaction().commit();
				session.close();
			}
		}
	}
	
	public List<String> ShortnerMethod_File() throws IOException{
	
		// The text file Test_URL.txt of URL's is placed inside src/main/resources folder
		File file = new File(getClass().getResource("/Test_URL").getFile()); 
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String t; 
		List<String> l=new ArrayList<String>();
		
		while ((t = br.readLine()) != null) {
			l.add(ShortnerMethod(t));
		}
		return l;
		
	}
	
}
