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
import javafx.scene.control.TextField;
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
    
    public static Card display(Card card) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Text sceneTitle = new Text(card.getTitle());
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        window.setTitle(sceneTitle.getText());
        window.setWidth(300);
        window.setHeight(175);
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

        System.out.println(card.getAssignedUser());

        Label cardTitle = new Label("Card Title:");
        grid.add(cardTitle, 0, 1);

        TextField cardTextField = new TextField();
        grid.add(cardTextField, 1, 1);

        Label cardDesc = new Label("Card Title:");
        grid.add(cardDesc, 0, 2);

        TextArea cardDescField = new TextArea();
        grid.add(cardDescField, 1, 2);

        Button saveBtn = new Button("Save");
        Button closeBtn = new Button("Close");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(closeBtn, saveBtn);
        grid.add(hbBtn, 1, 3);

        boolean update = false;
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                window.close();
            }
        });
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                window.close();
            }
        });

        window.setScene(new Scene(grid, 400, 175));
        window.showAndWait();

        return update ? card : null;
    }
}
