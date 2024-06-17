package com.example.weatherService.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.weatherService.model.DailyForecasts;
import com.example.weatherService.model.DailyResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ResponseMapper {
	private final LocalizationService localizationService;
	
	@Autowired
	public ResponseMapper(LocalizationService localizationService) {
		super();
		this.localizationService = localizationService;
	}

	public DailyResponse mapDailyResponseToModel(String jsonResponse) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonResponse);

		return extractNode(rootNode.path("DailyForecasts").get(0), rootNode);
	}

	private DailyResponse extractNode(JsonNode node, JsonNode rootNode) {
		// Extract relevant fields from the JSON response
		String dateString = node.path("Date").asText();
		String datePart = dateString.substring(0, dateString.indexOf('T')); // Extract the part of the string before 'T'
		LocalDate date = LocalDate.parse(datePart); // Parse the date part
		
		String description = rootNode.path("Headline").path("Text").asText();
		String minTemperature = node.path("Temperature").path("Minimum").path("Value")
				.asText();
		String maxTemperature = node.path("Temperature").path("Maximum").path("Value")
				.asText();
		String realFeelTemperature = node.path("RealFeelTemperature").path("Maximum")
				.path("Value").asText();
		String airQualityCategory = node.path("AirAndPollen").get(0).path("Category")
				.asText();
		int uvIndex = node.path("AirAndPollen").get(5).path("Value").asInt();

//        Relavant fields for Day
		JsonNode dayNode = node.path("Day");
		String overallWeatherDay = dayNode.path("IconPhrase").asText();
		int rainProbabilityDay = dayNode.path("RainProbability").asInt();
		int relativeHumidityDay = dayNode.path("RelativeHumidity").path("Average").asInt();

		// Extract relevant fields from the JSON response for Night
		JsonNode nightNode = node.path("Night");
		String overallWeatherNight = nightNode.path("IconPhrase").asText();
		int rainProbabilityNight = nightNode.path("RainProbability").asInt();
		int relativeHumidityNight = nightNode.path("RelativeHumidity").path("Average").asInt();

		// Combine the relevant fields from both Day and Night sections
		String overallWeather = "Day: " + overallWeatherDay + ", Night: " + overallWeatherNight;
		int rainProbability = Math.min(rainProbabilityDay, rainProbabilityNight);
		int relativeHumidity = Math.min(relativeHumidityDay, relativeHumidityNight);
		// Create a new DailyResponse object with the extracted fields

		DailyResponse dailyResponse = new DailyResponse();
		dailyResponse.setDate(date);
		dailyResponse.setDescription(description);
		dailyResponse.setOverallWeather(overallWeather);
		dailyResponse.setMinTemperature(localizationService.localizeTemp(minTemperature));
		dailyResponse.setMaxTemperature(localizationService.localizeTemp(maxTemperature));
		dailyResponse.setRealFeelTemperature(localizationService.localizeTemp(realFeelTemperature));
		dailyResponse.setAirQuality(airQualityCategory); // Set air quality category as a string
		dailyResponse.setUvIndex(uvIndex);
		dailyResponse.setRainProbability(rainProbability);
		dailyResponse.setRelativeHumidity(relativeHumidity);

		return dailyResponse;
	}
	
	public DailyForecasts mapDailyForecastsToModel(String jsonResponse) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode rootNode = objectMapper.readTree(jsonResponse);

	    List<DailyResponse> dailyResponses = new ArrayList<>();
	    DailyForecasts dailyForecasts = new DailyForecasts();
	    
	    for (JsonNode node: rootNode.path("DailyForecasts")) {
	    	DailyResponse response = extractNode(node, rootNode);
	    	dailyResponses.add(response);
	    }
	    dailyForecasts.setDailyForecasts(dailyResponses);
	    return dailyForecasts;	
	    
	}
}
