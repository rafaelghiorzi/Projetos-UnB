/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telas;

import classes.Aluno;
import classes.Aula;
import classes.Professor;
import classes.UsuarioLogado;
import enums.Especialidade;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import static telas.TelaInicial.listaAulas;
import static telas.TelaInicial.listaProfessores;
import javax.swing.JOptionPane;

/**
 *
 * @author rafae
 */
public class CriarReserva extends javax.swing.JFrame {
    private static final ArrayList<Aula> aulasFiltradas = new ArrayList<>();
    private final Aluno alunoLogado;
    
    public CriarReserva() {
        initComponents();
        this.alunoLogado = (Aluno) UsuarioLogado.getUsuarioLogado();
        estadoInicial();
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/gym.png"));
        this.setIconImage(icon.getImage());
        this.setTitle("Reserva uma vaga!");
    }
    
    private void estadoInicial() {
        popularComboBoxAula();
        popularComboBoxProfessor();
        popularTabela();
        botaoReserva.setEnabled(false);
    }
    
    private void popularComboBoxAula() {
        // adicionando as especialidades para o filtro de aulas
        for (Especialidade especialidade: Especialidade.values()) {
            aulaComboBox.addItem(especialidade.toString());
        }
    }
    
    private void popularComboBoxProfessor(){
        // adicionando os nomes de cada professor na comboBox para o filtro
        for (Professor professor: listaProfessores){
            // removendo da lista o admin
            if (!"Admin".equals(professor.getNome())) {
                professorComboBox.addItem(professor.getNome());
            }
        }
    }

    private void popularTabela() {
        // reinicia a lista
        aulasFiltradas.clear();

        // Obtendo valores dos inputs
        String inputProfessor = professorComboBox.getSelectedItem().toString();
        String inputAula = aulaComboBox.getSelectedItem().toString();

        // Iterando sobre cada aula para aplicar os filtros
        for (Aula aula : listaAulas) {
            System.out.println(listaAulas);
            System.out.println(aulasFiltradas);
            
            boolean professorValido = inputProfessor.equals("Todos") || aula.getProfessor().getNome().equals(inputProfessor);
            boolean aulaValida = inputAula.equals("Todos") || aula.getNome().equals(inputAula);

            // Verifica se o aluno já reservou essa aula
            boolean jaReservado = alunoLogado.getReservas().stream()
                .anyMatch(reserva -> reserva.getAula().equals(aula));
            
            // Verifica se a aula está na mesma unidade do aluno
            boolean mesmaUnidade = alunoLogado.getUnidade() == aula.getProfessor().getUnidade();

            // Adiciona a aula à lista apenas se ainda não foi reservada pelo aluno
            if (mesmaUnidade && !jaReservado && professorValido && aulaValida && !"Admin".equals(aula.getProfessor().getNome())) {
                aulasFiltradas.add(aula);
            }
        }
        
    

        // Popular a tabela com as aulas filtradas
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Aula", "Professor", "Horário", "Status"}, 0);

        // Iterando sobre a lista filtrada para adicionar as informações corretamente
        for (Aula aula : aulasFiltradas) {
            // Formatando a data e horário da aula
            SimpleDateFormat data = new SimpleDateFormat("dd/MM");
            SimpleDateFormat hora = new SimpleDateFormat("HH'h'mm");
            String dataFormatada = data.format(aula.getData());
            String horaFormatada = hora.format(aula.getData());
            String horario = dataFormatada + " - " + horaFormatada;

            // Verificando se a aula está cheia
            String capacidade = (aula.getCapacidade() == aula.getReservas().size()) ? "Aula Cheia!" : "Disponível!";

            // Adicionando linha ao modelo da tabela
            Object linha[] = new Object[] {
                aula.getNome(),
                aula.getProfessor().getNome(),
                horario,
                capacidade
            };
            modelo.addRow(linha);
        }

        // Atualizando a tabela com o novo modelo
        tabela.setModel(modelo);
    }
    
