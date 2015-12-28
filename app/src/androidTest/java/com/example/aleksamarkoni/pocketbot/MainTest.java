package com.example.aleksamarkoni.pocketbot;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by aleksamarkoni on 8.12.15..
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class MainTest {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE
            = "com.droidhang.ph";

    private UiDevice mDevice;
    private Context context;

    @Before
    public void beforeTheTest() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void TestToClick() {

        while (true) {
            restartTheTimer();

            // do the missions
            for (int food = 0; food < 5; food++) {
                closeTheGame();
                startTheGame();
                // start the level
                mDevice.click(1280, 680);
                // wait for the level to start
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // remove the dialogs
                for (int i = 0; i < 5; i++) {
                    mDevice.click(100, 100);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // wait for the pause to pass

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // remove the second set of dialogs
                for (int i = 0; i < 3; i++) {
                    mDevice.click(100, 100);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // wait for the group to set up on the postion
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // move the healer
                mDevice.drag(600, 700, 1200, 200, 20);
                // move the hunter
                mDevice.drag(400, 900, 1200, 400, 20);
                // move the wizard
                mDevice.drag(900, 950, 1200, 300, 20);


                // wait for the group to clear the level
                try {
                    Thread.sleep(120 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // close the ad
                //mDevice.pressBack();
                // wait for the add to finish
                //try {
                //    Thread.sleep(2000);
                //} catch (InterruptedException e) {
                //   e.printStackTrace();
                //}
                // click to return to the main menu
                //mDevice.click(500, 500);

                //try {
                //    Thread.sleep(10 * 1000);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}

                // close the fucking mail thing ffs
                //mDevice.click(150, 100);

                //try {
                //    Thread.sleep(1000);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
            }
        }
    }

    private void restartTheTimer() {


        mDevice.pressHome();

        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());

        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        mDevice.wait(Until.hasObject(By.pkg(Settings.ACTION_DATE_SETTINGS).depth(0)), LAUNCH_TIMEOUT);

        try {
            mDevice.findObject(new UiSelector().text("Set date")).click();
            Thread.sleep(1000);
            mDevice.findObject(new UiSelector().description("Increase day")).click();
            Thread.sleep(1000);
            mDevice.findObject(new UiSelector().text("OK")).click();
            Thread.sleep(1000);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void startTheGame() {
        mDevice.pressHome();

        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());

        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);

        //give time to the game to start
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // close the commercial
        mDevice.pressBack();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // start the game
        mDevice.click(2300, 1300);
        try {
            Thread.sleep(7 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // close the offer
        mDevice.click(150, 100);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void closeTheGame() {

        try {
            mDevice.pressRecentApps();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            mDevice.findObject(new UiSelector().description("Close all")).click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
