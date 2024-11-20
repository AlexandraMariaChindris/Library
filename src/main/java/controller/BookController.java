package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import mapper.OrderMapper;
import service.book.BookService;
import service.order.OrderService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;


public class BookController {

    private final BookView bookView;
    private final BookService bookService;
    private final OrderService orderService;

    public BookController(BookView bookView, BookService bookService, OrderService orderService) {
        this.bookView = bookView;
        this.bookService = bookService;
        this.orderService = orderService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSaleButtonListener(new SaleButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String stock = bookView.getStock();
            String price = bookView.getPrice();

            if(title.isEmpty() || author.isEmpty() || stock == null || price == null) {
                bookView.addDisplayAlertMessage("Save Error", "Problem at Author, Title, Stock or Price fields", "Can not have an empty field.");
            }
            else {

                BookDTO bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).setStock(Integer.valueOf(stock)).setPrice(Float.valueOf(price)).build();
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
            if(bookDTO != null) {
                boolean saleSuccessful = orderService.save(OrderMapper.convertBookDTOToOrder(bookDTO));
                boolean updateStockSuccessful = bookService.updateStock(BookMapper.convertBookDTOToBook(bookDTO));
                if(saleSuccessful && updateStockSuccessful) {
                    bookView.addDisplayAlertMessage("Sale Successful", "Book Sold", "Book was successfully sold.");
                    bookView.addOrderToObservableList(OrderMapper.convertBookDTOToOrderDTO(bookDTO));
                    BookMapper.convertBookDTOToBook(bookDTO).setStock(BookMapper.convertBookDTOToBook(bookDTO).getStock() - 1);
                    bookView.updateBookObservableList();
                }
                else{
                    bookView.addDisplayAlertMessage("Sale error", "Problem at selling the book", "There was a problem with the database. Please try again!");
                }
            }else{
                bookView.addDisplayAlertMessage("Sale Error", "Problem at selling the book", "You must select a book before pressing the sale button.");

            }
        }
    }
}
