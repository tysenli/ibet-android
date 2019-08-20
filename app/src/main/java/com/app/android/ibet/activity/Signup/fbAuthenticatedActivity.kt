package com.app.android.ibet.activity.Signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.app.android.ibet.R
import com.facebook.*
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.activity_fbauth.*


class fbAuthenticatedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fbauth)

        //var btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener(View.OnClickListener {
            // Logout
            if (AccessToken.getCurrentAccessToken() != null) {
                GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, GraphRequest.Callback {
                    AccessToken.setCurrentAccessToken(null)
                    LoginManager.getInstance().logOut()

                    finish()
                }).executeAsync()
            }
        })
    }


}
