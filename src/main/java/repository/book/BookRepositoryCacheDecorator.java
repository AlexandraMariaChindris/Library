package repository.book;

import model.Book;

import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecorator extends BookRepositoryDecorator{

    private Cache<Book> cache;

    //trimitem bookRepository injectat in BookRepositoryCacheDecorator
    public BookRepositoryCacheDecorator(BookRepository repository, Cache<Book> cache) {
        super(repository);
        this.cache = cache;
    }

    @Override
    public List<Book> findAll() {
        //daca nu avem nimic in cache, incarcam din baza de date
        if(cache.hasResult()){
            return cache.load();
        }
        List<Book> books = decoratedBookRepository.findAll();
        cache.save(books);

        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        if(cache.hasResult()){
            return cache.load().stream()
                    .filter(it -> it.getId().equals(id))
                    .findFirst();
        }

        return decoratedBookRepository.findById(id);
    }

    @Override
    public boolean save(Book book) {

        cache.invalidateCache();
        return decoratedBookRepository.save(book);
    }

    @Override
    public boolean delete(Book book) {
        cache.invalidateCache();
        return decoratedBookRepository.delete(book);
    }

    @Override
    public boolean updateStock(Book book) {
        return false;
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedBookRepository.removeAll();
    }
}
