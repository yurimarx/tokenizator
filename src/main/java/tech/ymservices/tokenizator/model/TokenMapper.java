package tech.ymservices.tokenizator.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TokenMapper implements RowMapper<Token> {

	@Override
	public Token mapRow(ResultSet resultSet, int i) throws SQLException {
		Token token = new Token();
		token.setOriginalValue(resultSet.getString("OriginalValue"));
		token.setTokenizedValue(resultSet.getString("TokenizedValue"));
		token.setTicket(resultSet.getString("Ticket"));
		return token;
	}

}
