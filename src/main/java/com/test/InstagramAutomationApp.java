package com.test;

import com.test.Parsers.ChatsParser;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.test.Utils.Utils.addDelay;

public class InstagramAutomationApp extends JFrame {

    private JButton startButton;
    private JTextArea logArea;
    private JProgressBar progressBar;
    private JComboBox<String> deviceDropdown;
    private AppiumDriver driver;

    public InstagramAutomationApp() {
        // Set up the GUI
        setTitle("Instagram Automation");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        startButton = new JButton("Start Automation");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setBackground(Color.BLUE);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);

        logArea = new JTextArea(10, 30);
        logArea.setEditable(false);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false); // Initially not running
        progressBar.setVisible(false); // Hidden initially

        // Fetch connected devices and populate the dropdown
        deviceDropdown = new JComboBox<>(getConnectedDevices().toArray(new String[0]));
        deviceDropdown.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(deviceDropdown, BorderLayout.WEST);
        topPanel.add(startButton, BorderLayout.EAST);

        // Add components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(logArea), BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);

        // Set the button action
        startButton.addActionListener(e -> new Thread(this::startAutomation).start());

        // Display the frame
        setVisible(true);
    }

    private void startAutomation() {
        logArea.append("Starting automation...\n");
        progressBar.setVisible(true); // Show progress bar
        progressBar.setIndeterminate(true); // Start indefinite loading

        try {
            // Get the selected device ID
            String selectedDevice = (String) deviceDropdown.getSelectedItem();
            if (selectedDevice == null) {
                logArea.append("No device selected. Please connect an Android device.\n");
                return;
            }
            String deviceId = selectedDevice.split(" ")[0]; // Extract the device ID from the selection

            var options = new BaseOptions()
                    .amend("platformName", "Android")
                    .amend("appium:platformVersion", "11")
                    .amend("appium:deviceName", "Redmi Note 8")
                    .amend("appium:automationName", "UiAutomator2")
                    .amend("appium:appPackage", "com.instagram.android")
                    .amend("appium:appActivity", "com.instagram.android.activity.MainTabActivity")
                    .amend("appium:udid", deviceId) // Pass the selected device ID
                    .amend("appium:noReset", true)
                    .amend("appium:setFullReset", false)
                    .amend("appium:newCommandTimeout", 300);

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Run your automation logic

            addDelay(5000);

            ChatsParser chatsParser = new ChatsParser(driver, wait);
            chatsParser.storeScreenshots();

            logArea.append("Automation completed.\n");

            driver.quit();
        } catch (Exception ex) {
            logArea.append("Error: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        } finally {
            progressBar.setIndeterminate(false); // Stop indefinite loading
            progressBar.setVisible(false); // Hide progress bar
        }
    }

    private List<String> getConnectedDevices() {
        List<String> devices = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec("adb devices");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // Read the output and filter for device lines
            devices = reader.lines()
                    .filter(line -> line.endsWith("device"))
                    .map(line -> line.split("\t")[0] + " (Connected Device)") // Extract device ID and add label
                    .collect(Collectors.toList());

            if (devices.isEmpty()) {
                devices.add("No devices found. Please connect a device.");
            }

        } catch (Exception e) {
            logArea.append("Error fetching devices: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
        return devices;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(InstagramAutomationApp::new);
    }


}
