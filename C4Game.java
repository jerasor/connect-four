import java.util.Scanner;

public class C4Game {

	private int field[];
	/*  Field Positions:
	 *  0 |1 |2 |3 |4 |5 |6
	 *  7 |8 |9 |10|11|12|13
	 *  14|15|16|17|18|19|20
	 *  21|22|23|24|25|26|27
	 *  28|29|30|31|32|33|34
	 *  35|36|37|38|39|40|41
	 */

	private compPlayer cP = new compPlayer();

	private Scanner input = new Scanner(System.in);


	public static void main(String[] args) {

		new C4Game();

	}//end main

	C4Game(){

		field = new int[42];

		System.out.println("Welcome to Connect Four!");

		//printing an empty field
		for(int i = 0; i < 42; i++){

			String toDisplay;

			if(field[i] == 0){
				toDisplay = "_";
			}
			else{
				toDisplay = field[i] + "";
			}

			System.out.print(toDisplay + "|");

			if(i == 6 || i == 13 || i == 20 || i == 27 || i == 34 || i == 41){
				System.out.println("");
			}//end if

		}//end for

		//printing the move choices
		System.out.println("1|2|3|4|5|6|7|\n");


		//alternating back and forth between computer and human moves
		//as long as there is not a winner
		while(Winner() == "none"){

			getMove("X", false);
			if(Winner() == "none"){
				getMove("O", true);
			}//end if

		}//end while

		//printing the game results
		if(Winner() != "tie"){
			System.out.println("Congratulations " + Winner() + " player! You have won the game!");
		}
		else{
			System.out.println("Both players have tied! The game ends in a draw.");
		}

	}//end default constructor

	public void getMove(String pc, boolean compPlayer){

		int move;
		
		String userMove;
			
		//getting moves from the human or computer
		if(compPlayer){
			move = cP.getCompMove(field);
		}
		else{
			System.out.println("Please enter your move (1-7):");
			userMove = input.nextLine();
			
			move = Character.getNumericValue(userMove.charAt(0)) - 1;
			
		}//end if

		displayMove(pc, move, compPlayer);

	}//end getMove

	public void displayMove(String pc, int move, boolean compPlayer){

		//if we get an invalid move from a human then we try to get a
		//valid one
		if(move > 6 && !compPlayer){
			getMove(pc, compPlayer);
			return;
		}
		else if (move < 0 && !compPlayer){
			getMove(pc, compPlayer);
			return;
		}//end if

		//incrementing move by 35 because I want to start displaying moves
		//from the bottom of the board to the top (does same thing as commented if statement)
		int displayMove = move + 35;

		//		if(move == 0){
		//			move = 35;
		//		}
		//		else if (move == 1){
		//			move = 36;
		//		}
		//		else if (move == 2){
		//			move = 37;
		//		}
		//		else if (move == 3){
		//			move = 38;
		//		}
		//		else if (move == 4){
		//			move = 39;
		//		}
		//		else if (move == 5){
		//			move = 40;
		//		}
		//		else if (move == 6){
		//			move = 41;
		//		}//end if

		boolean keepGoing = true;
		boolean moveWasMade = false;

		while(keepGoing){

			//putting the token in an empty spot and stopping the while loop
			//and letting us know that a legal move was made
			if(field[displayMove] == 0){

				if(pc == "X"){
					field[displayMove] = 1;
				}
				else if (pc == "O"){
					field[displayMove] = 2;
				}//end if
				keepGoing = false;
				moveWasMade = true;
			}//end if

			//decrementing display move by 7 so that way we can check the spot above
			//the last one we checked
			displayMove-=7;

			//if display move goes negative then we will be out of bounds
			//so we stop the while loop
			if(displayMove < 0){
				keepGoing = false;
			}//end if

		}//end while

		///if a move was not made, then that means the received move is an 
		//illegal move (or there are no legal moves to make)
		//, so we try to get a legal move instead as long as the game is not a tie
		if(!moveWasMade){

			if(!compPlayer && Winner()!="tie"){
				getMove(pc, compPlayer);
			}
			else if (Winner() != "tie"){
				
				//if the illegal move was from the computer
				//then this means something wrong happened in its AI
				//so we just keep trying different moves until we get a legal one
				move+=1;
				
				System.out.println("An error occured in the computer's AI");

				if(move > 6){
					move = 0;
				}//end if

				displayMove(pc, move, compPlayer);

			}//end if

		}//end if

		//printing out who made the legal move (either computer or player, or X or O)
		System.out.print(pc);
		if(!compPlayer && moveWasMade){
			System.out.print(" Player's Move:\n");
		}
		else if (moveWasMade){
			System.out.print(" Computer's Move:\n");
		}

		
		//if a legal move was made then we should print out the updated field
		if(moveWasMade){
			for(int i = 0; i < 42; i++){
				String toDisplay;

				if(field[i] == 0){
					toDisplay = "_";
				}
				else{
					if(field[i] == 1){
						toDisplay = "X";
					}
					else{
						toDisplay = "O";
					}
				}

				System.out.print(toDisplay + "|");
				
				//if we are at the end of a column then we start the next line
				if(i == 6 || i == 13 || i == 20 || i == 27 || i == 34 || i == 41){
					System.out.println("");
				}//end if

			}//end for
			
			//printing out the possible moves
			System.out.println("1|2|3|4|5|6|7|\n");
			
		}//end if

	}//end displayMove

