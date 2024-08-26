package com.test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.Duration;

public class InstagramAutomationApp extends JFrame {

    private JButton startButton;
    private JTextArea logArea;
    private JProgressBar progressBar;
    private AppiumDriver driver;

    public InstagramAutomationApp() {
        // Set up the GUI
        setTitle("Instagram Automation");
        setSize(400, 300);
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

        // Add components to the frame
        add(startButton, BorderLayout.NORTH);
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
            var options = new BaseOptions()
                    .amend("platformName", "Android")
                    .amend("appium:platformVersion", "11")
                    .amend("appium:deviceName", "Redmi Note 8")
                    .amend("appium:automationName", "UiAutomator2")
                    .amend("appium:appPackage", "com.instagram.android")
                    .amend("appium:appActivity", "com.instagram.android.activity.MainTabActivity")
                    .amend("appium:udid", "179124d9")
                    .amend("appium:noReset", true)
                    .amend("appium:setFullReset", false)
                    .amend("appium:newCommandTimeout", 300);

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

            // Run your automation logic

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

    private void clickOnProfile(WebDriverWait wait, AppiumDriver driver) {
        // Your automation code
        WebElement tabAvatar = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.instagram.android:id/tab_avatar")));
        tabAvatar.click();
        logArea.append("Clicked on Profile.\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InstagramAutomationApp::new);
    }
}
