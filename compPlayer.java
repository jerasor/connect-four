public class compPlayer {


	private int rowHeuristic[];
	private int colHeuristic[];
	private int leftToRightDiagHeuristic[];
	private int rightToLeftDiagHeuristic[];
	private int superHeuristic[];

	/*  Field Positions:
	 *  0 |1 |2 |3 |4 |5 |6
	 *  7 |8 |9 |10|11|12|13
	 *  14|15|16|17|18|19|20
	 *  21|22|23|24|25|26|27
	 *  28|29|30|31|32|33|34
	 *  35|36|37|38|39|40|41
	 */

	public static void main(String[] args) {
		new compPlayer();
	}//end main

	compPlayer(){
		
		//Initializing different heuristics see project proposal for why these are
		//the values

		rowHeuristic = new int[]{
				2,3,4,5,4,3,2,
				2,3,4,5,4,3,2,
				2,3,4,5,4,3,2,
				2,3,4,5,4,3,2,
				2,3,4,5,4,3,2,
				2,3,4,5,4,3,2
		};

		colHeuristic = new int[]{
				2,2,2,2,2,2,2,
				2,2,2,2,2,2,2,
				2,2,2,2,2,2,2,
				2,2,2,2,2,2,2,
				2,2,2,2,2,2,2,
				2,2,2,2,2,2,2
		};

		rightToLeftDiagHeuristic = new int[]{
				0,0,0,2,2,2,2,
				0,0,3,3,3,3,2,
				0,3,4,4,4,3,2,
				2,3,4,4,4,3,0,
				2,3,3,3,3,0,0,
				2,2,2,2,0,0,0
		};

		leftToRightDiagHeuristic = new int[]{
				2,2,2,2,0,0,0,
				2,3,3,3,3,0,0,
				2,3,4,4,4,3,0,
				0,3,4,4,4,3,2,
				0,0,3,3,3,3,2,
				0,0,0,2,2,2,2
		};

		superHeuristic = new int[42];

	}//end default constructor

	public int[] calculateRowH(int[] field){

		int modifiedH[] = new int[42];
		System.arraycopy(rowHeuristic, 0, modifiedH, 0, 42);

		int sameTokensInArea = 0;

		int rightValid = 0;
		int leftValid = 0;

		//looping through each of the rows
		for(int o = 0; o < 42; o+=7){

			//looping through the values in the rows
			for(int i = o; i < o+7; i++){

				//reseting values
				sameTokensInArea = 0;
				rightValid = 0;
				leftValid = 0;

				//if there is a token here then check its areas
				if(field[i] != 0){

					//setting this spot's value to 0 since it has a token here
					modifiedH[i] = 0;
					sameTokensInArea++;

					//checking right area
					
					//making sure we are not out of bounds then seeing if the area
					//is valid (the i - o < 6,5,4 is to make sure we do not skip to
					//the next row)
					if((i - o < 6) && (field[i+1] == field[i] || field[i+1] == 0)){
						rightValid++;
						//counting tokens in area
						if(field[i+1] == field[i]){
							sameTokensInArea++;
						}

						if((i - o < 5) && (field[i+2] == field[i] || field[i+2] == 0)){
							rightValid++;
							//counting tokens in area
							if(field[i+2] == field[i]){
								sameTokensInArea++;
							}

							if((i - o < 4) && (field[i+3] == field[i] || field[i+3] == 0)){
								rightValid++;
								//counting tokens in area
								if(field[i+3] == field[i]){
									sameTokensInArea++;
								}
							}//end if

						}//end if

					}//end if


					//checking left area
					
					//making sure we are not out of bounds then seeing if the area
					//is valid
					if((i - o > 0) && (field[i-1] == field[i] || field[i-1] == 0)){
						leftValid++;
						//counting tokens in area
						if(field[i-1] == field[i]){
							sameTokensInArea++;
						}

						if((i - o > 1) && (field[i-2] == field[i] || field[i-2] == 0)){
							leftValid++;
							//counting tokens in area
							if(field[i-2] == field[i]){
								sameTokensInArea++;
							}

							if((i - o > 2) && (field[i-3] == field[i] || field[i-3] == 0)){
								leftValid++;
								//counting tokens in area
								if(field[i-3] == field[i]){
									sameTokensInArea++;
								}
							}//end if

						}//end if

					}//end if

					//Modifying values if there is a valid area 
					//(a valid area needs at least three spots from the current spot
					//so that way it is possible to get four in a row)
					if(rightValid + leftValid >= 3){

						if(sameTokensInArea == 3){
							sameTokensInArea = 100;
						}
						else if(sameTokensInArea == 2){
							sameTokensInArea*=2;
						}//end if

						//Modifying right values
						if((i - o < 6) && (field[i+1] == 0)){

							modifiedH[i+1]*=(sameTokensInArea + 1);

							if((i - o < 5) && (field[i+2] == 0)){

								modifiedH[i+2]*=(sameTokensInArea + 1);

								if((i - o < 4) && (field[i+3] == 0)){

									modifiedH[i+3]*=(sameTokensInArea + 1);

								}//end if
							}//end if	
						}//end if

						//Modifying left values
						if((i - o > 0) && (field[i-1] == 0)){

							modifiedH[i-1]*=(sameTokensInArea + 1);

							if((i - o > 1) && (field[i-2] == 0)){

								modifiedH[i-2]*=(sameTokensInArea + 1);

								if((i - o > 2) && (field[i-3] == 0)){

									modifiedH[i-3]*=(sameTokensInArea + 1);

								}//end if
							}//end if	
						}//end if
					}//end if

				}//end if


			}//end inner for
		}//end outer for


		return modifiedH;

	}//end calculateRowH

	public int[] calculateColH(int[] field){

		int modifiedH[] = new int[42];
		System.arraycopy(colHeuristic, 0, modifiedH, 0, 42);

		int sameTokensInArea = 0;

		int areaValid = 0;

		//looping through each of the columns
		for(int o = 0; o < 7; o++){

			//looping through the values in the columns
			for(int i = o; i < 42; i+=7){

				//reseting values
				sameTokensInArea = 0;
				areaValid = 0;

				//if there is a token here then check its area
				if(field[i] != 0){

					//setting this spot's value to 0 since it has a token here
					//and increment sameTokensInArea since there is a token here
					modifiedH[i] = 0;
					sameTokensInArea++;

					//checking area above this spot
					
					//making sure we are not out of bounds then checking the area
					if((i - 7 >= 0) && (field[i-7] == field[i] || field[i-7] == 0)){
						areaValid++;
						//counting tokens in area
						if(field[i-7] == field[i]){
							sameTokensInArea++;
						}

						if((i - 14 >= 0) && (field[i-14] == field[i] || field[i-14] == 0)){
							areaValid++;
							//counting tokens in area
							if(field[i-14] == field[i]){
								sameTokensInArea++;
							}

							if((i - 21 >= 0) && (field[i-21] == field[i] || field[i-21] == 0)){
								areaValid++;
								//counting tokens in area
								if(field[i-21] == field[i]){
									sameTokensInArea++;
								}
							}//end if

						}//end if

					}//end if

					//Modifying values if area is Valid
					if(areaValid >= 3){

						if(sameTokensInArea == 3){
							sameTokensInArea = 100;
						}
						else if(sameTokensInArea == 2){
							sameTokensInArea*=2;
						}//end if

						//Modifying values
						if((i - 7 >= 0) && (field[i-7] == 0)){
							modifiedH[i-7]*=(sameTokensInArea + 1);
						}//end if

						if((i - 14 >= 0) && (field[i-14] == 0)){

							modifiedH[i-14]*=(sameTokensInArea + 1);
						}//end if	

						if((i - 21 >= 0) && (field[i-21] == 0)){

							modifiedH[i-21]*=(sameTokensInArea + 1);

						}//end if

					}//end if

				}//end if

			}//end inner for
		}//end outer for

		return modifiedH;

	}//end calculateRowH

	public int[] calculateRLDiagH(int[] field){

		int modifiedH[] = new int[42];
		System.arraycopy(rightToLeftDiagHeuristic, 0, modifiedH, 0, 42);

		int sameTokensInArea = 0;

		int rightValid = 0;
		int leftValid = 0;

		//looping through each of the right to left Diagonals
		for(int o = 0; o < 42; o++){

			//reseting values
			sameTokensInArea = 0;
			rightValid = 0;
			leftValid = 0;

			//if there is a token here then check its areas
			if(field[o] != 0){

				//setting this spot's value to 0 since it has a token here
				modifiedH[o] = 0;
				sameTokensInArea++;

				//checking left area(we are excluding the left column since it does
				//not have a spot to the lower left of it)
				if(o != 0 && o != 7 && o != 14 && o != 21 && o != 28 && o != 35){

					//making sure we are not out of bounds then checking for a
					//valid area
					if((o + 6 < 42) && (field[o+6] == field[o] || field[o+6] == 0)){
						leftValid++;
						//counting tokens in area
						if(field[o+6] == field[o]){
							sameTokensInArea++;
						}
						
						//(we are excluding the column right of the left column since it does
						//not have a spot two left and two down of it)
						if(o != 1 && o != 8 && o != 15 && o != 22 && o != 29 && o != 36){
							if((o + 12< 42) && (field[o+12] == field[o] || field[o+12] == 0)){
								leftValid++;
								//counting tokens in area
								if(field[o+12] == field[o]){
									sameTokensInArea++;
								}
								
								//(we are excluding the column two right of the left column since it does
								//not have a spot three left and three down of it)
								if(o != 2 && o != 9 && o != 16 && o != 23 && o != 30 && o != 37){
									if((o + 18 < 42) && (field[o+18] == field[o] || field[o+18] == 0)){
										leftValid++;
										//counting tokens in area
										if(field[o+18] == field[o]){
											sameTokensInArea++;
										}
									}//end if
								}//end if
							}
						}
					}//end if
				}//end if


				//checking right area( we are excluding the right column since it
				//does not have a spot to the upper right of it)
				if(o != 6 && o != 13 && o != 20 && o != 27 && o != 34 && o != 41){

					//making sure we are not out of bounds then checking the area
					//to see if it is valid
					if((o - 6 >= 0) && (field[o-6] == field[o] || field[o-6] == 0)){
						rightValid++;
						//counting tokens in area
						if(field[o-6] == field[o]){
							sameTokensInArea++;
						}

						//we are excluding the column left of the far right one since there is
						//not a valid spot up two and two to the right from the current spot
						if(o != 5 && o != 12 && o != 19 && o != 26 && o != 33 && o != 40){
							if((o - 12 >= 0) && (field[o-12] == field[o] || field[o-12] == 0)){
								rightValid++;
								//counting tokens in area
								if(field[o-12] == field[o]){
									sameTokensInArea++;
								}

								//we are excluding the column two left of the far right one since there is
								//not a valid spot up three and three to the right from the current spot
								if(o != 4 && o != 11 && o != 18 && o != 25 && o != 32 && o != 39){
									if((o - 18 >= 0) && (field[o-18] == field[o] || field[o-18] == 0)){
										rightValid++;
										//counting tokens in area
										if(field[o-18] == field[o]){
											sameTokensInArea++;
										}
									}//end if
								}//end if
							}
						}//end if
					}
				}//end if

				//Modifying values if there is a valid area
				if(rightValid + leftValid >= 3){

					if(sameTokensInArea == 3){
						sameTokensInArea = 100;
					}
					else if(sameTokensInArea == 2){
						sameTokensInArea*=2;
					}//end if

					//Modifying left values (if it is empty and valid)
					if(o != 0 && o != 7 && o != 14 && o != 21 && o != 28 && o != 35){
						if((o + 6 < 42) && (field[o+6] == 0)){

							modifiedH[o+6]*=(sameTokensInArea + 1);
						}

						if(o != 1 && o != 8 && o != 15 && o != 22 && o != 29 && o != 36){
							if((o + 12 < 42) && (field[o+12] == 0)){

								modifiedH[o+12]*=(sameTokensInArea + 1);
							}

							if(o != 2 && o != 9 && o != 16 && o != 23 && o != 30 && o != 37){
								if((o + 18 < 42) && (field[o+18] == 0)){

									modifiedH[o+18]*=(sameTokensInArea + 1);
								}

							}
						}
					}//end if

					//Modifying right values (if it is empty and valid)
					if(o != 6 && o != 13 && o != 20 && o != 27 && o != 34 && o != 41){
						if((o - 6 >= 0) && (field[o-6] == 0)){

							modifiedH[o-6]*=(sameTokensInArea + 1);
						}

						if(o != 5 && o != 12 && o != 19 && o != 26 && o != 33 && o != 40){
							if((o - 12 >= 0) && (field[o-12] == 0)){

								modifiedH[o-12]*=(sameTokensInArea + 1);
							}

							if(o != 4 && o != 11 && o != 18 && o != 25 && o != 32 && o != 39){
								if((o - 18 >= 0) && (field[o-18] == 0)){

									modifiedH[o-18]*=(sameTokensInArea + 1);
								}

							}
						}
					}//end if

				}//end if

			}//end if

		}//end outer for


		return modifiedH;


	}//end calculateRowH

	public int[] calculateLRDiagH(int[] field){

		//getting the base heuristic so it can be modified based upon
		//the current field
		int modifiedH[] = new int[42];
		System.arraycopy(leftToRightDiagHeuristic, 0, modifiedH, 0, 42);

		int sameTokensInArea = 0;

		int rightValid = 0;
		int leftValid = 0;

		//looping through each of the right to left Diagonals
		for(int o = 0; o < 42; o++){

			//reseting values
			sameTokensInArea = 0;
			rightValid = 0;
			leftValid = 0;

			//if there is a token here then check its areas
			if(field[o] != 0){

				//setting this spot's value to 0 since it has a token here
				modifiedH[o] = 0;
				sameTokensInArea++;

				//checking right area(we are excluding the right column since it does not
				//have diagonals to the bottom right of this spot)
				if(o != 6 && o != 13 && o != 20 && o != 27 && o != 34 && o != 41){

					//making sure that the spot to the bottom right is not out of bounds
					//then seeing if the area is valid
					if((o + 8 < 42) && (field[o+8] == field[o] || field[o+8] == 0)){
						rightValid++;
						//counting tokens in area
						if(field[o+8] == field[o]){
							sameTokensInArea++;
						}

						//checking right area(we are excluding the column left of the right column since it does not
						//have diagonals two down and two left of this spot)
						if(o != 5 && o != 12 && o != 19 && o != 26 && o != 33 && o != 40){
							if((o + 16< 42) && (field[o+16] == field[o] || field[o+16] == 0)){
								rightValid++;
								//counting tokens in area
								if(field[o+16] == field[o]){
									sameTokensInArea++;
								}

								//checking right area(we are excluding the column two left from the right column since it does not
								//have diagonals three down and three left of this spot)
								if(o != 4 && o != 11 && o != 18 && o != 25 && o != 32 && o != 39){
									if((o + 24 < 42) && (field[o+24] == field[o] || field[o+24] == 0)){
										rightValid++;
										//counting tokens in area
										if(field[o+24] == field[o]){
											sameTokensInArea++;
										}
									}//end if
								}//end if
							}//end if
						}
					}
				}//end if


				//checking left area(we are excluding the left column since it does not
				//have diagonals to the upper left of this spot)
				if(o != 0 && o != 7 && o != 14 && o != 21 && o != 28 && o != 35){

					//making sure that the spot to the upper left is not out of bounds
					//then seeing if the area is valid
					if((o - 8 >= 0) && (field[o-8] == field[o] || field[o-8] == 0)){
						leftValid++;
						//counting tokens in area
						if(field[o-8] == field[o]){
							sameTokensInArea++;
						}

						//checking left area(we are excluding the column to the right of the left column since it does not
						//have diagonals two up and two left of this spot)
						if(o != 1 && o != 8 && o != 15 && o != 22 && o != 29 && o != 36){
							if((o - 16 >= 0) && (field[o-16] == field[o] || field[o-16] == 0)){
								leftValid++;
								//counting tokens in area
								if(field[o-16] == field[o]){
									sameTokensInArea++;
								}

								//checking left area(we are excluding the column two to the right of the left column since it does not
								//have diagonals three up and three left of this spot)
								if(o != 2 && o != 9 && o != 16 && o != 23 && o != 30 && o != 37){
									if((o - 24 >= 0) && (field[o-24] == field[o] || field[o-24] == 0)){
										leftValid++;
										//counting tokens in area
										if(field[o-24] == field[o]){
											sameTokensInArea++;
										}
									}//end if
								}//end if
							}//end if
						}
					}
				}//end if

				//Modifying values if the area is valid
				if(rightValid + leftValid >= 3){

					if(sameTokensInArea == 3){
						sameTokensInArea = 100;
					}
					else if(sameTokensInArea == 2){
						sameTokensInArea*=2;
					}//end if

					//Modifying right values
					if(o != 6 && o != 13 && o != 20 && o != 27 && o != 34 && o != 41){

						if((o + 8 < 42) && (field[o+8] == 0)){

							modifiedH[o+8]*=(sameTokensInArea + 1);
						}

						if(o != 5 && o != 12 && o != 19 && o != 26 && o != 33 && o != 40){

							if((o + 16 < 42) && (field[o+16] == 0)){

								modifiedH[o+16]*=(sameTokensInArea + 1);
							}

							if(o != 4 && o != 11 && o != 18 && o != 25 && o != 32 && o != 39){

								if((o + 24 < 42) && (field[o+24] == 0)){

									modifiedH[o+24]*=(sameTokensInArea + 1);
								}

							}
						}
					}//end if

					//Modifying left values
					if(o != 0 && o != 7 && o != 14 && o != 21 && o != 28 && o != 35){
						if((o - 8 >= 0) && (field[o-8] == 0)){

							modifiedH[o-8]*=(sameTokensInArea + 1);
						}

						if(o != 1 && o != 8 && o != 15 && o != 22 && o != 29 && o != 36){
							if((o - 16 >= 0) && (field[o-16] == 0)){

								modifiedH[o-16]*=(sameTokensInArea + 1);
							}

							if(o != 2 && o != 9 && o != 16 && o != 23 && o != 30 && o != 37){
								if((o - 24 >= 0) && (field[o-24] == 0)){

									modifiedH[o-24]*=(sameTokensInArea + 1);
								}

							}
						}
					}//end if

				}//end if

			}//end if

		}//end outer for


		return modifiedH;

	}//end calculateRowH

	public int[] calculateSuperH(int[] field){

		//getting the modified values from all the heuristics
		int modifiedH[] = new int [42];
		System.arraycopy(superHeuristic, 0, modifiedH, 0, 42);

		int rowH[] = new int [42];
		System.arraycopy(calculateRowH(field), 0, rowH, 0, 42);

		int colH[] = new int[42];
		System.arraycopy(calculateColH(field), 0, colH, 0, 42);

		int rldH[] = new int[42];
		System.arraycopy(calculateRLDiagH(field), 0, rldH, 0, 42);

		int lrdH[] = new int[42];
		System.arraycopy(calculateLRDiagH(field), 0, lrdH, 0, 42);

		//summing their values into modifiedH
		for(int i = 0; i < 42; i++){

			modifiedH[i] += rowH[i];
			modifiedH[i] += colH[i];
			modifiedH[i] += rldH[i];
			modifiedH[i] += lrdH[i];

		}//end for
		
		//looping through modifiedH (except for last row)
		//so that way we can set the spots below a high value spot
		//to one (as long as it is empty and not also high value)
		//so that way the computer will not give up a high value
		//spot to its opponent, unless it has to
		for(int i = 0; i < 35; i++){
			if (modifiedH[i] > 500){
				if(modifiedH[i+7] != 0){
					if(modifiedH[i+7] < 500){
						modifiedH[i+7] = 1;
					}
				}
			}
		}

		return modifiedH;

	}//end calculateRowH

	public int getCompMove(int[] field){

		int bestMove = 0;
		int bMoveValue = -999;

		int valueField[] = new int[42];
		System.arraycopy(calculateSuperH(field), 0, valueField, 0, 42);
		
//		//prints out valueField (used for development)
//		System.out.println("determining compMove:");
//		for(int i = 0; i < 42; i++){
//			
//			System.out.print(valueField[i] + "|");
//
//			if(i == 6 || i == 13 || i == 20 || i == 27 || i == 34 || i == 41){
//				System.out.println("");
//			}//end if
//
//		}//end for

		int possibleMoves[] = new int[7];

		boolean validMoveFound = false;
		
		//looping through the possible moves 0-6
		for(int o = 0; o < 7; o++){

			validMoveFound = false;

			//starting at the bottom of the field array
			//and working our way up a column
			for(int i = 35 + o; i >= 0 + o; i-=7){

				//adding the value that is closest to the bottom
				//of the field array in a column to possibleMoves
				if(field[i] == 0 && !validMoveFound){
					validMoveFound = true;

					possibleMoves[o] = valueField[i];
				}//end if

			}//end for
		}//end outer for

		//getting the highest value in possible moves and returning its position
		for(int i = 0; i < 7; i++){

			if(possibleMoves[i] > bMoveValue){
				bestMove = i;
				bMoveValue = possibleMoves[i];
			}

		}//end for

		return bestMove;

	}//end getCompMove

	//getter and setters
	public int[] getRowHeuristic() {
		return rowHeuristic;
	}

	public void setRowHeuristic(int rowHeuristic[]) {
		this.rowHeuristic = rowHeuristic;
	}

	public int[] getColHeuristic() {
		return colHeuristic;
	}

	public void setColHeuristic(int colHeuristic[]) {
		this.colHeuristic = colHeuristic;
	}

	public int[] getLeftToRightDiagHeuristic() {
		return leftToRightDiagHeuristic;
	}

	public void setLeftToRightDiagHeuristic(int leftToRightDiagHeuristic[]) {
		this.leftToRightDiagHeuristic = leftToRightDiagHeuristic;
	}

	public int[] getRightToLeftDiagHeuristic() {
		return rightToLeftDiagHeuristic;
	}

	public void setRightToLeftDiagHeuristic(int rightToLeftDiagHeuristic[]) {
		this.rightToLeftDiagHeuristic = rightToLeftDiagHeuristic;
	}

	public int[] getSuperHeuristic() {
		return superHeuristic;
	}

	public void setSuperHeuristic(int superHeuristic[]) {
		this.superHeuristic = superHeuristic;
	}



}//end compPlayer
