import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Student> students;

    public Team(String name) {
        this.name = name;
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public double calculateAverage(Quiz quiz) {
        if (quiz.getAllowedTeams().contains(name)) {
            double total = 0;
            for (Student student : students) {
                total += student.getScore(quiz);
            }
            return (Double) total / students.size();
        }
        return -1;
    }
}
