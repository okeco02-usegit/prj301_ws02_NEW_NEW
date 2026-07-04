package lehuuhoanganh.utils;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private static final List<Book> bookList = new ArrayList<>();

    static {
        bookList.add(new Book("B001", "C#", 10.5));
        bookList.add(new Book("B002", "Java", 11.5));
        bookList.add(new Book("B003", "Spring", 12.5));
        bookList.add(new Book("B004", "JSP", 13.5));
        bookList.add(new Book("B005", "Ruby", 14.5));
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public Book getBookById(String id) {
        if (id == null) {
            return null;
        }

        for (Book b : bookList) {
            if (b.getId() != null && b.getId().trim().equalsIgnoreCase(id.trim())) {
                return b;
            }
        }
        return null;
    }
}
