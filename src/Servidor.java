import java.net.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

class Servidor {
  ServerSocket serverSocket = null;

  public static void main(String[] args) {
    new Servidor();
  }
//========================================================================================================================================//  
  
//  {{ CLASSE CONECTA E ENVIA DADOS PARA OS CLIENTES }}
  
  Servidor() {
    final int porto = 123;

    try {
      serverSocket = new ServerSocket(porto);
    } catch (IOException e) {
    
      System.err.println("O porto " + porto + " năo pode ser usado.\n" + e);      
      System.exit(1);
    }

    System.err.println("Servidor esperando cliente...\nDigite <ctrl>+C para terminar.");
      
    while (true) {
      Dados dados = new Dados();
      
      Scanner is[] = new Scanner[Dados.NUM_MAX_JOGADORES];
 
      PrintStream os[] = new PrintStream[Dados.NUM_MAX_JOGADORES];
      
      conectaCliente(Dados.CLIENTE_UM, is, os);
      new Recebendo(Dados.CLIENTE_UM, is, dados).start();
      
      // só começa a thread de envio após um cliente chegar
      new Enviando(os, dados).start();
      
      conectaCliente(Dados.CLIENTE_DOIS, is, os);
      new Recebendo(Dados.CLIENTE_DOIS, is, dados).start();
    }
  }
//========================================================================================================================================//
//                              {{ Conectas os PLAYERS }}  
 boolean  conectaCliente(int id, Scanner[] is, PrintStream[] os) {
	// TODO Auto-generated method stub
	  Socket clientSocket = null;
	  try {
	      clientSocket = serverSocket.accept();

	      System.out.println("Cliente " + id + " conectou!");
	      
	      is[id] = new Scanner(clientSocket.getInputStream());
	      os[id] = new PrintStream(clientSocket.getOutputStream());
	      
	    } catch (IOException e) {
	      System.err.println("Năo foi possível conectar com o cliente.\n" + e);
	      
	      return false;
	    }
	    return true;  //funcionou! 
}

//========================================================================================================================================//
}

/** Esta classe tem os dados dos elementos do jogo, a lógica e regras 
 * do comportamento destes elementos.
 */
class Dados {

//////////////////DADOS DOS JOGADORES/////////////////////
  static final int NUM_MAX_JOGADORES = 2;
  
///////////////////DADOS DO CLIENTE///////////////////////
  

  
/////////////////////////////////////////////////////////  
 
//////////////Limites do eixo Y jogaveis/////////////////

  
//////////////Limites do eixo X Jogaveis////////////////
 
  
  //VELOCIDADE

/////////////////////////////////////////////////////////   
  
  
  class EstadoJogador {
    char c;
    int x, y;
    int dx, dy;
    
    int lx;
  }
  
  class Plataforma {
	  
	  int x,y;
	  int lx,ly; // largura
  }
  
  
  class Bombas {
	  
	  
	  int x,y;
  }
  //cria jogadores
  EstadoJogador estado[] = new EstadoJogador[NUM_MAX_JOGADORES];
  Plataforma   plataforma[] = new Plataforma[4];
  Bombas bomba[]= new Bombas [16];
  
  
  Dados() {
	 
	//aqui fica os dados de posição do panel
		 
  }
  
