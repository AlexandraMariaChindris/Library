package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import launcher.LoginComponentFactory;
import mapper.BookMapper;
import mapper.OrderMapper;
import service.book.BookService;
import service.order.OrderService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class BookController {

    private final BookView bookView;
    private final BookService bookService;
    private final OrderService orderService;
    private Long user_id_logged;


    public BookController(BookView bookView, BookService bookService, OrderService orderService) {
        this.bookView = bookView;
        this.bookService = bookService;
        this.orderService = orderService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSaleButtonListener(new SaleButtonListener());
        this.bookView.addLogoutButtonListener(new LogoutButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String stock = bookView.getStock();
            String price = bookView.getPrice();
            String date  = bookView.getDate();

            if(title.isEmpty() || author.isEmpty() || stock.isEmpty() || price.isEmpty()) {
                bookView.addDisplayAlertMessage("Save Error", "Problem at Author, Title, Stock or Price fields", "Can not have an empty field (except the date field).");
            }
            else {
                BookDTO bookDTO = null;
                boolean dateEmptyOrValidDate = false;
                if(date.isEmpty()) {
                    bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).setStock(Integer.valueOf(stock)).setPrice(Float.valueOf(price)).build();
                    dateEmptyOrValidDate = true;
                } else
                    if(!isValidDate(date))
                        bookView.addDisplayAlertMessage("Save Error", "Problem at date field", "The Date field must be a valid date: yyyy-MM-dd");
                    else{
                        bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).setStock(Integer.valueOf(stock)).setPrice(Float.valueOf(price)).setPublishedDate(parseDate(date)).build();
                        dateEmptyOrValidDate = true;
                    }

                if(dateEmptyOrValidDate){
                    boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));
                    if(savedBook) {
                        bookView.addDisplayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                        bookView.addBookToObservableList(bookDTO);
                    }
                    else {
                        bookView.addDisplayAlertMessage("Save Error", "Problem at adding the Book ", "There was a problem at adding the book to the database. Please try again!");

                    }
                }
            }
        }
    }

    private boolean isValidDate(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            LocalDate.parse(date, dateFormatter);
            return true;
        }
        catch(DateTimeParseException e) {
            return false;
        }
    }

    private LocalDate parseDate(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dateFormatter);
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookDTO != null) {
                boolean deletionSuccessful = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if(deletionSuccessful) {
                    bookView.addDisplayAlertMessage("Delete Successful", "Book Deleted", "Book was successfully deleted from the database.");
                    bookView.removeBookFromObservableList(bookDTO);
                }
                else{
                    bookView.addDisplayAlertMessage("Delete error", "Problem at deleting the book", "There was a problem with the database. Please try again!");
                }
            }else{
                bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting the book", "You must select a book before pressing the delete button.");

            }
        }
    }

    private class SaleButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookDTO != null)
            {
                if(bookDTO.getStock() > 0) {
                    boolean saleSuccessful = orderService.save(OrderMapper.convertBookDTOToOrder(bookDTO, user_id_logged));
                    boolean updateStockSuccessful = bookService.updateStock(BookMapper.convertBookDTOToBook(bookDTO));
                    if (saleSuccessful && updateStockSuccessful) {
                        bookView.addDisplayAlertMessage("Sale Successful", "Book Sold", "Book was successfully sold.");
                        bookView.addOrderToObservableList(OrderMapper.convertBookDTOToOrderDTO(bookDTO));
                        bookView.updateBookObservableList(bookDTO.getId());
                    } else {
                        bookView.addDisplayAlertMessage("Sale error", "Problem at selling the book", "There was a problem with the database. Please try again!");
                    }
                }
                else
                    bookView.addDisplayAlertMessage("Sale Error", "Problem at selling the book", "Out of stock.");
            }else
                bookView.addDisplayAlertMessage("Sale Error", "Problem at selling the book", "You must select a book before pressing the sale button.");

        }
    }

    private class LogoutButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Stage stage = LoginComponentFactory.getInstance(false, bookView.getStage()).getLoginView().getStage();
            Scene scene = LoginComponentFactory.getInstance(false, bookView.getStage()).getLoginView().getScene();
            LoginComponentFactory.getInstance(false, stage).getLoginView().getStage().setScene(scene);
        }
    }

    public void setUser_id_logged(Long user_id_logged) {
        this.user_id_logged = user_id_logged;
    }
}
