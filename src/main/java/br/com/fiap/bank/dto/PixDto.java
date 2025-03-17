package br.com.fiap.bank.dto;

public record PixDto(Long contaOrigem,
                     Long contaDestino,
                     double valor) {
}
