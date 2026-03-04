/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telas;

import classes.Aluno;
import classes.Aula;
import classes.Professor;
import classes.Reserva;
import enums.Especialidade;
import enums.Planos;
import enums.Unidade;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.ImageIcon;
/**
 *
 * @author rafae
 */
public class TelaInicial extends javax.swing.JFrame {
    // criando listas e elementos placeholder
    public static ArrayList<Aluno> listaAlunos = new ArrayList<>();
    public static ArrayList<Professor> listaProfessores = new ArrayList<>();
    public static ArrayList<Aula> listaAulas = new ArrayList<>();
    public static ArrayList<Reserva> listaReservas = new ArrayList<>();
    

    
    public TelaInicial() {
        initComponents();
        adicionarDadosIniciais();
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/gym.png"));
        this.setIconImage(icon.getImage());
        this.setTitle("Painel CodeFit");
    }

private void adicionarDadosIniciais() {
    if (!listaProfessores.isEmpty() || !listaAlunos.isEmpty() || !listaAulas.isEmpty()) {
        return;
    }

    // Criar professores para cada unidade
    Professor admin = new Professor("admin", "admin@codefit.com", "admin", true);
    
    // Unidade Sul
    Professor andre = new Professor("André Luis", "51582341168", 'M', "andre@codefit.com", "21993559249", "senha", Unidade.Sul, Especialidade.Luta, 2000.0f, false);
    // Unidade Sudoeste
    Professor maria = new Professor("Maria Oliveira", "98765432109", 'F', "maria@gmail.com", "61991306015", "123", Unidade.Sudoeste, Especialidade.Funcional, 3200.0f, false);
    Professor carla = new Professor("Carla Silva", "12345678901", 'F', "carla@codefit.com", "61987654321", "senha", Unidade.Sudoeste, Especialidade.Pilates, 2500.0f, false);
    // Unidade Norte
    Professor joao = new Professor("João Santos", "11122233344", 'M', "joao@codefit.com", "61911112222", "senha", Unidade.Norte, Especialidade.Crossfit, 3000.0f, false);
    Professor ana = new Professor("Ana Paula", "55566677788", 'F', "ana@codefit.com", "61933334444", "senha", Unidade.Norte, Especialidade.Danca, 2800.0f, false);
    // Unidade Noroeste
    Professor carlos = new Professor("Carlos Silva", "99988877766", 'M', "carlos@codefit.com", "61955556666", "senha", Unidade.Noroeste, Especialidade.Danca, 2500.0f, false);
    Professor pedro = new Professor("Pedro Alves", "44455566677", 'M', "pedro@codefit.com", "61977778888", "senha", Unidade.Noroeste, Especialidade.Musculacao, 2700.0f, false);

    // Adicionar professores às listas
    listaProfessores.addAll(List.of(admin, andre, maria, carla, joao, ana, carlos, pedro));

    // Criar alunos para cada unidade
    // Unidade Sul
    Aluno alunoSul1 = new Aluno("Rafael Dias", "19943781742", 'M', "rafael@codefit.com", "21993556015", "senha", Unidade.Sul, Planos.Normal);
    Aluno alunoSul2 = new Aluno("Fernanda Lima", "12345678901", 'F', "fernanda@codefit.com", "21999999999", "senha", Unidade.Sul, Planos.Platinum);
    // Unidade Sudoeste
    Aluno alunoSudoeste1 = new Aluno("Luana Pereira", "98765432109", 'F', "luana@codefit.com", "11944444444", "senha", Unidade.Sudoeste, Planos.Gold);
    Aluno alunoSudoeste2 = new Aluno("Bruno Rocha", "56789012345", 'M', "bruno@codefit.com", "11955555555", "senha", Unidade.Sudoeste, Planos.Normal);
    // Unidade Norte
    Aluno alunoNorte1 = new Aluno("Felipe Santos", "11122233344", 'M', "felipe@codefit.com", "11933333333", "senha", Unidade.Norte, Planos.Normal);
    Aluno alunoNorte2 = new Aluno("Juliana Costa", "55566677788", 'F', "juliana@codefit.com", "11922222222", "senha", Unidade.Norte, Planos.Platinum);
    // Unidade Noroeste
    Aluno alunoNoroeste1 = new Aluno("Gabriel Souza", "99988877766", 'M', "gabriel@codefit.com", "11911111111", "senha", Unidade.Noroeste, Planos.Gold);
    Aluno alunoNoroeste2 = new Aluno("Patricia Lima", "44455566677", 'F', "patricia@codefit.com", "11900000000", "senha", Unidade.Noroeste, Planos.Normal);

    // Adicionar alunos às listas
    listaAlunos.addAll(List.of(alunoSul1, alunoSul2, alunoSudoeste1, alunoSudoeste2, alunoNorte1, alunoNorte2, alunoNoroeste1, alunoNoroeste2));

    // Criar aulas
    // Unidade Sul
    Aula aulaSul1 = new Aula("Musculação", 12, 80, "Aula intensa de musculação", Timestamp.valueOf("2025-02-22 14:00:00"), admin);
    Aula aulaSul2 = new Aula("Luta", 12, 100, "Aula de defesa pessoal", Timestamp.valueOf("2025-02-23 18:00:00"), andre);
    // Unidade Sudoeste
    Aula aulaSudoeste1 = new Aula("Funcional", 8, 90, "Aula de funcional para iniciantes", Timestamp.valueOf("2025-02-24 09:00:00"), maria);
    Aula aulaSudoeste2 = new Aula("Pilates", 8, 100, "Aula de Pilates avançado", Timestamp.valueOf("2025-02-25 16:00:00"), carla);
    // Unidade Norte
    Aula aulaNorte1 = new Aula("Crossfit", 15, 100, "Aula de Crossfit intenso", Timestamp.valueOf("2025-02-26 17:00:00"), joao);
    Aula aulaNorte2 = new Aula("Dança", 20, 80, "Aula de dança contemporânea", Timestamp.valueOf("2025-02-27 19:00:00"), ana);
    // Unidade Noroeste
    Aula aulaNoroeste1 = new Aula("Dança", 20, 80, "Aula de dança de salão", Timestamp.valueOf("2025-02-28 20:00:00"), carlos);
    Aula aulaNoroeste2 = new Aula("Musculação", 12, 80, "Aula de musculação para iniciantes", Timestamp.valueOf("2025-02-29 08:00:00"), pedro);

    // Adicionar reservas
    alunoSul1.criarReserva(aulaSul2); // Rafael reservou Musculação
    alunoSudoeste1.criarReserva(aulaSudoeste1); // Luana reservou Funcional
    alunoNorte2.criarReserva(aulaNorte2); // Juliana reservou Dança
    // Os demais alunos não possuem reservas
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        painelBotao = new javax.swing.JPanel();
        botaoProfessor = new javax.swing.JButton();
        botaoAluno = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        painel.setBackground(new java.awt.Color(255, 255, 255));

        titulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Bem-vindo à CodeFit!");

        painelBotao.setBackground(new java.awt.Color(255, 255, 255));

        botaoProfessor.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        botaoProfessor.setText("Sou professor");
        botaoProfessor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoProfessorActionPerformed(evt);
            }
        });

        botaoAluno.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        botaoAluno.setText("Sou aluno");
        botaoAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAlunoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelBotaoLayout = new javax.swing.GroupLayout(painelBotao);
        painelBotao.setLayout(painelBotaoLayout);
        painelBotaoLayout.setHorizontalGroup(
            painelBotaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelBotaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botaoAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
                .addComponent(botaoProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painelBotaoLayout.setVerticalGroup(
            painelBotaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBotaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelBotaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon.png"))); // NOI18N

        javax.swing.GroupLayout painelLayout = new javax.swing.GroupLayout(painel);
        painel.setLayout(painelLayout);
        painelLayout.setHorizontalGroup(
            painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelBotao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(233, 233, 233))
        );
        painelLayout.setVerticalGroup(
            painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(titulo)
                .addGap(31, 31, 31)
                .addComponent(painelBotao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAlunoActionPerformed
        LoginAluno login = new LoginAluno();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botaoAlunoActionPerformed

    private void botaoProfessorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoProfessorActionPerformed
        LoginProfessor login = new LoginProfessor();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botaoProfessorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaInicial().setVisible(true);
            }
        });        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoAluno;
    private javax.swing.JButton botaoProfessor;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel painel;
    private javax.swing.JPanel painelBotao;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
