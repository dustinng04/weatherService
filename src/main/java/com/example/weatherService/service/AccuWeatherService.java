package com.example.weatherService.service;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.weatherService.exception.InvalidLocationKeyException;
import com.example.weatherService.model.DailyForecasts;
import com.example.weatherService.model.DailyResponse;

@Service
public class AccuWeatherService {

	private final String baseUrl = "http://dataservice.accuweather.com";
	@Value("${accuweather.api.key}")
    private String apiKey;

	private final RestTemplate restTemplate;
	private final ResponseMapper responseMapper;
	private final LocalizationService localizationService;
	
	@Autowired
	public AccuWeatherService(RestTemplate restTemplate, ResponseMapper responseMapper, LocalizationService localizationService) {
		this.restTemplate = restTemplate;
		this.responseMapper = responseMapper;
		this.localizationService = localizationService;
	}

	public DailyResponse getDailyWeatherForecast(String locationKey, Locale locale) throws Exception {
		String code = localizationService.getLocalizationCode(locale);
		String url = baseUrl + "/forecasts/v1/daily/1day/" + locationKey;

		// Map the JSON response to DailyWeatherResponse using the mapper
		try {
            String jsonResponse = restTemplate.getForObject(url + "?apikey=" + apiKey + "&details=true" + "&language=" + code, String.class);
            return responseMapper.mapDailyResponseToModel(jsonResponse);
        } catch (HttpClientErrorException e) {
        	if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new InvalidLocationKeyException("Invalid location key: " + locationKey);
            }
            throw e;
        }
	}

	public DailyForecasts get5DayWeatherForecasts(String locationKey, Locale locale) throws Exception {
		String code = localizationService.getLocalizationCode(locale);
	    String url = baseUrl + "/forecasts/v1/daily/5day/" + locationKey;
	    String queryParams = "?apikey=" + apiKey + "&details=true" + "&language=" + code;
	    try {
	    	String jsonResponse = restTemplate.getForObject(url + queryParams, String.class);
	    	
	    	// Map the JSON response to DailyForecasts using the mapper    	
	    	return responseMapper.mapDailyForecastsToModel(jsonResponse);	    	
	    } catch (HttpClientErrorException e) {
        	if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new InvalidLocationKeyException("Invalid location key: " + locationKey);
            }
            throw e;
        }
	}
}