    public void fazerReserva(Aula aula) {
        if (aula.getCapacidade() == aula.getReservas().size()) {
            JOptionPane.showMessageDialog(null, "A aula já está cheia, não é possível reservá-la!", "Erro de reserva", JOptionPane.ERROR_MESSAGE);
            return;
        }
            alunoLogado.criarReserva(aula);
            JOptionPane.showMessageDialog(null, "Reserva realizada com sucesso para " + aula.getNome(), "Sucesso", JOptionPane.INFORMATION_MESSAGE); 
        }    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        painel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        filtroLabel = new javax.swing.JLabel();
        titulo = new javax.swing.JLabel();
        filtroAulaLabel = new javax.swing.JLabel();
        filtroProfessorLabel = new javax.swing.JLabel();
        aulaComboBox = new javax.swing.JComboBox<>();
        professorComboBox = new javax.swing.JComboBox<>();
        painelInfo = new javax.swing.JPanel();
        filtroLabel1 = new javax.swing.JLabel();
        nomeAulaInput = new javax.swing.JLabel();
        nomeProfessorInput = new javax.swing.JLabel();
        horaAulaInput = new javax.swing.JLabel();
        capacidadeInput = new javax.swing.JLabel();
        detalhesInput = new javax.swing.JLabel();
        botaoReserva = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        voltarPerfil = new javax.swing.JMenu();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        painel.setBackground(new java.awt.Color(255, 255, 255));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Aula", "Professor", "Horário", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setCellSelectionEnabled(false);
        tabela.setRowSelectionAllowed(true);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabelaMousePressed(evt);
            }
        });
        jScrollPane5.setViewportView(tabela);
        tabela.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        filtroLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        filtroLabel.setText("Filtrar as aulas disponíveis");

        titulo.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Reservar aula");

        filtroAulaLabel.setText("Aula:");
        filtroAulaLabel.setToolTipText("");

        filtroProfessorLabel.setText("Professor:");

        aulaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos" }));
        aulaComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aulaComboBoxMouseClicked(evt);
            }
        });
        aulaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aulaComboBoxActionPerformed(evt);
            }
        });

        professorComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos" }));
        professorComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                professorComboBoxMouseClicked(evt);
            }
        });
        professorComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                professorComboBoxActionPerformed(evt);
            }
        });

        painelInfo.setBackground(new java.awt.Color(245, 245, 245));
        painelInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        filtroLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        filtroLabel1.setText("Informações da aula selecionada");

        nomeAulaInput.setText("[Nome da aula]");

        nomeProfessorInput.setText("[Nome do professor]");

        horaAulaInput.setText("[Horário da aula]");

        capacidadeInput.setText("[Capacidade Máxima]");

        detalhesInput.setText("[Detalhes da aula]");

        botaoReserva.setText("Reservar");
        botaoReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoReservaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelInfoLayout = new javax.swing.GroupLayout(painelInfo);
        painelInfo.setLayout(painelInfoLayout);
        painelInfoLayout.setHorizontalGroup(
            painelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filtroLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(painelInfoLayout.createSequentialGroup()
                        .addGroup(painelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nomeAulaInput)
                            .addComponent(nomeProfessorInput)
                            .addComponent(horaAulaInput)
                            .addComponent(capacidadeInput)
                            .addComponent(detalhesInput)
                            .addComponent(botaoReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        painelInfoLayout.setVerticalGroup(
            painelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filtroLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nomeAulaInput)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nomeProfessorInput)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(horaAulaInput)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(capacidadeInput)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(detalhesInput)
                .addGap(18, 18, 18)
                .addComponent(botaoReserva)
                .addContainerGap(200, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout painelLayout = new javax.swing.GroupLayout(painel);
        painel.setLayout(painelLayout);
        painelLayout.setHorizontalGroup(
            painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(painelLayout.createSequentialGroup()
                        .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelLayout.createSequentialGroup()
                                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(filtroProfessorLabel)
                                    .addComponent(filtroAulaLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(aulaComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(professorComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(filtroLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(painelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        painelLayout.setVerticalGroup(
            painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelLayout.createSequentialGroup()
                        .addComponent(filtroLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(filtroAulaLabel)
                            .addComponent(aulaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(filtroProfessorLabel)
                            .addComponent(professorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(painelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        voltarPerfil.setText("Perfil");
        voltarPerfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                voltarPerfilMouseClicked(evt);
            }
        });
        jMenuBar1.add(voltarPerfil);

        setJMenuBar(jMenuBar1);

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

    private void voltarPerfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voltarPerfilMouseClicked
        this.dispose();
    }//GEN-LAST:event_voltarPerfilMouseClicked

    private void botaoReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoReservaActionPerformed
    int linhaSelecionada = tabela.getSelectedRow();

    if (linhaSelecionada >= 0) {
        Aula aula = aulasFiltradas.get(linhaSelecionada); // Pega o objeto real da lista
        System.out.println(alunoLogado.getLimite() +" " + alunoLogado.sizeReservas());
        if (alunoLogado.getLimite() == alunoLogado.sizeReservas()) {
            JOptionPane.showMessageDialog(null, "Não é possível se matricular, limite de reservas atingido!", "Erro de reserva", JOptionPane.ERROR_MESSAGE);
            return;
        } 
        if (aula.getCapacidade() == aula.getReservas().size()) {
            JOptionPane.showMessageDialog(null, "A aula já está cheia, não é possível reservá-la!", "Erro de reserva", JOptionPane.ERROR_MESSAGE);
        } else {
            alunoLogado.criarReserva(aula);
            JOptionPane.showMessageDialog(null, "Reserva realizada com sucesso para " + aula.getNome(), "Sucesso", JOptionPane.INFORMATION_MESSAGE); 

        }
    } else {
        JOptionPane.showMessageDialog(null, "Selecione uma aula antes de reservar!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
    popularTabela();
    }//GEN-LAST:event_botaoReservaActionPerformed

    private void aulaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aulaComboBoxActionPerformed
        popularTabela();
    }//GEN-LAST:event_aulaComboBoxActionPerformed

    private void professorComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_professorComboBoxActionPerformed
        popularTabela();
    }//GEN-LAST:event_professorComboBoxActionPerformed

    private void aulaComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aulaComboBoxMouseClicked

    }//GEN-LAST:event_aulaComboBoxMouseClicked

    private void professorComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_professorComboBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_professorComboBoxMouseClicked

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
    int linhaSelecionada = tabela.getSelectedRow();
    
    if (linhaSelecionada >= 0) {
        Aula aula = aulasFiltradas.get(linhaSelecionada); // Agora pega o objeto real diretamente da lista
        
        nomeAulaInput.setText(aula.getNome());
        nomeProfessorInput.setText(aula.getProfessor().getNome());
        horaAulaInput.setText(aula.getData().toString());
        capacidadeInput.setText(String.format("%d de %d", aula.getReservas().size(),aula.getCapacidade()));
        detalhesInput.setText(aula.getDetalhes());
        botaoReserva.setEnabled(true);
    } else {
    
    }
    }//GEN-LAST:event_tabelaMouseClicked

    private void tabelaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaMousePressed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CriarReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CriarReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CriarReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CriarReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CriarReserva().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> aulaComboBox;
    private javax.swing.JButton botaoReserva;
    private javax.swing.JLabel capacidadeInput;
    private javax.swing.JLabel detalhesInput;
    private javax.swing.JLabel filtroAulaLabel;
    private javax.swing.JLabel filtroLabel;
    private javax.swing.JLabel filtroLabel1;
    private javax.swing.JLabel filtroProfessorLabel;
    private javax.swing.JLabel horaAulaInput;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel nomeAulaInput;
    private javax.swing.JLabel nomeProfessorInput;
    private javax.swing.JPanel painel;
    private javax.swing.JPanel painelInfo;
    private javax.swing.JComboBox<String> professorComboBox;
    private javax.swing.JTable tabela;
    private javax.swing.JLabel titulo;
    private javax.swing.JMenu voltarPerfil;
    // End of variables declaration//GEN-END:variables
}
