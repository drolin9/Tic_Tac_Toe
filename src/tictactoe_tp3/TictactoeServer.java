/************************
cours: IFT3820
TP3  
Karl Rushford


**************************/

package tictactoe_tp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TictactoeServer 
{
	private static int PORT = 1500;
    private BufferedReader input;
    private PrintWriter output;
    private Socket socket;
    private String nomClient;
    private char[][] planche;
    private TictactoeJeu jeu;
    private String currentPlayer;
    private boolean gameover=false;
    private boolean retry=true;
    int count=0;
    private final int taille = 3; // taille du jeu
    
    public TictactoeServer( ServerSocket serverAddress) throws Exception 
    {
    	//initialisation des variable
    	socket = serverAddress.accept();
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
        
        //initialisation du tableau planche
		planche = new char[taille][taille];
		//on met un ' ' comme valeur par default
		for(int i=0;i<taille;i++)
		{
			for(int j=0;j<taille;j++)
			{
				
				planche[i][j]=' ';
			}
			
		}
        
        
        
    }//fin du constructeur
    
    
    //fontion de jeu
    public void play()throws Exception 
    {
    	

        try 
        {
        	//tant que la partie est a rejouer
        	while(retry=true)
        	{
        		count=0;
        		gameover=false;
        		
        		//stop and wait
	        	Thread.sleep(2500);
	        	//on envoie un confirmation au client
	            output.println("WELCOME ");
	            //on lit la reponse du client
	            String reponse = input.readLine();
	            if(reponse.startsWith("Client connect"))
	            	System.out.println("LE serveur a recu "+ reponse);
	            
	            //stop and wait
	            Thread.sleep(2500);
	            // on lit la reponse du client
	            reponse = input.readLine();
	            if(reponse.startsWith("Client jeu"))
	            	System.out.println("LE serveur a recu "+ reponse);
	            //stop and wait
	            Thread.sleep(2500);
	            //on envoit une confirmation au client
	            output.println("Serveur nom");
	            //on lit la reponse du client
	            reponse = input.readLine();
	            if(reponse.startsWith("Client nom"))
	            {
	            	//on extrait le nom de la reponse u client
	            	nomClient=reponse.substring(11);
	            	System.out.println("le serveur dit que le nom du joueru est "+nomClient);
	            	//on envoie une reponse au client
	            	output.println("Server Merci nom");
	            }
	            //stop and wait
	            Thread.sleep(2500);
	            //on initialise lobjet jeu
	        	jeu=new TictactoeJeu();
	        	//planche=jeu.getPlanche();
	        	System.out.println("La planche serveur a ete creer");
	        	//on affiche la planche serveur
	        	jeu.printBoard(planche);
	        	
	        	
	        	//on lit la reponse du serveur 
	            reponse=input.readLine();
	            System.out.println("serveur avant jeu client start "+reponse);
	        	if(reponse.startsWith("Jeu Start"))
	        	{
	        		//stop and wait
	        		Thread.sleep(2500);
	        		//on envoie une reponse au client
	        		output.println("Jeu Server");
	        		System.out.println("apres le ouput client jeu server ");
	        	}
	        	//tant que la partie nest pas terminer
	        	while(!gameover)
	        	{
	        		//on lit la reponse du client
		            reponse = input.readLine();
		            System.out.println(reponse);
		            if(reponse.startsWith("Jeu GO"))
		            {
		            	
		            	//on lit la reponse du client
		            	reponse = input.readLine();
		            	if(count>0)
		            		reponse = input.readLine();
		            	
		            	System.out.println("Les coordonne sont "+reponse);
		            	
		            	//on utilise une tavleau pour les coordonnes envoyer par le client
		            	String [] clientCoord= new String [2];
		            	
		            	clientCoord[0]=""+reponse.charAt(0);
		            	clientCoord[1]=""+reponse.charAt(1);
		            	
		            	//on remplie la planche avec la position du client
		            	planche[Integer.parseInt(clientCoord[0])][Integer.parseInt(clientCoord[1])]='x';
		            	
		            	//on affiche la planche serveur
		            	jeu.printBoard(planche);
		            	
		            	
		            	//on initialise le tableau pour la valeur 
		            	int [] valeur=new int [2];
		            	System.out.println("apres initiate " + valeur[0]+ " " + valeur[1]);
		            	//le cpu joue sont coup
		            	valeur=jeu.playerCPU(planche);
		            	System.out.println("apres cpu " + valeur[0]+ " " + valeur[1]);
		            	//on met le caracter u cpu ans la planche
		            	planche[valeur[0]][valeur[1]]='o';
		            	//on print la planche
		            	jeu.printBoard(planche);
		            	
		            	//on prepare la string pour lenvoie
		            	String envoi=""+valeur[0]+  valeur[1];
		            	//stop an wait
		            	Thread.sleep(2500);
		            	//on envoi la confirmation au client
		            	output.println(envoi);
		            	//stop and wait
		            	Thread.sleep(2500);
		            	//on envoi la confirmation au client
		            	output.println("Jeu Server");
		            	//output.println(envoi);
		            	//reponse=input.readLine();
	
		            }//fin du if
	            	count++;
	            	System.out.println("la valeur de count server "+count);
	            	if(count>=4)
	            	{
	            		// on met le statut de la partie a dalse
	            		gameover=true;	
	            		System.out.println("LA valeur de gameover server "+gameover);
	            		System.out.println("LE serveur a determiner la fin de partie "+jeu.gameStatus(planche));
	            		
	            		// on lit la reponse du serveur
	            		reponse=input.readLine();
	            		System.out.println("la valauer de reponse en temp " + reponse);
	            		// on lit la reponse  du serveur
	            		reponse=input.readLine();
	            		System.out.println("la reponse client s'il veux enore jouer " + reponse);
	            		//si le client veux rejouer
	            		if(reponse.startsWith("Retry"))
	            		{
	            			retry=true;
	            			//on reinitialise la planche
	            			for(int i=0;i<taille;i++)
	            			{
	            				for(int j=0;j<taille;j++)
	            				{
	            					
	            					planche[i][j]=' ';
	            				}
	            				
	            			}
	            			//stop and wait
	            			Thread.sleep(2500);
	            		}
	            		//si le client veux quitter
	            		if(reponse.startsWith("Quit"))
	            		{
	            			retry=false;
	            			System.exit(0);
	            		}
	            	}
	            	
	
	        	}//fin du while 
        	 
        	}
            
        } 
        catch (Exception e) 
        {
            System.out.println("Player died: " + e);
        }
        finally
        {
        	//on ferme correctement le socket
            socket.close();
        }
    	
	    	
    }
    
    //pour combiner la valeur e la piece jouer dans un string pour lenvoi
    //non implementer
    public String stringPush(int row,int col) 
    {
    	String message = String.valueOf(row)+String.valueOf(col);
    	return message;
    	
    }
    
    //pour decouper la string recu pour le jeu
    //non implementer 
    public char [] stringCut(String message)
    {
    	char [] convert=new char[2];
    	convert[0]=message.charAt(0);
    	convert[1]=message.charAt(1);
    	
    	
    	return convert;
    	
    }

	public static void main(String[] args) throws Exception 
	{
        ServerSocket listener = new ServerSocket(PORT);
		// TODO Auto-generated method stub
		String serverAddress = (args.length == 0) ? "localhost" : args[1];
		System.out.println(serverAddress);
        System.out.println("Tic Tac Toe Server is Running");
		TictactoeServer serveur = new TictactoeServer(listener);
		
        serveur.play();
        

	}

}
