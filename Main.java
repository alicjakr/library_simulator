public class Main {
    public static void main(String[] args) {
        //create library
        Library library=new Library();
        library.createUsers();
        System.out.println("Amount of users: "+library.users.size());
        library.readCSVfiles();
        System.out.println("Amount of books: "+library.books.size());
        System.out.println("Amount of journals: "+library.journals.size());
        System.out.println("Amount of films: "+library.films.size());

        //Simulation simulation=new Simulation(library);

    }
}
