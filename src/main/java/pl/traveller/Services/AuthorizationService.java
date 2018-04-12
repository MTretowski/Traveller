package pl.traveller.Services;

import org.springframework.http.HttpHeaders;

public interface AuthorizationService {

    boolean authenticate(HttpHeaders httpHeaders, long userId);

}
