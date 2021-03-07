import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Quiz {
    private String topic;
    private ArrayList<Question> questions;
    private Set<String> allowedTeams;
    private Integer time;

    public Quiz(String topic, Integer time) {
        this.topic = topic;
        questions = new ArrayList<>();
        allowedTeams = new HashSet<>();
        this.time = time;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void addTeam(String teamName) {
        allowedTeams.add(teamName);
    }

    public Set<String> getAllowedTeams() {
        return allowedTeams;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
