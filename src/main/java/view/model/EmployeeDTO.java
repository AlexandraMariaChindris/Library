package view.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeDTO {

    private StringProperty username;
    private StringProperty password;

    public void setUsername(String username) {
        usernameProperty().set(username);
    }

    public String getUsername() {
        return usernameProperty().get();
    }

    public StringProperty usernameProperty() {
        if(username == null) {
            username = new SimpleStringProperty(this, "username");
        }
        return username;
    }

    public void setPassword(String password) {
        passwordProperty().set(password);
    }

    public String getPassword() {
        return passwordProperty().get();
    }

    public StringProperty passwordProperty() {
        if(password == null) {
            password = new SimpleStringProperty(this, "password");
        }
        return password;
    }
}
