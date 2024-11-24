package com.example.incidentmanager.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@SuppressLint("Range")
@Composable
fun ButtonMenu(text: String, onClick: ()-> Unit ) {
    Button(
        modifier = Modifier
            .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 5.dp)
            .fillMaxWidth(6f),
        shape = RoundedCornerShape(5.dp),
        onClick = onClick,
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}