import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;

class Question {
    String question;
    String[] options;
    int correctAnswerIndex;

    public Question(String question, String[] options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}

public class TimedQuizApp extends JFrame implements ActionListener {
    private final List<Question> fullQuestionPool;
    private List<Question> quizQuestions;
    private int currentIndex = 0;
    private int score = 0;

    private JLabel questionLabel, timerLabel;
    private JRadioButton[] optionsButtons = new JRadioButton[4];
    private ButtonGroup optionsGroup;
    private JButton nextButton;
    private Timer questionTimer;
    private int timeLeft = 15;

    private Image backgroundImage;

    public TimedQuizApp() {
        setTitle("Quiz App");
        setSize(600, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Load background image
        backgroundImage = new ImageIcon("image2.png").getImage(); // Replace with your image path

        // Set custom panel with background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // Load questions from CSV
        fullQuestionPool = loadQuestionsFromCSV("questions.csv");
        if (fullQuestionPool.size() < 10) {
            JOptionPane.showMessageDialog(this, "Not enough questions in CSV!");
            System.exit(1);
        }

        Collections.shuffle(fullQuestionPool);
        quizQuestions = new ArrayList<>(fullQuestionPool.subList(0, 10));

        // UI setup
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        questionLabel = new JLabel("Question", JLabel.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setOpaque(false);
        topPanel.add(questionLabel, BorderLayout.CENTER);

        timerLabel = new JLabel("Time: 15", JLabel.RIGHT);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setOpaque(false);
        topPanel.add(timerLabel, BorderLayout.EAST);

        backgroundPanel.add(topPanel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        optionsPanel.setOpaque(false);

        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionsButtons[i] = new JRadioButton();
            optionsButtons[i].setOpaque(false);
            optionsGroup.add(optionsButtons[i]);
            optionsPanel.add(optionsButtons[i]);
        }
        backgroundPanel.add(optionsPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        backgroundPanel.add(nextButton, BorderLayout.SOUTH);

        // Timer setup
        questionTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);
            if (timeLeft <= 0) {
                questionTimer.stop();
                handleTimeout();
            }
        });

        displayQuestion();
        setVisible(true);
    }

    private List<Question> loadQuestionsFromCSV(String filePath) {
        List<Question> questions = new ArrayList<>();
        try (Scanner scanner = new Scanner(new java.io.File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",", -1);
                if (parts.length != 6) continue;

                String questionText = parts[0];
                String[] options = Arrays.copyOfRange(parts, 1, 5);
                int correctIndex = Integer.parseInt(parts[5]);

                questions.add(new Question(questionText, options, correctIndex));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV: " + e.getMessage());
        }
        return questions;
    }

    private void displayQuestion() {
        if (currentIndex >= quizQuestions.size()) {
            showResult();
            return;
        }

        timeLeft = 15;
        timerLabel.setText("Time: " + timeLeft);
        questionTimer.start();

        Question q = quizQuestions.get(currentIndex);
        questionLabel.setText("Q" + (currentIndex + 1) + ": " + q.question);
        for (int i = 0; i < 4; i++) {
            optionsButtons[i].setText(q.options[i]);
            optionsButtons[i].setSelected(false);
        }
    }

    private void handleTimeout() {
        JOptionPane.showMessageDialog(this, "Time's up!");
        currentIndex++;
        displayQuestion();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        questionTimer.stop();
        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (optionsButtons[i].isSelected()) {
                selected = i;
                break;
            }
        }

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "No answer selected. Moving on...");
        } else if (selected == quizQuestions.get(currentIndex).correctAnswerIndex) {
            score++;
        }

        currentIndex++;
        displayQuestion();
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this, "Quiz Over! Your score: " + score + "/" + quizQuestions.size());
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TimedQuizApp::new);
    }
}
