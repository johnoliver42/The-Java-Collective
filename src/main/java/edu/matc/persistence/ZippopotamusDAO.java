package edu.matc.persistence;

import Zippopotamus.API.StateCity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Zippopotamus.API.ZipCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class ZippopotamusDAO {
    private final Logger logger = LogManager.getLogger(this.getClass());

    Client client = ClientBuilder.newClient();


    public ZipCode GetCityState(int zipcode) throws JsonProcessingException {
        //building url to send API
        String apiUrl = "http://api.zippopotam.us/us/"+zipcode;
        WebTarget target =
                client.target(apiUrl);
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        logger.info("Sent API "+zipcode+" return from API: "+response);
        //loading values returned from API into objects.  ZipCode will
        //load ZipCodePlacesItem as it processes
        ObjectMapper mapper = new ObjectMapper();
        ZipCode zc = mapper.readValue(response, ZipCode.class);

        return zc;
    }

    public StateCity GetZipCodes(String state, String city) throws JsonProcessingException {
        //building url to send API
        String apiUrl = "http://api.zippopotam.us/us/"+state+"/"+city;
        WebTarget target =
                client.target(apiUrl);
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        logger.info("Sent API: "+state+"/"+city+" return from API: "+response);
        //loading values returned from API into objects.  StateCity will
        //load StateCityPlacesItem as it processes
        ObjectMapper mapper = new ObjectMapper();
        StateCity sc = mapper.readValue(response, StateCity.class);
        //this one object can have multiple places items (zip codes)
        return sc;
    }




}
