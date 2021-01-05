package com.saifymatteo.mykampusradiounofficial

import android.app.Notification
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    // media player declaration
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize buttons
        val buttonPlay: ImageButton = findViewById(R.id.button_play)
        val buttonExit: Button = findViewById(R.id.button_exit)
        val textDisplay: TextView = findViewById(R.id.text_playing)
        val buttonMenu: ImageButton = findViewById(R.id.button_menu)

        // audio URL reference
        mediaPlayer = MediaPlayer.create(this@MainActivity, Uri.parse("https://usa13.fastcast4u.com/proxy/voicespl?mp=/1"))

        // play pause button
        buttonPlay.setOnClickListener {
            if (mediaPlayer?.isPlaying!!) {
                mediaPlayer?.pause()
                buttonPlay.setImageResource(R.drawable.ic_button_play)
                textDisplay.text = getString(R.string.text_on_pause)
            } else {
                mediaPlayer?.start()
                buttonPlay.setImageResource(R.drawable.ic_button_pause)
                textDisplay.text = getString(R.string.text_on_playing)
            }
        }

        // Exit button function
        buttonExit.setOnClickListener {
            exitProcess(0)
        }

        /* call checkTheme function */
        checkTheme()

        /* Complete menu button */
        buttonMenu.setOnClickListener {
            val popupMenu = PopupMenu(this, buttonMenu)
            popupMenu.menuInflater.inflate(R.menu.popup, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_theme -> {
                        chooseThemeDialog()
                        mediaPlayer?.pause()
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

    /* function to check user saved theme */
    private fun checkTheme() {
        when (UserPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }

    /* function to let user choose their theme */
    private fun chooseThemeDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_theme_text))
        val styles = arrayOf("Light", "Dark", "System Default")
        val checkedItem = UserPreferences(this).darkMode
        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->
            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    UserPreferences(this).darkMode = 0
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    UserPreferences(this).darkMode = 1
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    UserPreferences(this).darkMode = 2
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}