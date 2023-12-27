import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface MyStorageBackendInterface<T, U> {
    void insert(T key, U item);

    List<U> search(T key);

    void deleteByStudentNumber(T key);

    void deleteByOverallScore(String key, T score);

}

@FunctionalInterface
interface MyHashFunction<T> {
    int hash(T key);
}

public class DBMS implements DBMSInterface {
    private MyStorageBackendInterface<String, Student> studentNumberIndex;
    private MyStorageBackendInterface<Integer, Student> overallScoreIndex;

    public DBMS() {
        final int hashNumber2 = 6;
        // Hashing for student number SkipList
        this.studentNumberIndex = new MyStorageBackend<>(
                (s) -> {
                    int hash = 0;
                    for (int i = 0; i < s.toString().length(); i++) {
                        hash = (hash * 31) + (int) s.toString().charAt(i);
                    }
                    return hash;
                }
        );
        // Hashing for student overall score SkipList
        this.overallScoreIndex = new MyStorageBackend<>(
                (s) -> {
                    int resultHashNumber = 0;
                    for (int i = 0; i < s.toString().length(); i++) {
                        resultHashNumber = s.toString().charAt(i) * 12;
                    }
                    Pair pair = new Pair(resultHashNumber, s);
                    return resultHashNumber;
                }
        );
    }

    @Override
    public void insertStudent(Student student) {
        studentNumberIndex.insert(student.getStudentNumber(), student);
        overallScoreIndex.insert(student.getOverallScore(), student);
    }

    /**
     * Find students with a specific student number
     *
     * @param studentNumber which is string to be used for search between student numbers
     * @return a student object with the specific student number
     */
    @Override
    public Student queryByStudentNumber(String studentNumber) {
        List<Student> data = studentNumberIndex.search(studentNumber);
        if (data.size() > 0) {
            return data.get(0);
        } else return null;
    }

    /**
     * Find students with a specific score
     *
     * @param score is an integer which is overScore
     * @return list of students with specific score
     */
    @Override
    public List<Student> queryByScore(int score) {
        return overallScoreIndex.search(score);
    }

    /**
     * Remove student
     *
     * @param student is a student object
     */
    @Override
    public void deleteStudent(Student student) {
        this.studentNumberIndex.deleteByStudentNumber(student.getStudentNumber());
        this.overallScoreIndex.deleteByOverallScore(student.getStudentNumber(), student.getOverallScore());
    }
}

class SkipNode<N extends Comparable<? super N>> {
    public static int level = 5;
    Pair data;
    SkipNode<N>[] next = (SkipNode<N>[]) new SkipNode[level];
    SkipNode(Pair data) {
        this.data = data;
    }

    /**
     * changing and fixing skiplist pointers after deleting a node
     *
     * @param level integer which is a specific skip list level
     */
    public void setPointersAfterDeletingData(int level) {
        SkipNode<N> current = this;
        while (current != null && current.getNext(level) != null) {
            if (current.getNext(level).data == null) {
                SkipNode<N> successor = current.getNext(level).getNext(level);
                current.setNext(successor, level);
                return;
            }
            current = current.getNext(level);
        }

    }

    /**
     * set the next pointer
     *
     * @param next  is next node
     * @param level integer which is a specific skip list level
     */
    void setNext(SkipNode<N> next, int level) {
        this.next[level] = next;
    }

    /**
     * get the next node
     *
     * @param level integer which is a specific skip list level
     */
    SkipNode<N> getNext(int level) {
        return this.next[level];
    }

    /**
     * search for a student in different levels
     *
     * @param data  is integer which is a hash code of student number or student overallScore
     * @param level integer which is a specific skip list level
     * @return list of students
     */
    List<Student> searchNode(int data, int level) {
        List<Student> result = new ArrayList<>();
        SkipNode<N> current = this.getNext(level);
        while (current != null) {
            if (current.data != null)
                if (current.data.first.equals(data)) {
                    result.add((Student) current.data.second);
                }
            current = current.getNext(level);
        }

        return result;
    }

    /**
     * searching for a node in different levels to be deleted
     *
     * @param data  is integer which is a hash code of student number or student overallScore
     * @param level integer which is a specific skip list level
     * @return list of students
     */
    List<SkipNode<N>> searchNodeDelete(int data, int level) {
        List<SkipNode<N>> result = new ArrayList<>();
        SkipNode<N> current = this.getNext(level);
        while (current != null) {
            if (current.data.first.equals(data)) {
                result.add(current);
            }
            current = current.getNext(level);
        }

        return result;
    }

