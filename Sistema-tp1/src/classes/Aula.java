/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author rafae
 */
public class Aula {
    protected String nome;
    protected int capacidade;
    protected float comissao;
    protected String detalhes;
    protected Timestamp data;
    protected Professor professor;
    protected ArrayList<Reserva> reservas;

    public Aula(String nome, int capacidade, int comissao, String detalhes, Timestamp data, Professor professor) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.comissao = comissao;
        this.detalhes = detalhes;
        this.data = data;
        this.professor = professor;
        this.reservas = new ArrayList<>();
        
        professor.criarAula(this);
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public float getcomissao() {
        return comissao;
    }

    public void setcomissao(int comissao) {
        this.comissao = comissao;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public Timestamp getData() {
        return this.data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void adicionarReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }
    
    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    // Função para exibir detalhes da aula
    public void exibirDetalhes() {
        System.out.println("Aula: " + nome);
        System.out.println("Data: " + data);
        System.out.println("Capacidade: " + capacidade);
        System.out.println("Detalhes: " + detalhes);
    }
}

