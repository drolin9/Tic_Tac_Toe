/************************
cours: IFT3820
TP3  
Karl Rushford


**************************/

package tictactoe_tp3;

import java.util.Random;


public class TictactoeJeu
{
	
	private final int taille = 3; // taille du jeu
	private char[][] planche; // tableu de la planche
	private boolean gameOver; // fin du jeu
	
	public TictactoeJeu()
	{
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
		gameOver = false;	
	}
	
	
	//pour avoir la valeur de la planche inutile dans la version actuel
	public char[][] getPlanche()
	{
		return planche;
		
	}
	//pour avoir la valeur e gameover inutile dans la version actuel
	public boolean getGameOver()
	{
		return gameOver;
	}
	
	//pour imprimer la valeur de la case utiliser dans printboard
	private void printSymbol( int column, char value )
	{
	  System.out.printf( "|   %c   ", value );

	  if ( column == 2 )
	     System.out.println( "|" );
	} // end method printSymbol
	
	
	// afficher le tableau
	public void printBoard(char[][] plancheT) 
	{
	  System.out.println( " _______________________ " );

	  for ( int row = 0; row < taille; row++ )
	  {
	     System.out.println( "|       |       |       |" );

	     for ( int column = 0; column < taille; column++ )
	        printSymbol( column, plancheT[ row ][ column ] );

	     System.out.println(  "|_______|_______|_______|" );
	  } // end for
	} // end method printBoard	
	
	
	// pour savoir si le coup jouer est valide
	public boolean validMove( int row, int column,char[][] plancheT )
	{
	  return row >= 0 && row < 3 && column >= 0 && column < 3 &&
	     plancheT[ row ][ column ] == ' ';
	} // end method validMove
	
	
	//pour jouer automatiquement un coup
	public int[] playerCPU(char[][] plancheT)
	{
		Random random= new Random();
		//le tableau pour les valeur 
		int[] valeurAleatoire = new int [2];
		
		do
		{
			//on fait un random de 0 a 2
			valeurAleatoire[0]=random.nextInt(3);
			valeurAleatoire[1]=random.nextInt(3);
		}while(!validMove( valeurAleatoire[0], valeurAleatoire[1],plancheT )) ;
			
			
		//planche[valeurAleatoire[0]][valeurAleatoire[1]]='o';
		System.out.println("dans game cpu "+ valeurAleatoire[0] + "   "+valeurAleatoire[1]);
		return valeurAleatoire;
		
	
	}
	
	
	//pour determiner le vainqueur 
	public String gameStatus(char[][] plancheT)
	{   
	  int a;
	  String message="";
	  
	  // check for a win on diagonals
	  if ( plancheT[ 0 ][ 0 ] != ' ' && plancheT[ 0 ][ 0 ] == plancheT[ 1 ][ 1 ] && plancheT[ 0 ][ 0 ] == plancheT[ 2 ][ 2 ] )
	  {
		 message="Le gagnant est le joueur "+plancheT[ 0 ][ 0 ];
	     return message;
	  }
	  else if ( plancheT[ 2 ][ 0 ] != ' ' && plancheT[ 2 ][ 0 ] == plancheT[ 1 ][ 1 ] && plancheT[ 2 ][ 0 ] == plancheT[ 0 ][ 2 ] )
	  {
			 message="Le gagnant est le joueur "+plancheT[ 2 ][ 0 ];
		     return message;
	     
	  }

	  // check for win in rows
	  for ( a = 0; a < 3; a++ )
	     if ( plancheT[ a ][ 0 ] != ' ' && plancheT[ a ][ 0 ] == plancheT[ a ][ 1 ] && plancheT[ a ][ 0 ] == plancheT[ a ][ 2 ] )
	     {
			 message="Le gagnant est le joueur "+plancheT[ a ][ 0 ];
		     return message;
	     }

	  // check for win in columns
	  for ( a = 0; a < 3; a++ )
	     if ( plancheT[ 0 ][ a ] != ' ' && plancheT[ 0 ][ a ] == plancheT[ 1 ][ a ] && plancheT[ 0 ][ a ] == plancheT[ 2 ][ a ] )
	     {
			 message="Le gagnant est le joueur "+plancheT[ 0 ][ a ];
		     return message;
	     }


	  return "LA partie est Nule"; // game is a draw
	} // end method gameStatus

}
