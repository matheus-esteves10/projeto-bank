package br.com.fiap.bank.exceptions;

public class InvalidWithdraw extends RuntimeException {

    public InvalidWithdraw(String message) {
        super(message);
    }
}
