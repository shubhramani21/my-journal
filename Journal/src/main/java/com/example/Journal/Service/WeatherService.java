package com.example.Journal.Service;

import com.example.Journal.api.response.WeatherResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;


    @PostConstruct
    public void connect(){
        if(apiKey == null || apiKey.isEmpty()){
            log.warn("No api key is initialized!");
        }

        log.info("API key is initialized");
        if(apiUrl == null || apiUrl.isEmpty()){
            log.warn("Url is not defined!");
        }

        log.info("API url is defined: {}", apiUrl);

    }

    public WeatherResponse getWeather(String city){
        try {
            String url = apiUrl + "?access_key=" + apiKey + "&query=" + city;

            final ResponseEntity<WeatherResponse> response =
                    restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);

            return response.getBody();
        } catch (RestClientException e) {
            log.error("Error Occurred: ", e);
            return null;
        }
    }

}
