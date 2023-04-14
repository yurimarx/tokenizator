package tech.ymservices.tokenizator.model;

import java.util.Objects;

public class Token {
	
	private String tokenizedValue;
	private String originalValue;
	private String ticket;
	
	
	public Token() {
		super();
	}

	public Token(String tokenizedValue, String originalValue, String ticket) {
		super();
		this.tokenizedValue = tokenizedValue;
		this.originalValue = originalValue;
		this.ticket = ticket;
	}

	public String getTokenizedValue() {
		return tokenizedValue;
	}
	
	public void setTokenizedValue(String tokenizedValue) {
		this.tokenizedValue = tokenizedValue;
	}
	
	public String getOriginalValue() {
		return originalValue;
	}
	
	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}
	
	public String getTicket() {
		return ticket;
	}
	
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Override
	public int hashCode() {
		return Objects.hash(originalValue, ticket);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		return Objects.equals(originalValue, other.originalValue) && Objects.equals(ticket, other.ticket);
	}

	@Override
	public String toString() {
		return "Token [originalValue=" + originalValue + ", ticket=" + ticket + "]";
	}
	
}
