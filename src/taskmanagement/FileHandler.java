/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author alex
 */
public class FileHandler {

    String usersFilename = "users.json";
    String boardsFilename = "boards.json";
    String cardsFilename = "cards.json";

    JSONParser parser = new JSONParser();

    ArrayList<JSONObject> users = new JSONArray();
    ArrayList<JSONObject> boards = new JSONArray();
    ArrayList<JSONObject> cards = new JSONArray();

    public FileHandler() {
        this.loadUsers();
        this.loadBoards();
        this.loadCards();
    }

    public void loadUsers() {
	    try {
		this.users = this.readJSON(new File(this.usersFilename),"UTF-8");
	    } catch (FileNotFoundException e) {
			try (FileWriter file = new FileWriter(this.usersFilename, true)) {
			    file.append("");
			    System.out.println("Created users file");
			} catch(Exception f) {
			    System.out.println("Error: create users file");
			    e.printStackTrace();
			}
	    } catch (ParseException e) {
//			e.printStackTrace();
	    }
    }

    public void loadBoards() {
        try {
		this.boards = this.readJSON(new File(this.boardsFilename),"UTF-8");
        } catch(FileNotFoundException e) {
			try (FileWriter file = new FileWriter(this.boardsFilename, true)) {
			    file.append("");
			    System.out.println("Created boards file");
			} catch(Exception f) {
			    System.out.println("Error: create boards file");
			    e.printStackTrace();
			}
        } catch(Exception e) {
//            e.printStackTrace();
        }
    }

    public void loadCards() {
        try {
		this.cards = this.readJSON(new File(this.cardsFilename),"UTF-8");
        } catch(FileNotFoundException e) {
			try (FileWriter file = new FileWriter(this.cardsFilename, true)) {
			    file.append("");
			    System.out.println("Created cards file");
			} catch(Exception f) {
			    System.out.println("Error: create cards file");
			    e.printStackTrace();
			}
        } catch(Exception e) {
//            e.printStackTrace();
        }
    }

    public ArrayList<JSONObject> readJSON(File file, String encoding)
        throws FileNotFoundException, ParseException {
    	Scanner scanner = new Scanner(file, encoding);
		ArrayList<JSONObject> json = new ArrayList<JSONObject>();

		//Reading and Parsing Strings to Json
	    while(scanner.hasNext()){
			JSONObject obj= (JSONObject) new JSONParser().parse(scanner.nextLine());
			json.add(obj);
	    }

	    return json; 
    }

    public void writeToFile(User user) {
	    JSONObject userObj = new JSONObject();
	    userObj.put("id", user.getId());
		userObj.put("name", user.getName());
		userObj.put("role", user.getRole());

        try (FileWriter file = new FileWriter(this.usersFilename, true)) {
            file.append(userObj.toJSONString());
            file.append('\n');
		    System.out.println("Successfully created new user: " + userObj);
        } catch(Exception e) {
            System.out.println("Error: Could not save user: " + userObj);
            e.printStackTrace();
        }
    }

