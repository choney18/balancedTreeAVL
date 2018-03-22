/*
 * The University of North Carolina at Charlotte
 * ITCS 3153 - Intro to Artificial Intelligence
 * 
 * Programming Assignment 2 - Adversarial Search
 * 
 * Based on code from Dilip Kumar Subramanian
 * 
 * Modified by Julio C. Bahamon
 */

import java.util.ArrayList;


public class Minimax
{
	/**
	 *
	 * This will recursively call Minimax depending on the current player, if the
	 * current player is O, the algorithm will find the MAX available board and if
	 * the current player is X, the algorithm will find the MIN avaiLable board.
	 * 
	 * We assume that the human player is X and that the AI is O
	 * 
	 * The terminal state check is done at the start before recursively calling
	 * Minimax, the terminal checks are checkWinner for player X(Human) and O(AI) and
	 * if the board state is full, if either of the conditions gets satisfied then
	 * it will return the value as decided if winner is AI(O), assign +1, if
	 * winner is User(X) assign -1 and if the state is draw assign 0 and return
	 *
	 * @param state
	 *            board for which the Minimax will be called recursively
	 * @param player
	 *            player for whom the game state should be generated
	 * @return boolean true/false
	 **/
	static int badMoves = 0;
	static int goodMoves = 0;
	public static int miniMax(GameState state, String player)
	{
		
		
		if (state.boardFullCheck(state.getBoardState())==true){
			return 0;
		}
		if (state.checkWinner(state.getBoardState(), "X")==true){
			return (-1000);
		}
		if (state.checkWinner(state.getBoardState(), "O")==true){
			return (1000);
		}
		
		GameAI.setTotalCount(GameAI.getTotalCount() + 1);
//check three conditions
		
		if(player == "O"){
		ArrayList<GameState> list = new ArrayList<GameState>();
		list = state.generateSuccessors(state, "X");
		double value = Double.POSITIVE_INFINITY;
		for(int i = 0;i< list.size();i++){		
			
			value = Math.min(value, miniMax(list.get(i), "X"));
			/*if (i == (list.size()-1){
				badMoves++;
			}*/
			//badMoves++;
			
			}return (int)value;
		
		}
		
		if(player == "X"){
			
			//	TODO: Implement the minimax function
			ArrayList<GameState> list = new ArrayList<GameState>();
			list = state.generateSuccessors(state, "O");
			double value = Double.NEGATIVE_INFINITY;
			for(int i = 0;i< list.size();i++){	
					
			value = Math.max(value, miniMax(list.get(i), "O"));
				
				}return (int)value;
			}
		//System.out.println(goodMoves-badMoves);
		//return (goodMoves-badMoves);
	
		/*	HINTS:
		 *  
		 *  	Use Double.NEGATIVE_INFINITY and Double.POSITIVE_INFINITY
		 *  for the initialization values
		 *  
		 *  Use the checkWinner method in GameState to check leaf nodes
		 *  
		 *  Use boardFullCheck method in GameState to check for tied games
		 *  
		 *  Use the printBoardStateMax to produce debug output
		 *  
		 */
		
		
//		DEBUG OUTPUT CODE
//		Log.debug("Inside maxValue " + " " + value);
//		Log.debug("Inside minValue " + " " + best);
		return 0;

	}
}