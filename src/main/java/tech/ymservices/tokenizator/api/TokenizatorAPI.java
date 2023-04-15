package tech.ymservices.tokenizator.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tech.ymservices.tokenizator.dao.TokenDAO;
import tech.ymservices.tokenizator.model.Token;

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
    public List<Token> findByTicket(@RequestParam String ticket) {
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
    

}
