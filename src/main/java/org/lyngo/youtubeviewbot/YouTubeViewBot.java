/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lyngo.youtubeviewbot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 *
 * @author Ly Ngo
 */
public class YouTubeViewBot {

    private static WebDriver driver;
    private static final String DRIVER_PATH = "chromedriver.exe";
    private static final String[] VIDEO_URLS = {
        "https://www.youtube.com/watch?v=k8Y6ZTjmCXs", // red lights skz
        "https://www.youtube.com/watch?v=7BPwaTGqcQY", // ice.cream hyunjin
        "https://www.youtube.com/watch?v=jzorNBLDIEI", // little star hyunjin
        "https://www.youtube.com/watch?v=KagzGPwDR9w", // cause i like you felix changbin
        "https://www.youtube.com/watch?v=jYSlpC6Ud2A", // case 143 skz
        "https://www.youtube.com/watch?v=Smgql_DESn8", // surfin felix lee know changbin
        "https://www.youtube.com/watch?v=APB-AKpnKT4", // maniac skz
        "https://www.youtube.com/watch?v=ly3HDyks4Bs", // thunderouse skz
        "https://www.youtube.com/watch?v=7xA7ObLAq5k", // no problem nayeon felix
        "https://www.youtube.com/watch?v=TQTlCHxyuu8", // god's menu skz
        "https://www.youtube.com/watch?v=wBr2O6_es2U", // let me love you wayv
        "https://www.youtube.com/watch?v=5Tt18iLaIYs", // miracle wayv
        "https://www.youtube.com/watch?v=dtV1I9WtQuo" // chill skz
    };
    private static final int VIDEO_URLS_LENGTH = VIDEO_URLS.length;
    private static final int COUNTER = 15;

    public static void main(String[] args) throws IOException, MalformedURLException, InterruptedException {
        setUpDriver();
        openYoutube();
        closeDriver();
    }

    public static void setUpDriver() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--incognito");
        opt.addArguments("--lang=en-AU");
        driver = new ChromeDriver(opt);
        driver.manage().window().maximize();
    }

    public static void openYoutube() throws MalformedURLException, IOException, InterruptedException {
        for (int i = 1; i < COUNTER; i++) {
            System.out.println("Interation: " + i);
            int randomVideoUrl = (int) (Math.random() * VIDEO_URLS_LENGTH);
            driver.get(VIDEO_URLS[randomVideoUrl]);
            //press space to play video
            WebElement webElement = driver.findElement(By.tagName("body"));
            if (i == 1) {
                webElement.sendKeys(Keys.SPACE);
            }

            //skip advertisement after 10 second if any
            Thread.sleep(10000);
            try {
                WebElement btnSkipAdvertisement = driver.findElement(By.cssSelector(".ytp-ad-skip-button.ytp-button"));
                btnSkipAdvertisement.click();
            } catch (Exception e) {
            }

            //play the video for 200 second, then load another video in the list
            Thread.sleep(200000);
        }
    }

    public static int getVideoDuration(String url) {
        int size = 0;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            size = conn.getContentLength();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static void closeDriver() {
        driver.quit();
    }
}
