package com.example.nanoclientkotlin.handlers

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.nanoclientkotlin.consts.ConstsCommSvc

object ServiceCheck {
    fun isServiceRunning(context: Context, packageName:String, serviceClassName: String): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val runningServices = manager.getRunningServices(Integer.MAX_VALUE)
//
//        for (serviceInfo in runningServices) {
//            if (serviceClassName == serviceInfo.service.className) {
//                // The service is running
//                return true
//            }
//        }
        val runningProcesses = manager.runningAppProcesses
        if (runningProcesses != null) {
            for (processInfo in runningProcesses) {
                if (processInfo.processName == "$packageName:$serviceClassName") {
                    // The service is running
                    return true
                }
            }
        }

        // The service is not running
        return false
    }
//    fun startService(context: Context) {
//        // Check if your service is running before starting it
//        val serviceClassName = ConstsCommSvc.INTENT_SVC_PACKAGE_NAME
//        if (!isServiceRunning(context, packageName  ,serviceClassName)) {
//            val intent = Intent(ConstsCommSvc.INTENT_SVC_START)
//            intent.setPackage(ConstsCommSvc.INTENT_SVC_PACKAGE_NAME)
//            intent.putExtra(ConstsCommSvc.INTENT_ACTION_NEED_KNOX, true)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                context.startForegroundService(intent)
//            } else {
//                context.startService(intent)
//            }
//        } else {
//            // The service is already running
//        }
//    }
//TODO: Pending Intent
//val serviceClassName = ConstsCommSvc.INTENT_SVC_PACKAGE_NAME
//    if (!isServiceRunning(context, serviceClassName)) {
//        val intent = Intent(ConstsCommSvc.INTENT_SVC_START)
//        intent.setPackage(ConstsCommSvc.INTENT_SVC_PACKAGE_NAME)
//        intent.putExtra(ConstsCommSvc.INTENT_ACTION_NEED_KNOX, true)
//
//        // Create a PendingIntent to receive a result from the service
//        val pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notification = createNotification() // Create a notification for the foreground service
//            context.startForegroundService(intent)
//        } else {
//            context.startService(intent)
//        }
//    } else {
//        // The service is already running
//    }
//    class YourService : Service() {
//        // ...
//
//        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//            // Your service logic here
//
//            // Send a result back to the PendingIntent
//            val resultIntent = Intent()
//            resultIntent.putExtra("resultKey", "resultData")
//
//            // Use startService with the result intent
//            val pendingIntent = intent?.getParcelableExtra<PendingIntent>("pendingIntent")
//            pendingIntent?.send(this, 0, resultIntent)
//
//            // Stop the service when the work is done
//            stopSelf()
//
//            return START_NOT_STICKY
//        }
//
//        // ...
//    }
}