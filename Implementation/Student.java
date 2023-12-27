public class Student {
    private String studentNumber;
    private String name;
    private int midtermScore;
    private int finalScore;

    public Student(String studentNumber, String name, int midtermScore, int finalScore) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMidtermScore() {
        return midtermScore;
    }

    public void setMidtermScore(int midtermScore) {
        this.midtermScore = midtermScore;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public int getOverallScore() {
        return midtermScore + finalScore;
    }

    @Override
    public String toString() {
        return "Student Number: " + studentNumber + ", Name: " + name + ", Overall Score: " + getOverallScore();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            Student student = (Student) obj;
            return studentNumber.equals(student.studentNumber) &&
                name.equals(student.name) &&
                midtermScore == student.midtermScore &&
                finalScore == student.finalScore;
        }
        return false;
    }
}
