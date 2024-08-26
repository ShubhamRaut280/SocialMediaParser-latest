package com.test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InstagramAutomationApp::new);
    }



//    public static void main(String[] args) {
//        var options = new BaseOptions()
//                .amend("platformName", "Android")
//                .amend("appium:platformVersion", "11")
//                .amend("appium:deviceName", "Redmi Note 8")
//                .amend("appium:automationName", "UiAutomator2")
//                .amend("appium:appPackage", "com.instagram.android")
//                .amend("appium:appActivity", "com.instagram.android.activity.MainTabActivity")
//                .amend("appium:udid", "179124d9")
//                .amend("appium:noReset", true)
//                .amend("appium:setFullReset", false)
//                .amend("appium:newCommandTimeout", 300)
//                .amend("appium:ensureWebviewsHavePages", true)
//                .amend("appium:nativeWebScreenshot", true)
//                .amend("appium:connectHardwareKeyboard", true);
//
//        try {
//
//            AppiumDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
//
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//
//
////            clickonProflie(wait, driver);
//            ChatsParser chatsParser = new ChatsParser(driver, wait);
//            chatsParser.storeScreenshots();
//
//
//
//
//            driver.quit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private static void clickonProflie(WebDriverWait wait, AppiumDriver driver) {
        // click on profile
        WebElement tabAvatar = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.instagram.android:id/tab_avatar")));
        tabAvatar.click();

        goToFollowers(wait, driver);
    }

    private static void goToFollowers(WebDriverWait wait, AppiumDriver driver) {
        // com.instagram.android:id/row_profile_header_following_container

        // go to follwing
        WebElement profileContainer = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.instagram.android:id/row_profile_header_following_container")));
        profileContainer.click();


        // click on ronaldo
        // (//android.widget.LinearLayout[@resource-id="com.instagram.android:id/follow_list_content_container"])[2]
        WebElement followListButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//android.widget.LinearLayout[@resource-id=\"com.instagram.android:id/follow_list_content_container\"])[1]")));
        followListButton.click();

        goToFollowing(wait, driver);


        // go to his followers
        // com.instagram.android:id/row_profile_header_followers_container
        WebElement followersListOfCR7 = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.instagram.android:id/row_profile_header_followers_container")));
        followersListOfCR7.click();

        // //androidx.recyclerview.widget.RecyclerView

        // androidx.recyclerview.widget.RecyclerView
        scrollAndClickAllButtonsInRecyclerView(driver);
    }

    public static void scrollAndClickAllButtonsInRecyclerView(AppiumDriver driver) {
        WebElement recyclerView = driver.findElement(By.className("androidx.recyclerview.widget.RecyclerView"));
        int i = 1;
        while(true){
            try {
                WebElement dialog = driver.findElement(By.xpath("//android.view.ViewGroup[@resource-id=\"com.instagram.android:id/dialog_container\"]"));
                    dialog.findElement(By.xpath("//android.widget.LinearLayout[@resource-id=\"com.instagram.android:id/sticky_footer\"]")).findElement(By.xpath("//android.widget.Button[@resource-id=\"com.instagram.android:id/primary_button\"]")).click();
            } catch (Exception e) {
                System.out.println("error : "+ e.getMessage());
            }
            try {
                WebElement item = recyclerView.findElement(By.xpath("(//android.widget.LinearLayout[@resource-id=\"com.instagram.android:id/follow_list_container\"])["+i+"]"));
                WebElement followBtn = item.findElement(By.id("com.instagram.android:id/follow_list_row_large_follow_button"));
                followBtn.click();
                i++;
            } catch (Exception e) {
                System.out.println("follow errro : "+ e.getMessage());
            }
        }


    }

    public static boolean scrollDown(AppiumDriver driver) {
        int startX = driver.manage().window().getSize().width / 2;
        int startY = (int) (driver.manage().window().getSize().height * 0.8);
        int endY = (int) (driver.manage().window().getSize().height * 0.2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(swipe));

        List<WebElement> newListItems = driver.findElements(By.xpath("//android.widget.ListView/android.widget.LinearLayout"));
        return !newListItems.isEmpty();
    }

    private static void goToFollowing(WebDriverWait wait, AppiumDriver driver) {
        // com.instagram.android:id/row_profile_header_following_container

        // go to follwing
        WebElement profileContainer = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.instagram.android:id/row_profile_header_following_container")));
        profileContainer.click();


        // click on ronaldo
        // (//android.widget.LinearLayout[@resource-id="com.instagram.android:id/follow_list_content_container"])[2]
        WebElement list = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//android.widget.ListView[@resource-id=\"android:id/list\"]"
        )));
        int i = 1;
        while(true){
            try {
                WebElement dialog = driver.findElement(By.xpath("//android.view.ViewGroup[@resource-id=\"com.instagram.android:id/dialog_container\"]"));
                dialog.findElement(By.xpath("//android.widget.LinearLayout[@resource-id=\"com.instagram.android:id/sticky_footer\"]")).findElement(By.xpath("//android.widget.Button[@resource-id=\"com.instagram.android:id/primary_button\"]")).click();
            } catch (Exception e) {
                System.out.println("error : "+ e.getMessage());
            }
            try {
                WebElement item = list.findElement(By.xpath("//android.widget.ListView[@resource-id="+"android:id/list"+"]/android.widget.LinearLayout["+i+"]"));
                WebElement followBtn = item.findElement(By.id("com.instagram.android:id/follow_list_row_large_follow_button"));
                followBtn.click();
                i++;
            } catch (Exception e) {
                System.out.println("follow errro : "+ e.getMessage());
            }
        }
    }


}