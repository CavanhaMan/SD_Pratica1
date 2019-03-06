package ChatTesteV2;

import java.awt.Container;
import java.text.ParseException;
  
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.MaskFormatter;
  
public class RunServer extends JFrame {
  
    private static final long serialVersionUID = 1L;
        
    public static void main(String[] args) {  
       RunServer field = new RunServer();
       field.testaJFormattedTextField();
    }
  
    private void testaJFormattedTextField() {
        Container janela = getContentPane();
        setLayout(null);
  
        JLabel labelTitulo = new JLabel("CONFIGURAR DADOS DE CONEXÃO");
        //Define os rótulos dos botões
        JLabel labelIP = new JLabel("IP: ");
        JLabel labelPorta = new JLabel("Porta: ");
        JLabel labelNome = new JLabel("Nome: ");
        labelTitulo.setBounds(30,20,300,20);
        labelIP.setBounds(50,60,100,20);
        labelPorta.setBounds(50,100,100,20);
        labelNome.setBounds(50,140,100,20);
        
        //Define as máscaras
        MaskFormatter mascaraIP = null;
        MaskFormatter mascaraPorta = null;
        MaskFormatter mascaraNome = null;
        
        try{
            mascaraIP = new MaskFormatter("###.###.###.###");
            mascaraPorta = new MaskFormatter("########");
            mascaraIP.setPlaceholderCharacter('_');
            mascaraPorta.setPlaceholderCharacter('_');
        } catch(ParseException excp) {
            System.err.println("Erro na formatação: " + excp.getMessage());
            System.exit(-1);
        }
        
        //Seta as máscaras nos objetos JFormattedTextField
        JFormattedTextField jFormattedTextIP = new JFormattedTextField(mascaraIP);
        JFormattedTextField jFormattedTextPorta = new JFormattedTextField(mascaraPorta);
        JFormattedTextField jFormattedTextNome = new JFormattedTextField();
        jFormattedTextIP.setBounds(100,60,100,20);
        jFormattedTextPorta.setBounds(100,100,100,20);
        jFormattedTextNome.setBounds(100,140,100,20);
        
        //Adiciona os rótulos e os campos de textos com máscaras na tela
        janela.add(labelTitulo);
        janela.add(labelIP);
        janela.add(labelPorta);
        janela.add(labelNome);
        janela.add(jFormattedTextIP);
        janela.add(jFormattedTextPorta);
        janela.add(jFormattedTextNome);
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}