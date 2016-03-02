/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abohawa;

import org.json.JSONObject;

/**
 *
 * @author rana
 */
public class FetchThread implements Runnable {

    Thread t;
    private String cityName;
    JSONObject json;

    public FetchThread(String city) {

        cityName = city;
        t = new Thread(this, "Get Data Thread");
        System.out.println("Running fetch");
        t.start();
    }

    @Override
    public void run() {
        System.out.println("run method");

        try {
            json = RemoteFetch.getJSON(cityName);

        } catch (Exception e) {
            System.out.println("Error fetching");
        }

    }
    
    JSONObject getJSON(){
        return json;
    }

}
