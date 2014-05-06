import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

class RunnableDemo implements Runnable {
	static boolean contains;
	static int n, m;
	static String p;
	static int solutionCountQ=0;
	static int solutionCountR=0;
	static int solutionCountB=0;
	static int solutionCount;
	static LinkedList<int[]> solutionList_i = new LinkedList<int[]>();
	static LinkedList<int[]> solutionList_Q = new LinkedList<int[]>();
	static LinkedList<int[]> solutionList_R = new LinkedList<int[]>();
	static LinkedList<String[]> solutionList_B = new LinkedList<String[]>();
	static LinkedList<String[]> solutionList_s = new LinkedList<String[]>();
	static int diag1[];
	static int diag2[];
	static int diag3[];
	static int finalSolution[];
	static int finalSolution1[];
	
   private Thread t;
   private String threadName;
   
   RunnableDemo( String name){
       threadName = name;
       System.out.println("Creating " +  threadName );
   }
   
   public void run() {
      if (threadName.equals("Q")){
    		diag1 = new int[n];
    		diag2= new int[n];
    		finalSolution= new int[n]; 
    		for(int i=0; i<n; i++)
    		{
    			diag1[i] = -1;
    			diag2[i] = -1;
    			finalSolution[i] = -1;
    		}

    		solutionList_Q=twoDim_Q_recurse(0, finalSolution, solutionList_Q, diag1, diag2);
    		if(p.equals("Q")){
    			solutionList_i = solutionList_Q;
    			solutionCount = solutionCountQ;
    		}

      }
      else if (threadName.equals("R")){
  		finalSolution1= new int[n]; 

  		for(int i=0; i<n; i++)
  		{
  			finalSolution1[i] = -1;
  		}
  		solutionList_R = twoDim_R_recurse(0,finalSolution1, solutionList_R);
  		if(p.equals("R")){
  			solutionList_i = solutionList_R;
  			solutionCount = solutionCountR;
  		}

      }
      else if (threadName.equals("B")){
  		diag3 = new int[n+m-1];
  		String finalSolution2[];
  		if(  (m-  ((m-(n-1))*2)  )   <0){
  			finalSolution2= new String[m]; // accomodate for x and y coordinates 
  		}
  		else{
  			finalSolution2= new String[m+(m-((m-(n-1))*2))];
  		}
  		for(int i=0; i<diag3.length; i++)
  		{
  			diag3[i] = -1;
  		}

  		for(int i=0; i<finalSolution2.length; i++)
  		{	    
  			finalSolution2[i] = ("" + -1);
  		}
  		solutionList_B = twoDim_B_recurse(0, 0, solutionList_B, finalSolution2, diag3);
  		if(p.equals("B")){
  			solutionList_s = solutionList_B;
  			solutionCount = solutionCountB;
  		}
      }
      
   }
   
   public void start ()
   {
      System.out.println("Starting " +  threadName );
      if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }


