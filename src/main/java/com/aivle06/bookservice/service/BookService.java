package com.aivle06.bookservice.service;

import com.aivle06.bookservice.domain.Book;

import java.util.List;

public interface BookService {

    // 저장
    Long createBook(Book book);

    // 단건 조회
    Book getBookById(Long id);

    // 다건 조회
    List<Book> getAllBooks();

    // 업데이트
    void updateBook(Long id, Book bookDetails);

    // 삭제
    void deleteBook(Long id);

}
