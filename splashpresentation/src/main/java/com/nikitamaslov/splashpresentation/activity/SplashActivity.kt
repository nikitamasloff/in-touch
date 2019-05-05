package com.nikitamaslov.splashpresentation.activity

import android.os.Bundle
import com.nikitamaslov.core.activity.InjectingActivity
import com.nikitamaslov.splashpresentation.R

class SplashActivity : InjectingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}
