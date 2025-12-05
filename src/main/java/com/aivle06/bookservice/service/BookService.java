package com.aivle06.bookservice.service;

import com.aivle06.bookservice.domain.Book;

import java.util.List;

public interface BookService {

    // 저장
    Book createBook(Book book);
    //implements에서 충돌해서 Long에서 Book으로 다시 바꿨습니다

    // 단건 조회
    Book getBookById(Long id);

    // 다건 조회
    List<Book> getAllBooks();

    // 업데이트
    Book updateBook(Long id, Book bookDetails);
    //저장하면서 Book이 return되면서 void랑 충돌했습니다;;

    // 삭제
    void deleteBook(Long id);

}
