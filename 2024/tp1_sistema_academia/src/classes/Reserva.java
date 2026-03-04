/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import enums.Status;

/**
 *
 * @author rafae
 */
public class Reserva {
    protected Status status;
    protected Aluno aluno;
    protected Aula aula;

    public Reserva(Status status, Aluno aluno, Aula aula) {
        this.status = status;
        this.aluno = aluno;
        this.aula = aula;
    }

    // Getters e Setters
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Aula getAula() {
        return aula;
    }
}