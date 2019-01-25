/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author alex
 */
public class CardDetails {
    
    static boolean update = false;

    public static Card display(Card card) {
        System.out.println(card.getTitle());
        System.out.println(card.getDescription());
        System.out.println(card.getAssignedUser().getName());
        System.out.println(card.getColumnIndex());

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Text sceneTitle = new Text(card.getTitle());
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        window.setTitle(sceneTitle.getText());
        window.setWidth(500);
        window.setHeight(375);
        window.initStyle(StageStyle.UTILITY);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        if (card.getAssignedUser() != null) {
            Label assignedUser = new Label(card.getAssignedUser().getName());
            Label assignedUserRole = new Label(card.getAssignedUser().getRole());

            grid.add(assignedUser, 0, 0);
            grid.add(assignedUserRole, 1, 0);
        }

        Label cardTitle = new Label("Card Title:");
        grid.add(cardTitle, 0, 1);

        TextField cardTextField = new TextField(card.getTitle());
        grid.add(cardTextField, 1, 1);

        Label cardDesc = new Label("Card Title:");
        grid.add(cardDesc, 0, 2);

        TextArea cardDescField = new TextArea(card.getDescription());
        grid.add(cardDescField, 1, 2);

        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("To Do");
        rb1.setToggleGroup(group);
        rb1.setSelected(card.getColumnIndex() == 0);
        RadioButton rb2 = new RadioButton("In Progress");
        rb2.setToggleGroup(group);
        rb2.setSelected(card.getColumnIndex() == 1);
        RadioButton rb3 = new RadioButton("For Review");
        rb3.setToggleGroup(group);
        rb3.setSelected(card.getColumnIndex() == 2);
        HBox columnSel = new HBox(10);
        columnSel.setAlignment(Pos.BOTTOM_RIGHT);
        columnSel.getChildren().addAll(rb1, rb2, rb3);
        grid.add(columnSel, 1, 3);

        Button saveBtn = new Button("Save");
        Button closeBtn = new Button("Close");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(closeBtn, saveBtn);
        grid.add(hbBtn, 1, 4);

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                card.setTitle(cardTextField.getText());
                card.setDescription(cardDescField.getText());
                card.assignColumn(getSelectedColumn(group));
                update = true;
                window.close();
            }
        });
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                window.close();
            }
        });

        window.setScene(new Scene(grid, 600, 175));
        window.showAndWait();

        return update ? card : null;
    }

    public static int getSelectedColumn(ToggleGroup group) {
        Toggle g = group.getSelectedToggle();
        if (g.toString().contains("To Do")) {
            return 0;
        }
        if (g.toString().contains("In Progress")) {
            return 1;
        }
        if (g.toString().contains("For Review")) {
            return 2;
        }
        return 0;
    }
}
