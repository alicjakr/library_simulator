import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    //on time users always return item on last day of loan period if item not already returned
    //loan period doesn't count the day of borrowing but counts the last day
    Library library;
    Random random;

    //double borrowProbability=0.05;
    final double BOOK_PROBABILITY=0.05;
    final double JOURNAL_PROBABILITY=0.08;
    final double FILM_PROBABILITY=0.05;
    final double RETURN_PROBABILITY=0.02;

    public Simulation(Library library) {
        this.library=library;
        this.random=new Random();
    }

    public void simulateYear() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 1, 1);

        for (int day = 0; day < 365; day++) {
            LocalDate currentDate = startDate.plusDays(day);
            for (User user : library.users) {
                //try borrowing
                tryBorrowUser(user, currentDate);
                //try returning
                tryReturnUser(user, currentDate);
                //check due date
                checkReliableUserReturns(user, currentDate);
            }
        }
    }

    //try borrowing
    private void tryBorrowUser(User user, LocalDate date) {
        Random r=new Random();
        Random r2=new Random();
        //for book
        if(r.nextDouble()<BOOK_PROBABILITY && user.canBorrowBook()) {
            //find random available
            Book bookToBorrow=findAvailableBook();
            //borrow
            assert bookToBorrow!=null;
            library.borrowItem(user, bookToBorrow, date);
        }
        //for journal
        if(r.nextDouble()<JOURNAL_PROBABILITY && user.canBorrowJournal()) {
            Journal journalToBorrow=findAvailableJournal();
            assert journalToBorrow!=null;
            library.borrowItem(user, journalToBorrow, date);
        }
        //for film
        if(r.nextDouble()<FILM_PROBABILITY && user.canBorrowFilm()) {
            Film filmToBorrow=findAvailableFilm();
            assert filmToBorrow!=null;
            library.borrowItem(user, filmToBorrow, date);
        }
    }

    private Book findAvailableBook() {
        List<Book> availableBooks=new ArrayList<>();
        for(Book book : library.books) {
            if(!book.borrowed) {
                availableBooks.add(book);
            }
        }
        //return null if no available books
        if(availableBooks.isEmpty()) {
            return null;
        }
        int randomIndex=random.nextInt(availableBooks.size());
        return availableBooks.get(randomIndex);
    }

    private Journal findAvailableJournal() {
        List<Journal> availableJournals=new ArrayList<>();
        for(Journal journal : library.journals) {
            if(!journal.borrowed) {
                availableJournals.add(journal);
            }
        }
        //return null if no available books
        if(availableJournals.isEmpty()) {
            return null;
        }
        int randomIndex=random.nextInt(availableJournals.size());
        return availableJournals.get(randomIndex);
    }

    private Film findAvailableFilm() {
        List<Film> availableFilms=new ArrayList<>();
        for(Film film : library.films) {
            if(!film.borrowed) {
                availableFilms.add(film);
            }
        }
        //return null if no available books
        if(availableFilms.isEmpty()) {
            return null;
        }
        int randomIndex=random.nextInt(availableFilms.size());
        return availableFilms.get(randomIndex);
    }

    //try returning
    private void tryReturnUser(User user, LocalDate date) throws Exception {
        Random r=new Random();
        if(r.nextDouble()<RETURN_PROBABILITY) {
            //pick random item
            LibraryItem libraryItem=getRandomBorrowedItem(user);
            if(libraryItem!=null) {
                library.returnItem(user, libraryItem, date);
            }
        }
    }

    private LibraryItem getRandomBorrowedItem(User user) {
        List<LibraryItem> borrowedItems=new ArrayList<>();
        borrowedItems.addAll(user.borrowedBooks);
        borrowedItems.addAll(user.borrowedJournals);
        borrowedItems.addAll(user.borrowedFilms);

        if(borrowedItems.isEmpty()) {
            return null;
        }

        int randomIndex=random.nextInt(borrowedItems.size());
        return borrowedItems.get(randomIndex);
    }

    //check due date
    private void checkReliableUserReturns(User user, LocalDate date) throws Exception {
        List<LibraryItem> borrowedItems=new ArrayList<>();
        borrowedItems.addAll(user.borrowedBooks);
        borrowedItems.addAll(user.borrowedJournals);
        borrowedItems.addAll(user.borrowedFilms);
        if(user.returnsOnTime) {
            for(LibraryItem item : borrowedItems) {
                if(LibraryItem.daysOverdue(date)==0) {
                    library.returnItem(user, item, date);
                }
            }
        }
    }
}