package com.nikitamaslov.loginpresentation.activity

import android.os.Bundle
import com.nikitamaslov.core.activity.InjectingActivity
import com.nikitamaslov.loginpresentation.R

class LoginActivity : InjectingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