   public static void main(String args[]) {
      
  	JFrame gui = new JFrame("GUI");
	JOptionPane.showMessageDialog(gui,"Version 1.1\nCalculates number of optimal solutions such that a max number of Queens, Rooks, or Bishops are placed on an NxM chessboard without checking any other piece.\nInputs: Number of rows (N), Number of Columns (M), and Chess piece (P)\nOutputs: Calculation time for all three chess pieces (Q/R/B) on an NxM board, Number of optimal solutions for P, Option to display optimal solutions for P");
	n = Integer.parseInt(JOptionPane.showInputDialog(gui,"Number of Rows (N): "));
	m = Integer.parseInt(JOptionPane.showInputDialog(gui,"Number of columns (M): "));
	p = "";
	while (!(p.equals("Q") || p.equals("R") || p.equals("B"))){
	p = (String) JOptionPane.showInputDialog(gui,"Piece (P): [Q/R/B] ");
	}

	if(n>m){
		int temp=n;
		n=m;
		m=temp;
		System.out.println("Swapped n and m");
	}
	
	//long startTime= System.nanoTime();
	
	//if(p.equals("Q")){

		//solutionList_i = solutionList_Q;
		//long endQueen = System.nanoTime()- startTime;
		//startTime= System.nanoTime();
	//}
	//else if(p.equals("R")){

		//solutionList_i = twoDim_R_recurse(0,finalSolution, solutionList_i);
		//long endRook = System.nanoTime()- startTime;
		//startTime= System.nanoTime();
	//}
	//else if(p.equals("B")){

		//solutionList_s = twoDim_B_recurse(0, 0, solutionList_s, finalSolution2, diag3);
		//long endBishop = System.nanoTime()- startTime;
	//}

	      RunnableDemo R1 = new RunnableDemo("Q");
	      RunnableDemo R2 = new RunnableDemo( "R");
	      RunnableDemo R3 = new RunnableDemo( "B");
	      R1.start();
	      R2.start();	      
	      R3.start();
	      
	String print;
//	/long totaltime = endQueen+endRook+endBishop;
	//JOptionPane.showMessageDialog(gui,"The computation time for Queen: " + endQueen + " nanoseconds\nThe computation time for Rook: " + endRook + " nanoseconds\nThe computation time for Bishop: " + endBishop + " nanoseconds\nThe total computation time: " + totaltime + " nanoseconds");


	
	while(true) {

		int inp = Integer.parseInt(JOptionPane.showInputDialog(gui,
				"There are " + solutionCount + " unique solutions for " + p + ", " + n + "x" + 
						m + ".\n Please enter a number between 1 and " + solutionCount + " to view a solution. Enter -1 to quit.",JOptionPane.PLAIN_MESSAGE));
		if(inp == -1)
			break;

		if(p.equals("Q") || p.equals("R")){
			int t[] = solutionList_i.get(inp-1);
			print = printNiceBoard_i(t,p);
		}
		else{
			String t[] = solutionList_s.get(inp-1);
			print = printNiceBoard_s(t,p);
		}

		JOptionPane.showMessageDialog(gui,print);
	}
	System.exit(0);
   }  
   
   //QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ
   
   private static LinkedList<int[]> twoDim_Q_recurse(int c, int[] solution, LinkedList<int[]> A, int[] diag1, int[] diag2){
 	  System.out.println("Queen");
//      try {
//    	  Thread.sleep(1);
//      } catch (InterruptedException e) {
//      }
 	 contains = false;

		if (c == n)
			return A;
		for(int r=0; r<m; r++){ //go by row

			for(int k = 0; k < c; k++){ // go through the arrays to check if the row is valid
				if(solution[k] == r || diag1[k] == r-c+(n-1) || diag2[k] == r+c){
					contains= true;
					break;
				}		
			}
			if(contains==false)
			{ //if all ways are clear, add
				solution[c]= r;
				diag1[c] = r-c+(n-1);
				diag2[c] = r+c;
				A = twoDim_Q_recurse(c+1, solution, A, diag1, diag2);
			}
			contains=false;
		}
		int ret[] = new int[solution.length];

		//we've exhausted the rows we can put queens for the specified column
		if(solution[n-1] != -1){
			String temp = "";
			for(int i =0; i< solution.length; i++) {
				temp=temp.concat("" + solution[i]);
			}

			for(int i = 0; i < solution.length; i++)
				ret[i] = solution[i];
			A.add(ret);

			solutionCountQ++;
		}

		solution[c] =-1;
		return A;
	}

	/// RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR

	private static LinkedList<int[]> twoDim_R_recurse(int c, int[] solution, LinkedList<int[]> A){
	 	  System.out.println("Rook");
//	      try {
//	    	  Thread.sleep(1);
//	      } catch (InterruptedException e) {
//	      }
	 	  contains = false;
		if (c == n)
			return A;
		for(int r=0; r<n; r++){ //go by row
			for(int k = 0; k < c; k++){ // go through the arrays to check if the row is valid
				if(solution[k] == r){
					contains= true;
					break;
				}		
			}
			if(contains==false)
			{ //if all ways are clear, add
				solution[c]= r;
				A = twoDim_R_recurse(c+1, solution, A);
			}
			contains=false;
		}  
		//we've exhausted the rows we can put queens for the specified column
		int ret[] = new int[solution.length];
		//we've exhausted the rows we can put rook for the specified column
		if(solution[n-1] != -1){

			for(int i = 0; i < solution.length; i++)
				ret[i] = solution[i];
			A.add(ret);

			solutionCountR++;
		}

		solution[c] =-1;
		return A;
	}

