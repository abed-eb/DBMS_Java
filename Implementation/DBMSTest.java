import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;


public class DBMSTest {
    private final DBMSInterface dbms = new DBMS();
    Student student1 = new Student("S12345", "John Doe", 85, 90);
    Student student2 = new Student("S22323", "Olivia Martinez", 75, 22);
    Student student3 = new Student("S34578", "Ethan Kim", 65, 33);
    Student student4 = new Student("S4445422", "Sophia Patel1", 15, 55);
    Student student5 = new Student("S42454", "Sophia Patel2", 15, 55);
    Student student6 = new Student("S42455", "Sophia Patel3", 15, 55);

    @Test
    public void test1() {
        dbms.insertStudent(student1);
        dbms.insertStudent(student2);
        Student queryResult1 = dbms.queryByStudentNumber("S12345");
        assertEquals(student1, queryResult1);
    }

    @Test
    public void test2() {
        dbms.insertStudent(student3);
        dbms.insertStudent(student4);
        dbms.insertStudent(student5);
        dbms.deleteStudent(student4);
        List<Student> queryResult1 = dbms.queryByScore(70);
        assertEquals(student5, queryResult1.get(0));
    }

    @Test
    public void test3() {
        dbms.insertStudent(student1);
        dbms.insertStudent(student2);
        dbms.insertStudent(student6);
        dbms.deleteStudent(student1);
        Student queryResult1 = dbms.queryByStudentNumber("S12345");
        assertEquals(null, queryResult1);
    }

}
