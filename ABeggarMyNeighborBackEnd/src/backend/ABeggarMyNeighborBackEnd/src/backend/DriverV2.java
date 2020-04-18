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
					playerCard = playerDeck.peek();						//put try catch here later, if no card in deck the catch the exception and change winnerDecided to true
					System.out.println("Player Card: " + playerCard);
					
					
					
					
					while(counter) {									//while counter is true, flip the amount of cards needed to try and trump face card
						if(playerCard.getRank() > 10) {
							counter = false;
							break;
						}
						int faceCardRank = centerDeck.getFirst().getRank();
						int loop =0;
						switch(faceCardRank) {
							case 11:
								loop = 1;
								break;
							case 12:
								loop = 2;
								break;
							case 13:
								loop = 3;
								break;
							case 14:
								loop = 4;
								break;
						}
						System.out.println("Time to loop: " + loop);
						System.out.println("Player Card: " + playerCard.getRank());
						
						for(int i = 0; i>loop;i++) {
							if(playerCard.getRank() <= 10) {						//if card drawn is not a face card
								centerDeck.addFirst(playerCard); 				//adding player card to deck
								System.out.println("Center Deck: " + centerDeck.peek() + "\t\tSize: " + centerDeck.size());

								playerDeck.remove();							//remove card from OG deck
								playerCard = playerDeck.peek();					//put try catch here later, if no card in deck the catch the exception and change winnerDecided to true
							}else {
							counter = false;
							break;
							
							
							}//end of if else statement
						}//end of for loop
						
						
						
						if(playerCard.getRank() <= 10) { //drawn x amount of cards to try and trump face card, but has failed, the center deck goes to opponents deck
							centerDeck.addFirst(playerCard);		//add card to center deck
							playerDeck.remove(playerCard);			//remove card from OG deck
							cpuDeck.addAll(centerDeck);				//add center deck to opponents deck
							counter = false;						//set counter to false
						}
					}//end of while counter is true loop
					
					
					
					
					
					
					//perfect do not touch below
					if(!counter) {
					centerDeck.addFirst(playerCard); 		//adding player card to deck
					
					System.out.println("Center Deck: " + centerDeck.peek() + " Size: " + centerDeck.size());

					System.out.println("Removed from Player Deck: " +playerDeck.remove());
					}
					
					
					if(playerCard.getRank() > 10 ) { 		//if player card is a face card then turn counter to true
						
						counter = true;
					}
					
					playerTurn = false;			//switch turns
					cpuTurn = true;
				}
				
				
				
				
				
				
				while(cpuTurn) { //while cpu turn is true
					System.out.println("\t\t\t\t\t\tCPU Turn");

					cpuCard = cpuDeck.peek();
					
					System.out.println("CPU Card: " + cpuCard);

					
					
					
					while(counter) {			//while counter is true, flip the amount of cards needed to try and trump face card

						if(cpuCard.getRank() > 10) {
							counter = false;
							break;
						}
						
						int faceCardRank = centerDeck.getFirst().getRank();
						int loop =0;
						
						switch(faceCardRank) {
						case 11:
							loop = 1;
							break;
						case 12:
							loop = 2;
							break;
						case 13:
							loop = 3;
							break;
						case 14:
							loop = 4;
							break;
					}
						
						
						
						
						for(int i = 0; i>loop;i++) {
							System.out.print("i: " + i);
							if(cpuCard.getRank() <= 10) {				//if card drawn is not a face card
								centerDeck.addFirst(cpuCard); 		//adding player card to deck
								cpuDeck.remove();					//remove card from OG deck
								cpuCard = cpuDeck.peek();			//put try catch here later, if no card in deck the catch the exception and change winnerDecided to true
							}else {
							counter = false;
							break;
							}//end of if else statement
						}//end of for loop
						
						//if counter is still true and have drawn x amount of cards to try and trump face card, but has failed, the center deck goes to opponents deck
						if(cpuCard.getRank() <= 10) {
							
							centerDeck.addFirst(cpuCard);		//add card to center deck
							cpuDeck.remove(cpuCard);			//remove card from OG deck
							
							playerDeck.addAll(centerDeck);				//add center deck to opponents deck
							
							counter = false;						//set counter to false
						}
						
					}//end of while counter is true loop
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					///perfect do not touch below
					if(!counter) {

					centerDeck.addFirst(cpuCard); 		//adding cpu card to deck
					
					System.out.println("Center Deck: " + centerDeck.peek() + " Size: " + centerDeck.size());

					System.out.println("Removed from CPU Deck: " +cpuDeck.remove());
					}
					
					if(cpuCard.getRank() > 10 ) { 		//if player card is a face card then turn counter to true
						
						counter = true;
					}
					
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
	
	
	public int tryFaceCard() {
		
		return 0;
	}
	
	public int findLoop(int number) {
		int numberLoop = 0;
		switch(number) {
			case 11:
				numberLoop = 1;
				break;
			case 12:
				numberLoop = 2;
				break;
			case 13:
				numberLoop = 3;
				break;
			case 14:
				numberLoop = 4;
				break;
		}
		return numberLoop;
	}
	
	
	
	
}//end of driver


































