package br.com.fiap.bank.exceptions;

public class InvalidDeposit extends RuntimeException {

    public InvalidDeposit(String message) {
        super(message);
    }
}
