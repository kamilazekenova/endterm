import java.sql.*;
import java.util.Scanner;

public class Database {
    private final Scanner scanner = new Scanner(System.in);
    private Connection connection = null;

    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizsystem", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPerson() throws SQLException {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into people values(?,?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void addTeam() throws SQLException {
        System.out.println("Enter team name:");
        String name = scanner.next();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into teams values(?)");
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void addStudent() throws SQLException {
        System.out.println("Enter student name:");
        String student_name = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into people values(?,?)");
        preparedStatement.setString(1, student_name);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        System.out.println("Enter team name:");
        String team_name = scanner.next();
        preparedStatement = connection.prepareStatement("insert into students_to_teams values(?,?)");
        preparedStatement.setString(1, team_name);
        preparedStatement.setString(2, student_name);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void addQuiz() throws SQLException {
        System.out.println("Enter topic:");
        String topic = scanner.next();
        System.out.println("Enter allowed time");
        int time = scanner.nextInt();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into quizzes(topic,time) values(?,?)");
        preparedStatement.setString(1, topic);
        preparedStatement.setInt(2, time);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        int quiz_id = getQuizId(topic, time);
        System.out.println("Enter count of questions:");
        int count_of_questions = scanner.nextInt();
        for (int i = 0; i < count_of_questions; i++) {
            System.out.println("Enter question:");
            scanner.nextLine();
            String question = scanner.nextLine();
            System.out.println("Enter correct option:");
            String correct_option = scanner.next();
            preparedStatement = connection.prepareStatement("insert into questions values(?,?)");
            preparedStatement.setString(1, question);
            preparedStatement.setString(2, correct_option);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement = connection.prepareStatement("insert into questions_to_quizzes values(?,?)");
            preparedStatement.setInt(1, quiz_id);
            preparedStatement.setString(2, question);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Enter count of options:");
            int count_of_options = scanner.nextInt();
            for (int j = 0; j < count_of_options; j++) {
                System.out.println("Enter option:");
                String option = scanner.next();
                preparedStatement = connection.prepareStatement("insert into options_to_questions values(?,?)");
                preparedStatement.setString(1, question);
                preparedStatement.setString(2, option);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        }
    }

    public void allowQuizForTeam() throws SQLException {
        System.out.println("Enter quiz topic:");
        String topic = scanner.next();
        System.out.println("Enter quiz time:");
        int time = scanner.nextInt();
        int quiz_id = getQuizId(topic, time);
        System.out.println("Enter team name:");
        String team_name = scanner.next();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into teams_to_quizzes values(?,?)");
        preparedStatement.setInt(1, quiz_id);
        preparedStatement.setString(2, team_name);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public int getQuizId(String topic, int time) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select id from quizzes where topic='" + topic + "' and time=" + time);
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return 0;
    }

    public Question getQuestion(String question_name) throws SQLException {
        Question question = null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from questions where question='" + question_name + "'");
        if (resultSet.next()) {
            question = new Question(resultSet.getString("question"), resultSet.getString("correct_option"));
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("select * from options_to_questions where question='" + question_name + "'");
            while (resultSet1.next()) {
                question.getOptions().add(resultSet1.getString("option_name"));
            }
        }
        return question;
    }

    public Quiz getQuiz(int quiz_id) throws SQLException {
        Quiz quiz = null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from quizzes where id=" + quiz_id);
        if (resultSet.next()) {
            quiz = new Quiz(resultSet.getString("topic"), resultSet.getInt("time"));
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("select * from questions_to_quizzes where quiz_id=" + quiz_id);
            while (resultSet1.next()) {
                Question question = getQuestion(resultSet1.getString("question"));
                if (question != null) {
                    quiz.addQuestion(question);
                }
            }
            statement1.close();
            resultSet1.close();
            statement1 = connection.createStatement();
            resultSet1 = statement1.executeQuery("select * from teams_to_quizzes where quiz_id=" + quiz_id);
            while (resultSet1.next()) {
                quiz.addTeam(resultSet1.getString("team_name"));
            }
        }
        return quiz;
    }

    public Student getStudent(String student_name) throws SQLException {
        Student student = null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from people where username='" + student_name + "'");
        if (resultSet.next()) {
            student = new Student(resultSet.getString("username"), resultSet.getString("password"));
        }
        resultSet.close();
        statement.close();
        if (student != null) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from quizzes_to_students where student_name='" + student_name + "'");
            while (resultSet.next()) {
                Quiz quiz = getQuiz(resultSet.getInt("quiz_id"));
                if (quiz != null) {
                    student.getQuizzes().add(quiz);
                    student.getScores().add(resultSet.getInt("score"));
                }
            }
        }
        return student;
    }

    public Team getTeam(String team_name) throws SQLException {
        Team team = new Team(team_name);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from students_to_teams where team_name='" + team_name + "'");
        while (resultSet.next()) {
            Student student = getStudent(resultSet.getString("student_name"));
            if (student != null) {
                team.addStudent(student);
            }
        }
        return team;
    }

    public void takeQuiz(Student student) throws SQLException {
        if (student != null) {
            System.out.println("Enter topic of quiz:");
            String topic = scanner.next();
            System.out.println("Enter allowed time:");
            int time = scanner.nextInt();
            int quiz_id = getQuizId(topic, time);
            Quiz quiz = getQuiz(quiz_id);
            if (quiz != null) {
                student.takeQuiz(quiz);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into quizzes_to_students values(?,?,?)");
                preparedStatement.setString(1, student.getUsername());
                preparedStatement.setInt(2, quiz_id);
                preparedStatement.setInt(3, student.getScore(quiz));
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        }
    }

    public void competeTeams() throws SQLException {
        System.out.println("Enter quiz topic:");
        String topic = scanner.next();
        System.out.println("Enter quiz time:");
        int time = scanner.nextInt();
        int quiz_id = getQuizId(topic, time);
        Quiz quiz = getQuiz(quiz_id);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from teams_to_quizzes where quiz_id=" + quiz_id);
        double min = 10000000;
        String leaving_team = "";
        while (resultSet.next()) {
            String team_name = resultSet.getString("team_name");
            Team team = getTeam(team_name);
            if (team.calculateAverage(quiz) < min) {
                min = team.calculateAverage(quiz);
                leaving_team = team_name;
            }
        }
        System.out.println("Team " + leaving_team + " is leaving quiz " + topic + ". Average score is " + min);
        PreparedStatement preparedStatement = connection.prepareStatement("delete from teams_to_quizzes where quiz_id=? and team_name=?");
        preparedStatement.setInt(1, quiz_id);
        preparedStatement.setString(2, leaving_team);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public int signInToSystem(String username, String password) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from people where username='" + username + "' and password='" + password + "'");
        if (resultSet.next()) {
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("select * from students_to_teams where student_name='" + username + "'");
            if (resultSet1.next()) {
                return 1;
            } else {
                return 2;
            }
        }
        return 0;
    }
}
