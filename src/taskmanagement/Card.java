/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagement;

import java.util.UUID;

/**
 *
 * @author alex
 */
public class Card {
    private String id;
    private String title;
    private String description;
    private User assignedUser;
    private int boardId;
    private int columnIndex;

    public Card() {
	    this.id = UUID.randomUUID().toString();
    }

    public Card(String title, User assignedUser) {
	    this.id = UUID.randomUUID().toString();
        this.title = title;
        this.assignedUser = assignedUser;
    }

    public Card(String id, String title) {
	    this.id = id;
        this.title = title;
    }

    public Card(String id, String title, User assignedUser) {
	    this.id = id;
        this.title = title;
        this.assignedUser = assignedUser;
    }

    public String getId() {
	return this.id;
    }

    public User getAssignedUser() {
	    return this.assignedUser;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public int getBoardId() {
        return this.boardId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void assignCard(TeamMember member) {
	    this.assignedUser = member;
    }

    public void assignColumn(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public void assignBoard(int boardId) {
        this.boardId = boardId;
    }
}
