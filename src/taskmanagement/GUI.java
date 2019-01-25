package taskmanagement;

import javafx.event.ActionEvent;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class GUI extends Application {

    Stage window;
    TextField titleInput;
    ComboBox userSelection;
    int todoIndex = 1;
    int inProgressIndex = 1;
    int reviewIndex = 1;
    FileHandler fileHandler;
    GridPane gridPane;
    ObservableList<Card> cards;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fileHandler = new FileHandler();
        window = primaryStage;
        window.setTitle("Task Management Project - Java 2");

        buildGrid();

        // create a menu 
        Menu m = new Menu("File"); 

        // create menuitems 
        MenuItem m1 = new MenuItem("Create new board..."); 
        MenuItem m2 = new MenuItem("Add new user..."); 
        MenuItem m3 = new MenuItem("Quit"); 

        EventHandler<ActionEvent> createBoard = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Text boardTitle = BoardForm.display();
                TaskManagement.createBoard(boardTitle.getText());
                System.out.println(boardTitle.getText());
            }
        };
        EventHandler<ActionEvent> addUser = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                User newUser = UserForm.display();
                TaskManagement.createUser(newUser.getName(), newUser.getRole());
            }
        };
        EventHandler<ActionEvent> quit = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Platform.exit();
            }
        };

        m1.setOnAction(createBoard);  
        m2.setOnAction(addUser);  
        m3.setOnAction(quit);  

        // add menu items to menu 
        m.getItems().add(m1); 
        m.getItems().add(m2); 
        m.getItems().add(m3); 

        // create a menubar 
        MenuBar mb = new MenuBar(); 

        // add menu to menubar 
        mb.getMenus().add(m); 

        fileHandler.loadUsers();
        ArrayList<User> users = fileHandler.getUsers();


        // Card Title input
        titleInput = new TextField();
        titleInput.setPromptText("Title");
        titleInput.setMinWidth(100);

        // Add Card Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked());


        Label projectName = new Label("Project Title");

        HBox header = new HBox();
        header.getChildren().addAll(projectName);
        header.setAlignment(Pos.CENTER);

        userSelection = new ComboBox();
        userSelection.getItems().addAll(users);


        HBox control = new HBox();
        control.setPadding(new Insets(10,10,10,10));
        control.setSpacing(10);
        control.getChildren().addAll(titleInput, userSelection, addButton);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        scrollPane.setPrefHeight(500);


        VBox root = new VBox();
        BorderPane content = new BorderPane();
        content.setPadding(new Insets(20));
        content.setTop(header);
        content.setCenter(scrollPane);

        // Gap
        Region spacer = new Region();
        root.setVgrow(spacer, Priority.ALWAYS);

        root.getChildren().addAll(mb, content, spacer, control);

        Scene scene = new Scene(root, 800, 600);
        window.setScene(scene);
        window.show();
    }

    public void buildGrid() {

        gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        VBox column1 = new VBox();
        VBox column2 = new VBox();
        VBox column3 = new VBox();

        column1.setSpacing(5);
        column2.setSpacing(5);
        column3.setSpacing(5);
        column1.setPrefWidth(240);
        column2.setPrefWidth(240);
        column3.setPrefWidth(240);

        // Set column labels
        Label toDoLabel = new Label("To Do");
        Label inProgressLabel = new Label("In Progress");
        Label forReviewLabel = new Label("For Review");

        toDoLabel.setStyle("-fx-label-padding: 10px; -fx-wrap-text: true;");
        inProgressLabel.setStyle("-fx-label-padding: 10px; -fx-wrap-text: true;");
        forReviewLabel.setStyle("-fx-label-padding: 10px; -fx-wrap-text: true;");

        column1.getChildren().add(toDoLabel);
        column2.getChildren().add(inProgressLabel);
        column3.getChildren().add(forReviewLabel);
        gridPane.add(column1, 0, 0);
        gridPane.add(column2, 1, 0);
        gridPane.add(column3, 2, 0);

        todoIndex = 1;
        inProgressIndex = 1;
        reviewIndex = 1;
        cards = getCards();
        loadCards();
    }

    public void refreshGrid() {
        gridPane.getChildren().clear();
        gridPane.setPadding(new Insets(5));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        VBox column1 = new VBox();
        VBox column2 = new VBox();
        VBox column3 = new VBox();

        column1.setSpacing(5);
        column2.setSpacing(5);
        column3.setSpacing(5);
        column1.setPrefWidth(240);
        column2.setPrefWidth(240);
        column3.setPrefWidth(240);

        // Set column labels
        Label toDoLabel = new Label("To Do");
        Label inProgressLabel = new Label("In Progress");
        Label forReviewLabel = new Label("For Review");

        toDoLabel.setStyle("-fx-label-padding: 10px; -fx-wrap-text: true;");
        inProgressLabel.setStyle("-fx-label-padding: 10px; -fx-wrap-text: true;");
        forReviewLabel.setStyle("-fx-label-padding: 10px; -fx-wrap-text: true;");

        column1.getChildren().add(toDoLabel);
        column2.getChildren().add(inProgressLabel);
        column3.getChildren().add(forReviewLabel);
        gridPane.add(column1, 0, 0);
        gridPane.add(column2, 1, 0);
        gridPane.add(column3, 2, 0);

        todoIndex = 1;
        inProgressIndex = 1;
        reviewIndex = 1;
        loadCards();
    }

    //Add button clicked
    public void addButtonClicked(){

        Card card = TaskManagement.createCard(titleInput.getText(),
            (User)userSelection.getValue(),
            new Board());
        
        System.out.println("AddButtonClicked: added card");

        cards.add(card);
        gridPane.add(prepareCard(card), 0, cards.size());

        titleInput.clear();
    }

    public DropShadow getDropShadow() {

        // Drop shadow for cards
        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.GAUSSIAN);
        ds.setColor(Color.DARKGRAY);
        ds.setHeight(1);
        ds.setWidth(1);
        ds.setOffsetX(1);
        ds.setOffsetY(1);
        ds.setRadius(5);
        ds.setSpread(0.01);

        return ds;
    }

    public Button prepareCard(Card card) {

        EventHandler<javafx.scene.input.MouseEvent> eventHandler = 
                    new EventHandler<javafx.scene.input.MouseEvent>() { 
            @Override 
            public void handle(javafx.scene.input.MouseEvent e) { 
                Card c = CardDetails.display(card);
                if (c != null) {
                    for (int i = 0; i < cards.size(); i++) {
                        if (cards.get(i).getId().equals(c.getId())) {
                            cards.set(i, c);
                        }
                    }
                    fileHandler.writeToFile(c);
                    refreshGrid();
                }
            } 
        };  

        Button cardContainer = new Button(card.getTitle());
        cardContainer.setStyle("-fx-background-color: white; -fx-pref-width: 200px;");
        cardContainer.setAlignment(Pos.BASELINE_LEFT);
        cardContainer.setEffect(getDropShadow());
        cardContainer.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandler);

        return cardContainer;
    }

    //Get all of the products
    public ObservableList<Card> getCards() {
        return FXCollections.observableArrayList(fileHandler.getCards());
    }

    public void loadCards() {
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            int column = card.getColumnIndex();
            gridPane.add(prepareCard(card), column, getColumnIndex(column));
            incrementColumn(column);
        }
    }

    /**
     * 0 todoIndex
     * 1 inProgressIndex
     * 2 reviewIndex
     */
    public int getColumnIndex(int column) {
        int index = 1;

        switch(column) {
            case 0: index = todoIndex; break;
            case 1: index = inProgressIndex; break;
            case 2: index = reviewIndex; break;
        }
        return index;
    }

    public void incrementColumn(int column) {
        switch(column) {
            case 0: todoIndex++; break;
            case 1: inProgressIndex++; break;
            case 2: reviewIndex++; break;
        }
    }


}