	//BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB

	private static LinkedList<String[]> twoDim_B_recurse(int c, int r, LinkedList<String[]> A, String[] solution, int[] otherDiag){
	 	  System.out.println("Bishop");
//	      try {
//	    	  Thread.sleep(1);
//	      } catch (InterruptedException e) {
//	      }
	 	  contains = false;
		if (solution[solution.length-1] != ("" +(-1)) ) //n+m-1 is the number of diagonals
			return A; 

		while(c>(-1) && r<m){ //  go through each square in the diagonal
			for(int k = 0; k < solution.length; k++){ // go through the arrays to check if the row is valid
				if(otherDiag[k] == (r-c+(n-1))){
					contains= true;
					break;
				}		
			}
			if(contains==false){ // clear
				solution[r+c]= (c + "" + r);
				otherDiag[r+c] = (r-c+(n-1));

				// Proceed to next diagonal. 
				if( (c+r+1) < n){ // If still on the top, go to the next column
					A = twoDim_B_recurse(c+r+1, 0, A, solution, otherDiag);
				}
				else if( (c+r+1) >=n){ // If you've reached the side, go down the column
					A = twoDim_B_recurse(n-1, ( (c+r+1)-(n-1) ), A, solution, otherDiag);
				}
			}
			contains=false;
			c--; // iterate to next space in diagonal by subtracting 1 from column
			r++; // iterate to next space in diagonal by adding 1 to the row

		}

		String ret[] = new String[solution.length];

		if(!solution[solution.length-1].equals("" + (-1 ))){  //good solution

			for(int i = 0; i < solution.length; i++)
				ret[i] = (solution[i]);
			A.add(ret);

			solutionCountB++;
		}

		solution[r+c] = ("" +(-1));
		otherDiag[r+c] = (-1);
		return A;

	}


	static private void printBoard(int[] board){
		for(int z:board)
			System.out.print(z);//good solution
		System.out.println();	

	}

	static private String printNiceBoard_i(int[] coordinates, String piece){
		String ret2 = "";
		for(int a: coordinates)
			ret2=ret2.concat("" +a);
		ret2=ret2.concat("\n");

		// Draw board
		for(int c=0; c<n; c++){
			if(c==0){
				for(int t=0; t<m+3+(m/5); t++)
					ret2 = ret2.concat("=");
				ret2 = ret2.concat("\n");	
			}

			ret2 = ret2.concat("[ ");	

			int ct = 0;
			for(int d=0; d<m; d++) {
				if (d == coordinates[c]) {
					ret2 = ret2.concat(piece);
					ct++;
				}
				else
					ret2 = ret2.concat("O");
			}
			ct = 0;

			ret2 = ret2.concat(" ]");
			ret2 = ret2.concat("\n");

			if(c==n-1){
				for(int t=0; t<m+3+ (m/5); t++)
					ret2 = ret2.concat("=");
				ret2 = ret2.concat("\n");	
			}

		}
		return ret2;
	}
	static private String printNiceBoard_s(String[] coordinates, String piece){
		String ret2 = "";
		boolean found = false;
		for(String a: coordinates)
			ret2=ret2.concat(a);
		ret2=ret2.concat("\n");
		// Draw board

		for(int c=0; c<n; c++){
			if(c==0){
				for(int t=0; t<m+3+(m/5); t++)
					ret2 = ret2.concat("=");
				ret2 = ret2.concat("\n");	
			}

			ret2 = ret2.concat("[ ");	
			int ct = 0;
			for(int d=0; d<m; d++) {
				for(String s: coordinates){
					if(s.contains(""+c+d) ) {
						found = true;
						break;
					}
				}
				if(found==true ) {
					ret2 = ret2.concat(piece);
					ct++;
				}
				else
					ret2 = ret2.concat("O");
				found= false;
			}
			ct = 0;

			ret2 = ret2.concat(" ]");
			ret2 = ret2.concat("\n");

			if(c==n-1){
				for(int t=0; t<m+3+(m/5); t++)
					ret2 = ret2.concat("=");
				ret2 = ret2.concat("\n");	
			}

		}
		return ret2;
	}
   
   
}
