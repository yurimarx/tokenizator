package tech.ymservices.tokenizator.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.andreinc.mockneat.types.enums.IPv4Type;
import net.andreinc.mockneat.unit.financial.CreditCards;
import net.andreinc.mockneat.unit.networking.IPv4s;
import net.andreinc.mockneat.unit.networking.IPv6s;
import net.andreinc.mockneat.unit.regex.Regex;
import net.andreinc.mockneat.unit.types.Doubles;
import net.datafaker.Faker;
import net.datafaker.providers.base.Address;
import net.datafaker.providers.base.DateAndTime;
import net.datafaker.providers.base.Name;
import tech.ymservices.tokenizator.dao.TokenDAO;
import tech.ymservices.tokenizator.model.Token;
import tech.ymservices.tokenizator.model.TokenType;

@RestController
@RequestMapping("/token")
public class TokenizatorAPI {

	@Autowired
    private TokenDAO dao;

    @GetMapping
    public List<Token> findAll() {
        return dao.getAllTokens();
    }

    @GetMapping(value = "/query-id")
    public Token findById(@RequestBody TokenTicketAndTokenizedValue id ) {
        return dao.getTokenByTicketAndTokenizedValue(id.getTicket(), id.getTokenizedValue());
    }
    
    @GetMapping(value = "/query-ticket/{ticket}")
    public List<Token> findByTicket(@PathVariable String ticket) {
        return dao.getTokenByTicket(ticket);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean create(@RequestBody Token token) {
        return dao.createToken(token);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestBody Token token) {
        dao.deleteToken(token);
    }
    
    @PostMapping(value = "/tokenize")
    @ResponseStatus(HttpStatus.OK)
    public TokenizationResponse tokenize(@RequestBody List<TokenizationRequest> request) {
    	
    	TokenizationResponse response = new TokenizationResponse();
    	
    	List<TokenizationResponseItem> responseItemList = new ArrayList<TokenizationResponseItem>();
    	response.setTicket(getUUID());
    	
    	for(TokenizationRequest token:request) {
    		if(token.getTokenType().equals(TokenType.STARS)) {
    			tokenizeWithStars(response, responseItemList, token);
    		} else if(token.getTokenType().equals(TokenType.PERSON)) {
    			tokenizeWithPerson(response, responseItemList, token);
    		} else if(token.getTokenType().equals(TokenType.IPADDRESS)) {
    			tokenizeWithIPAdress(response, responseItemList, token);
    		} else if(token.getTokenType().equals(TokenType.NUMBER)) {
    			tokenizeWithNumber(response, responseItemList, token);
    		} else if(token.getTokenType().equals(TokenType.CREDITCARD)) {
    			tokenizeWithCreditCard(response, responseItemList, token);
    		} else if(token.getTokenType().equals(TokenType.HASH)) {
    			tokenizeWithHash(response, responseItemList, token);
    		} else if(token.getTokenType().equals(TokenType.REGEX)) {
    			tokenizeWithRegex(response, responseItemList, token);
    		}
    	}
    	saveTokenizeResults(response);
    	
    	return response;
    }

	private void tokenizeWithStars(TokenizationResponse response, 
			List<TokenizationResponseItem> responseItemList,
			TokenizationRequest token) {
		
		Integer starsPosition = Integer.valueOf(token.getSettings().get("starsPosition"));
		Integer starsQuantity = Integer.valueOf(token.getSettings().get("starsQuantity"));
		
		String starsString = StringUtils.repeat("*", starsQuantity);
		
		String originalValue = token.getOriginalValueString();
		String target = originalValue.substring(starsPosition-1, (starsPosition-1) + starsQuantity);
		String tokenizedValue = originalValue.replace(target, starsString);
		
		TokenizationResponseItem responseItem = new TokenizationResponseItem();
		responseItem.setOriginalValueString(originalValue);
		responseItem.setTokenizedValueString(tokenizedValue);
		responseItem.setField("stars");
		responseItemList.add(responseItem);
		
		response.setTokens(responseItemList);
	}
	
	private void tokenizeWithIPAdress(TokenizationResponse response, 
			List<TokenizationResponseItem> responseItemList,
			TokenizationRequest token) {
		
		String classType = token.getSettings().get("classType");
		Integer ipSize = Integer.valueOf(token.getSettings().get("ipSize"));
		
		
		String tokenizedValue = "";
		
		if(ipSize == 4) {			
			if(classType.equals("CLASS_A")) {
				tokenizedValue = IPv4s.ipv4s().types(IPv4Type.CLASS_A).get();
			} else if(classType.equals("CLASS_B")) {
				tokenizedValue = IPv4s.ipv4s().types(IPv4Type.CLASS_B).get();
			} else if(classType.equals("CLASS_C")) {
				tokenizedValue = IPv4s.ipv4s().types(IPv4Type.CLASS_C).get();
			} else if(classType.equals("CLASS_D")) {
				tokenizedValue = IPv4s.ipv4s().types(IPv4Type.CLASS_D).get();
			} else if(classType.equals("CLASS_E")) {
				tokenizedValue = IPv4s.ipv4s().types(IPv4Type.CLASS_E).get();
			} else {
				tokenizedValue = IPv4s.ipv4s().get();
			}
		} else {
			tokenizedValue = IPv6s.ipv6s().get();
		}
		
		String originalValue = token.getOriginalValueString();
		
		TokenizationResponseItem responseItem = new TokenizationResponseItem();
		responseItem.setOriginalValueString(originalValue);
		responseItem.setTokenizedValueString(tokenizedValue);
		responseItem.setField("IP");
		responseItemList.add(responseItem);
		
		response.setTokens(responseItemList);
	}
	
	private void tokenizeWithNumber(TokenizationResponse response, 
			List<TokenizationResponseItem> responseItemList,
			TokenizationRequest token) {
		
		Double minRange = Double.valueOf(token.getSettings().get("minRange"));
		Double maxRange = Double.valueOf(token.getSettings().get("maxRange"));
		
		
		Double tokenizedValue = Doubles.doubles().range(minRange, maxRange).get();
			
		Double originalValue = token.getOriginalValueNumber();
		
		TokenizationResponseItem responseItem = new TokenizationResponseItem();
		responseItem.setOriginalValueNumber(originalValue);
		responseItem.setTokenizedValueNumber(tokenizedValue);
		responseItem.setField("number");
		responseItemList.add(responseItem);
		
		response.setTokens(responseItemList);
	}
	
	private void tokenizeWithCreditCard(TokenizationResponse response, 
			List<TokenizationResponseItem> responseItemList,
			TokenizationRequest token) {
		
		String type = token.getSettings().get("type");
		
		String tokenizedValue = "";
		
		if(type.equals("VISA")) {
			tokenizedValue = CreditCards.creditCards().visa().get();
		} else if(type.equals("MASTERCARD")) {
			tokenizedValue = CreditCards.creditCards().masterCard().get();
		} else if(type.equals("AMEX")) {
			tokenizedValue = CreditCards.creditCards().amex().get();
		} else {
			tokenizedValue = CreditCards.creditCards().get();
		}
		
		String originalValue = token.getOriginalValueString();
		
		TokenizationResponseItem responseItem = new TokenizationResponseItem();
		responseItem.setOriginalValueString(originalValue);
		responseItem.setTokenizedValueString(tokenizedValue);
		responseItem.setField("creditcard");
		responseItemList.add(responseItem);
		
		response.setTokens(responseItemList);
	}
	
	private void tokenizeWithHash(TokenizationResponse response, 
			List<TokenizationResponseItem> responseItemList,
			TokenizationRequest token) {
		
		String tokenizedValue = DigestUtils.sha256Hex(token.getOriginalValueString());;
		
		String originalValue = token.getOriginalValueString();
		
		TokenizationResponseItem responseItem = new TokenizationResponseItem();
		responseItem.setOriginalValueString(originalValue);
		responseItem.setTokenizedValueString(tokenizedValue);
		responseItem.setField("hash");
		responseItemList.add(responseItem);
		
		response.setTokens(responseItemList);
	}
	
	private void tokenizeWithRegex(TokenizationResponse response, 
			List<TokenizationResponseItem> responseItemList,
			TokenizationRequest token) {
		
		String regex = token.getSettings().get("regex");
		
		String tokenizedValue = Regex.regex(regex).get();
		
		String originalValue = token.getOriginalValueString();
		
		TokenizationResponseItem responseItem = new TokenizationResponseItem();
		responseItem.setOriginalValueString(originalValue);
		responseItem.setTokenizedValueString(tokenizedValue);
		responseItem.setField("regex");
		responseItemList.add(responseItem);
		
		response.setTokens(responseItemList);
	}
	
	private void tokenizeWithPerson(TokenizationResponse response, 
			List<TokenizationResponseItem> responseItemList,
			TokenizationRequest token) {
		
		String localeLanguage = token.getSettings().get("localeLanguage");
		String localeCountry = token.getSettings().get("localeCountry");
		String withAddress = token.getSettings().get("withAddress");
		
		String originalValue = token.getOriginalValueString();
		response.setTicket(getUUID());

		
		Faker faker = new Faker(new Locale(localeLanguage, localeCountry), new Random(0));
		Name name = faker.name();
		
		addTokenizationResponseItem(responseItemList, originalValue, name.fullName(), "name.fullName");
		
		Address address = faker.address();
		if(withAddress.equals("true")) {
			addTokenizationResponseItem(responseItemList, originalValue, address.fullAddress(), "address.fullAddress");
		}
		
		DateAndTime date = faker.date();
		addTokenizationResponseItem(responseItemList, originalValue, date.birthday(),"date.birthday");
		
		response.setTokens(responseItemList);
	}

	private void addTokenizationResponseItem(
			List<TokenizationResponseItem> responseItemList, 
			String originalValue, 
			String tokenizedValue, 
			String field) {
		
		TokenizationResponseItem responseItem = new TokenizationResponseItem();
		responseItem.setOriginalValueString(originalValue);
		responseItem.setTokenizedValueString(tokenizedValue);
		responseItem.setField(field);
		responseItemList.add(responseItem);
	}
	
	private void addTokenizationResponseItem(
			List<TokenizationResponseItem> responseItemList, 
			String originalValue, 
			Timestamp tokenizedValue,
			String field) {
		
		TokenizationResponseItem responseItem = new TokenizationResponseItem();
		responseItem.setOriginalValueString(originalValue);
		responseItem.setTokenizedValueDate(tokenizedValue);
		responseItem.setField(field);
		responseItemList.add(responseItem);
	}
    
    private void saveTokenizeResults(TokenizationResponse response) {
    	
    	for(TokenizationResponseItem item:response.getTokens()) {    		
    		Token token = new Token();
    		if(item.getOriginalValueString() != null) {    			
    			token.setOriginalValue(item.getOriginalValueString());
    		} else if(item.getOriginalValueDate() != null) {
    			token.setOriginalValue(item.getOriginalValueDate().toString());
    		} else if(item.getOriginalValueNumber() != null) {
    			token.setOriginalValue(item.getOriginalValueNumber().toString());
    		}
    		
    		if(item.getTokenizedValueString() != null) {    			
    			token.setTokenizedValue(item.getTokenizedValueString());
    		} else if(item.getTokenizedValueDate() != null) {
    			token.setTokenizedValue(item.getTokenizedValueDate().toString());
    		} else if(item.getTokenizedValueNumber() != null) {
    			token.setTokenizedValue(item.getTokenizedValueNumber().toString());
    		}
    		token.setTicket(response.getTicket());
    		token.setField(item.getField());
    		dao.createToken(token);
    	}
    }
    
    private String getUUID() {
    	UUID uuid = UUID.randomUUID();
    	return uuid.toString();
    }
}

