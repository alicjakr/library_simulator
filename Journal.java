public class Journal extends LibraryItem {
    String title;
    String eISSN;
    String publisher;
    String latestIssue;
    String journalURL;

    @Override
    int getLoanPeriodDays() {
        if(borrower instanceof Student) {
            return 3;
        } else {
            return 7;
        }
    }

    @Override
    double getDailyFine() {
        return 2.0;
    }
}