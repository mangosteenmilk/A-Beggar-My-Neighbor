package backend;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;


public class DriverV3 {

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
	
			
				while(playerTurn) { 									//while player turn is true
					System.out.println("\n\n\t\t\t\t\tPlayer Turn");
					System.out.println("\t\t\t\t\tBefore Player Deck Size: " + playerDeck.size());
					System.out.println("\t\t\t\t\tBefore CPU Deck Size: " + cpuDeck.size());
				
				
					playerCard = playerDeck.peek();						//put try catch here later, if no card in deck the catch the exception and change winnerDecided to true

					if(playerCard == null) {							//if card is null then break from while loop and decide the winner
						winnerDecided = true;					
						playerTurn = false;
						cpuTurn = false;
						break;
					}
					
					
					
					
					System.out.println("Player Card: " + playerCard);
					
					centerDeck.addFirst(playerCard); 		//adding player card to deck
					
					System.out.println("Center Deck Size: " + centerDeck.size());

					System.out.println("Removed from Player Deck: " + playerDeck.remove());
					
					
					
					System.out.println("\t\t\t\t\tAfter Player Deck Size: " + playerDeck.size());
					System.out.println("\t\t\t\t\tAfter CPU Deck Size: " + cpuDeck.size());
				
					
					playerTurn =false;
					cpuTurn = true;
					
				}//end of while counter is true loop
					
					

				while(cpuTurn) { //while cpu turn is true
					System.out.println("\n\n\t\t\t\t\tCPU Turn");
					System.out.println("\t\t\t\t\tBefore Player Deck Size: " + playerDeck.size());
					System.out.println("\t\t\t\t\tBefore CPU Deck Size: " + cpuDeck.size());
				
				
					cpuCard = cpuDeck.peek();						//put try catch here later, if no card in deck the catch the exception and change winnerDecided to true

					if(cpuCard == null) {							//if card is null then break from while loop and decide the winner
						winnerDecided = true;					
						playerTurn = false;
						cpuTurn = false;
						break;
					}
					
					
					
					
					System.out.println("CPU Card: " + cpuCard);
					
					centerDeck.addFirst(cpuCard); 		//adding player card to deck
					
					System.out.println("Center Deck Size: " + centerDeck.size());

					System.out.println("Removed from CPU Deck: " + cpuDeck.remove());
					
					
					
					System.out.println("\t\t\t\t\tAfter Player Deck Size: " + playerDeck.size());
					System.out.println("\t\t\t\t\tAfter CPU Deck Size: " + cpuDeck.size());
					
					
					cpuTurn = false;
					playerTurn =true;

				}
					
		}
		
			
		
		if(playerDeck.isEmpty()) {
			System.out.print("CPU is the Winner");
		}else{
			System.out.print("Player is the Winner");

		}//end of if else statement // prints out winner
		
		
	}//end of main 	
	
	
	
	
	
}//end of driver


































