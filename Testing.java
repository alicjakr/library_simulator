import org.junit.Test;

public class Testing {

    Library library = new Library();
    library.createUsers();

    @Test
    public void testCreateUsers() {
        assert(library.users.size()==100);
    }

    @Test
    void borrowItem() {

    }

    @Test
    void returnItem() {
    }
}