package com.example.resq.presentaion.usersetting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import com.example.resq.presentaion.usersetting.model.Country
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.resq.R


@Composable
fun SelectCountry(
    onDismiss: () -> Unit,
    onCountrySelected: (String) -> Unit
) {
    val countries = listOf(
        Country("KR", stringResource(R.string.country_kr)),
        Country("US", stringResource(R.string.country_us)),
        Country("GB", stringResource(R.string.country_gb)),
        Country("JP", stringResource(R.string.country_jp)),
        Country("CN", stringResource(R.string.country_cn)),
        Country("DE", stringResource(R.string.country_de)),
        Country("FR", stringResource(R.string.country_fr)),
        Country("MX", stringResource(R.string.country_mx))
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.select_country))
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                countries.forEach { country ->
                    Text(
                        text = country.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp)
                            .clickable {
                                onCountrySelected(country.code)
                                onDismiss()
                            }
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}
