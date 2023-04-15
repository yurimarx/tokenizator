package tech.ymservices.tokenizator.api;

import java.util.List;

public class TokenizationResponse {
	
	private String ticket;
	private List<TokenizationResponseItem> tokens;
	
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public List<TokenizationResponseItem> getTokens() {
		return tokens;
	}
	public void setTokens(List<TokenizationResponseItem> tokens) {
		this.tokens = tokens;
	}
	
	

}
