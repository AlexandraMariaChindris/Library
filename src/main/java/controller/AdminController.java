package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import service.user.AuthenticationService;
import view.AdminView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

public class AdminController {

    private final AdminView adminView;
    private final AuthenticationService authenticationService;

    public AdminController(AdminView adminView, AuthenticationService authenticationService) {
        this.adminView = adminView;
        this.authenticationService = authenticationService;

        adminView.addSaveButtonListener(new SaveButtonListener());
        adminView.addPdfButtonListener(new PdfButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {


        }
    }

    private class PdfButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {


        }
    }

}
