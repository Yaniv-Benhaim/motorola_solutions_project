package tech.gamedev.motorolasolutions.utils.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import tech.gamedev.motorolasolutions.data.models.User
import tech.gamedev.motorolasolutions.data.remote.responses.Result
import tech.gamedev.motorolasolutions.data.remote.responses.UserResult


fun Result.getUser(): User {
    return User(
        fullName = "${this.name.first} ${this.name.last}",
        dateOfBirth = this.dob.date,
        profileImage = this.picture.large,
        email = this.email
    )
}

fun User.composeEmail(context: Context) {

    val builder = AlertDialog.Builder(context)
    builder.setTitle("Send email")
    builder.setMessage("Do you want to send an email using your default email application ?")

    builder.setPositiveButton("Yes") { dialog, _ ->
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email") // only email apps should handle this
        }
        startActivity(context, intent, null)
        dialog.dismiss()
    }

    builder.setNegativeButton("Cancel") { dialog, _ ->
        dialog.dismiss()
    }
    builder.show()
}