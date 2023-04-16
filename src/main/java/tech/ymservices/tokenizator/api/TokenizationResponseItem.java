package tech.ymservices.tokenizator.api;

import java.util.Date;

public class TokenizationResponseItem {
	
	private String originalValueString;
	private Double originalValueNumber;
	private Date originalValueDate;
	private String field;
	
	private String tokenizedValueString;
	private Double tokenizedValueNumber;
	private Date tokenizedValueDate;
	
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
	public String getTokenizedValueString() {
		return tokenizedValueString;
	}
	public void setTokenizedValueString(String tokenizedValueString) {
		this.tokenizedValueString = tokenizedValueString;
	}
	public Double getTokenizedValueNumber() {
		return tokenizedValueNumber;
	}
	public void setTokenizedValueNumber(Double tokenizedValueNumber) {
		this.tokenizedValueNumber = tokenizedValueNumber;
	}
	public Date getTokenizedValueDate() {
		return tokenizedValueDate;
	}
	public void setTokenizedValueDate(Date tokenizedValueDate) {
		this.tokenizedValueDate = tokenizedValueDate;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
}
