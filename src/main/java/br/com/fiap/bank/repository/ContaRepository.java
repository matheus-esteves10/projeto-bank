package br.com.fiap.bank.repository;

import br.com.fiap.bank.model.ContaUsuario;
import br.com.fiap.bank.model.StatusContaAtiva;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContaRepository {

    List<ContaUsuario> repository = new ArrayList<>();

    public List<ContaUsuario> index() {
        return repository;
    }

    public ContaUsuario getById(Long numeroConta) {
        return getContaUsuario(numeroConta);
    }

    public ContaUsuario getByCpf(String cpf) {
        return repository.stream()
                .filter(conta -> conta.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }

    public ContaUsuario create(ContaUsuario conta) {
            repository.add(conta);
            return conta;
    }

    public ContaUsuario update(Long numeroConta) {

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