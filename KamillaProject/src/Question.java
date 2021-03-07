import java.util.ArrayList;

public class Question {
    private String text;
    private ArrayList<String> options;
    private String correctOption;

    public Question(String text, String correctOption) {
        this.text = text;
        this.correctOption = correctOption;
        options = new ArrayList<>();
    }

    public void addOption(String option) {
        options.add(option);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public String getCorrectOption() {
        return correctOption;
    }
}
