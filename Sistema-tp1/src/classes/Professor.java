/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import enums.Especialidade;
import enums.Unidade;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import static telas.TelaInicial.listaAulas;
// imports do PDF agora

/**
 *
 * @author rafae
 */
public class Professor extends Pessoa implements Autenticavel {
    protected Especialidade especialidade;
    protected float salario;
    protected ArrayList<Aula> aulas;
    protected boolean admin;

    public Professor(String nome, String cpf, char sexo, String email, String celular, String senha, Unidade unidade,
                     Especialidade especialidade, float salario, boolean admin) {
        super(nome, cpf, sexo, email, celular, senha, unidade);
        this.especialidade = especialidade;
        this.salario = salario;
        this.aulas = new ArrayList<>();
        this.admin = admin;
    }
    
    // construtor principalmente pro admin aqui
    public Professor(String nome, String email, String senha, boolean admin) {
        super(nome, "admin", 'H', email, "61991307015" ,senha, null);
        this.especialidade = Especialidade.Musculacao;
        this.unidade = Unidade.Geral;
        this.salario = 0;
        this.aulas = new ArrayList<>();
        this.admin = admin;
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

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public ArrayList<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(ArrayList<Aula> aulas) {
        this.aulas = aulas;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    // Funções auxiliares
    public void criarAula(Aula aula) {
        for (Aula a : aulas) {
            if (a.getData().equals(aula.getData())) {
                System.out.println("Já existe uma aula nesse horário na unidade " + this.unidade);
                return;
            }
        }
        aulas.add(aula);
        listaAulas.add(aula);
    }

    public void editarAula(Aula aula, Timestamp novaData, String novoNome, int novaCapacidade, int novaComissao, String novosDetalhes) {
        aula.setData(novaData);
        aula.setNome(novoNome);
        aula.setCapacidade(novaCapacidade);
        aula.setcomissao(novaComissao);
        aula.setDetalhes(novosDetalhes);
    }

    public void excluirAula(Aula aula) {
        aulas.remove(aula);
    }

    public float gerarSalarioTotal() {
        float salarioTotal = salario;
        for (Aula aula : aulas) {
            salarioTotal += aula.getcomissao();
        }
        return salarioTotal;
    }

    // fazer relatório?
    
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
    
    public boolean gerarRelatorioMes() {
        // gerar relatório de produtividade do mês do trabalhador
        Calendar calendario = Calendar.getInstance();
        int mes = calendario.get(Calendar.MONTH); // de 0 a 11
        int ano = calendario.get(Calendar.YEAR);
        
        ArrayList<Aula> aulasDoMes = new ArrayList<>();
        float comissaoTotal = 0;
        
        // filtrando as aulas que aconteceram no mês
        for (Aula aula : aulas) {
            Timestamp dataAula = aula.getData();
            Calendar calendarioAula = Calendar.getInstance();
            calendarioAula.setTime(dataAula);
            int mesAula = calendarioAula.get(Calendar.MONTH);
            int anoAula = calendarioAula.get(Calendar.YEAR);
            if (mes == mesAula && ano == anoAula) {
                aulasDoMes.add(aula);
                comissaoTotal += aula.getcomissao();
            }
        }
        
        float salarioMes = this.salario +  comissaoTotal;
        
        // criando o documento PDF
        try {
            // Arrumando para ele ir direto para a pasta de downloads
            String user = System.getProperty("user.home");
            String downloadPath = user + File.separator + "Downloads" + File.separator + "relatorio.pdf";            
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(downloadPath));
            document.open();
            
            // Cabeçalho
            document.add(new Paragraph("Relatório de aulas - Mês atual"));
            document.add(new Paragraph("Professor: " + this.getNome()));
            document.add(new Paragraph("Período: " + (mes + 1) + "/" + ano));
            document.add(new Paragraph("Salário Base: R$ " + String.format("%.2f", this.salario)));
            document.add(new Paragraph("Total de comissões: R$ " + String.format("%.2f", comissaoTotal)));
            document.add(new Paragraph("Compensação líquida total: R$ " + String.format("%.2f", salarioMes)));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("INFORMAÇÕES SOBRE AS AULAS"));
            
            // para cada aula
            for (Aula aula : aulasDoMes) {
                document.add(new Paragraph("Nome da Aula: " + aula.getNome()));
                document.add(new Paragraph("Data/Hora: " + aula.getData().toString()));
                document.add(new Paragraph("Capacidade: " + aula.getCapacidade()));
                document.add(new Paragraph("Comissão: R$ " + String.format("%.2f", aula.getcomissao())));
                document.add(new Paragraph("Detalhes: " + aula.getDetalhes()));
                document.add(new Paragraph("Alunos Inscritos:"));
                document.add(new Paragraph(" "));
                
                // criando uma tabela para mostrar os alunos inscritos
                PdfPTable tabelaReservas = new PdfPTable(3);
                tabelaReservas.addCell("Aluno");
                tabelaReservas.addCell("Email do aluno");
                tabelaReservas.addCell("Status da reserva");
                
                ArrayList<Reserva> reservas = aula.getReservas();
                if (reservas != null && !reservas.isEmpty()) {
                    for (Reserva reserva : reservas) {
                        tabelaReservas.addCell(reserva.getAluno().getNome());
                        tabelaReservas.addCell(reserva.getAluno().getEmail());
                        tabelaReservas.addCell(reserva.getStatus().toString());
                    }
                } else {
                    tabelaReservas.addCell("Nenhum aluno inscrito");
                    tabelaReservas.addCell("-");
                    tabelaReservas.addCell("-");
                }
                document.add(tabelaReservas);
                document.add(new Paragraph(" "));
            }
            
            document.close();
            System.out.println("PDF Gerado com sucesso em " + downloadPath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


