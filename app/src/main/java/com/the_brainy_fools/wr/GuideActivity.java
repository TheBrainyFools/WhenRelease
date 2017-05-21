package com.the_brainy_fools.wr;

import android.content.Intent;
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

        AhoyOnboarderCard zero = new AhoyOnboarderCard("Hi!", "Welcome to the WhenRelease!!", R.drawable.ic_splash);
        AhoyOnboarderCard first = new AhoyOnboarderCard("Follow it!", "Follow interesting movies!", R.drawable.guide_follow);
        AhoyOnboarderCard second = new AhoyOnboarderCard("Remind!", "You will get notification when new movie comes out!", R.drawable.guide_reminder);
        AhoyOnboarderCard third = new AhoyOnboarderCard("Watched!", "Mark watched movies, and we will hide them!", R.drawable.guide_watched);
        AhoyOnboarderCard fourth = new AhoyOnboarderCard("Favorite!", "Mark your favorite movies, and we will show you similar!", R.drawable.guide_favourite);
        AhoyOnboarderCard fifth = new AhoyOnboarderCard("Rate it!", "Rate the film and the program - get more!", R.drawable.guide_rate);
        AhoyOnboarderCard sixth = new AhoyOnboarderCard("So...", "Other features you will see in the tooltips!\nEnjoy it!", R.drawable.guide_finish);

        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(zero);
        pages.add(first);
        pages.add(second);
        pages.add(third);
        pages.add(fourth);
        pages.add(fifth);
        pages.add(sixth);

        setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
