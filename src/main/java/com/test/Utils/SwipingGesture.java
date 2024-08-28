package com.test.Utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

public class SwipingGesture {

    public static void swipeLeft(AppiumDriver driver){

        Dimension dimension = driver.manage().window().getSize();

        int startX = (int) (dimension.getWidth() * 0.6);
        int endX = (int) (dimension.getWidth() * 0.3);

        int y = dimension.getHeight() / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence sequence = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, y))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(sequence));
    }

    public static void swipeRight(AppiumDriver driver){

        Dimension dimension = driver.manage().window().getSize();

        int startX = (int) (dimension.getWidth() * 0.3);
        int endX = (int) (dimension.getWidth() * 0.6);

        int y = dimension.getHeight() / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence sequence = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, y))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(sequence));
    }

    public static void swipeDown(AppiumDriver driver){

        Dimension dimension = driver.manage().window().getSize();

        int x = dimension.getWidth() / 2;

        int startY = (int) (dimension.getHeight() * 0.3);
        int endY = (int) (dimension.getHeight() * 0.6);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence sequence = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), new Point(x, startY)))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(200), PointerInput.Origin.viewport(), new Point(x, endY)))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(sequence));
    }

    public static void swipeUp(AppiumDriver driver){

        Dimension dimension = driver.manage().window().getSize();

        int x = dimension.getWidth() / 2;
        int startY = (int) (dimension.getHeight() * 0.6);
        int endY = (int) (dimension.getHeight() * 0.3);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence sequence = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), new Point(x, startY)))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(200), PointerInput.Origin.viewport(), new Point(x, endY)))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(sequence));
    }
}
