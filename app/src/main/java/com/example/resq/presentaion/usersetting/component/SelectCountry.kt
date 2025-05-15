package com.example.resq.presentaion.usersetting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.resq.presentaion.usersetting.model.Country

@Composable
fun SelectDialog(
    text: String,
    onDismiss: () -> Unit,
    selectOptions: List<Country>,
    onSelectedOptions: (Country) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = text)
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                selectOptions.forEach { country ->
                    Text(
                        text = country.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp)
                            .clickable {
                                onDismiss()
                                onSelectedOptions(country)
                            }
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}
