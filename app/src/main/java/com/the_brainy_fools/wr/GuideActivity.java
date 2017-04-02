package com.the_brainy_fools.wr;

import android.os.Bundle;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AhoyOnboarderActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setColorBackground(R.color.colorPrimary);
        setFinishButtonTitle("Get them! :)");

        AhoyOnboarderCard zero = new AhoyOnboarderCard("Hi!", "Welcome to WhenRelease!");

        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(zero);

        setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {

    }
}
