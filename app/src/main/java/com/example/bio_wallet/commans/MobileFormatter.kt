package com.example.bio_wallet.commans

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText


fun mobileNumberFilter(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 10) text.text.substring(0..9) else text.text

    val annotatedString = AnnotatedString.Builder().run {
        for (i in trimmed.indices) {
            append(trimmed[i])
            if (i == 2 || i == 5) {
                append(" ")
            }
        }
        pushStyle(SpanStyle(color = Color.LightGray))
        append(Constants.PHONE_MASK.takeLast(Constants.PHONE_MASK.length - length))
        toAnnotatedString()
    }
    //"XXX XXX XXXX"
    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 5) return offset + 1
            if (offset <= 10) return offset + 2
            return 12
        }

        override fun transformedToOriginal(offset: Int): Int {
            return if (offset > 0 && trimmed.isEmpty()) {
                0
            } else if (offset > 0 && trimmed.isNotEmpty()) {
                trimmed.length
            } else 0
//
//            if (offset <= 3) return offset
//            if (offset <= 7) return offset - 1
//            if (offset <= 12) return offset - 2
//            return 10
        }
    }

    return TransformedText(annotatedString, phoneNumberOffsetTranslator)
}

