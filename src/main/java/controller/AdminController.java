package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import launcher.LoginComponentFactory;
import model.User;
import model.validator.Notification;
import service.order.OrderService;
import service.user.AuthenticationService;
import view.AdminView;
import view.model.EmployeeDTO;
import view.model.builder.EmployeeDTOBuilder;

import static database.Constants.Roles.EMPLOYEE;

public class AdminController {

    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final OrderService orderService;

    public AdminController(AdminView adminView, AuthenticationService authenticationService, OrderService orderService) {
        this.adminView = adminView;
        this.authenticationService = authenticationService;
        this.orderService = orderService;

        adminView.addAddButtonListener(new AddButtonListener());
        adminView.addPdfButtonListener(new PdfButtonListener());
        adminView.addLogoutButtonListener(new LogoutButtonListener());
    }

    private class AddButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();

            Notification<Boolean> addedEmployeeNotification = authenticationService.register(username, password, EMPLOYEE);

            if(addedEmployeeNotification.hasErrors()) {
                adminView.addDisplayAlertMessage("Add Error", "Problem at Username or Password fields", addedEmployeeNotification.getFormattedErrors());
                System.out.println(addedEmployeeNotification.getFormattedErrors());
            }
            else {
                System.out.println("User added successfully");
                EmployeeDTO employeeDTO = new EmployeeDTOBuilder().setUsername(username).setPassword(password).build();

                adminView.addDisplayAlertMessage("Save Successful", "Employee Added", "Employee was successfully added to the database.");
                adminView.addEmployeeToObservableList(employeeDTO);
            }
        }
    }

    private class PdfButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
                if(!orderService.generatePdfRaport()){
                    adminView.addDisplayAlertMessage("Pdf Error", "Problem when generating the pdf", "The pdf could not be generated.");
                }
                else {
                    adminView.addDisplayAlertMessage("Pdf Successful", "Pdf generated", "The pdf has been generated successfully.");
                }

        }
    }

    private class LogoutButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Stage stage = LoginComponentFactory.getInstance(false, adminView.getStage()).getLoginView().getStage();
            Scene scene = LoginComponentFactory.getInstance(false, adminView.getStage()).getLoginView().getScene();
            LoginComponentFactory.getInstance(false, stage).getLoginView().getStage().setScene(scene);
        }
    }

}
