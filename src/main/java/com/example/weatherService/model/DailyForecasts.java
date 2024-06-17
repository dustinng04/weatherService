package com.example.weatherService.model;

import java.util.List;

public class DailyForecasts {
	List<DailyResponse> dailyForecasts;
	
	public DailyForecasts() {
		super();
	}

	public DailyForecasts(List<DailyResponse> dailyForecasts) {
		super();
		this.dailyForecasts = dailyForecasts;
	}

	public List<DailyResponse> getDailyForecasts() {
		return dailyForecasts;
	}

	public void setDailyForecasts(List<DailyResponse> dailyForecasts) {
		this.dailyForecasts = dailyForecasts;
	}
	
}
