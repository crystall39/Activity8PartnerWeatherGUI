import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// add the photo

public class WeatherGUIController implements ActionListener
{
    private JFrame frame;
    private JTextArea currentInfo;
    private JTextField weatherEntryField;
    private WeatherNetworkingClient client;

    public WeatherGUIController()
    {
        frame = new JFrame("Weather App");
        currentInfo = new JTextArea(2, 35);
        weatherEntryField = new JTextField();
        client = new WeatherNetworkingClient();

        setupGui();
    }

    private void setupGui()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Current Weather");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.pink);

        JPanel logoWelcomePanel = new JPanel();
        logoWelcomePanel.add(welcomeLabel);

        //------------------------------------------

        JLabel weatherLabel = new JLabel("Enter Zip Code: ");
        weatherEntryField = new JTextField(5);
        JButton submitButton = new JButton("Submit");
        JButton clearButton = new JButton("Clear");
        JCheckBox celsiusCheck = new JCheckBox("Show Celsius");

        JPanel entryPanel = new JPanel();
        entryPanel.add(weatherLabel);
        entryPanel.add(weatherEntryField);
        entryPanel.add(submitButton);
        entryPanel.add(clearButton);
        entryPanel.add(celsiusCheck);

        //------------------------------------------

        currentInfo.setText("\nWaiting for an input...\n");
        currentInfo.setFont(new Font("Arial", Font.BOLD, 10));
        currentInfo.setWrapStyleWord(true);
        currentInfo.setLineWrap(true);
        currentInfo.setOpaque(false);

        JPanel weatherPanel = new JPanel();
        weatherPanel.add(currentInfo);

        //------------------------------------------

        frame.add(logoWelcomePanel, BorderLayout.NORTH);
        frame.add(entryPanel, BorderLayout.CENTER);
        frame.add(weatherPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
        celsiusCheck.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        JButton button = new JButton();
        JCheckBox checkBox = new JCheckBox();
        String text = "";
        boolean selected = false;
        try
        {
            button = (JButton) (e.getSource());
            text = button.getText();
        }
        catch(Exception i)
        {
            checkBox = (JCheckBox) (e.getSource());
            selected = checkBox.isSelected();
        }



        String zipcode = weatherEntryField.getText();
        Weather forecast = client.getCurrentForecast(zipcode);

        if (selected)
        {
            String currentText = ("Temperature: " + forecast.getTempC() + "   Condition: " + forecast.getCurrentCondition());
            currentInfo.setText(currentText);
        }
        else
        {
            String currentText = ("Temperature: " + forecast.getTempF() + "   Condition: " + forecast.getCurrentCondition());
            currentInfo.setText(currentText);
        }

        if (text.equals("Clear"))
        {
            weatherEntryField.setText("");
            currentInfo.setText("\nWaiting for an input...\n");
        }


    }
}