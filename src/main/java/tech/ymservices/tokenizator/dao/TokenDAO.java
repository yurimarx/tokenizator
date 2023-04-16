package tech.ymservices.tokenizator.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import tech.ymservices.tokenizator.model.Token;
import tech.ymservices.tokenizator.model.TokenMapper;

@Component
public class TokenDAO {
	
	JdbcTemplate jdbcTemplate;

	private final String SQL_FIND = "select * from Token where Ticket = ? and TokenizedValue = ?";
	private final String SQL_FIND_TICKET = "select * from Token where Ticket = ?";
	private final String SQL_DELETE = "delete from Token where Ticket = ? and TokenizedValue = ?";
	private final String SQL_UPDATE = "update Token set Ticket = ?, OriginalValue = ?, TokenizedValue  = ?, Field = ? where Ticket = ? and TokenizedValue = ? and Field = ?";
	private final String SQL_GET_ALL = "select * from Token";
	private final String SQL_INSERT = "insert into Token(Ticket, OriginalValue, TokenizedValue, Field) values(?,?,?,?)";

	public TokenDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Token getTokenByTicketAndTokenizedValue(String ticket, String tokenizedValue) {
		return jdbcTemplate.queryForObject(
				SQL_FIND, 
				new TokenMapper(), ticket, tokenizedValue);
	}
	
	public List<Token> getTokenByTicket(String ticket) {
		return jdbcTemplate.query(
				SQL_FIND_TICKET, 
				new TokenMapper(), ticket);
	}

	public List<Token> getAllTokens() {
		return jdbcTemplate.query(SQL_GET_ALL, new TokenMapper());
	}

	public boolean deleteToken(Token token) {
		return jdbcTemplate.update(SQL_DELETE, token.getTicket(), token.getTokenizedValue()) > 0;
	}

	public boolean updateToken(Token token) {
		return jdbcTemplate.update(SQL_UPDATE, 
				token.getTicket(), 
				token.getOriginalValue(), 
				token.getTokenizedValue(),
				token.getField(),
				token.getTicket(), token.getTokenizedValue()) > 0;
	}

	public boolean createToken(Token token) {
		return jdbcTemplate.update(SQL_INSERT, 
				token.getTicket(), 
				token.getOriginalValue(), 
				token.getTokenizedValue(),
				token.getField()) > 0;
	}
}
