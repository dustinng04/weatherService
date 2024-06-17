package com.example.weatherService.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.weatherService.exception.InvalidLocationKeyException;
import com.example.weatherService.model.DailyForecasts;
import com.example.weatherService.model.DailyResponse;
import com.example.weatherService.service.AccuWeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/weather")
public class WeatherController {

	private final AccuWeatherService accuWeatherService;

	@Autowired
	public WeatherController(AccuWeatherService accuWeatherService) {
		this.accuWeatherService = accuWeatherService;
	}

	@Operation(summary = "Get daily weather forecast", description = "Fetches the daily weather forecast for a given location. Optionally, the response can be localized to the specified language.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful response"),
			@ApiResponse(responseCode = "400", description = "Invalid location key or language"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/daily/{locationKey}")
	public ResponseEntity<DailyResponse> getDailyWeather(
	        @Parameter(description = "The key of the location to get the weather forecast for", required = true)
	        @PathVariable String locationKey,
	        @Parameter(description = "Optional language code for localizing the response")
	        @RequestParam(required = false) String lang) {
	        try {
	            Locale locale = lang != null ? Locale.forLanguageTag(lang) : Locale.getDefault();
	            DailyResponse response = accuWeatherService.getDailyWeatherForecast(locationKey, locale);
	            return ResponseEntity.ok(response);
	        } catch (InvalidLocationKeyException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

    @ExceptionHandler(InvalidLocationKeyException.class)
    public ResponseEntity<String> handleInvalidLocationKeyException(InvalidLocationKeyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    
    
    @Operation(summary = "Get weather forecast for 5 next days", description = "Fetches the 5-day weather forecast for a given location. Optionally, the response can be localized to the specified language.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful response"),
			@ApiResponse(responseCode = "400", description = "Invalid location key or language"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/5day/{locationKey}")
	public ResponseEntity<DailyForecasts> get5DaysForecasts(@PathVariable String locationKey, @RequestParam(required = false) String lang) {
		try {
			Locale locale = lang != null ? Locale.forLanguageTag(lang) : Locale.getDefault();
			DailyForecasts response = accuWeatherService.get5DayWeatherForecasts(locationKey, locale);
			return ResponseEntity.ok(response);
		} catch (InvalidLocationKeyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
	}	
	
}
