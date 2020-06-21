package com.library.bookgallery.service;

import com.library.bookgallery.domain.Author;
import com.library.bookgallery.domain.Book;
import com.library.bookgallery.exception.NotFoundException;
import com.library.bookgallery.repository.AuthorRepository;
import com.library.bookgallery.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(long id){
        return authorRepository.findById(id).orElseThrow(()-> new NotFoundException("Object not found in database"));
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Author findByName(String name) {
        return authorRepository.findByName(name);
    }
}