  /** Envia os dados dos elementos do jogo aos clientes
   */
  
//                       {{{ RESPONSAVEL POR ENVIAR OS DADOS }}  
  synchronized boolean enviaClientes(PrintStream os[]) {
    try 
    {
     // Dados enviados para o Player 1
      if (os[CLIENTE_UM] != null) {
        // para enviar ao cliente um inverte o lado do cliente dois
        os[CLIENTE_UM].println(estado[CLIENTE_UM].c);
        os[CLIENTE_UM].println(estado[CLIENTE_UM].x);
        os[CLIENTE_UM].println(estado[CLIENTE_UM].y);
        os[CLIENTE_UM].println(estado[CLIENTE_DOIS].c);
        os[CLIENTE_UM].println(estado[CLIENTE_DOIS].x);
        os[CLIENTE_UM].println(estado[CLIENTE_DOIS].y);
        
      }
      if (os[CLIENTE_DOIS] != null) {
        // para enviar ao cliente dois inverte o lado do cliente um
        os[CLIENTE_DOIS].println(estado[CLIENTE_DOIS].c);
        os[CLIENTE_DOIS].println(estado[CLIENTE_DOIS].x);
        os[CLIENTE_DOIS].println(estado[CLIENTE_DOIS].y);
        os[CLIENTE_DOIS].println(estado[CLIENTE_UM].c);
        os[CLIENTE_DOIS].println(estado[CLIENTE_UM].x);
        os[CLIENTE_DOIS].println(estado[CLIENTE_UM].y);
        
       
        
        //enviando bombas
        for (int i = 0 ; i < 4; i++)
        {        
         os[CLIENTE_DOIS].println(bomba[i].x);
         os[CLIENTE_DOIS].println(bomba[i].y);        
        }
      
      }
      
      if (os[CLIENTE_UM] != null)
        os[CLIENTE_UM].flush();
      if (os[CLIENTE_DOIS] != null)
        os[CLIENTE_DOIS].flush();
    } catch (Exception ex) {
      System.err.println("O servidor interrompeu a comunicaçăo.");
       
      return false;
    }
    return true;
  }
  
  synchronized void alteraDados(char c, int id) 
 {
    estado[id].c = c;
 }
 
  
  
//   {{RESPONSAVEL POR ATUALIZAR OS DADOS APARTIR DO DADO RECEBIDO DO CLIENTE }}    
synchronized void alteraDados(int x, int y, int id,char c) {
	
	// Player pressionou a seta para cima   
     if ( c == 'C')
        {	      
         /** IMPLMENTA AQ SUA FUNCAO JONAS **/       
        }
        
        
        if (c == 'B')
        {        	
        	/** IMPLMENTA AQ SUA FUNCAO JONAS **/
        }
 }
       
	    
        
        
  
  
  synchronized void alteraDadosVelocidade(int dx, int dy, int id) {
    estado[id].dx = dx;
    estado[id].dy = dy;
  }
  
