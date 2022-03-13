package com.example.ocbcassignment.base

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ocbcassignment.misc.Const
import com.example.ocbcassignment.misc.UnauthorizedAccessEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class BaseActivity : AppCompatActivity() {

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUnauthorizedEvent(event: UnauthorizedAccessEvent) {
        EventBus.getDefault().unregister(this)
        logout()
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
        val prefs = getSharedPreferences(Const.PREFERENCE_NAME, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(Const.LAST_ACTIVITY, javaClass.name)
        editor.apply()
    }

    private fun logout() {
        Intent().apply {
            setClassName("com.example.ocbcassignment",
                "com.example.ocbcassignment.presentation.auth.LoginActivity")
            getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE).edit().clear().apply()
            startActivity(this)
        }
        Toast.makeText(this, "Unauthorized", Toast.LENGTH_LONG).show()
        finishAffinity()
    }


}