    void insertNode(SkipNode<N> skipNode, int level) {
        SkipNode<N> current = this.getNext(level);
        if (current == null) {
            this.setNext(skipNode, level);
            return;
        }

        if (skipNode.data.first.equals(current.data.first)) {
            this.setNext(skipNode, level);
            skipNode.setNext(current, level);
            return;
        }

        while (current.getNext(level) != null && current.data.first.equals(skipNode.data.first) &&
                current.getNext(level).data.first.equals(skipNode.data.first)) {
            current = current.getNext(level);
        }

        SkipNode<N> successor = current.getNext(level);
        current.setNext(skipNode, level);
        skipNode.setNext(successor, level);
    }

}

class MyStorageBackend<T extends Comparable<? super T>, U> implements MyStorageBackendInterface<T, U> {
    public static int level = 5;
    public final SkipNode<T> head = new SkipNode<>(null);
    private final Random rand = new Random();
    MyHashFunction hashFunction;

    public MyStorageBackend(MyHashFunction h) {
        hashFunction = h;
    }

    /**
     * insert student by student number or overallScore
     *
     * @param key  is string student number or integer student overallScore
     * @param item is a student object
     */
    @Override
    public void insert(T key, U item) {
        int hashedKey = hashFunction.hash(key);
        Pair pair = new Pair(hashedKey, item);
        SkipNode<T> skipNode = new SkipNode<>(pair);
        for (int i = 0; i < level; i++) {
            // Insert with prob = 1/(2^i)
            if (rand.nextInt((int) Math.pow(2, i)) == 0) {
                head.insertNode(skipNode, i);
            }
        }
    }

    /**
     * Remove student by score
     *
     * @param studentNumber is string student number
     * @param score         is integer student overallScore
     */
    @Override
    public void deleteByOverallScore(String studentNumber, T score) {
        List<SkipNode<T>> victims = searchForDelete(score);
        // Checking to delete the exact student and not delete students with same score
        for (int i = 0; i < victims.size(); i++) {
            if (victims.get(i).data.second.toString().contains(studentNumber)) {
                victims.get(i).data = null;
            }
        }
        // Fixing the next pointers after deletion
        for (int j = 0; j < level; j++) {
            head.setPointersAfterDeletingData(j);
        }
    }

    /**
     * Remove student by student number
     *
     * @param key is student number
     */
    @Override
    public void deleteByStudentNumber(T key) {
        List<SkipNode<T>> victims = searchForDelete(key);
        // Remove null data
        for (int i = 0; i < victims.size(); i++) {
            victims.get(i).data = null;
        }
        // Fixing the next pointers after deletion
        for (int j = 0; j < level; j++) {
            head.setPointersAfterDeletingData(j);
        }
    }

    /**
     * Search for finding students which may be deleted
     *
     * @param data can be string student number or integer student overallScore
     * @return list of nodes which contain students
     */
    public List<SkipNode<T>> searchForDelete(T data) {
        // Hashing pure data to search between hash values
        int hashedData = hashFunction.hash(data);
        List<SkipNode<T>> result = new ArrayList<>();
        for (int i = level - 1; i >= 0; i--) {
            List<SkipNode<T>> nodes = head.searchNodeDelete(hashedData, i);
            // Delete Null Data
            for (int j = 0; j < nodes.size(); j++) {
                if (nodes.get(j) == null) {
                    nodes.remove(j);
                }
            }
            result = nodes;
        }
        return result;
    }

    /**
     * Search for finding students which may be deleted
     *
     * @param data can be string student number or integer student overallScore
     * @return list of generic data which will be students
     */
    public List<U> search(T data) {
        // Hashing pure data to search between hash values
        int hashedData = hashFunction.hash(data);
        List<Student> result = new ArrayList<>();
        for (int i = level - 1; i >= 0; i--) {
            List<Student> foundStudents = head.searchNode(hashedData, i);
            if (foundStudents.size() > 0) {
                // Break as soon as found the student number
                if (data instanceof String) {
                    result.add(foundStudents.get(0));
                    return (List<U>) result;
                }
                // Do not add redundant data
                if (data instanceof Integer)
                    result = foundStudents;
            }
        }
        return (List<U>) result;
    }

}
