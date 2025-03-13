package br.com.fiap.bank.controller;

import br.com.fiap.bank.exceptions.InvalidCreatedAccount;
import br.com.fiap.bank.model.ContaUsuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaController {

    List<ContaUsuario> repository = new ArrayList<>();

    @GetMapping()
    public List<ContaUsuario> index() {
        return repository;
    }

    @PostMapping()
    public ResponseEntity<?> create (@RequestBody ContaUsuario conta) {
        try {
            repository.add(conta);
            return ResponseEntity.status(201).body(conta);
        } catch (InvalidCreatedAccount e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @GetMapping("/{numeroConta}")
    public ContaUsuario getById(@PathVariable Long numeroConta) {
        return repository.stream()
                .filter(conta -> conta.getNumeroConta().equals(numeroConta))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }


    @GetMapping("/cpf/{cpf}")
    public ContaUsuario getByCpf(@PathVariable String cpf) {
        return repository.stream()
                .filter(conta -> conta.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Conta não encontrada para o CPF: " + cpf));
    }







}
