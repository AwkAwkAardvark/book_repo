package com.aivle06.bookservice.controller;

import com.aivle06.bookservice.domain.Book;
import com.aivle06.bookservice.dto.BookDetailResponseDTO;
import com.aivle06.bookservice.dto.BookListResponseDTO;
import com.aivle06.bookservice.dto.BookRequestDTO;
import com.aivle06.bookservice.dto.common.DataResponse;
import com.aivle06.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 새 책 등록 (POST /api/books)
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookRequestDTO.Create book) {
        Book createdBook = bookService.createBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    // 전체 목록 조회 (GET /api/books/?page={page_num})
    @GetMapping
    public ResponseEntity<DataResponse<BookListResponseDTO>> getAllBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        DataResponse<BookListResponseDTO> response = new DataResponse<>(bookService.getAllBookListResponseWithPaging(page, size));
        return ResponseEntity.ok(response);
    }

    // 특정 ID로 조회 (GET /api/books/{id})
    @GetMapping("/{id}")
    public ResponseEntity<BookDetailResponseDTO> getBookById(@PathVariable Long id) {
        BookDetailResponseDTO dto = bookService.getBookDetailResponseDTOById(id);
        return ResponseEntity.ok(dto);
    }

    // 업뎃 (PUT /api/books/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestBody BookRequestDTO.Update book) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    // 삭제 (DELETE /api/books/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
