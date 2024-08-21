package model;

import enums.BookGenre;
import enums.BookAvailability;

public class Book {
    private String title;
    private String author;
    private int pages;
    private String ISBN;
    private BookGenre genre;
    private String language;
    private BookAvailability availability;

    public Book(String title, String author, int pages, String isbn, BookGenre genre,
                String language) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.language = language;
        this.availability = BookAvailability.AVAILABLE;
        setPages(pages);
        setISBN(isbn);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        if (pages > 1) {
            this.pages = pages;
        } else {
            throw new IllegalArgumentException("Pages must be greater than 0");
        }
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String isbn) {
        if (isValidISBN(isbn)) {
            this.ISBN = isbn;
        } else {
            throw new IllegalArgumentException("Invalid ISBN format");
        }
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public BookAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(BookAvailability availability) {
        this.availability = availability;
    }

    private boolean isValidISBN(String isbn) {
        isbn = isbn.replaceAll("-", "").trim();
        if (isbn.length() == 10) {
            return isValidISBN10(isbn);
        } else if (isbn.length() == 13) {
            return isValidISBN13(isbn);
        } else {
            return false;
        }
    }

    private boolean isValidISBN10(String isbn) {
        int sum = 0;
        for (int index = 0; index < 9; index++) {
            if (!Character.isDigit(isbn.charAt(index))) return false;
            sum += (isbn.charAt(index) - '0') * (10 - index);
        }
        char checkChar = isbn.charAt(9);
        sum += (checkChar == 'X') ? 10 : (checkChar - '0');
        return (sum % 11 == 0);
    }

    private boolean isValidISBN13(String isbn) {
        int sum = 0;
        for (int index = 0; index < 13; index++) {
            if (!Character.isDigit(isbn.charAt(index))) return false;
            int digit = isbn.charAt(index) - '0';
            sum += (index % 2 == 0) ? digit : digit * 3;
        }
        return (sum % 10 == 0);
    }


    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre=" + genre + '\'' +
                ", language=" + language +
                '}';
    }
}
