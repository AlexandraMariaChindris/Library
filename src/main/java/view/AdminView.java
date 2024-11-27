package view;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.model.BookDTO;

public class AdminView {

    private TableView employeesTableView;
    private final ObservableList<EmployeeDTO> booksObservableList;

    private TextField usernameTextField;
    private TextField passwordTextField;

    private Label usernameLabel;
    private Label passwordLabel;

    private Button saveButton;
    private Button pdfButton;

    public AdminView(Stage primaryStage, List<EmployeeDTO>) {

    }
}