	public String Winner(){

		String winner = "none";

		/*
		 * NOTE: Zero spots do not need to be checked
		 * 
		 * Possible Wins Diagonally in 4x4:
		 * 1|0|0|2
		 * 0|1|2|0
		 * 0|2|1|0
		 * 2|0|0|1
		 * 
		 * Possible Wins by Row in 4x4:
		 * 1|1|1|1
		 * 2|2|2|2
		 * 3|3|3|3
		 * 4|4|4|4
		 * 
		 * Possible Wins by Column in 4x4:
		 * 1|2|3|4
		 * 1|2|3|4
		 * 1|2|3|4
		 * 1|2|3|4
		 * 
		 * Example of a 4x4 grid based on field position 15 (grid is ones):
		 * 0|0|0|0|0|0|0
		 * 0|0|0|0|0|0|0
		 * 0|1|1|1|1|0|0
		 * 0|1|1|1|1|0|0
		 * 0|1|1|1|1|0|0
		 * 0|1|1|1|1|0|0
		 */

		//we are only looping from 0-17 (inclusive) because
		//a 4x4 grid based on field position 17 would allow us to check
		//the other spots as well
		for(int o = 0; o <= 17; o++){

			//skipping down to the next row at spots 4 and 11
			//since a 4x4 grid at spots 3 and 10 would allow us to 
			//check for a win to the right and below that
			if(o == 4){
				o = 7;
			}
			else if (o == 11){
				o = 14;
			}//end if

			//checking spots diagonally down (left to right) from the current spot
			//we are at to see if there is a win there
			if(field[o] == field[o+8]){
				if(field [o] == field[o+16]){
					if(field[o] == field[o+24]){
						if(field[o] != 0){
							if(field[o] == 1){
								winner = "X";
							}
							else{
								winner = "O";
							}
						}
					}
				}
			}//end if for first diagonal check

			//looping through the different column positions to check
			//the spots below it
			for(int i = o; i <= o + 3; i++){

				//checking the spots below this one
				//to see if there are four 1s or 2s in a row
				if(field[i] == field[i+7]){
					if(field [i] == field[i+14]){
						if(field[i] == field[i+21]){
							if(field[i] != 0){
								if(field[i] == 1){
									winner = "X";
								}
								else{
									winner = "O";
								}
							}
						}
					}
				}//end if for column check

			}//end column inner for

			//looping through the different row positions to check
			//the spots to the right of it
			for(int i = o; i <= o + 21; i+=7){

				//checking the spots to the right of this one
				//to see if there are four 1s or 2s in a row
				if(field[i] == field[i+1]){
					if(field [i] == field[i+2]){
						if(field[i] == field[i+3]){
							if(field[i] != 0){
								if(field[i] == 1){
									winner = "X";
								}
								else{
									winner = "O";
								}
							}
						}
					}
				}//end if for row check

			}//end row inner for

			//checking the diagonal line from 3 spots from our current spot
			//this is to check the two possible diagonal wins in our
			//4x4 gird
			if(field[o+3] == field[o+3+6]){
				if(field [o+3] == field[o+3+12]){
					if(field[o+3] == field[o+3+18]){
						if(field[o+3] != 0){
							if(field[o+3] == 1){
								winner = "X";
							}
							else{
								winner = "O";
							}
						}
					}
				}
			}//end if for second diagonal check

		}//end outer for

		//checking for a tie
		int emptySpots = 0;

		//counting how many empty spots there are
		for(int i = 0; i < 42; i++){
			if(field[i] == 0){
				emptySpots++;
			}
		}

		//if there is not any empty spots then a tie has occurred
		if(emptySpots == 0){
			winner = "tie";
		}

		return winner;

	}//end isGameOver

	public int[] getField() {
		return field;
	}//end getField

	public void setField(int field[]) {
		this.field = field;
	}//end setField

}//end C4Game
