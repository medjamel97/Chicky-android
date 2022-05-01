package tn.esprit.chicky.ui.activities

import android.app.UiModeManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import tn.esprit.chicky.R

class SettingsActivity : AppCompatActivity() {

    var darkmodeSwitch: SwitchCompat? = null
    private var uiModeManager: UiModeManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager?;

        darkmodeSwitch = findViewById(R.id.darkmodeSwitch)
        darkmodeSwitch!!.setOnClickListener {
            if (darkmodeSwitch!!.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}