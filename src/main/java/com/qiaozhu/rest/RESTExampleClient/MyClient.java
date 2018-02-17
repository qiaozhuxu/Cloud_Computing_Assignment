package com.qiaozhu.rest.RESTExampleClient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class MyClient {

	private static final String STUDENT_BASE = "http://localhost:8080/RESTExample/webapi/neu/student";

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		
		String result = client.target(STUDENT_BASE).request().get(String.class);
		
		System.out.println(result);
		
	}

}
