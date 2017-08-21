package ml.qingsu.fuckview

import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.view.View
import android.view.WindowManager

import android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL

/**
 * Created by w568w on 2017-7-12.
 */


abstract class GlobalPopupWindow(activity: Activity) {
    internal var appContext: Context
    private var mWindowManager: WindowManager? = null
    internal var params: WindowManager.LayoutParams
    private val view: View
    private var isShown = false

    protected abstract fun onCreateView(context: Context): View

    protected abstract val gravity: Int

    protected abstract fun onHide()
    protected var  activity: Activity

    init {
        this.appContext = activity.applicationContext
        this.activity = activity
        mWindowManager = appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        params = WindowManager.LayoutParams()
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        params.flags = FLAG_NOT_TOUCH_MODAL or FLAG_NOT_FOCUSABLE
        params.format = PixelFormat.TRANSLUCENT
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.gravity = gravity
        view = onCreateView(appContext)
    }

    fun setFocusable(focusable: Boolean) {
        params.flags = if (focusable) FLAG_NOT_TOUCH_MODAL else FLAG_NOT_FOCUSABLE or FLAG_NOT_TOUCH_MODAL
        try {
            if (isShown)
                mWindowManager!!.updateViewLayout(view, params)
        } catch (ignored: Exception) {
        }

    }

    fun updateLayout() {
        params.gravity = gravity
        try {
            mWindowManager!!.updateViewLayout(view, params)
        } catch (ignored: Exception) {
        }

    }

    fun show() {
        if (isShown) return
        isShown = true
        mWindowManager!!.addView(view, params)
        onShow()
    }

    abstract fun onShow()

    fun hide() {
        if (!isShown) return
        println("HIDE!")
        isShown = false
        mWindowManager!!.removeView(view)
        onHide()
    }
}

