package com.example.weatherService.model;

import java.time.LocalDate;

public class DailyResponse {
	private LocalDate date;
	private String description;
	private String overallWeather;
	private String minTemperature;
	private String maxTemperature;
	private String realFeelTemperature;
	private String airQuality;
	private int uvIndex;
	private int rainProbability;
	private int relativeHumidity;

//	Constructors & Get & setters
	public DailyResponse() {
		super();
	}

	public DailyResponse(LocalDate date, String description, String overallWeather, String minTemperature,
			String maxTemperature, String realFeelTemperature, String airQuality, int uvIndex, int rainProbability,
			int relativeHumidity) {
		super();
		this.date = date;
		this.description = description;
		this.overallWeather = overallWeather;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		this.realFeelTemperature = realFeelTemperature;
		this.airQuality = airQuality;
		this.uvIndex = uvIndex;
		this.rainProbability = rainProbability;
		this.relativeHumidity = relativeHumidity;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOverallWeather() {
		return overallWeather;
	}

	public void setOverallWeather(String overallWeather) {
		this.overallWeather = overallWeather;
	}

	public String getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(String minTemperature) {
		this.minTemperature = minTemperature;
	}

	public String getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(String maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public String getRealFeelTemperature() {
		return realFeelTemperature;
	}

	public void setRealFeelTemperature(String realFeelTemperature) {
		this.realFeelTemperature = realFeelTemperature;
	}

	public String getAirQuality() {
		return airQuality;
	}

	public void setAirQuality(String airQuality) {
		this.airQuality = airQuality;
	}

	public int getUvIndex() {
		return uvIndex;
	}

	public void setUvIndex(int uvIndex) {
		this.uvIndex = uvIndex;
	}

	public int getRainProbability() {
		return rainProbability;
	}

	public void setRainProbability(int rainProbability) {
		this.rainProbability = rainProbability;
	}

	public int getRelativeHumidity() {
		return relativeHumidity;
	}

	public void setRelativeHumidity(int relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}

}
