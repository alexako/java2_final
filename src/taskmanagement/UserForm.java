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
public class UserForm {
    
    public static User display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Text sceneTitle = new Text("Create new User");
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

        Label userName = new Label("User name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label userRole = new Label("User role:");
        grid.add(userRole, 0, 2);

        TextField userRoleTextField = new TextField();
        grid.add(userRoleTextField, 1, 2);

        Button btn = new Button("Create User");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 3);

        Text userNameString = new Text();
        Text userRoleString = new Text();
        User newUser = new User();
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                userNameString.setText(userTextField.getText());
                userRoleString.setText(userRoleTextField.getText());
                newUser.setName(userNameString.getText());
                newUser.setRole(userRoleString.getText());
                window.close();
            }
        });

        window.setScene(new Scene(grid, 300, 175));
        window.showAndWait();
        return newUser;
    }
}
