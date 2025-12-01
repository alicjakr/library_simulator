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
        int currentbooks=library.books.size();
        int currentjournals=library.journals.size();
        int currentfilms=library.films.size();

        for(Book book:library.books) {
            if(book.borrowed) {
                currentbooks++;
            }
        }
        for(Journal journal:library.journals) {
            if(journal.borrowed) {
                currentjournals++;
            }
        }
        for(Film film:library.films) {
            if(film.borrowed) {
                currentfilms++;
            }
        }

        System.out.println("Final statistics:");
        System.out.println("Books borrowed: "+currentbooks);
        System.out.println("Journals borrowed: "+currentjournals);
        System.out.println("Films borrowed: "+currentfilms);

    }
}
