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

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        var options = new BaseOptions()
                .amend("platformName", "Android")
                .amend("appium:platformVersion", "12")
                .amend("appium:deviceName", "moto e32")
                .amend("appium:automationName", "UiAutomator2")
                .amend("appium:appPackage", "com.instagram.android")
                .amend("appium:appActivity", "com.instagram.android.activity.MainTabActivity")
                .amend("appium:udid", "ZD222BBYPS")
                .amend("appium:noReset", true)
                .amend("appium:setFullReset", false)
                .amend("appium:newCommandTimeout", 300)
                .amend("appium:ensureWebviewsHavePages", true)
                .amend("appium:nativeWebScreenshot", true)
                .amend("appium:connectHardwareKeyboard", true);

        try {
            AppiumDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));




            WebElement tabAvatar = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.instagram.android:id/tab_avatar")));
            tabAvatar.click();

            // com.instagram.android:id/row_profile_header_following_container

            WebElement profileContainer = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.instagram.android:id/row_profile_header_following_container")));
            profileContainer.click();

            // (//android.widget.LinearLayout[@resource-id="com.instagram.android:id/follow_list_content_container"])[2]
            WebElement followListButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//android.widget.LinearLayout[@resource-id=\"com.instagram.android:id/follow_list_content_container\"])[2]")));
            followListButton.click();

            // com.instagram.android:id/row_profile_header_followers_container
            WebElement followersListOfCR7 = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.instagram.android:id/row_profile_header_followers_container")));
            followersListOfCR7.click();

            // //androidx.recyclerview.widget.RecyclerView

            // androidx.recyclerview.widget.RecyclerView
            scrollAndClickAllButtonsInRecyclerView(driver);


            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scrollAndClickAllButtonsInRecyclerView(AppiumDriver driver) {
        WebElement recyclerView = driver.findElement(By.className("androidx.recyclerview.widget.RecyclerView"));

        List<WebElement> items = recyclerView.findElements(By.className("android.widget.LinearLayout"));

        System.out.println(items.size());

        scrollDown(driver);

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


}