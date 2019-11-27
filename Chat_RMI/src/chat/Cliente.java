package chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends javax.swing.JFrame { 
    
    // Ao se criar um novo cliente, recebe-se como parâmetro da tela de login
    // o nome do usuário caso digitado, se não 'Anonimo". Em seguida, pega-se
    // o registro e busca dentro dele o stub Chat, gravando-o na variável stub.
    // Logo em seguida, utiliza-se o método entrou para que o servidor adicione
    // ao feed que determinado usuário entrou no chat.    
    
    public Cliente(String user) {
        initComponents();
        this.user=user;
        try{
            Registry registry=LocateRegistry.getRegistry(hots);
            stub=(Chat) registry.lookup("Chat");
            stub.entrou(user);
            new Thread(receber).start();
          
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    // Função para receber as mensagens que estão no registro. Isso ocorre através
    // da função receber já registrada no servidor. Verifica-se até onde o usuário já possui as mensagens
    // através do parâmetro retornado do método disposto no servidor. Caso ele seja um novo usuário, iniciará
    // da posição 0, ou seja, receberá todas as mensagens já dispostas no chat anteriormente. Caso seja 
    // retornado nulo do servidor, é porque o usuário já possui todas as mensagens disponíveis.
    
    private static Runnable receber = new Runnable() {
        public void run() {
            try{
                String temp=null;
                while(true){
                    temp=stub.receber(n);
                    if(temp!=null){
                        n=Integer.parseInt(temp.substring(0,1));
                        chat.setText(chat.getText()+temp.substring(1,temp.length())+"\n");
                    }
                }
            } catch (Exception e){}
 
        }
    };
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        msg = new javax.swing.JTextArea();
        enviarbtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        chat = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        msg.setColumns(20);
        msg.setRows(5);
        jScrollPane1.setViewportView(msg);

        enviarbtn.setText("Enviar");
        enviarbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarbtnActionPerformed(evt);
            }
        });

        chat.setColumns(20);
        chat.setRows(5);
        jScrollPane2.setViewportView(chat);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enviarbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 148, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(enviarbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(38, 38, 38))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Função para que a mensagem seja enviada ao clicar-se no botão "Enviar" na interface.
    // Após enviada, o conteúdo do textfield é zerado.
    
    private void enviarbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarbtnActionPerformed
        try {
                enviar(user,msg.getText());
                msg.setText("");
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_enviarbtnActionPerformed

    // Função para notificar ao servidor através do método saiu que determinado usuário
    // saiu do chat. Essa informação será adicionada ao feed pelo servidor.
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{            
            stub.saiu(user);
        }catch(RemoteException ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing
    
    // Função enviar, recebendo como parâmetro a mensagem e o nome do usuário, e então é enviado
    // para o servidor através do método enviar registrado no stub.
    
    private void enviar(String user, String msg) throws RemoteException{
        stub.enviar(user, msg);
    }
    
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                hots=(args.length<1)?null:args[0];
            }
        });
    }
    private static String user;
    private static int n=0;
    private static String hots;
    private static Chat stub;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextArea chat;
    private javax.swing.JButton enviarbtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea msg;
    // End of variables declaration//GEN-END:variables
}
