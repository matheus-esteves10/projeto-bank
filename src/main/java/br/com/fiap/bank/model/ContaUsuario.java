package br.com.fiap.bank.model;

import br.com.fiap.bank.exceptions.InvalidCreatedAccount;
import br.com.fiap.bank.exceptions.InvalidDeposit;
import br.com.fiap.bank.exceptions.InvalidWithdraw;

import java.time.LocalDate;

public class ContaUsuario {

    private Long numeroConta;
    private int agencia;
    private String nomeTitular;
    private String cpf;
    private LocalDate dataAbertura;
    private double saldo;
    private StatusContaAtiva ativa = StatusContaAtiva.S;
    private TipoConta tipo;

    public ContaUsuario() {
    }

    public ContaUsuario(Long numeroConta, int agencia, String nomeTitular, String cpf,
                        LocalDate dataAbertura, double saldo, TipoConta tipo) {
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        setNomeTitular(nomeTitular);
        setCpf(cpf);
        setDataAbertura(dataAbertura);
        setSaldo(saldo);
        this.tipo = tipo;
        this.ativa = StatusContaAtiva.S;
    }

    public Long getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Long numeroConta) {
        this.numeroConta = numeroConta;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        if (nomeTitular == null || nomeTitular.trim().isEmpty()) {
            throw new InvalidCreatedAccount("O nome do titular é obrigatório.");
        }
        this.nomeTitular = nomeTitular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new InvalidCreatedAccount("O CPF do titular é obrigatório.");
        }
        this.cpf = cpf;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        if (dataAbertura == null) {
            throw new InvalidCreatedAccount("A data de abertura é obrigatória.");
        }
        if (dataAbertura.isAfter(LocalDate.now())) {
            throw new InvalidCreatedAccount("A data de abertura não pode ser maior que a data atual.");
        }
        this.dataAbertura = dataAbertura;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        if (saldo < 0) {
            throw new InvalidCreatedAccount("O saldo inicial não pode ser negativo.");
        }
        this.saldo = saldo;
    }

    public StatusContaAtiva getAtiva() {
        return ativa;
    }

    public void setAtiva(StatusContaAtiva ativa) {
        this.ativa = ativa;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) { //validação feita com enum
        this.tipo = tipo;
    }

    public void depositar(double valor) {
        if (valor < 0) {
            throw new InvalidDeposit("O valor do depósito não pode ser negativo.");
        }
        saldo += valor;
    }

    public void sacar(double valor) {
        if (saldo < valor) {
            throw new InvalidWithdraw("O valor do saque não pode ser maior do que o saldo.");
        };
        if (valor < 0) {
            throw new InvalidWithdraw("O valor do saque não pode ser negativo.");
        }
        saldo -= valor;
    }
}
