package br.com.fiap.bank.controller;

import br.com.fiap.bank.dto.MovimentacaoDto;
import br.com.fiap.bank.exceptions.InvalidCreatedAccount;
import br.com.fiap.bank.exceptions.InvalidDeposit;
import br.com.fiap.bank.exceptions.InvalidWithdraw;
import br.com.fiap.bank.model.ContaUsuario;
import br.com.fiap.bank.model.StatusContaAtiva;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private Logger log = LoggerFactory.getLogger(getClass());
    List<ContaUsuario> repository = new ArrayList<>();

    @GetMapping()
    public List<ContaUsuario> index() {
        return repository;
    }

    @GetMapping("/{numeroConta}")
    public ContaUsuario getById(@PathVariable Long numeroConta) {
        return getContaUsuario(numeroConta);
    }

    @GetMapping("/cpf/{cpf}")
    public ContaUsuario getByCpf(@PathVariable String cpf) {
        return repository.stream()
                .filter(conta -> conta.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ContaUsuario conta) {
        log.info("Cadastrando conta número " + conta.getNumeroConta());

        try {
            repository.add(conta);
            return ResponseEntity.status(201).body(conta);
        } catch (InvalidCreatedAccount e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @PatchMapping("/deposito")
    public ResponseEntity<?> deposito(@RequestBody MovimentacaoDto movimentacaoDto) {
        log.info("Fazendo depósito de " + movimentacaoDto.valor() + " para a conta " + movimentacaoDto.contaDestino());

        var contaDestino = getContaUsuario(movimentacaoDto.contaDestino());
        try {
            contaDestino.depositar(movimentacaoDto.valor());
        } catch (InvalidDeposit e) {
            return ResponseEntity.status(400).body(e);
        }

        return ResponseEntity.status(200).body(contaDestino);
    }

    @PatchMapping("/saque")
    public ResponseEntity<?> saque(@RequestBody MovimentacaoDto movimentacaoDto) {
        log.info("Sacando R$" + movimentacaoDto.valor() + " da conta " + movimentacaoDto.contaOrigem());

        var contaOrigem = getContaUsuario(movimentacaoDto.contaOrigem());
        try {
            contaOrigem.sacar(movimentacaoDto.valor());
        } catch (InvalidWithdraw e) {
            return ResponseEntity.status(400).body(e);
        }

        return ResponseEntity.status(200).body(contaOrigem);
    }

    @PutMapping("/pix")
    public ResponseEntity<?> createPix (@RequestBody MovimentacaoDto dto) {
        log.info("Fazendo Pix da conta " + dto.contaOrigem() + " para a conta " + dto.contaDestino());

        var contaOrigem = getContaUsuario(dto.contaOrigem());
        var contaDestino = getContaUsuario(dto.contaDestino());
        try {
            contaOrigem.sacar(dto.valor());
            contaDestino.depositar(dto.valor());
        } catch (InvalidWithdraw | InvalidDeposit e) {
            return ResponseEntity.status(400).body(e);
        }

        return ResponseEntity.status(200).body(contaOrigem);
    }

    @PatchMapping("/desativar/{numeroConta}")
    public ContaUsuario update(@PathVariable Long numeroConta) {
        log.info("Marcando a conta " + numeroConta + " como inativa.");

        var conta = getContaUsuario(numeroConta);
        conta.setAtiva(StatusContaAtiva.N);
        return conta;
    }

    private ContaUsuario getContaUsuario(Long numeroConta) {
        return repository.stream()
                .filter(conta -> conta.getNumeroConta().equals(numeroConta))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }
}
