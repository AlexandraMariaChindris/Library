package service.order;

import model.Order;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import repository.order.OrderRepository;

import java.io.IOException;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order with id: %d was not found.".formatted(id)));
    }

    @Override
    public boolean save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public boolean delete(Order order) {
        return orderRepository.delete(order);
    }

    @Override
    public boolean generatePdfRaport() {
        List<Order> orders = orderRepository.findAll();
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try{
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.setFont(PDType1Font.COURIER, 12);
            contentStream.newLineAtOffset(40, 750);

            contentStream.showText("PDF Raport: Orders-Employees");
            contentStream.newLine();
            contentStream.newLine();

            contentStream.showText(String.format("%-10s %-15s %-10s %-8s %-12s %-20s", "Employee", "Title", "Author", "Quantity", "Price", "Time"));
            contentStream.newLine();
            contentStream.newLine();

            for(Order order : orders) {
                contentStream.showText(String.format("%-10d %-15s %-10s %-8d %-12.2f %-20s", order.getUser_id(), order.getTitle(), order.getAuthor(), order.getQuantity(), order.getPrice(), order.timeToString(order.getTime())));
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();
            document.save("PdfRaport.pdf");
            document.close();

            return true;
        }catch (IOException e) {
            return false;
        }
    }
}
