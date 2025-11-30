public class Film extends LibraryItem {
    String title;
    String genre;
    String director;
    int year;
    int runtime;
    double ratingEntries;

    @Override
    int getLoanPeriodDays() {
        return 2;
    }
    @Override
    double getDailyFine() {
        return 5.0;
    }
}
