package br.com.fiap.bank.dto;

public record MovimentacaoDto(Long contaOrigem,
                              Long contaDestino,
                              double valor) {
}
