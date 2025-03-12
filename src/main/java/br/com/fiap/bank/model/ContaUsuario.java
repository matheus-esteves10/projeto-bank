package br.com.fiap.bank.model;

import br.com.fiap.bank.exceptions.InvalidCreatedAccount;
import java.time.LocalDate;
import java.util.Objects;

public class ContaUsuario {

    private Long numeroConta;
    private int agencia;
    private String nomeTitular;
    private String cpf;
    private LocalDate dataAbertura;
    private double saldo;
    private String ativa;
    private TipoConta tipo;

    public ContaUsuario() {
    }

    public ContaUsuario(Long numeroConta, int agencia, String nomeTitular, String cpf,
                        LocalDate dataAbertura, double saldo, String ativa, TipoConta tipo) {
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        setNomeTitular(nomeTitular);
        setCpf(cpf);
        setDataAbertura(dataAbertura);
        setSaldo(saldo);
        this.ativa = ativa;
        setTipo(tipo);
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

    public String getAtiva() {
        return ativa;
    }

    public void setAtiva(String ativa) {
        this.ativa = ativa;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) { //validação feita com enum
        this.tipo = tipo;
    }
}
