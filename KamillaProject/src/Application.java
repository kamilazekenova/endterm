import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Database database = new Database();

    private static void adminMenu() throws SQLException {
        boolean signIn = false;
        String admin_name = "";
        while (!signIn) {
            System.out.println("Admin name:");
            admin_name = scanner.next();
            System.out.println("Admin password:");
            String password = scanner.next();
            if (database.signInToSystem(admin_name, password) == 2) {
                signIn = true;
            }
        }
        int menu = 0;
        while (menu != 7) {
            System.out.println("////////////////////////////////////////////////////////////");
            System.out.println("Admin menu:");
            System.out.println("1) Add new admin");
            System.out.println("2) Add quiz");
            System.out.println("3) Add team");
            System.out.println("4) Add student");
            System.out.println("5) Add allowed team to quiz");
            System.out.println("6) Take results of quiz");
            System.out.println("7) Quit");
            menu = scanner.nextInt();
            switch (menu) {
                case 1 -> database.addPerson();
                case 2 -> database.addQuiz();
                case 3 -> database.addTeam();
                case 4 -> database.addStudent();
                case 5 -> database.allowQuizForTeam();
                case 6 -> database.competeTeams();
                case 7 -> System.out.println("Good bye, " + admin_name);
                default -> System.out.println("Unknown command...");
            }
        }
    }

    private static void studentMenu() throws SQLException {
        boolean signIn = false;
        Student student = null;
        while (!signIn) {
            System.out.println("Student name:");
            String student_name = scanner.next();
            System.out.println("Student password:");
            String password = scanner.next();
            if (database.signInToSystem(student_name, password) == 1) {
                signIn = true;
                student = database.getStudent(student_name);
            }
        }
        int menu = 0;
        while (menu != 3) {
            System.out.println("************************************************************");
            System.out.println("Student menu:");
            System.out.println("1) Take quiz");
            System.out.println("2) Get info");
            System.out.println("3) Quit");
            menu = scanner.nextInt();
            switch (menu) {
                case 1 -> database.takeQuiz(student);
                case 2 -> System.out.println(student);
                case 3 -> System.out.println("Good bye, " + student.getUsername());
                default -> System.out.println("Unknown command...");
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to quiz system!");
        System.out.println("Choose type: admin | student");
        String type = scanner.next();
        if (type.equals("admin")) {
            adminMenu();
        } else if (type.equals("student")) {
            studentMenu();
        } else {
            System.out.println("Sorry, you do not have a permission to the system!");
        }
    }
}
