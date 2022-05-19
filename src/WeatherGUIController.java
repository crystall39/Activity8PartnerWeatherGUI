import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class WeatherGUIController implements ActionListener
{
    private JFrame frame;
    private JLabel currentInfo;
    private JTextField weatherEntryField;
    private WeatherNetworkingClient client;
    private JPanel weatherPanel;

    public WeatherGUIController()
    {
        frame = new JFrame("Weather App");
        currentInfo = new JLabel();
        weatherEntryField = new JTextField();
        client = new WeatherNetworkingClient();
        weatherPanel = new JPanel();

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
        currentInfo.setOpaque(false);

        weatherPanel = new JPanel();
        weatherPanel.add(currentInfo);

        //------------------------------------------

        frame.add(logoWelcomePanel, BorderLayout.NORTH);
        frame.add(entryPanel, BorderLayout.CENTER);
        frame.add(weatherPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
        celsiusCheck.addActionListener(this);

        //------------------------------------------

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

        ImageIcon weatherIcon = new ImageIcon();

        try
        {
            BufferedImage image = ImageIO.read(new URL("https:" + forecast.getConditionIcon()));
            weatherIcon = new ImageIcon(image);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        Image imageData = weatherIcon.getImage();
        Image scaledImage = imageData.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        weatherIcon = new ImageIcon(scaledImage);
        JLabel pictureLabel = new JLabel(weatherIcon);

        if (selected)
        {
            weatherPanel.removeAll();
            weatherPanel.add(currentInfo);
            String currentText = ("Temperature: " + forecast.getTempC() + "   Condition: " + forecast.getCurrentCondition());
            currentInfo.setText(currentText);
            weatherPanel.add(pictureLabel);
        }
        else
        {
            weatherPanel.removeAll();
            weatherPanel.add(currentInfo);
            String currentText = ("Temperature: " + forecast.getTempF() + "   Condition: " + forecast.getCurrentCondition());
            currentInfo.setText(currentText);
            weatherPanel.add(pictureLabel);
        }

        if (text.equals("Clear"))
        {
            weatherEntryField.setText("");
            weatherPanel.removeAll();
            weatherPanel.add(currentInfo);
            currentInfo.setText("\nWaiting for an input...\n");
        }
    }
}