package com.orlandev.cashcounter.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore

object ShareIntent {
    fun shareIt(ctx: Context, bm: Bitmap, shareText: String, shareBy: String) {
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.putExtra(
            Intent.EXTRA_TEXT,
            shareText
        )

        val path = MediaStore.Images.Media.insertImage(ctx.contentResolver, bm, shareText, shareBy)
        val screenshotUri: Uri = Uri.parse(path)
        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
        intent.type = "image/*"
        ctx.startActivity(
            Intent.createChooser(
                intent,
                shareBy
            )
        )
    }
    fun shareIt(ctx: Context, shareText: String, shareBy: String) {
        val intent = Intent(Intent.ACTION_SEND)

        intent.putExtra(
            Intent.EXTRA_TEXT,
            shareText
        )

        intent.type = "text/*"

        ctx.startActivity(
            Intent.createChooser(
                intent,
                shareBy
            )
        )
    }
}