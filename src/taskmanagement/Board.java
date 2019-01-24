/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author alex
 */
public class Board {
    private String id;
    private String title;
    private List<String> columns = new ArrayList<String>();
    private List<Card> cards = new ArrayList<Card>();
    private List<User> members = new ArrayList<User>();

    public Board() {
        this.id = UUID.randomUUID().toString();
    }

    public Board(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
    }

    public Board(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Board(String id, String title, List<Card> cards) {
        this.id = id;
        this.title = title;
        this.cards = cards;
    }

    public Board(String id, String title, List<String> columns, List<Card> cards, List<User> members) {
        this.id = id;
        this.title = title;
        this.columns = columns;
        this.cards = cards;
        this.members = members;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getColumns() {
        return this.columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<User> getMembers() {
        return this.members;
    }

    public List<Card> getCards() {
	    return this.cards;
    }

    public void addCard() {
        Card newCard = new Card();
        this.cards.add(newCard);
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void moveCard(Card card, int target) {
        card.assignColumn(target);
    }

    public void deleteCard(Card card) {
        this.cards.remove(card);
    }
}