    public void writeToFile(Board board) {
        JSONObject boardObj = new JSONObject();
	    boardObj.put("id", board.getId());
        boardObj.put("title", board.getTitle());

//        JSONArray columnsList = new JSONArray();
//	Arrays.asList(board.getColumns()).forEach((column) -> {
//		columnsList.add(column);
//	    });
//        boardObj.put("columns", columnsList);

        JSONArray cardsList = new JSONArray();
		board.getCards().forEach((card) -> {
			JSONObject cardObj = new JSONObject();
			cardObj.put("id", card.getId());
			cardObj.put("title", card.getTitle());

			JSONObject assignedUserObj = new JSONObject();
			assignedUserObj.put("id", card.getAssignedUser().getId());
			assignedUserObj.put("name", card.getAssignedUser().getName());
			cardObj.put("assignedUser", assignedUserObj);
			cardsList.add(cardObj);
	    });
        boardObj.put("cards", cardsList);

		boolean isUpdate = false;
		ArrayList<Board> boards = getBoards();
		for (int i = 0; i < boards.size(); i++) {
			isUpdate = boards.get(i).getId().equals(board.getId());
			if (isUpdate) {
				break;
			}
		};


		if (isUpdate) {
			try (FileWriter file = new FileWriter(this.boardsFilename, false)) {
				file.append(boardObj.toJSONString());
				file.append('\n');

				boards.forEach((b) -> {
					JSONObject bObj = new JSONObject();
					bObj.put("id", b.getId());
					bObj.put("title", b.getTitle());
					JSONArray cList = new JSONArray();
					b.getCards().forEach((c) -> {
						JSONObject cObj = new JSONObject();
						cObj.put("id", c.getId());
						cObj.put("title", c.getTitle());

						JSONObject assObj = new JSONObject();
						assObj.put("id", c.getAssignedUser().getId());
						assObj.put("name", c.getAssignedUser().getName());
						cObj.put("assignedUser", assObj);
						cList.add(cObj);
					});
					bObj.put("cards", cList);
					if (!board.getId().equals(b.getId())) {
						try {
							file.append(bObj.toJSONString());
							file.append('\n');
						} catch(Exception e) {}
					}
				});

				System.out.println("Reset boards file");
			} catch(Exception e) {
				System.out.println("Error: create boards file");
				e.printStackTrace();
			}
		} else {
			try (FileWriter file = new FileWriter(this.boardsFilename, true)) {
				file.append(boardObj.toJSONString());
				file.append('\n');
					System.out.println("Successfully created new board: " + boardObj);
			} catch(Exception e) {
				System.out.println("Error: Could not save board: " + boardObj);
				e.printStackTrace();
			}
		}
    }

    public void writeToFile(Card card) {
		JSONObject cardObj = new JSONObject();
	    cardObj.put("id", card.getId());
		cardObj.put("title", card.getTitle());
		cardObj.put("description", card.getDescription());
		cardObj.put("assignedUser", card.getAssignedUser().getId());
        try (FileWriter file = new FileWriter(this.cardsFilename, true)) {
            file.append(cardObj.toJSONString());
            file.append('\n');
		    System.out.println("Successfully created new card: " + cardObj);
        } catch(Exception e) {
            System.out.println("Error: Could not save card: " + cardObj);
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
	    ArrayList<User> users = new ArrayList();
	    for (Object user : this.users) {
		    JSONObject userObj = (JSONObject)user;
		    users.add(
		    new User(
			userObj.get("id").toString(),
			userObj.get("name").toString(),
			userObj.get("role").toString()
		    )
		    );
	    }

	    return users;
    }

    public ArrayList<Board> getBoards() {
	    ArrayList<Board> boards = new ArrayList();
	    for (Object board: this.boards) {
			JSONObject boardObj = (JSONObject)board;
			ArrayList<Card> cards = new ArrayList();
			JSONArray cardsObj = (JSONArray)boardObj.get("cards");
			cardsObj.forEach((card) -> {
				JSONObject cardObj = (JSONObject)card;
				JSONObject userObj = (JSONObject)cardObj.get("assignedUser");
				User assignedUser = new User(
					userObj.get("id").toString(),
					userObj.get("name").toString(),
					true
					);
				cards.add(new Card(
					cardObj.get("id").toString(),
					cardObj.get("title").toString(),
					assignedUser
					));
			});
		    boards.add(new Board(
				boardObj.get("id").toString(),
				boardObj.get("title").toString(),
				cards
		    ));
	    }

	    return boards;
    }

    public ArrayList<Card> getCards() {
	    ArrayList<Card> cards = new ArrayList();
	    for (Object card : this.cards) {
			JSONObject cardObj = (JSONObject)card;
			User user = getUserById(cardObj.get("assignedUser").toString());
			Card c;
			if (user != null) {
				TeamMember t = new TeamMember(user.getId(), user.getName(), user.getRole());
				c = new Card(cardObj.get("id").toString(), cardObj.get("title").toString(), t);
			} else {
				c = new Card(cardObj.get("id").toString(), cardObj.get("title").toString());
			}
			if (cardObj.get("description") != null) {
				c.setDescription(cardObj.get("description").toString());
			}
		    cards.add(c);
	    }

	    return cards;
	}
	
	public User getUserById(String userId) {
		ArrayList<User> users = getUsers();
		for(User user: users) {
			if(user.getId().equals(userId)) {
				return user;
			}
		}
		return null;
	}
}
