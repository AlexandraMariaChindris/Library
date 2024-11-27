package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import launcher.EmployeeComponentFactory;
import model.User;
import model.validator.Notification;
import service.user.AuthenticationService;
import view.LoginView;


public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;

    private Long user_id_logged;


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
            Long user_id_logged = null;

            Notification<User> LoginNotification = authenticationService.login(username, password);
            //boolean loginSuccessfull = false;
            if (LoginNotification.hasErrors()){
                loginView.setActionTargetText(LoginNotification.getFormattedErrors());
            }else{
                //loginView.setActionTargetText("LogIn Successfull!");
                user_id_logged = LoginNotification.getResult().getId();

                Stage stage = EmployeeComponentFactory.getInstance(false, loginView.getStage()).getBookView().getStage();
                Scene scene = EmployeeComponentFactory.getInstance(false, loginView.getStage()).getBookView().getScene();
                EmployeeComponentFactory.getInstance(false, stage).getBookView().getStage().setScene(scene);

                EmployeeComponentFactory.getInstance(false, loginView.getStage()).getBookController().setUser_id_logged(user_id_logged);
                //loginSuccessfull = true;
            }

//            if(loginSuccessfull){
//
//                ComponentFactory.getInstance(false, loginView.getStage()).getBookController().setUser_id_logged(user_id_logged);
//                //ComponentFactory.getInstance(false, loginView.getStage());
//            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            }else
                loginView.setActionTargetText("Register Successfull!");
        }
    }

    public Long getUser_id_logged() {
        return user_id_logged;
    }

    public void setUser_id_logged(Long user_id_logged) {
        this.user_id_logged = user_id_logged;
    }
}
