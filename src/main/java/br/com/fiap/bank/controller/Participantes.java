package br.com.fiap.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Participantes {

    @GetMapping
    public String retornaParticipantes(){
        return """
                Pz Bank 
                Matheus Esteves (rm554769) e 
                Gabriel Martins Falanga (rm555061)
                """;
    }
}
