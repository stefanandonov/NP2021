package mk.ukim.finki.av8;

import java.util.*;
import java.util.stream.Collectors;

class Book {
    String title;
    String category;
    float price;

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %.2f", title, category, price);
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Float.compare(book.price, price) == 0 &&
                title.equals(book.title) &&
                category.equals(book.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, category, price);
    }

    //    @Override not useful for the second printing method
//    public int compareTo(Book o) {
//        int res = this.title.compareTo(o.title);
//        if (res==0){
//            return Float.compare(this.price, o.price);
//        } else {
//            return res;
//        }
//    }
}

//OLD WAY
//class TitleAndPriceComparator implements Comparator<Book> {
//    @Override
//    public int compare(Book o1, Book o2) {
//        int res = o1.title.compareTo(o2.title);
//        if (res==0){
//            return Float.compare(o1.price, o2.price);
//        } else {
//            return res;
//        }
//    }
//}
//
//class PriceComparator implements Comparator<Book>{
//
//    @Override
//    public int compare(Book o1, Book o2) {
//        int res = Float.compare(o1.price, o2.price);
//        if (res==0)
//            return o1.title.compareTo(o2.title);
//        else
//            return res;
//    }
//}

class BookCollection {
    List<Book> books;

    //improved way to create comparators with lambda expression
//    final Comparator<Book> titleAndPriceComparator = (o1, o2) -> {
//        int res = o1.title.compareTo(o2.title);
//        if (res == 0) {
//            return Float.compare(o1.price, o2.price);
//        } else {
//            return res;
//        }
//    };
//
//    final Comparator<Book> priceComparator = (o1,o2) -> {
//        int res = Float.compare(o1.price, o2.price);
//        if (res==0)
//            return o1.title.compareTo(o2.title);
//        else
//            return res;
//    };

    final Comparator<Book> titleAndPriceComparator = Comparator
            .comparing(Book::getTitle)
            .thenComparing(Book::getPrice);

    final Comparator<Book> priceComparator = Comparator.comparing(Book::getPrice)
            .thenComparing(Book::getTitle);

    BookCollection(){
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void printByCategory(String category) {
        books.stream()
                .filter(book -> book.category.equalsIgnoreCase(category))
                .sorted(titleAndPriceComparator)
                .forEach(System.out::println);
    }

    public List<Book> getCheapestN(int n) {
        return books.stream()
                .sorted(priceComparator)
                .limit(n)
                .collect(Collectors.toList());
    }
}

public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

// Вашиот код овде