package com.example.weatherService.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LocalizationService {
	private final MessageSource messageSource;

	public LocalizationService(MessageSource messageSource) {
		super();
		this.messageSource = messageSource;
	}
	
	public String localizeTemp(String temperature) {
        Locale locale = LocaleContextHolder.getLocale();
        double tempValue = Double.parseDouble(temperature);
        String unit = getTemperatureUnit(locale);

        if (unit.equals("C")) {
            // Convert F to C
            tempValue = (tempValue - 32) * 5 / 9;
        }
        // Format temperature with unit
        return String.format("%.1f °%s", tempValue, unit);
    }

    private String getTemperatureUnit(Locale locale) {
        // Check if the locale corresponds to the United States
        return locale.equals(Locale.UK) ? "F" : "C";
    }
    
    // Get Local code
    public String getLocalizationCode(Locale locale) {
    	String language = locale.getLanguage();
    	String country = locale.getCountry();
    	
    	// Combine language and country to form the localization code
        if (!country.isEmpty()) {
            return language + "-" + country;
        } else {
            return language;
        }
    }
}
