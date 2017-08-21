package ml.w568w.currentactivity

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import kotlinx.android.synthetic.main.activity_main.*
import ml.qingsu.fuckview.view_dumper.DumperService

class MainActivity : Activity() {
    lateinit var mFloat: FloatWindow
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFloat = FloatWindow(this)
        open.setOnClickListener {
            if (DumperService.getInstance() == null) {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                return@setOnClickListener
            }
            mFloat.show()
        }
        close.setOnClickListener {
            sendBroadcast(Intent(FloatWindow.ACTION_CLOSE))
        }
    }

}
