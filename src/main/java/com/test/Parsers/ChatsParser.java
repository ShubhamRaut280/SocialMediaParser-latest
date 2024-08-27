package com.test.Parsers;

import com.test.Utils.TapGesture;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class ChatsParser {
    AppiumDriver driver;
    WebDriverWait wait;

    public ChatsParser(AppiumDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private String backBtnId = "com.instagram.android:id/action_bar_button_back";

    public void storeScreenshots() {
        System.out.println("Inside storeScreenshots");

        // Navigate to the messages screen
        WebElement messenger = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("com.instagram.android:id/action_bar_inbox_button"))));
        TapGesture.singleTap(messenger, driver);


        try {
            List<WebElement> users =  driver.findElements(By.id("com.instagram.android:id/row_inbox_container"));
            for (WebElement user : users) {

                String username= user.findElement(By.id("com.instagram.android:id/row_inbox_username")).getText();
                TapGesture.singleTap(user, driver);

                while (true) {
                    // Take a screenshot of the current view
                    File screenshot = driver.getScreenshotAs(OutputType.FILE);
                    saveScreenshot(screenshot, username);

                    scrollUp();

                    if (isthisEnd()) {
                        break;
                    }
                }

                // Go back to the list of users
                TapGesture.singleTap(driver.findElement(By.id(backBtnId)), driver);
            }
        } catch (StaleElementReferenceException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void saveScreenshot(File screenshot, String username) {
        try {
            String sourceFolderPath = System.getProperty("user.home") + "/Desktop/chats/"+username+"/"; // Update this path as needed

            // Save the screenshot in the specified folder
            String filePath = sourceFolderPath + "/messages" + System.currentTimeMillis() + ".png";
            FileUtils.copyFile(screenshot, new File(filePath));
            System.out.println("Screenshot saved: " + filePath);
        } catch (IOException e) {
            System.out.println("Saving error: " + e.getMessage());
        }
    }


    public boolean scrollUp() {
        int startX = driver.manage().window().getSize().width / 2;
        int startY = (int) (driver.manage().window().getSize().height * 0.2); // Start near the top
        int endY = (int) (driver.manage().window().getSize().height * 0.8); // End near the bottom

        // Perform the swipe action
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(swipe));

        // Check if new items are loaded after scrolling
        List<WebElement> newListItems = driver.findElements(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.instagram.android:id/message_list\"]"));
        return !newListItems.isEmpty(); // Continue scrolling if more items are loaded
    }



    private boolean isthisEnd() {
        boolean ans = false;
        try {
            WebElement ele = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"com.instagram.android:id/user_avatar\"]"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
