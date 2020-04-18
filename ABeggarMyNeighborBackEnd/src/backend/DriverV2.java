package backend;

import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;


public class DriverV2 {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		ArrayList<Card> deckOfCards = new ArrayList<Card>(52);
		Deque<Card> playerDeck = new LinkedList<Card>();
		Deque<Card> cpuDeck = new  LinkedList<Card>();
		Deque<Card> centerDeck = new LinkedList<Card>();
		Random rand = new Random();
		Card playerCard = new Card(0, "");
		Card cpuCard = new Card(0, "");
		boolean playerTurn = false, cpuTurn = false;
		boolean counter = false;
		boolean winnerDecided = false;
		
		for(int j=1; j<14; j++)							//add Cards to deckOfCards
			deckOfCards.add(new Card(j, "Spades"));
		for(int j=1; j<14; j++)
			deckOfCards.add(new Card(j, "Hearts"));
		for(int j=1; j<14; j++)
			deckOfCards.add(new Card(j, "Diamonds"));
		for(int j=1; j<14; j++)
			deckOfCards.add(new Card(j, "Clubs"));
		
		Collections.shuffle(deckOfCards);				//shuffle the deck
		
		for(int i = 0; i<deckOfCards.size(); i++) {		//split the deck 
			if(i%2 == 0) {								//if i mod 2 = 0
				playerDeck.add(deckOfCards.get(i));		//then add card to playerDeck
			}else {										//if i mod 2 does not equal 0
				cpuDeck.add(deckOfCards.get(i));		//then add card to computerDeck
			}
		}
		
		int turnDecider = rand.nextInt(2);
		switch(turnDecider) {
		
		case 0:
			playerTurn = true;
			break;
		case 1:
			cpuTurn = true;
			break;
		
		}
		
		//initializing all the above
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		System.out.println("Player Deck Size: " + playerDeck.size());
		System.out.println("CPU Deck Size: " + cpuDeck.size());
		System.out.println("Turn decider: " + turnDecider);
		
		
		while(!winnerDecided) {
	
			
				while(playerTurn) { //while player turn is true
					System.out.println("\t\t\t\t\t\tPlayer Turn");
					
					playerCard = playerDeck.peek();
					
					System.out.println("Player Card: " + playerCard);
					
					centerDeck.addFirst(playerCard); 		//adding player card to deck
					
					System.out.println("Center Deck: " + centerDeck.peek() + " Size: " + centerDeck.size());

					System.out.println("Removed from Player Deck: " +playerDeck.remove());
					
					
					
					playerTurn = false;			//switch turns
					cpuTurn = true;
				}
				
				
				
				
				
				
				while(cpuTurn) { //while cpu turn is true
					System.out.println("\t\t\t\t\t\tCPU Turn");

					cpuCard = cpuDeck.peek();
					
					System.out.println("CPU Card: " + cpuCard);
					
					centerDeck.addFirst(cpuCard); 		//adding player card to deck
					
					System.out.println("Center Deck: " + centerDeck.peek() + " Size: " + centerDeck.size());

					System.out.println("Removed from CPU Deck: " +cpuDeck.remove());
					
					
					
					
					
					cpuTurn = false;			//switch turns
					playerTurn = true;
				}
				
			
				

			
							
		}//end of while loop, ends if the winner is decided "winner = true"
		
		if(playerDeck.isEmpty()) {
			System.out.print("CPU is the Winner");
		}else{
			System.out.print("Player is the Winner");

		}//end of if else statement // prints out winner
		
		
		
	}//end of main 	
	
	
	
}//end of driver































