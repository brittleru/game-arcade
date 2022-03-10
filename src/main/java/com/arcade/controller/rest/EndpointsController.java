package com.arcade.controller.rest;

import com.arcade.service.rest.EndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class EndpointsController {

    private final EndpointService endpointService;

    @Autowired
    public EndpointsController(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    @GetMapping("/endpoints")
    public Map<String, String> getEndpoints() {
        return endpointService.getAllRestEndpoints();
    }

}
