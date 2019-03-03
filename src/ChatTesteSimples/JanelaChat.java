package ChatTesteSimples;
/*--------------------*
 * Rodrigo CavanhaMan *
 *--------------------*
https://www.paulocollares.com.br/2012/08/chat-simples-em-java/
*/
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class JanelaChat extends javax.swing.JFrame implements Observer {

    private Conexao conexao;

    public JanelaChat(Conexao conexao) {
        super("Chat Simples em Java by @pcollares");
        this.conexao = conexao;
        initComponents();
        conexao.addObserver(this);
        escreve("Chat iniciado com " + conexao.getIp() + ":" + conexao.getPorta());
        mensagemjTextArea.requestFocusInWindow();
    }

    private void envia() {
        if (!mensagemjTextArea.getText().isEmpty()) {
            conexao.envia(mensagemjTextArea.getText());
            escreve("Você disse: "+mensagemjTextArea.getText());
            mensagemjTextArea.setText("");
        }
    }

    private void escreve(String texto){
        chatjTextArea.append(texto+"\n");
         if (!chatjTextArea.getText().isEmpty() && !chatjTextArea.isFocusOwner()) {
                chatjTextArea.setCaretPosition(chatjTextArea.getText().length() - 1);
            }
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        chatjTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        mensagemjTextArea = new javax.swing.JTextArea();
        enviarjButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        chatjTextArea.setEditable(false);
        chatjTextArea.setColumns(20);
        chatjTextArea.setRows(5);
        jScrollPane1.setViewportView(chatjTextArea);

        mensagemjTextArea.setColumns(20);
        mensagemjTextArea.setRows(5);
        mensagemjTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mensagemjTextAreaKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(mensagemjTextArea);

        enviarjButton.setText("Enviar");
        enviarjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarjButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enviarjButton, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(enviarjButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void enviarjButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        envia();
    }                                             

    private void mensagemjTextAreaKeyReleased(java.awt.event.KeyEvent evt) {                                              
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            envia();
        }
    }                                             

    // Variables declaration - do not modify                     
    private javax.swing.JTextArea chatjTextArea;
    private javax.swing.JButton enviarjButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea mensagemjTextArea;
    // End of variables declaration                   

    @Override
    public void update(Observable o, Object arg) {
        escreve(conexao.getMensagem());
    }
}