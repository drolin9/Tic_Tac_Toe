/************************
cours: IFT3820
TP3  
Karl Rushford


**************************/
package tictactoe_tp3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TictactoeClient
{

	
	private static int PORT = 1500;
    private BufferedReader input;
    private PrintWriter output;
    private Socket socket;
    private String nom;
    private char[][] planche;
    private TictactoeJeu jeu;
    private String currentPlayer;
    private boolean gameover=false;
    private boolean retry=true;
    int count=0;
    private final int taille = 3; // taille du jeu
    
    
    public TictactoeClient (String serverAddress) throws Exception 
    {
    	//initialisation des variable
    	socket = new Socket(serverAddress, PORT);
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
 
        
    }
    
    //fontion de jeu
    public void play() throws Exception
    {
	        String response;
	        try 
	        {
	        	while(retry==true)
	        	{
	        		count=0;
	        		gameover=false;
	        		System.out.println("Debut u jeu client");
	        		//stop and wait
		        	Thread.sleep(2500);
		        	//on lit la reponse du serveur
		        	response = input.readLine();
		        	System.out.println("Le client a attraper la reponse du serveur " + response);
		            if(response.startsWith("WELCOME"))
		            	//on envoit une confirmation au Serveur
		            	output.println("Client connect");
		          //stop and wait
		        	Thread.sleep(2500);
		        	
		        	//on envoit une confirmation au Serveur
		        	output.println("Client jeu");
		        	//stop and wait
		        	Thread.sleep(2500);
		        	//on lit la reponse du serveur
		        	response = input.readLine();
		        	if(response.startsWith("Serveur nom"))
		        	{
		        		System.out.println("Quel est votre nom");
		        		Scanner scanner = new Scanner(System.in);
		        		nom = scanner.nextLine();
		        		//on envoit une confirmation au Serveur
		        		output.println("Client nom "+nom);
		        		//stop and wait
		        		Thread.sleep(2500);
		        		
		        	}
		        	//on lit la reponse du serveur
		        	response = input.readLine();
		            if(response.startsWith("Server Merci nom"))
		            {
		            	System.out.println("le serveur a accepter le nom "+nom);
		            	//stop and wait
		                Thread.sleep(2500);
		                //on initialise lobjet jeu
		            	jeu=new TictactoeJeu();
		            	//planche=jeu.getPlanche();
		            	System.out.println("La planche client "+nom+" a ete creer");
		            	//on imprime la planche
		            	jeu.printBoard(planche);
		            	//on envoit une confirmation au Serveur
		            	output.println("Jeu Start");
		            	//System.out.println(" ceci est le output");
		                
		            }
		            while(!gameover)
		            {
		            	//stop and wait
		            	Thread.sleep(2500);
		            	//on lit la reponse du serveur
			            response=input.readLine();
			            System.out.println("avant le jeur sercer "+ response);
			            if(response.startsWith("Jeu Server"))
			            {
			            	System.out.println("Veullez entre les coordonnee a jouer " + nom + " 2 digit entre 0 et 2");
			            	
			            	String[] rowcol= new String [2];
			
			            	
			        		do
			        		{
			                	Scanner scanner = new Scanner(System.in);
			                	String coord = scanner.nextLine();
			                	
			                	
			        			
			                	rowcol[0]=""+coord.charAt(0);
			                	rowcol[1]=""+coord.charAt(1);
			        		}while(!jeu.validMove( Integer.parseInt(rowcol[0]), Integer.parseInt(rowcol[1]),planche )) ;
			
			
			            	//System.out.println("Apres le while nove valide");
			            	
			        		//on met les coordonner client dans la planche
			            	planche[Integer.parseInt(rowcol[0])][Integer.parseInt(rowcol[0])]='x';
			            	//on imprime la planche client
			            	jeu.printBoard(planche);
			
			            	//stop and wait
			            	Thread.sleep(2500);		
			            		
			            	//on envoit une confirmation au Serveur
			            	output.println("Jeu GO");
			            	//on envoit une confirmation au Serveur avec les coordonneee
			            	output.println(rowcol[0]+rowcol[1]);
			            	
			            	//on lit la reponse du serveur
			            	response=input.readLine();
			            	
			            	System.out.println("Les coordonne serveur sont "+response);
			            	
			            	String [] serverCoord= new String [2];
			            	
			            	serverCoord[0]=""+response.charAt(0);
			            	serverCoord[1]=""+response.charAt(1);
			            	
			            	//on met dans la plance les coordonnee serveur
			            	planche[Integer.parseInt(serverCoord[0])][Integer.parseInt(serverCoord[1])]='o';
			            	
			            	//on imprime la planche client
			            	jeu.printBoard(planche);
			            	//stop and wait
			            	Thread.sleep(2500);
			            	
			            	//on envoit une confirmation au Serveur
			            	output.println("Jeu GO");
			            	//response=input.readLine();
			            	
			            }//fin du if
			            	
		            	count++;
		            	System.out.println("la valeur de count client "+count);
		              	if(count>=4)
		            	{
		            		gameover=true;	
		            		System.out.println("LA valeur de gameover client "+gameover);
		            		System.out.println("Voulex vous rejouer y or n");
		                	Scanner scanner = new Scanner(System.in);
		                	String rejouer = scanner.nextLine();
		                	/*while(rejouer!="y" || rejouer!="n")
		                	{
		                		System.out.println("Essayer encore voulez vous rejouer");
		                		rejouer = scanner.nextLine();
		                	}*/
		                	System.out.println("la valeur de rejouer Client est "+rejouer);
		                	//si le client veux rejouer
	                		if(rejouer.startsWith("y"))
	                		{
	                			//on envoit une confirmation au Serveur
	                			output.println("Retry");
	                			System.out.println("dans le retry client");
	                			//on lit la reponse du serveur
	                			response=input.readLine();
	                			System.out.println("dans la reponse serveur temp "+ response);
	                			//on reinitialise la planche
	                			for(int i=0;i<taille;i++)
	                			{
	                				for(int j=0;j<taille;j++)
	                				{
	                					
	                					planche[i][j]=' ';
	                				}
	                				
	                			}
	                		}
	                		//si le client veux quitter
	                		else if(rejouer.startsWith("n"))
	                		{
	                			//on envoit une confirmation au Serveur
	                			output.println("Quit");
	                			retry=false;
	                			System.out.println("dans le quit client");
	                			System.exit(0);
	                			
	                		}
		            		
		            	}
		            	
		            }//fun du while
	            
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
		// TODO Auto-generated method stub
		String serverAddress = (args.length == 0) ? "localhost" : args[1];
		TictactoeClient client = new TictactoeClient(serverAddress);
		
    
        System.out.println("Tic Tac Toe client is Running");
        client.play();
        
        
        
        

        
        

	}

}
