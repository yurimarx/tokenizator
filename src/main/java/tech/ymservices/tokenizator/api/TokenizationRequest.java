package tech.ymservices.tokenizator.api;

import java.util.Date;
import java.util.HashMap;

import tech.ymservices.tokenizator.model.TokenType;

public class TokenizationRequest {
	
	private TokenType tokenType;
	private String originalValueString;
	private Double originalValueNumber;
	private Date originalValueDate;
	private HashMap<String, String> settings;
	
	public TokenType getTokenType() {
		return tokenType;
	}
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}
	public String getOriginalValueString() {
		return originalValueString;
	}
	public void setOriginalValueString(String originalValueString) {
		this.originalValueString = originalValueString;
	}
	public Double getOriginalValueNumber() {
		return originalValueNumber;
	}
	public void setOriginalValueNumber(Double originalValueNumber) {
		this.originalValueNumber = originalValueNumber;
	}
	public Date getOriginalValueDate() {
		return originalValueDate;
	}
	public void setOriginalValueDate(Date originalValueDate) {
		this.originalValueDate = originalValueDate;
	}
	public HashMap<String, String> getSettings() {
		return settings;
	}
	public void setSettings(HashMap<String, String> settings) {
		this.settings = settings;
	}

	
}
