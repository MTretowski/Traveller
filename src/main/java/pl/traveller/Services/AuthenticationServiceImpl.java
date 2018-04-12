package pl.traveller.Services;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import pl.traveller.Config.Security.TokenAuthenticationService;
import pl.traveller.Entities.UserEntity;
import pl.traveller.Repositories.UserRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserRepository userRepository;
    private String secret;
    private String headerString;


    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
        this.secret = TokenAuthenticationService.getSECRET();
        this.headerString = TokenAuthenticationService.getHeaderString();
    }

    private String getUsernameFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    @Override
    public boolean authenticate(HttpHeaders httpHeaders, long userId) {
        String token = httpHeaders.getFirst(headerString);
        if(token == null){
            return false;
        }
        else {
            String username = getUsernameFromToken(token);
            UserEntity userEntity = userRepository.findByUsername(username);
            return userEntity != null && userEntity.getId() == userId;
        }
    }
}
