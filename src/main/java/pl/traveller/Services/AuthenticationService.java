package pl.traveller.Services;

import org.springframework.http.HttpHeaders;

public interface AuthenticationService {

    boolean authenticate(HttpHeaders httpHeaders, long userId);

}
