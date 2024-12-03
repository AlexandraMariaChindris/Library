package view;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import view.model.BookDTO;
import view.model.OrderDTO;

import java.util.*;

public class BookView {

    private TableView bookTableView;
    private TableView orderTableView;
    private final ObservableList<BookDTO> booksObservableList;
    private final ObservableList<OrderDTO> ordersObservableList;
    private TextField authorTextField;
    private TextField titleTextField;
    private TextField stockTextField;
    private TextField priceTextField;
    private TextField dateTextField;
    private Label authorLabel;
    private Label titleLabel;
    private Label stockLabel;
    private Label priceLabel;
    private Label dateLabel;
    private Button saveButton;
    private Button deleteButton;
    private Button saleButton;
    private Button logoutButton;
    private Stage stage;
    private Scene scene;


    public BookView(Stage primaryStage, List<BookDTO> books, List<OrderDTO> orders) {
        stage = primaryStage;
        stage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializerGridpage(gridPane);

        scene = new Scene(gridPane, 1000, 500);
        stage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);
        ordersObservableList = FXCollections.observableArrayList(orders);


        initTableView(gridPane); //tot ce adaugam in grid se adauga automat in scene si in primary stage
        initOrderTableView(gridPane);
        initSaveOptions(gridPane);

        stage.show();
    }

    private void initSaveOptions(GridPane gridPane) {
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1);

        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 3, 1);

        authorTextField = new TextField();
        gridPane.add(authorTextField, 4, 1);

        dateLabel = new Label("Date");
        gridPane.add(dateLabel, 5, 1);

        dateTextField = new TextField();
        gridPane.add(dateTextField, 6, 1);

        stockLabel = new Label("Stock");
        gridPane.add(stockLabel, 1, 2);

        stockTextField = new TextField();
        gridPane.add(stockTextField, 2, 2);

        priceLabel = new Label("Price");
        gridPane.add(priceLabel, 3, 2);

        priceTextField = new TextField();
        gridPane.add(priceTextField, 4, 2);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 7, 1);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 8, 1);

        saleButton = new Button("Sale");
        gridPane.add(saleButton, 9, 1);

        logoutButton = new Button("Logout");
        gridPane.add(logoutButton, 10, 1);

    }

    private void initTableView(GridPane gridPane) {
        bookTableView = new TableView<BookDTO>();
        bookTableView.setPlaceholder(new Label("No books to display"));
        //data binding
        TableColumn<BookDTO, String> titleColumn = new TableColumn<BookDTO, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookDTO, String> authorColumn = new TableColumn<BookDTO, String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookDTO, String> publishedDateColumn = new TableColumn<BookDTO, String>("Published Date");
        publishedDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));

        TableColumn<BookDTO, String> stockColumn = new TableColumn<BookDTO, String>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<BookDTO, String> priceColumn = new TableColumn<BookDTO, String>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        bookTableView.getColumns().addAll(titleColumn, authorColumn, publishedDateColumn, stockColumn, priceColumn);
        bookTableView.setItems(booksObservableList);

        gridPane.add(bookTableView, 0, 0, 5, 1);

    }

    private void initOrderTableView(GridPane gridPane) {
        orderTableView = new TableView<OrderDTO>();
        orderTableView.setPlaceholder(new Label("No orders to display"));


        TableColumn<OrderDTO, String> titleColumn = new TableColumn<OrderDTO, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<OrderDTO, String> authorColumn = new TableColumn<OrderDTO, String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<OrderDTO, String> quantityColumn = new TableColumn<OrderDTO, String>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<OrderDTO, String> priceColumn = new TableColumn<OrderDTO, String>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        orderTableView.getColumns().addAll( titleColumn, authorColumn, quantityColumn, priceColumn);
        orderTableView.setItems(ordersObservableList);


        gridPane.add(orderTableView, 5, 0, 5, 1);
    }

    private void initializerGridpage(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener){
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addSaleButtonListener(EventHandler<ActionEvent> saleButtonListener){
        saleButton.setOnAction(saleButtonListener);
    }

    public void addLogoutButtonListener(EventHandler<ActionEvent> logoutButtonListener){
        logoutButton.setOnAction(logoutButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String contentInformation){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentInformation);

        alert.showAndWait();
    }

    public Stage getStage() {
        return stage;
    }

    public String getAuthor(){
        return authorTextField.getText();
    }

    public String getTitle(){
        return titleTextField.getText();
    }

    public String getStock(){
        return stockTextField.getText();
    }

    public String getPrice(){
        return priceTextField.getText();
    }

    public String getDate(){
        return dateTextField.getText();
    }

    public void addBookToObservableList(BookDTO bookDTO){
        this.booksObservableList.add(bookDTO);
    }

    public void removeBookFromObservableList(BookDTO bookDTO){
        this.booksObservableList.remove(bookDTO);
    }

    public void updateBookObservableList(Long id){
        for(BookDTO bookDTO : booksObservableList){
            if(Objects.equals(bookDTO.getId(), id)){
                bookDTO.setStock(bookDTO.getStock() - 1);
            }
        }

    }

    public void addOrderToObservableList(OrderDTO orderDTO){
        this.ordersObservableList.add(orderDTO);
    }

    public TableView getBookTableView(){
        return bookTableView;
    }

    public Scene getScene() {
        return scene;
    }


}
