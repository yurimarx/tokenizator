package tech.ymservices.tokenizator.api;

import java.io.Serializable;
import java.util.Objects;

public class TokenTicketAndTokenizedValue implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ticket;
	private String tokenizedValue;
	
	public TokenTicketAndTokenizedValue() {
		super();
	}
	
	public TokenTicketAndTokenizedValue(String ticket, String tokenizedValue) {
		super();
		this.ticket = ticket;
		this.tokenizedValue = tokenizedValue;
	}

	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getTokenizedValue() {
		return tokenizedValue;
	}
	public void setTokenizedValue(String tokenizedValue) {
		this.tokenizedValue = tokenizedValue;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ticket, tokenizedValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenTicketAndTokenizedValue other = (TokenTicketAndTokenizedValue) obj;
		return Objects.equals(ticket, other.ticket) && Objects.equals(tokenizedValue, other.tokenizedValue);
	}

	@Override
	public String toString() {
		return "TokenTicketAndTokenizedValueVO [ticket=" + ticket + ", tokenizedValue=" + tokenizedValue + "]";
	}
	
}
