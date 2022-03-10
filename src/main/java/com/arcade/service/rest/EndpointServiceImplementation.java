package com.arcade.service.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EndpointServiceImplementation implements EndpointService {

    @Value("${rest.endpoints}")
    private String[] restEndpoints;

    @Override
    public Map<String, String> getAllRestEndpoints() {
        final Map<String, String> endpointsMap = new HashMap<>();
        for (String restEndpoint : restEndpoints) {
            int index = restEndpoint.lastIndexOf("/");
            String endpointLastSlash = restEndpoint.substring(index + 1);
            endpointsMap.put(endpointLastSlash, restEndpoint);
        }

        return endpointsMap;
    }

}
