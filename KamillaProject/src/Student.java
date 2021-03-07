import java.util.*;

public class Student extends Person {
    private List<Quiz> quizzes;
    private List<Integer> scores;

    public Student(String username, String password) {
        super(username, password);
        this.quizzes = new ArrayList<>();
        this.scores = new ArrayList<>();
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void takeQuiz(Quiz quiz) throws IndexOutOfBoundsException {
        int score = 0;
        long start_time = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);
        for (Question question : quiz.getQuestions()) {
            System.out.println(question.getText());
            for (int i = 1; i <= question.getOptions().size(); i++) {
                System.out.println(i + ". " + question.getOptions().get(i - 1));
            }
            System.out.println("Choose option: ");
            int choice = scanner.nextInt();
            if (question.getOptions().get(choice - 1).equals(question.getCorrectOption())) {
                score += 10;
            } else {
                score += -5;
            }
        }
        long finish_time = System.currentTimeMillis();
        double taken_time = ((double) ((finish_time - start_time)) / 1000) / 60;
        quizzes.add(quiz);
        if ((double) (quiz.getTime()) < taken_time) {
            score = 0;
        }
        scores.add(score);
    }

    public Integer getScore(Quiz quiz) {
        for (int i = 0; i < quizzes.size(); i++) {
            if (quizzes.get(i).getTopic().equals(quiz.getTopic())) {
                return scores.get(i);
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        String student_info = "Student{" + super.toString() +
                "quizzes=";
        for (int i = 0; i < quizzes.size(); i++) {
            student_info += quizzes.get(i).getTopic();
            student_info += ": ";
            student_info += scores.get(i);
            student_info += "; ";
        }
        student_info += '}';
        return student_info;
    }
}
