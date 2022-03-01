package com.prasadshirvandkar.fetchrewardscodingexercise.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.prasadshirvandkar.fetchrewardscodingexercise.Constants
import com.prasadshirvandkar.fetchrewardscodingexercise.R
import com.prasadshirvandkar.fetchrewardscodingexercise.ui.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(), "TAG")
                .commitNow()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("TAG", "Saved")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(false)
                title = getString(R.string.app_name)
            }
        } else {
            super.onBackPressed()
        }
    }
}