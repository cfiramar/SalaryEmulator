import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Graphics extends JFrame {
    private static final String
            WINDOW_NAME = "Salary Emulator",
            FORMAT = "עם %d לקוחות, המשלמים ₪%.0f לטיפול - המשכורת היא: \t%,.2f";
    private static final Font bodyFont = new Font("Assistant", Font.PLAIN, 12);
    private static final Font headerFont = new Font("Assistant", Font.BOLD, 12);

    private final Map<Prompts, JTextField> fieldByPrompt = new HashMap<>();
    private static final ArrayList<Prompts>
            incomePrompts = new ArrayList<>(Arrays.asList
            (Prompts.CUR_WAGE,
                    Prompts.WANTED_WAGE,
                    Prompts.CUR_NUM_OF_CLIENTS,
                    Prompts.WANTED_NUM_OF_CLIENTS)),
            billsPrompts = new ArrayList<>(Arrays.asList
                    (Prompts.ELECTRICITY,
                            Prompts.WATER,
                            Prompts.INTERNET,
                            Prompts.RATES)),
            expensesPrompts = new ArrayList<>(Arrays.asList
                    (Prompts.RENT,
                            Prompts.PRODUCTS,
                            Prompts.EDUCATION,
                            Prompts.ADVISION,
                            Prompts.RETIREMENT_FEE)),
            offsetPrompts = new ArrayList<>(Arrays.asList
                    (Prompts.TAX_FACTOR,
                            Prompts.PAID_CANCELS_AMOUNT,
                            Prompts.SELF_CANCELS_AMOUNT,
                            Prompts.CANCELING_FEE_FACTOR));

    private Calculator calculator;
    private final JLabel curSalaryLabel = new JLabel(""),
            betterWageSalaryLabel = new JLabel(""),
            moreClientsSalaryLabel = new JLabel(""),
            maxSalaryLabel = new JLabel("");

    public Graphics() {
        super(WINDOW_NAME);
        // Set window size
        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (screenSize.width * 0.35), (int) (screenSize.height * 0.9));
        setLayout(new BorderLayout());
//        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        super.setLocationRelativeTo(null);
        inputPanel();
        resultPanel();
        // addSlider(); // TODO
        buttonPanel();
    }

    private void addSlider() {
        JPanel sliderPanel = new JPanel();
        JSlider slider = new JSlider(1, 10);
        sliderPanel.add(slider);

        Label title = new Label("slider");
        sliderPanel.add(title);

        add(sliderPanel);
    }

//    private void addDivider() {
//        var divider = new JPanel();
//        divider.setBackground(Color.BLUE);
//        divider.setPreferredSize(new Dimension(100, 1));
//        add(divider, BorderLayout.CENTER);
//    }

    private JPanel createPanel(DataTypes dataTypes) {
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        Label title = new Label(dataTypes.title);
        title.setFont(headerFont);
        containerPanel.add(title);

        ArrayList<Prompts> prompts;
        switch (dataTypes) {
            case INCOME:
                prompts = incomePrompts;
                break;
            case BILLS:
                prompts = billsPrompts;
                break;
            case EXPENSES:
                prompts = expensesPrompts;
                break;
            default: // OFFSET
                prompts = offsetPrompts;
        }

        for (Prompts prompt : prompts) {
            JPanel panel = new JPanel();
            var field = new JTextField(10);
            fieldByPrompt.put(prompt, field);
            panel.add(field);
            var temp_label = new JLabel(prompt.label);
            temp_label.setFont(bodyFont);
            panel.add(temp_label);
            containerPanel.add(panel);
        }

        add(containerPanel, BorderLayout.NORTH);
        return containerPanel;
    }

    private void inputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        for (var dataType : DataTypes.values()) {
            inputPanel.add(createPanel(dataType));
//            addDivider();
        }

        add(inputPanel, BorderLayout.NORTH);
    }

    private void resultPanel() {
        JPanel resultPanel = new JPanel();
        resultPanel.add(curSalaryLabel);
        resultPanel.add(betterWageSalaryLabel);
        resultPanel.add(moreClientsSalaryLabel);
        resultPanel.add(maxSalaryLabel);

        add(resultPanel, BorderLayout.CENTER);
    }

    private boolean areInputsValid() {
        for (var field : fieldByPrompt.values()) {
            var text = field.getText();
            if (text.trim().isEmpty()) {
                JOptionPane.showMessageDialog(field,
                        "אין להשאיר שדות ריקים", "שגיאה", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private void buttonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton calculateButton = new JButton("חשב");
        calculateButton.addActionListener(e -> {
            if (areInputsValid()) {
                calculate();
                var curSalaryText = String.format(FORMAT, calculator.curClientAmount, calculator.curWage,
                        calculator.curSalary);
                var betterWageSalaryText = String.format(FORMAT, calculator.curClientAmount, calculator.wantedWage,
                        calculator.betterWageSalary);
                var moreClientsSalaryText = String.format(FORMAT, calculator.wantedClientAmount,
                        calculator.curWage,
                        calculator.moreClientsSalary);
                var maxSalaryText = String.format(FORMAT, calculator.wantedClientAmount, calculator.wantedWage,
                        calculator.maxSalary);

                curSalaryLabel.setText(curSalaryText);
                betterWageSalaryLabel.setText(betterWageSalaryText);
                moreClientsSalaryLabel.setText(moreClientsSalaryText);
                maxSalaryLabel.setText(maxSalaryText);

            }
            // TODO: add more salaries, change "XX"
        });
        buttonPanel.add(calculateButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void calculate() {
        calculator = new Calculator(fieldByPrompt);
    }
}
