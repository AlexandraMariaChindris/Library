package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import launcher.AdminComponentFactory;
import launcher.EmployeeComponentFactory;
import model.Role;
import model.User;
import model.validator.Notification;
import service.user.AuthenticationService;
import view.LoginView;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.CUSTOMER;


public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;


    public LoginController(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;


        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();


            Notification<User> LoginNotification = authenticationService.login(username, password);

            if (LoginNotification.hasErrors()){
                loginView.setActionTargetText(LoginNotification.getFormattedErrors());
            }else{
                loginView.setActionTargetText("Login Successfull!");
                User user = LoginNotification.getResult();

                if(isAdministrator(user)){
                    Stage stage = AdminComponentFactory.getInstance(false, loginView.getStage()).getAdminView().getStage();;
                    Scene scene = AdminComponentFactory.getInstance(false, loginView.getStage()).getAdminView().getScene();

                    AdminComponentFactory.getInstance(false, stage).getAdminView().getStage().setScene(scene);
                }else{
                    Stage stage = EmployeeComponentFactory.getInstance(false, loginView.getStage()).getBookView().getStage();
                    Scene scene = EmployeeComponentFactory.getInstance(false, loginView.getStage()).getBookView().getScene();

                    EmployeeComponentFactory.getInstance(false, stage).getBookView().getStage().setScene(scene);
                    EmployeeComponentFactory.getInstance(false, loginView.getStage()).getBookController().setUser_id_logged(user.getId());
                }
            }
        }
    }

    private boolean isAdministrator(User user){
        for(Role role : user.getRoles()){
            if(role.getRole().equals(ADMINISTRATOR))
                return true;
        }
        return false;
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password, CUSTOMER);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            }else
                loginView.setActionTargetText("Register Successfull!");
        }
    }

}
