package br.com.fiap.bank.service;

import br.com.fiap.bank.dto.MovimentacaoDto;
import br.com.fiap.bank.model.ContaUsuario;
import br.com.fiap.bank.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repository;

    public ContaUsuario deposito(MovimentacaoDto movimentacaoDto) {

        var contaDestino = repository.getById(movimentacaoDto.contaDestino());
        contaDestino.depositar(movimentacaoDto.valor());

        return contaDestino;
    }

    public ContaUsuario saque(MovimentacaoDto movimentacaoDto) {

        var contaDestino = repository.getById(movimentacaoDto.contaOrigem());
        contaDestino.sacar(movimentacaoDto.valor());

        return contaDestino;
    }

    public ContaUsuario createPix (MovimentacaoDto dto) {

        var contaOrigem = repository.getById(dto.contaOrigem());
        var contaDestino = repository.getById(dto.contaDestino());

            contaOrigem.sacar(dto.valor());
            contaDestino.depositar(dto.valor());

        return contaOrigem;
    }
}
