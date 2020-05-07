package com.library.bookgallery.service;

import com.library.bookgallery.domain.Book;
import com.library.bookgallery.exception.NotFoundException;
import com.library.bookgallery.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
//@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(long id){
        return bookRepository.findById(id).orElseThrow(()-> new NotFoundException("Object not found in database"));
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }
}
