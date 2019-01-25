/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagement;

import javax.swing.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author alex
 */
public class TaskManagement {

	/**
	 * @param args the command line arguments
	 */
	// public static void main(String[] args) {

	// 	boolean exit = false;

	// 	if (!login()) {
	// 		System.out.println("Invalid login credentials");
	// 		exit = true;
	// 	}

	// 	System.out.println("Access granted");

	// 	while(!exit) {
	// 		String selection = displayMenu();
	// 		switch(selection) {
	// 			case "1": createBoard(); break;
	// 			case "2": createUser(); break;
	// 			case "3": createCard(); break;
	// 			case "4": viewBoard(); break;
	// 			case "5": displayData("board"); break;
	// 			case "6": displayData("user"); break;
	// 			case "7": displayData("all"); break;
	// 			default: exit = true; break;
	// 		}
	// 	}

	// 	System.out.println("Task management system is exiting...");
	// }

	public static void createBoard(String boardName) {
		Board board = new Board(boardName);
		List<String> columns = Arrays.asList("To Do", "In Progress", "Review");
		board.setColumns(columns);

		FileHandler fileHandler = new FileHandler();
		fileHandler.writeToFile(board);
		System.out.println(boardName + " successfully created and saved.");
	}

	public static void createCard() {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter card title: ");
		String cardTitle = input.nextLine();

		FileHandler fileHandler = new FileHandler();

		displayData("user");
		System.out.print("Select user to assign: ");
		String assignedUserSelection = input.next();

		ArrayList<User> users = fileHandler.getUsers();
		User selectedUser = users.get(Integer.parseInt(assignedUserSelection) - 1);
		System.out.println(selectedUser.getName() + ", " + selectedUser.getRole() + " has been assigned to this card.");

		displayData("board");
		System.out.print("Select board to add this card to: ");
		String boardSelection = input.next();
		ArrayList<Board> boards = fileHandler.getBoards();
		Board selectedBoard = boards.get(Integer.parseInt(boardSelection) - 1);
		System.out.println(cardTitle + " has been added to " + selectedBoard.getTitle());

		Card card = new Card(cardTitle, selectedUser);
		selectedBoard.addCard(card);

		fileHandler.writeToFile(card);
		fileHandler.writeToFile(selectedBoard);

		System.out.println(selectedBoard.getTitle() + " successfully updated with new card.");
		System.out.println(cardTitle + " successfully created and saved.");
	}

	public static void createUser(String userName, String userRole) {

		FileHandler fileHandler = new FileHandler();
		if (userRole.equals("developer")) {
			TeamMember user = new TeamMember(userName, "Developer");
			fileHandler.writeToFile(user);
		} else {
			TeamLead user = new TeamLead(userName, "Team Lead");
			fileHandler.writeToFile(user);
		}
		
		System.out.println(userName + " successfully created and saved.");
	}

	public static void createTestValues() {
		TeamMember user = new TeamMember("Tom", "Developer");

		Card card = new Card();
		card.setTitle("Test card");
		card.assignCard(user);

		Board board = new Board();
		board.setTitle("Test board");
		List<String> columns = Arrays.asList("First column", "Second column", "Third column");
		board.setColumns(columns);
		board.addCard(card);
		

		FileHandler fileHandler = new FileHandler();
		fileHandler.writeToFile(user);
		fileHandler.writeToFile(board);
		fileHandler.writeToFile(card);
	}

	public static boolean login() {

		String username = JOptionPane.showInputDialog("Enter username: ");
		String password = JOptionPane.showInputDialog("Enter password: ");


		boolean validUser = username.equals("admin");
		boolean validPassword = password.equals("password");

		return validUser && validPassword;
	}

	public static void viewBoard() {
		FileHandler fileHandler = new FileHandler();
		ArrayList<Board> boards = fileHandler.getBoards();
		Scanner input = new Scanner(System.in);

		displayData("board");
		System.out.print("Select board to view: ");
		String boardSelection = input.next();
		Board selectedBoard = boards.get(Integer.parseInt(boardSelection) - 1);


		System.out.println("======== " + selectedBoard.getTitle() + ": " + selectedBoard.getCards().size() + " cards" + " =========");
		List<Card> cards = selectedBoard.getCards();
		cards.forEach((card) -> {
			System.out.println(card.getTitle());
			System.out.println(card.getAssignedUser().getName());
			System.out.println();
		});
	}

	public static void displayData(String dataType) {
		FileHandler fileHandler = new FileHandler();
		Scanner input = new Scanner(System.in);

		System.out.println("\n================================");
		if (dataType.equals("user") || dataType.equals("all")) {
			System.out.println("all users:");
			ArrayList<User> users = fileHandler.getUsers();
			for (int i = 0; i < users.size(); i++) {
				System.out.print((i+1) + ". ");
				System.out.print(" " + users.get(i).getName());
				System.out.print(" " + users.get(i).getRole());
				System.out.println(" " + users.get(i).getId());
			}
			System.out.println("");
		}

		if (dataType.equals("board") || dataType.equals("all")) {
			System.out.println("all boards:");
			ArrayList<Board> boards = fileHandler.getBoards();
			for (int i = 0; i < boards.size(); i++) {
				System.out.print((i+1) + ". ");
				System.out.print(" " + boards.get(i).getTitle());
				System.out.println(" " + boards.get(i).getId());
			}
			System.out.println("");
		}

		if (dataType.equals("all")) {
			System.out.println("all cards:");
			int index = 1;
			for (Card c: fileHandler.getCards()) {
				System.out.print(index + ". ");
				System.out.print(" " + c.getTitle());
				System.out.println(" " + c.getId());
				index += 1;
			}
			System.out.println("");
		}

		System.out.println("Press enter to continue...");
		try {
		System.in.read();
		} catch(Exception e) { }
	}

	public static String displayMenu() {
		System.out.println("\n\n\nMain Menu");
		System.out.println("=========================================");
		System.out.println("\t1. Create new board");
		System.out.println("\t2. Create new user");
		System.out.println("\t3. Create new card");
		System.out.println("\t4. View board");
		System.out.println("\t5. View all boards");
		System.out.println("\t6. View users");
		System.out.println("\t7. View all data");
		System.out.println("\t8. Exit");
		System.out.println("=========================================");

		System.out.print("Enter selection: ");
		Scanner input = new Scanner(System.in);
		return input.next();
	}
}