  /** Logica do jogo. Os testes das jogadas e das movimentaçőes dos 
   * elementos na arena do jogo săo atualizados aqui.
   */
  synchronized void logicaDoJogo() {
	
	 //Laterais esquerda  
	//primeiro andar
	  /*bomba[1].x = LIMITE_LATERALE;
		 bomba[1].y = 550;
		 bomba[0].x = LIMITE_LATERALD;
		 bomba[0].y = 550;
		 
		 //segundo andar 
		 //alturas fixadas
		 bomba[2].x = LIMITE_LATERALD;
		 bomba[2].y = 400;
		 bomba[3].x = LIMITE_LATERALE;
		 bomba[3].y = 400; 
		*/
		//bomba 0 ainda n saiu
		if ( bomba[0].x > LIMITE_LATERALE)
			 bomba[0].x -= VEL_BOMBA;
		
		if (bomba[1].x < LIMITE_LATERALD)
			bomba[1].x += VEL_BOMBA;
		
		if ( bomba[2].x > LIMITE_LATERALE)
			 bomba[2].x -= VEL_BOMBA;
		
		if (bomba[3].x < LIMITE_LATERALD)
			bomba[3].x += VEL_BOMBA;
		
		// volta as bombas
		
	    if ( bomba[0].x  <= LIMITE_LATERALE)
	    	bomba[0].x = LIMITE_LATERALD;
	    if ( bomba[1].x  >= LIMITE_LATERALD)
	    	bomba[1].x = LIMITE_LATERALE;
	    if ( bomba[2].x  <= LIMITE_LATERALE)
	    	bomba[2].x = LIMITE_LATERALD;
	    if ( bomba[3].x  <= LIMITE_LATERALD)
	    	bomba[3].x = LIMITE_LATERALE;
	    
	    
    for (int i = 0; i < NUM_MAX_JOGADORES; i++) {
       	// logica de quedra / ou
    	if ( estado[i].y > LIMITE_INF)
    		estado[i].y = LIMITE_INF;
    	if   (estado[i].y <= 580 && estado[i].y >=520)
		{
	          if (bomba[0].x == estado[i].x || bomba[1].x == estado[i].x)
	          {
	        	  if  (i == 0)
	        		  JOptionPane.showMessageDialog(null, "Jogador 2 Venceu", "GAME OVER ",JOptionPane.CLOSED_OPTION); 
	        	  else
	        		  JOptionPane.showMessageDialog(null, "Jogador 1 Venceu", "GAME OVER ",JOptionPane.CLOSED_OPTION); 
	          }
		}
    	if ( estado[i].y < 600)	    		
    	{
    		
    		if ( estado[i].y + VEL_QUEDA >= 450 && estado[i].y <= 550 + VEL_QUEDA)
    		{
    			if (estado[i].x >= 50 && estado[i].x <= 220 )
    			{
    				estado[i].y = plataforma[1].y-60;
    				EMCIMA_PLATAFORMA = true;
    			}
    				
    			else 
    			if (estado[i].x >= 550 && estado[i].x <= 720 )
    			{
    				estado[i].y = plataforma[3].y-60;
    				EMCIMA_PLATAFORMA = true;
    			}
    				
    			
    		}else estado[i].y += VEL_QUEDA;
    		
    		if ( estado[i].y + VEL_QUEDA >= 300 && estado[i].y <= 360 + VEL_QUEDA)
    		{
    			if (estado[i].x >= 250 && estado[i].x <= 420 )
    			{
    				estado[i].y = plataforma[2].y - 60;
    			}
    				
    			
    		}else estado[i].y += VEL_QUEDA;
    		
    	}
    }
  }
}

/** Esta classe é responsável por receber os dados de cada cliente.
 * Uma instância para cada cliente deve ser executada.
 */
class Recebendo extends Thread {
  Scanner is[];
  Dados dados;
  int idCliente;
/*
  Recebendo(int id, DataInputStream is[], Dados d) {
    idCliente = id;
    dados = d;
    //this.is = is;
  }*/
  
  public Recebendo(int id, Scanner is[], Dados d) {
	  
	idCliente =id;
	dados = d;
	this.is = is;
}

public void run() {  
	  	  
    try {
      while (true) {
        //char c = is[idCliente].nextChar();
        char c = is[idCliente].next().charAt(0);
        
        if (c == 'C')
        {
        	
        	dados.alteraDados(0,-160,idCliente,c);
        }
        if (c == 'D')
        {
        	dados.alteraDados(20, 0,idCliente,c);
        }
        if (c == 'E')
        {
        	dados.alteraDados(-20, 0,idCliente,c);
        }
        dados.alteraDados(c, idCliente);
        
      }

    }  catch (Exception e) {
        System.err.println("Conexacao terminada pelo cliente");
   // } catch (NoSuchElementException e) {
     //   System.err.println("Conexacao terminada pelo cliente");
    
      
    }
    
  }

};

/** Esta classe é responsável por enciar os dados dos elementos para os 
 * cliente. Uma única instância envia os dados para os dois clientes.
 */
class Enviando extends Thread {
  PrintStream os[];
  Dados dados;

 /* Enviando(DataOutputStream os[], Dados d) {
    dados = d;
    this.os = os;
  }*/
  
  

  public Enviando(PrintStream os[], Dados d) {
	 dados = d;
	 this.os = os;
}

public void run() {
	  
    while (true) {
      dados.logicaDoJogo();
      if (!dados.enviaClientes(os)) {
        break;
      }
      try {
        sleep(33);   // o cliente receberá 30 vezes por segundo

      } catch (InterruptedException ex) {}
      
    }
    
  }

};
