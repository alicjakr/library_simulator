import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Library {
    //80 students, 20 faculty members
    int students=80;
    int facultyMembers=20;
    List<User> users=new ArrayList<>();
    List<Book> books=new ArrayList<>();
    List<Journal> journals=new ArrayList<>();
    List<Film> films=new ArrayList<>();

    public void createUsers() {
        for(int i=0; i<students; i++) {
            users.add(new Student());
        }
        for(int i=0; i<facultyMembers; i++) {
            users.add(new FacultyMember());
        }

        Random r=new Random();
        //assign returnig the book on time
        for(User user : users) {
            if(r.nextDouble()<0.67) {
                user.returnsOnTime=true;
            } else {
                user.returnsOnTime=false;
            }

        }
    }

    public void readCSVfiles() {
        final String DELIMITER=";";

        //books
        int bookID=1;
        try(BufferedReader br=new BufferedReader(new FileReader("books.csv"))) {
            String line;
            boolean firstline=true;
            while((line=br.readLine())!=null) {
                if(firstline) {
                    firstline=false;
                    continue;
                }

                String[] valuesbook=line.split(DELIMITER);

                Book book=new Book();
                book.libraryID=bookID++;
                book.title=valuesbook[0];
                book.author=valuesbook[1];
                book.genre=valuesbook[2];
                book.publisher=valuesbook[4];

                this.books.add(book);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
                
        //journals
        int journalID=423;
        try(BufferedReader brr=new BufferedReader(new FileReader("jlist.csv"))) {
            String linee;
            boolean firstlinee=true;
            while((linee=brr.readLine())!=null) {
                if(firstlinee) {
                    firstlinee=false;
                    continue;
                }

                String[] valuesjournal=linee.split(DELIMITER);

                Journal journal=new Journal();
                journal.libraryID=journalID++;
                journal.title=valuesjournal[0];
                journal.eISSN=valuesjournal[3];
                journal.publisher=valuesjournal[4];
                journal.latestIssue=valuesjournal[6];
                journal.journalURL=valuesjournal[12];

                this.journals.add(journal);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        //films
        int filmID=4384;
        try(BufferedReader brrr=new BufferedReader(new FileReader("movies.csv"))) {
            String lineee;
            boolean firstlineee=true;
            while((lineee=brrr.readLine())!=null) {
                if(firstlineee) {
                    firstlineee=false;
                    continue;
                }

                String[] valuesmovies=lineee.split(DELIMITER);

                Film film=new Film();
                film.libraryID=filmID++;
                film.title=valuesmovies[1];
                film.genre=valuesmovies[2];
                film.director=valuesmovies[4];
                film.year=Integer.parseInt(valuesmovies[6]);
                film.runtime=Integer.parseInt(valuesmovies[7]);
                film.ratingEntries=Double.parseDouble(valuesmovies[8]);

                this.films.add(film);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //allows to borrow any item on any day
    public void borrowItem(User user, LibraryItem item, LocalDate date) {
        if(item.borrowed) {
            return;
        }
        boolean canBorrow=false;
        if(user.canBorrowBook() && item instanceof Book) {
            user.borrowedBooks.add((Book) item);
            canBorrow=true;
        } else if(user.canBorrowJournal() && item instanceof Journal) {
            user.borrowedJournals.add((Journal)item);
            canBorrow=true;
        } else if(user.canBorrowFilm() && item instanceof Film) {
            user.borrowedFilms.add((Film) item);
            canBorrow=true;
        }if(canBorrow) {
            item.borrowed=true;
            item.borrowDate=date;
            item.borrower=user;
        }
    }

    //returning items
    public double returnItem(User user, LibraryItem item, LocalDate date) throws Exception {
        if(!item.borrowed || item.borrower!=user) {
            return 0.0;
        }
        double fine=item.computeFine(date);

        if(item instanceof Book) {
            user.borrowedBooks.remove(item);
        } else if (item instanceof Journal) {
            user.borrowedJournals.remove(item);
        } else if(item instanceof Film) {
            user.borrowedFilms.remove(item);
        }

        item.borrowed=false;
        item.borrower=null;
        item.borrowDate=null;

        return fine;
    }
}