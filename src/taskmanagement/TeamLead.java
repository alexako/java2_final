/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagement;

/**
 *
 * @author alex
 */
public class TeamLead extends User {

	public TeamLead() {
		super();
	}
	
	public TeamLead(String name, String role) {
		super(name, role);
	}

	public void assignCard(Card card, TeamMember member) {
		card.assignCard(member);
	}
}
