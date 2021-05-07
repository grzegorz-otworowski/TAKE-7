/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 *
 * @author Komp
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Client client = ClientBuilder.newClient();
        
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String id = reader.readLine();

        String count = client.target("http://localhost:8080/Complaints/"
                + "resources/complaints/count")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        System.out.println("Count: " + count);

        String complaints = client.target("http://localhost:8080/Complaints/"
                + "resources/complaints")
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        System.out.println(complaints);
        
        String openComplaint = client.target("http://localhost:8080/Complaints/"
                + "resources/complaints/"
                + id)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        System.out.println(openComplaint);
        
        JSONObject jo = new JSONObject(openComplaint);
        jo.put("status", "closed");
        Complaint c = new Complaint(jo.get("id").toString(),jo.get("complaintDate").toString(),jo.get("complaintText").toString(),jo.get("author").toString(),jo.get("status").toString());
        System.out.println(jo);
        
        client.target("http://localhost:8080/Complaints/"
                + "resources/complaints/"
                + id)
                .request()
                .put(Entity.entity(c, MediaType.APPLICATION_JSON));
        
        String allOpen = client.target("http://localhost:8080/Complaints/"
                + "resources/complaints"
                + "?status=open")
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        System.out.println(allOpen);
        
        client.close();
    }
}
