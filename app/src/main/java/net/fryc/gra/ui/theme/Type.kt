package net.fryc.gra.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val OPTION_FONT_SIZE = 26.sp
val SMALLER_OPTION_FONT_SIZE = 20.sp

val BUTTON_TEXT_FONT_SIZE = 24.sp
val SMALLER_BUTTON_TEXT_FONT_SIZE = 20.sp

val VERY_LARGE_FONT = 28.sp
val LARGE_FONT = 24.sp
val BIGGER_FONT = 18.sp
val NORMAL_FONT = 16.sp

val Typography = Typography(
        bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = NORMAL_FONT,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
                color = Color.White
        )
)

val LightTypography = Typography(
        bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = NORMAL_FONT,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
                color = Color.Black
        )
)
