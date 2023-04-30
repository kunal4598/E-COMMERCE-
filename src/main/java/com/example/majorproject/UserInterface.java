package com.example.majorproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane loginPage;
    HBox headerBar ;
    HBox footerBar;
    Customer loggedinCustomer;
    ProductList productList = new ProductList();
    VBox productPage;
    VBox body;


    Button signInButton;
    Button placeOrderButton = new Button("Place Order");



    Label welcomeLabel;
    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();


    BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setTop(headerBar);
        root.setPrefSize(800, 600);
//        root.getChildren().add(loginPage);
//        root.setCenter(loginPage);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);

        productPage = productList.getAllProducts();
        body.getChildren().add(productPage);
//        root.setCenter(productPage);

        root.setBottom(footerBar);
        return root;

    }

    public UserInterface(){
        createLogInPage();
        createHeaderBar();
        createfooterBar();
    }

    private void createLogInPage(){
        Text userNameText = new Text("UserName");
        Text passwordText = new Text("Password");
        TextField userName = new TextField();
        Button loginButton = new Button("Log In");
        Label messageLabel = new Label("Hi");
        userName.setPromptText("enter the username please");
        PasswordField password = new PasswordField();
        password.setPromptText("enter your password please ");

        loginPage = new GridPane();

        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(userNameText, 0,0);
        loginPage.add(userName, 1, 0);
        loginPage.add(passwordText, 0,1);
        loginPage.add(password,1,1);
        loginPage.add(messageLabel, 0,2);
        loginPage.add(loginButton, 1,2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String pass = password.getText();
                Login login = new Login();
                loggedinCustomer = login.customerLogin(name, pass);
                if(loggedinCustomer != null){

                    messageLabel.setText("Welcom " + loggedinCustomer.getName());
                    welcomeLabel.setText("Welcome " + loggedinCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }
                else{
                    messageLabel.setText("Login Failed! please enter the correct username and password");
                }
            }
        });

    }

    private void createHeaderBar(){
        Button homeButton = new Button();
        Image image = new Image("file:C:\\Users\\shadan khan\\IdeaProjects\\majorProject\\src\\img.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(80);
        homeButton.setGraphic(imageView);


        TextField searchBar = new TextField();
        searchBar.setPromptText("search here");
        searchBar.setPrefWidth(320);
        welcomeLabel = new Label();

        Button searchButton = new Button("Search");
        signInButton = new Button("Sign In");
        Button cartButton = new Button("Cart");
        Button orderButton = new Button("Orders");


        headerBar = new HBox();
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton,searchBar,searchButton,signInButton,cartButton,orderButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear(); // remove everything from page
                body.getChildren().add(loginPage); // add the login page
                headerBar.getChildren().remove(signInButton);
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getIProductsInCart(itemsInCart);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false);
            }
        });


        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // needs a list of product and customer

                if(itemsInCart == null){
                    // no product is selected
                    showDialogue("Please add some items inside the card");
                    return;
                }
                if(loggedinCustomer == null){
                    showDialogue("Please logged first to place order");
                    return;
                }
                int count = Order.placeMultipleOrder(loggedinCustomer, itemsInCart);
                if(count != 0){
                    showDialogue("Order for "+count +" product placed succesfully");
                }
                else{
                    showDialogue("Order failed!! ");
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if(loggedinCustomer == null && headerBar.getChildren().indexOf(signInButton)== -1){
                     headerBar.getChildren().add(signInButton);
                }
            }
        });
    }

    private void createfooterBar(){
        Button buyNowButton = new Button("Buy Now");
        Button addToCartButton = new Button("Add to Cart");

        footerBar = new HBox();
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton,addToCartButton);


        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    // no product is selected
                    showDialogue("Please select atleast one items before place the order");
                    return;
                }
                if(loggedinCustomer == null){
                    showDialogue("Please logged first to place order");
                    return;
                }
                boolean status = Order.placeOrder(loggedinCustomer, product);
                if(status == true){
                    showDialogue("Order placed succesfully");
                }
                else{
                    showDialogue("Order failed!! ");
                }

            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    // no product is selected
                    showDialogue("Please select atleast one items before add to the cart");
                    return;
                }
                itemsInCart.add(product);
                showDialogue("Selected Items should be added successfully inside the cart");
            }
        });
    }

    private void showDialogue(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();

    }
}
