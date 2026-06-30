package aufgaben.a2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Book {
    private static final List<Book> books = new ArrayList<>();

    private String title;
    private String author;
    private int yearOfPublication;
    private double price;

    public Book(String title, String author, int yearOfPublication, double price) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.price = price;

        books.add(this);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", price=" + price +
                '}';
    }

    public static List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public static List<Book> getBooksBeforeYear(List<Book> bookList, int year) {
        return bookList.stream().filter(b -> b.yearOfPublication < year).toList();
    }

    public static List<Book> getBooksBeforeYearNoStreams(List<Book> bookList, int year) {
        List<Book> result = new ArrayList<>();
        for (Book b : bookList) {
            if (b.yearOfPublication < year) {
                result.add(b);
            }
        }
        return result;
    }

    public static List<Book> getBooksSortedByTitle(List<Book> bookList) {
        return bookList.stream().sorted((b1, b2) -> b1.title.compareTo(b2.title)).toList();
    }

    public static List<String> getBookTitles(List<Book> bookList) {
        return bookList.stream().map(b -> b.title).toList();
    }

    public static double getAveragePrice(List<Book> bookList) {
        return bookList.stream().mapToDouble(b -> b.price).average().orElse(0.0);
    }

    public static Book getMostExpensiveBook(List<Book> bookList) {
        return bookList.stream().max((b1, b2) -> Double.compare(b1.price, b2.price)).orElse(null);
    }

    public static Map<String, List<Book>> getAuthorMap(List<Book> bookList) {
        return bookList.stream().collect(Collectors.groupingBy(b -> b.author));
    }
}
