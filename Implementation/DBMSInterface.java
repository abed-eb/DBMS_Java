import java.util.List;

public interface DBMSInterface {
    public void insertStudent(Student student);
    public Student queryByStudentNumber(String studentNumber);
    public List<Student> queryByScore(int score);
    public void deleteStudent(Student student);
}
