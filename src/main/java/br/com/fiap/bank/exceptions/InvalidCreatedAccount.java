package br.com.fiap.bank.exceptions;

public class InvalidCreatedAccount extends RuntimeException {

    public InvalidCreatedAccount(String message) {
        super(message);
    }
}
