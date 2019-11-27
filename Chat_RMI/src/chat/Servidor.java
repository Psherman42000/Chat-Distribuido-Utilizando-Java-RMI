package chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Servidor extends javax.swing.JFrame implements Chat{

    // Inicializa a fila de mensagens ao se iniciar o servidor.    
    
    public Servidor() {
        initComponents();
        fila=new ArrayList<String>();
    }
    
    // Função de enviar mensagens, recebendo como parametro o usuário que está enviando
    // e a mensagem a ser enviada. Ela é adicionada na fila de mensagens do chat e em
    // seguida é adicionado ao feed que foi se foi recebido a mensagem.
    
    public void enviar(String user, String msg) throws RemoteException {
        String imsg=user+": "+msg;
        fila.add(imsg);
        this.msg=imsg;
        feed.setText(feed.getText()+"Mensagem recebida: "+imsg+"\n");
       
    }
    
    // Função de adicionar ao Feed quando um usuário entra no chat, recebendo como parametro
    // o nome do usuário.
    
    public void entrou(String user) throws RemoteException {
        String entrou="O usuário "+user+" entrou no Chat";
        this.msg=entrou;
        feed.setText(feed.getText()+""+entrou+"\n");
    }
    
    // Função de adicionar ao Feed quando um usuário sai do chat, recebendo como parametro
    // o nome do usuário
    
    public void saiu(String user) throws RemoteException{
        String saiu = "O usuário "+user+" saiu do Chat";
        this.msg=saiu;
        feed.setText(feed.getText()+""+saiu+"\n");
    }
    
    // Função que retorna as mensagens ao usuário. Ela também adiciona ao feed quando se está reenviando
    // a mensagem ao usuário.

    public String receber(int n) throws RemoteException {
           if(n<fila.size()){
                feed.setText(feed.getText()+"Reenviando msg: "+fila.get(n)+"\n\n");
                return (n+1)+fila.get(n);
            }
            return null;
    }    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        feed = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        feed.setColumns(20);
        feed.setRows(5);
        jScrollPane1.setViewportView(feed);

        jLabel1.setText("FEED DE ATUALIZAÇÕES");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
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
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            
            // Cria-se o servidor, registrando-se o stub do Chat com o nome de "Chat".
            
            public void run() {
                try{
                    Servidor obj=new Servidor();
                    obj.setVisible(true);
                    Chat stub=(Chat) UnicastRemoteObject.exportObject(obj, 0);
                    Registry registry=LocateRegistry.getRegistry();
                    registry.bind("Chat",stub);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    
    
    private static int x=0;
    private static ArrayList<String> fila;
    String msg;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextArea feed;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
