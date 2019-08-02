package com.ditronic.securezipnotes

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.ditronic.securezipnotes.activities.MainActivity
import java.io.FileOutputStream


fun precondition_cleanStart(rule: ActivityTestRule<MainActivity>, preLaunchHook: (() -> Unit)? = null) {
    preLaunchHook?.invoke()
    rule.launchActivity(null)
}

fun precondition_singleNote(rule: ActivityTestRule<MainActivity>, preLaunchHook: (() -> Unit)? = null) {
    resetAndLoadAsset("singlenote.aeszip")
    preLaunchHook?.invoke()
    rule.launchActivity(null)
    main_assertListState(listOf("Note 1"), rule.activity)
}

fun precondition_fourNotes(rule: ActivityTestRule<MainActivity>) {
    resetAndLoadAsset("4notes.aeszip")
    rule.launchActivity(null)
    main_assertListState(listOf("Note 1", "Note 2", "Note 3", "Note 4"), rule.activity)
}

private fun resetAndLoadAsset(assetPath: String) {
    //resetAppData()

    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    val testContext = InstrumentationRegistry.getInstrumentation().context

    val sourceStream = testContext.assets.open(assetPath)
    val targetStream = FileOutputStream(CryptoZip.getMainFilePath(appContext))
    sourceStream.copyTo(targetStream)
    sourceStream.close()
    targetStream.close()
}
