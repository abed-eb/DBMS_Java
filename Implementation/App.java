import java.util.List;

public class App {
    public static void main(String[] args) {
//        System.out.println("Hello, World!");
        DBMS database = new DBMS();

        Student student1 = new Student("S12345", "John Doe", 85, 90);
        Student student2 = new Student("S22323", "Olivia Martinez", 75, 22);
        Student student3 = new Student("S34578", "Ethan Kim", 65, 33);
        Student student4 = new Student("S4445422", "Sophia Patel1", 15, 55);
        Student student5 = new Student("S42454", "Sophia Patel2", 15, 55);
        Student student6 = new Student("S42455", "Sophia Patel3", 15, 55);
        Student student7 = new Student("S42456", "Sophia Patel4", 15, 55);
        Student student8 = new Student("S42457232", "Sophia Patel5", 15, 55);
//        Student student6 = new Student("S44445", "Jackson Brown", 0, 99);
//        Student student7 = new Student("S34336", "Mia Nguyen", 30, 68);
        database.insertStudent(student1);
        database.insertStudent(student2);
        database.insertStudent(student3);
        database.insertStudent(student4);
        database.insertStudent(student5);
        database.insertStudent(student6);
        database.insertStudent(student7);
        database.insertStudent(student8);

//        Student queryResult1 = database.queryByStudentNumber("S12345");
//        List<Student> queryResult2 = database.queryByScore(97);
//        List<Student> queryResult3 = database.queryByScore(98);
//        List<Student> queryResult4 = database.queryByScore(70);
//
//        System.out.println("response: ");
//        System.out.println(queryResult1);
//        System.out.println(queryResult2);
//        System.out.println(queryResult3);
//        System.out.println(queryResult4);
//        System.out.println("response end ");
        database.deleteStudent(student4);
//        database.deleteStudent(student7);
//        database.deleteStudent(student6);
//        database.deleteStudent(student2);
//        List<Student> queryResultDelete = database.queryByScore(70);
        Student queryResultDelete2 = database.queryByStudentNumber("S4445422");
        List<Student> queryResultDelete3 = database.queryByScore(70);
        System.out.println("After Delete: " + queryResultDelete2);
        System.out.println("After Delete2: " + queryResultDelete3);
//        System.out.println("After Delete2: " + queryResultDelete2);
    }
}
