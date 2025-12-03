public class Student extends User {
    int maxBooks=3;
    int maxJournals=3;
    int maxFilms=1;

    @Override
    public boolean canBorrowBook() {
        return borrowedBooks.size() < maxBooks;
    }

    @Override
    public boolean canBorrowJournal() {
        return borrowedJournals.size() < maxJournals;
    }

    @Override
    public boolean canBorrowFilm() {
        return borrowedFilms.size() < maxFilms;
    }
}