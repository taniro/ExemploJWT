package ufrn.br.exemplojwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ufrn.br.exemplojwt.dto.LoginDTO;
import ufrn.br.exemplojwt.dto.TokenDTO;
import ufrn.br.exemplojwt.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity<TokenDTO> auth(@RequestBody @Validated LoginDTO loginDTO){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        try{
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenDTO("Bearer", token));

        } catch (Exception e){
            throw new UsernameNotFoundException("Username not found") ;
        }
    }

}