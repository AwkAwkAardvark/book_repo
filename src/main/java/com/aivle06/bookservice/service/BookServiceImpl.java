package com.aivle06.bookservice.service;

import com.aivle06.bookservice.domain.Book;
import com.aivle06.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    @Autowired
    private final BookRepository bookRepository;

    //저장
    @Override
    public Book createBook(Book book){
        return bookRepository.save(book);
    }

    //전체 조회
    @Override
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    //단건 조회
    @Override
    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("책이 존재하지 않습니다."));
    }

    //업데이트
    @Override
    public Book updateBook(Long id, Book book){
        Book b = getBookById(id);
        b.setTitle(book.getTitle());
        b.setAuthor(book.getAuthor());
        b.setImage_url(book.getImage_url());

        return bookRepository.save(b);

    }

    //삭제
    public void deleteBook(Long id){
        Book b = getBookById(id);
        bookRepository.delete(b);
    }


}
