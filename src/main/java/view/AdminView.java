package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.model.EmployeeDTO;

import java.util.List;

public class AdminView {

    private TableView employeesTableView;
    private final ObservableList<EmployeeDTO> employeesObservableList;

    private TextField usernameTextField;
    private TextField passwordTextField;

    private Label usernameLabel;
    private Label passwordLabel;

    private Button addButton;
    private Button pdfButton;
    private Button logoutButton;

    private Text actionTarget;

    private Stage stage;
    private Scene scene;

    public AdminView(Stage primaryStage, List<EmployeeDTO> employees) {
        stage = primaryStage;
        stage.setTitle("Admin View");

        GridPane gridPane = new GridPane();
        initializerGridpage(gridPane);

        scene = new Scene(gridPane, 1000, 500);
        stage.setScene(scene);

        employeesObservableList = FXCollections.observableList(employees);

        initTableView(gridPane);
        initializeFields(gridPane);

        stage.show();

    }

    private void initializeFields(GridPane gridPane) {

        usernameLabel = new Label("Username");
        gridPane.add(usernameLabel, 1, 1);

        usernameTextField = new TextField();
        gridPane.add(usernameTextField, 2, 1);

        passwordLabel = new Label("Password");
        gridPane.add(passwordLabel, 3, 1);

        passwordTextField = new TextField();
        gridPane.add(passwordTextField, 4, 1);

        addButton = new Button("Add");
        gridPane.add(addButton, 5, 1);

        pdfButton = new Button("Generate pdf");
        gridPane.add(pdfButton, 6, 1);

        logoutButton = new Button("Logout");
        gridPane.add(logoutButton, 7, 1);

        actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);
        gridPane.add(actionTarget, 3, 2);

    }

    private void initTableView(GridPane gridPane) {

        employeesTableView = new TableView<EmployeeDTO>();
        employeesTableView.setPlaceholder(new Label("No employees"));

        TableColumn<EmployeeDTO, String> usernameColumn = new TableColumn<EmployeeDTO, String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        employeesTableView.getColumns().add(usernameColumn);
        employeesTableView.setItems(employeesObservableList);

        gridPane.add(employeesTableView, 0, 0, 5, 1);
    }

    private void initializerGridpage(GridPane gridPane) {

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    public void addAddButtonListener(EventHandler<ActionEvent> saveButtonListener) {
        addButton.setOnAction(saveButtonListener);
    }

    public void addPdfButtonListener(EventHandler<ActionEvent> pdfButtonListener) {
        pdfButton.setOnAction(pdfButtonListener);
    }

    public void addLogoutButtonListener(EventHandler<ActionEvent> logoutButtonListener){
        logoutButton.setOnAction(logoutButtonListener);
    }

    public void removeEmployeeFromObservableList(EmployeeDTO employeeDTO){
        this.employeesObservableList.remove(employeeDTO);
    }

    public void addEmployeeToObservableList(EmployeeDTO employeeDTO){
        this.employeesObservableList.add(employeeDTO);
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public TableView getEmployeesTableView() {
        return employeesTableView;
    }

    public void addDisplayAlertMessage(String title, String header, String contentInformation){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentInformation);

        alert.showAndWait();
    }

    public void setActionTargetText(String text){ this.actionTarget.setText(text);}

}
