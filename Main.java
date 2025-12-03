public class Main {
    public static void main(String[] args) throws Exception {
        //create library
        Library library=new Library();
        library.createUsers();
        System.out.println("Amount of users: "+library.users.size());
        library.readCSVfiles();
        System.out.println("Amount of books: "+library.books.size());
        System.out.println("Amount of journals: "+library.journals.size());
        System.out.println("Amount of films: "+library.films.size());

        System.out.println("\nStarting the simulation...\n");
        Simulation simulation=new Simulation(library);
        long startTime = System.nanoTime();
        simulation.simulateYear();
        long endTime = System.nanoTime();
        System.out.println("Simulation finished...\n");
        long executionTime=(endTime-startTime)/1000000;
        System.out.println("Simulating the library for a year takes "+executionTime+"ms\n");
        printStatistics(library);
    }

    private static void printStatistics(Library library) {
        int currentBooks=library.books.size();
        int currentJournals=library.journals.size();
        int currentFilms=library.films.size();

        for(Book book:library.books) {
            if(book.borrowed) {
                currentBooks++;
            }
        }
        for(Journal journal:library.journals) {
            if(journal.borrowed) {
                currentJournals++;
            }
        }
        for(Film film:library.films) {
            if(film.borrowed) {
                currentFilms++;
            }
        }

        System.out.println("Final statistics:");
        System.out.println("Books borrowed: "+currentBooks);
        System.out.println("Journals borrowed: "+currentJournals);
        System.out.println("Films borrowed: "+currentFilms);

    }
}
