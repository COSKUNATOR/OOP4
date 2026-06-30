package aufgaben.a2;

public class Main {
    public static void main(String[] args) {
        createBooks();

        Book.getBooks().forEach(System.out::println);
        System.out.println("-------------------");
        Book.getBooksBeforeYear(Book.getBooks(), 2000).forEach(System.out::println);
        System.out.println("-------------------");
        Book.getBooksSortedByTitle(Book.getBooksBeforeYear(Book.getBooks(), 2000)).forEach(System.out::println);
        System.out.println("-------------------");
        Book.getBookTitles(Book.getBooksSortedByTitle(Book.getBooksBeforeYear(Book.getBooks(), 2000))).forEach(System.out::println);
        System.out.println("-------------------");
        System.out.println("Average price: " + Book.getAveragePrice(Book.getBooksBeforeYear(Book.getBooks(), 2000)));
        System.out.println("-------------------");
        System.out.println("Most expensive book: " + Book.getMostExpensiveBook(Book.getBooks()));
        System.out.println("-------------------");
        System.out.println(Book.getAuthorMap(Book.getBooks()));

    }

    private static void createBooks()
    {
        new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 24.99);
        new Book("1984", "George Orwell", 1949, 19.84);
        new Book("To Kill a Mockingbird", "Harper Lee", 1960, 18.60);
        new Book("The Catcher in the Rye", "J.D. Salinger", 1951, 15.98);
        new Book("Brave New World", "Aldous Huxley", 1932, 12.80);
        new Book("Der Hobbit", "J.R.R.Tolkien", 1937, 10.75);
        new Book("Der Herr der Ringe", "J.R.R.Tolkien", 1954, 22.90);
        new Book("Stolz und Vorurteil" ,"Jane Austen", 1813, 12.50);
        new Book("Moby Dick", "Herman Melville", 1851, 17.40);
        new Book("Astrophysics for People in a Hurry", "Neil deGrasse Tyson", 2017, 19.50);
    }
}
