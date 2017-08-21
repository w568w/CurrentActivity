package ml.w568w.currentactivity

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import ml.qingsu.fuckview.GlobalPopupWindow
import ml.qingsu.fuckview.view_dumper.DumperService


class FloatWindow(activity: Activity) : GlobalPopupWindow(activity) {

    override fun onHide() {
        appContext.unregisterReceiver(mReceive)
    }

    lateinit var mText: TextView
    lateinit var mReceive: BroadcastReceiver

    companion object {
        @JvmStatic val ACTION = "float_window"
        @JvmStatic val ACTION_CLOSE = "float_window_close"
    }

    override fun onShow() {
        mReceive = receiver()
        val intentF=IntentFilter()
        intentF.addAction(ACTION)
        intentF.addAction(ACTION_CLOSE)
        appContext.registerReceiver(mReceive,intentF)
    }

    override fun onCreateView(context: Context): View {
        val ll = LayoutInflater.from(context)
        val layout = ll.inflate(R.layout.float_view, null)
        mText = layout.findViewById(R.id.text) as TextView
        return layout
    }

    override val gravity: Int
        get() {
            return Gravity.TOP
        }

    inner class receiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action == ACTION) {
                try {
                    mText.text = "${DumperService.getInstance().activityName}\n${DumperService.getInstance().pkg}"
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (intent.action == ACTION_CLOSE) hide()

        }

    }
}