public class Book extends LibraryItem {
    String title;
    String author;
    String genre;
    String publisher;

    @Override    
    int getLoanPeriodDays() {
        return 14;
    }

    @Override    
    double getDailyFine() {
        return 0.5;
    }
}