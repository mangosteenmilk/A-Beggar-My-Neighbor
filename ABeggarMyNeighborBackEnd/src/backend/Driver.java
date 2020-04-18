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


public class Driver {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		ArrayList<Card> deckOfCards = new ArrayList<Card>(52);
		Deque<Card> playerDeck = new LinkedList<Card>();
		Deque<Card> compDeck = new  LinkedList<Card>();
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
				compDeck.add(deckOfCards.get(i));		//then add card to computerDeck
			}
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		String inputFlip;
		Scanner scan = new Scanner(System.in);
		
		int turnDecider = rand.nextInt(2);
		switch(turnDecider) {
		
		case 0:
			playerTurn = true;
			break;
		case 1:
			cpuTurn = true;
			break;
		
		}
		
		System.out.println("Turn decider: " + turnDecider);
		
		
		while(!winnerDecided) {
	
			
				while(playerTurn) {
					System.out.println("\t\tPlayer's Turn");
					System.out.println("\t\tPlayer Deck Size: " + playerDeck.size());
					System.out.println("\t\tPlayer Card: " + playerDeck.peek().getRank());
					System.out.println("\t\tCounter: " + counter);
					try{
						playerCard = playerDeck.peek();			//if cannot peek then it means deck is empty and winner is decided
					}catch(Exception e) {
						winnerDecided = true;
						break;
					}
					
					
					
					while(counter) {
						 if(playerCard.getRank() > 10) {						//if the player draws a face card
							 counter = true;
							 centerDeck.add(playerCard);
							 break;
						 }else {
							 int loop = cpuCard.getRank();
							 switch(loop) {									//set the amount of tries the player can have to trump face card
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
							 
							 for(int i = 0; i < loop; i++) {
								 System.out.println("loop: " + i);
								 System.out.println("center deck before: " + centerDeck.peek());

								 centerDeck.addFirst(playerCard);				//add the card to center deck
								 
								 System.out.println("center after before: " + centerDeck.peek());
								 playerDeck.remove();
								 try{										//then try to draw another card
									 playerCard = playerDeck.peek();		//if cannot peek then it means deck is empty and winner is decided
									}catch(Exception e) {
										winnerDecided = true;
										counter = false;
										break;
									}
								 System.out.println("card inside for loop Player card: " + playerCard);

								 if(playerCard.getRank()<10) {
									 centerDeck.addFirst(playerCard);
									 break;
								 }
								// playerDeck.remove();

							 }
							
							if(playerCard.getRank() < cpuCard.getRank()) {	//if player cannot trump cpu face card then center deck goes to cpu
								compDeck.addAll(centerDeck);
							}
						 }
						
					}
					
					
					if(playerCard.getRank() > 10 && !counter) {//if the card is a face card and there is no rank then set  counter to true;
						counter = true;
					}

					centerDeck.addFirst(playerCard);
					playerDeck.remove();

					
					System.out.println("\t\tAfterPlayer Deck Size: " + playerDeck.size());
					System.out.println("\t\tAfterPlayer Card: " + playerDeck.peek().getRank());
					System.out.println("\t\tAfter Counter: " + counter);

					playerTurn = false;
					cpuTurn = true;
					System.out.println("CPU's Turn: " + cpuTurn);

				}
				
				
				
				
				
				
				while(cpuTurn) {
					System.out.println("\t\tCPU's Turn");
					System.out.println("\t\tCPU Deck Size: " + compDeck.size());
					System.out.println("\t\tCPU Card: " + compDeck.peek().getRank());
					System.out.println("\t\tCounter: " + counter);

					try{
						cpuCard = compDeck.peek();			//if cannot peek then it means deck is empty and winner is decided
					}catch(Exception e) {
						winnerDecided = true;
						break;
					}
					
					while(counter) {
						 if(cpuCard.getRank() > 10) {						//if the cpu draws a face card
							 counter = true;
							 centerDeck.addFirst(cpuCard);
							 break;
						 }else {
							 int loop = playerCard.getRank();
							 switch(loop) {									//set the amount of tries the cpu can have to trump face card
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
							 
							 for(int i = 1; i <= loop; i++) {
								 System.out.println("loop: " + i);
								 System.out.println("center deck before: " + centerDeck.peek());
								 centerDeck.addFirst(cpuCard);				//add the card to center deck
								 System.out.println("center deck after: " + centerDeck.peek());
								 compDeck.remove();

								 try{										//then try to draw another card
									 cpuCard = compDeck.peek();		//if cannot peek then it means deck is empty and winner is decided
									}catch(Exception e) {
										winnerDecided = true;
										counter = false;
										break;
									}
								 System.out.println("inside for loop CPU card: " + cpuCard);
								 if(cpuCard.getRank()<10) {
									 centerDeck.addFirst(cpuCard);
									 break;
								 }
								 //compDeck.remove();
							 }
							
							if(cpuCard.getRank() < playerCard.getRank()) {	//if cpu cannot trump Player face card then center deck goes to player
								playerDeck.addAll(centerDeck);
							}
						 }
						
					}
					
					if(cpuCard.getRank() > 10 && !counter) {//if the card is a face card and there is no rank then set  counter to true;
						counter = true;
					}

					centerDeck.addFirst(cpuCard);
					compDeck.remove();
					
					
					
					System.out.println("\t\tAfter CPU Deck Size: " + compDeck.size());
					System.out.println("\t\tAfter CPU Card: " + compDeck.peek().getRank());
					System.out.println("\t\tAfter Counter: " + counter);

					cpuTurn = false;
					playerTurn = true;
					System.out.println("Player turn: "+ playerTurn);
				}
				
			
				

			
							
		}
		
		if(playerDeck.isEmpty()) {
			System.out.print("CPU is the Winner");
		}else{
			System.out.print("Player is the Winner");

		}
		
		
		
	}//end of main 	
	
	
	
}//end of driver































