package FilesTest;
import java.awt.Container;
import java.text.ParseException;
  
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.MaskFormatter;
  
public class TestandoJFormattedTextField extends JFrame {
  
       private static final long serialVersionUID = 1L;
        
    public static void main(String[] args)  
    {  
       TestandoJFormattedTextField field = new TestandoJFormattedTextField();
       field.testaJFormattedTextField();
    }
  
    private void testaJFormattedTextField() {
             Container janela = getContentPane();
             setLayout(null);
  
             JLabel labelTitulo = new JLabel("CONFIGURAR DADOS DE CONEXÃO");
             //Define os rótulos dos botões
             JLabel labelIP = new JLabel("IP: ");
             JLabel labelCep = new JLabel("CEP: ");
             JLabel labelTel = new JLabel("Fone: ");
             JLabel labelCpf = new JLabel("CPF: ");
             JLabel labelData = new JLabel("Data: ");
             labelTitulo.setBounds(30,20,300,20);
             labelIP.setBounds(50,60,100,20);
             labelCep.setBounds(50,100,100,20);
             labelTel.setBounds(50,140,100,20);
             labelCpf.setBounds(50,180,100,20);
             labelData.setBounds(50,220,100,20);
  
             //Define as máscaras
             MaskFormatter mascaraIP = null;
             MaskFormatter mascaraCep = null;
             MaskFormatter mascaraTel = null;
             MaskFormatter mascaraCpf = null;
             MaskFormatter mascaraData = null;
  
             try{
                    mascaraIP = new MaskFormatter("###.###.###.###");
                    mascaraCep = new MaskFormatter("#####-###");
                    mascaraTel = new MaskFormatter("(##)####-####");
                    mascaraCpf = new MaskFormatter("#########-##");
                    mascaraData = new MaskFormatter("##/##/####");
                    mascaraIP.setPlaceholderCharacter('_');
                    mascaraCep.setPlaceholderCharacter('_');
                    mascaraTel.setPlaceholderCharacter('_');
                    mascaraCpf.setPlaceholderCharacter('_');
                    mascaraData.setPlaceholderCharacter('_');
             }
             catch(ParseException excp) {
                    System.err.println("Erro na formatação: " + excp.getMessage());
                    System.exit(-1);
             }
  
             //Seta as máscaras nos objetos JFormattedTextField
             JFormattedTextField jFormattedTextIP = new JFormattedTextField(mascaraIP);
             JFormattedTextField jFormattedTextCep = new JFormattedTextField(mascaraCep);
             JFormattedTextField jFormattedTextTel = new JFormattedTextField(mascaraTel);
             JFormattedTextField jFormattedTextCpf = new JFormattedTextField(mascaraCpf);
             JFormattedTextField jFormattedTextData = new JFormattedTextField(mascaraData);
             jFormattedTextIP.setBounds(100,60,100,20);
             jFormattedTextCep.setBounds(100,100,100,20);
             jFormattedTextTel.setBounds(100,140,100,20);
             jFormattedTextCpf.setBounds(100,180,100,20);
             jFormattedTextData.setBounds(100,220,100,20);
              
             //Adiciona os rótulos e os campos de textos com máscaras na tela
             janela.add(labelTitulo);
             janela.add(labelIP);
             janela.add(labelCep);
             janela.add(labelTel);
             janela.add(labelCpf);
             janela.add(labelData);
             janela.add(jFormattedTextIP);
             janela.add(jFormattedTextCep);
             janela.add(jFormattedTextTel);
             janela.add(jFormattedTextCpf);
             janela.add(jFormattedTextData);
             setSize(300, 300);
             setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             setVisible(true);
    }
     
}