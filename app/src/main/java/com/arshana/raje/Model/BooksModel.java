package com.arshana.raje.Model;

public class BooksModel {
    private String id;
    private String book_name;
    private String book_pdf_link;
    private String book_Content;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_writer_name() {
        return book_pdf_link;
    }

    public void setBook_writer_name(String book_writer_name) {
        this.book_pdf_link = book_writer_name;
    }

    public String getBook_Content() {
        return book_Content;
    }

    public void setBook_Content(String book_Content) {
        this.book_Content = book_Content;
    }
}
