/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import enums.Planos;
import enums.Status;
import enums.Unidade;
import java.util.ArrayList;
import java.util.InputMismatchException;
import static telas.TelaInicial.listaReservas;

/**
 *
 * @author rafae
 */
public class Aluno extends Pessoa implements Autenticavel {
    protected float mensalidade;
    protected ArrayList<Reserva> reservas;
    protected Planos plano;
    protected int limite;

    public Aluno(String nome, String cpf, char sexo, String email, String celular, String senha, Unidade unidade, Planos plano) {
        super(nome, cpf, sexo, email, celular, senha, unidade);
        this.plano = plano;
        switch (plano) {
            case Planos.Normal:
                this.limite = 2;
                if (this.unidade.equals(Unidade.Geral)) {
                    this.mensalidade = 130;
                } else {
                this.mensalidade = 105;
                }
                break;
            case Planos.Gold:
                this.limite = 5;
                if (this.unidade.equals(Unidade.Geral)) {
                    this.mensalidade = 150;
                } else {
                this.mensalidade = 130;
                }
                break;
            case Planos.Platinum:
                this.limite = 10;
                if (this.unidade.equals(Unidade.Geral)) {
                    this.mensalidade = 200;
                }
                this.mensalidade = 180;
                break;
            default:
                break;
        }
        this.reservas = new ArrayList<>();
    }

    // Getters e Setters
    @Override
    public String getCPF() {
        return this.cpf;
    }
    
    @Override
    public String getNome() {
        return this.nome;
    }
    
    @Override
    public boolean validarSenha(String senha) {
        return this.senha.equals(senha);
    }
    
    public float getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(float mensalidade) {
        this.mensalidade = mensalidade;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }
   
    public int sizeReservas() {
        return this.reservas.size();
    }

    public int getLimite() {
        return limite;
    }

    public Planos getPlano() {
        return plano;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public void setPlano(Planos plano) {
        this.plano = plano;
    }

    // Funções auxiliares
    public void criarReserva(Aula aula) {
        for (Reserva reserva : reservas) {
            if (reserva.getAula().equals(aula)) {
                System.out.println("Você já tem uma reserva para essa aula.");
                return;
            }
        }
        Reserva novaReserva = new Reserva(Status.Pendente, this, aula);
        aula.adicionarReserva(novaReserva);
        reservas.add(novaReserva);
        listaReservas.add(novaReserva);
    }

    public void confirmarReserva(Reserva reserva) {
        reserva.setStatus(Status.Confirmada);
    }

    public void cancelarReserva(Reserva reserva) {
        reserva.setStatus(Status.Cancelada);
    }
    
    public boolean validarCPF(String CPF) {
        // função de checar por CPF válido, copiado de https://www.devmedia.com.br/validando-o-cpf-em-uma-aplicacao-java/22097
            if 
           (CPF.equals("00000000000") || CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
           (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere "0" no inteiro 0
        // (48 eh a posicao de "0" na tabela ASCII)
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
                } catch (InputMismatchException erro) {
                return(false);
            }     
        
    }
}


// será que vai dar o meu push, por favor pelamor - Schoen 