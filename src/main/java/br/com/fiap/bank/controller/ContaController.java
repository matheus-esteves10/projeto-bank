package br.com.fiap.bank.controller;

import br.com.fiap.bank.dto.MovimentacaoDto;
import br.com.fiap.bank.exceptions.InvalidCreatedAccount;
import br.com.fiap.bank.exceptions.InvalidDeposit;
import br.com.fiap.bank.exceptions.InvalidWithdraw;
import br.com.fiap.bank.model.ContaUsuario;
import br.com.fiap.bank.repository.ContaRepository;
import br.com.fiap.bank.service.ContaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private Logger log = LoggerFactory.getLogger(getClass());
    private ContaRepository repository;
    private ContaService service;

    public ContaController(ContaRepository repository, ContaService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping()
    public List<ContaUsuario> index() {
        return repository.index();
    }

    @GetMapping("/{numeroConta}")
    public ContaUsuario getById(@PathVariable Long numeroConta) {
        return repository.getById(numeroConta);
    }

    @GetMapping("/cpf/{cpf}")
    public ContaUsuario getByCpf(@PathVariable String cpf) {
        return repository.getByCpf(cpf);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ContaUsuario conta) {
        log.info("Cadastrando conta número " + conta.getNumeroConta());

        try {
            repository.create(conta);
            return ResponseEntity.status(201).body(conta);
        } catch (InvalidCreatedAccount e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @PatchMapping("/deposito")
    public ResponseEntity<?> deposito(@RequestBody MovimentacaoDto movimentacaoDto) {
        log.info("Fazendo depósito de " + movimentacaoDto.valor() + " para a conta " + movimentacaoDto.contaDestino());

        try {
            var contaDestino = service.deposito(movimentacaoDto);
            return ResponseEntity.status(200).body(contaDestino);
        } catch (InvalidDeposit e) {
            return ResponseEntity.status(400).body(e);
        }


    }

    @PatchMapping("/saque")
    public ResponseEntity<?> saque(@RequestBody MovimentacaoDto movimentacaoDto) {
        log.info("Sacando R$" + movimentacaoDto.valor() + " da conta " + movimentacaoDto.contaOrigem());

        try {
            var contaOrigem= service.saque(movimentacaoDto);
            return ResponseEntity.status(200).body(contaOrigem);
        } catch (InvalidWithdraw e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @PutMapping("/pix")
    public ResponseEntity<?> createPix (@RequestBody MovimentacaoDto dto) {
        log.info("Fazendo Pix da conta " + dto.contaOrigem() + " para a conta " + dto.contaDestino());

        try {
            var contaOrigem = service.createPix(dto);
            return ResponseEntity.status(200).body(contaOrigem);
        } catch (InvalidWithdraw | InvalidDeposit e) {
            return ResponseEntity.status(400).body(e);
        }

    }

    @PatchMapping("/desativar/{numeroConta}")
    public ContaUsuario update(@PathVariable Long numeroConta) {
        log.info("Marcando a conta " + numeroConta + " como inativa.");

        var conta = repository.update(numeroConta);
        return conta;
    }

}
