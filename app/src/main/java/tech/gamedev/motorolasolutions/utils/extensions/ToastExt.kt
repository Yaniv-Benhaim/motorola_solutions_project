package tech.gamedev.motorolasolutions.utils.extensions

import android.content.Context
import android.widget.Toast
import tech.gamedev.motorolasolutions.data.other.Constants.FAILED_REQUEST_MSG

fun showErrorMsg(context: Context) {
    Toast.makeText(context, FAILED_REQUEST_MSG, Toast.LENGTH_SHORT).show()
}