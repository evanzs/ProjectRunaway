import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;


import java.io.*;
import java.net.*;
import java.util.*;

class ClienteRede extends JFrame {
// variaveis ligadas a conexao	 
  final int porto = 123; // porta que será conectada
  String IP = "127.0.0.1";  // endereço ip  que será conectado a maquina 
//============================================================================//
  
//====================variaveis ligadas as imagens do fundo===================//   
  Image bg;
//============================================================================//  
  
//===============variaveis que serão a posição X e Y do background ===========//
  int   backgroundX = 0;
  int   backgroundY = 0;
//============================================================================//
  
  
  
  Desenho des = new Desenho();
  int posX = 0, posY = 0;
  String texto = "O";
  int posXAd = 0, posYAd = 0;
  String textoAdversario = "O";
  PrintStream os = null;
  Scanner is = null;
  Socket socket = null;
  
 
  
  
  
 

  class Desenho extends JPanel {
    Desenho() {  	
    	
    	try {
    		
            bg   = ImageIO.read(new File("Back1.png")); // carrega o background          
          // se ele não consegui carrega alguma imagem retorna um erro   
          } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "A imagem não pode ser carregada!\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
          }  
      setPreferredSize(new Dimension(1050, 700));
    }
//========================================================================================================================================//
    
//                                        {{CLASSE DESENHA OS COMPONENTES NA TELA }} 
    
    public void paintComponent(Graphics g) 
    {
      super.paintComponent(g);
      // desenha o background com tamanho que ele foi feito
      g.drawImage(bg,backgroundX,backgroundY, getSize().width, getSize().height, this);
      
      
      
      
      
      Toolkit.getDefaultToolkit().sync();
    }
  }
//========================================================================================================================================//
 
  
  
  
 ClienteRede()
{
    super("Cliente");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    add(des);
    
    try 
    {
      // conecta ao IP lOCAL
      socket = new Socket(IP,porto);      
      os = new PrintStream(socket.getOutputStream());      
      is = new Scanner(socket.getInputStream());
    } catch (UnknownHostException e) 
    {
      // coloque um JOptionPane para mostrar esta mensagem de erro
      // mostra ERRO que não achou servidor
      JOptionPane.showMessageDialog(this, "O servidor desconhecido\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    } catch (IOException e) {
      // coloque um JOptionPane para mostrar esta mensagem de erro
      JOptionPane.showMessageDialog(this, "O servidor desconhecido\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
      System.exit(1);
}
//========================================================================================================================================//    
    // Thread que recebe os dados vindos do servidor, prepara as 
    // variaveis de estados dos elementos do jogo e pede o repaint() ( atualiza a possição nas telas dos clientes
    new Thread() {
      public void run() {
        try {
          while (true) {
            // um caracter extra pode ser usado para indicar o tipo de
            // dados estᡳendo recebido.
        	  
            texto = is.next();
            posX = is.nextInt();
            posY = is.nextInt();
            
            textoAdversario = is.next();
            posXAd = is.nextInt();
            posYAd = is.nextInt(); 
            
            backgroundX = is.nextInt();
            backgroundY = is.nextInt();            
           
            repaint();
          }
        } catch (Exception ex) {
          // coloque um JOptionPane para mostrar esta mensagem de erro
        	JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.ERROR_MESSAGE);
          System.exit(1);
        }
      }
    }.start();
    
 //========================================================================================================================================//
 
    //                                       {{ ClASSE ANONIMA PARA LER AS TECLAS DO TECLADO }}    


    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        try 
        {
        		// Seta pra Baixo
        	 if (e.getKeyCode()==40){
        		 os.println("B");
             }
             //Seta pra cima        
             if (e.getKeyCode()==38){
            	 os.println("C");
             }
             // só letras são enviadas  
 
        } catch (Exception ex) {
          // coloque um JOptionPane para mostrar esta mensagem de erro
          System.err.println("O servidor interrompeu a comunica褯");
          JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
          System.exit(1);
        }
        
      }
    });
    
    pack();
    setVisible(true);
  }

  static public void main(String[] args) {
    ClienteRede f = new ClienteRede();
  }
}
