package com.fahrulredho0018.assessment1.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fahrulredho0018.assessment1.R
import com.fahrulredho0018.assessment1.navigation.Screen
import com.fahrulredho0018.assessment1.ui.theme.Assessment1Theme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController){
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor =  MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier){
    var berat by rememberSaveable { mutableStateOf("") }
    var beratError by rememberSaveable { mutableStateOf(false) }
    var air by rememberSaveable { mutableStateOf("") }
    var airError by rememberSaveable { mutableStateOf(false) }

    val radioOptions = listOf(
        stringResource(id = R.string.pria),
        stringResource(id = R.string.wanita)
    )
    var gender by rememberSaveable { mutableStateOf(radioOptions[0]) }

    var ka by rememberSaveable { mutableFloatStateOf(0f) }
    var kategori by rememberSaveable { mutableIntStateOf(0) }

    val context = LocalContext.current

    var selectedDate by rememberSaveable { mutableStateOf<Long?>(null) }
    var isShowModal by rememberSaveable { mutableStateOf(true) }

    Column (
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text (
            text = stringResource(id = R.string.bmi_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = berat,
            onValueChange = { berat = it},
            label = { Text(text = stringResource(R.string.berat_badan)) },
            trailingIcon = { IconPicker(beratError, "Kg") },
            supportingText = { ErrorHint(beratError) },
            isError = beratError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = air,
            onValueChange = { air = it},
            label = { Text(text = stringResource(R.string.konsumsi_air)) },
            trailingIcon = { IconPicker(airError, "L") },
            supportingText = { ErrorHint(airError) },
            isError = airError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = selectedDate?.let { convertMillisToDate(it) } ?: "",
            onValueChange = { },
            label = {
                Text(
                    text = stringResource(R.string.tanggal),
                    fontWeight = FontWeight.SemiBold
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.placeholders)
                )
            },
            trailingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = "Selected one")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .pointerInput(selectedDate) {
                    awaitEachGesture {  }
                }
        )
        if (isShowModal){
            DatePickerModal(
                onDateSelected = { selectedDate = it },
                onDismiss =  { isShowModal = false }
            )
        }

        Row (
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach {text ->
                GenderOption(
                    label = text,
                    isSelected = gender == text,
                    modifier = Modifier
                        .selectable(
                            selected = gender == text,
                            onClick =  { gender = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }

        Button(
            onClick = {
                beratError = (berat == ""|| berat == "0")
                airError = (air == "" || air == "0")
                if (beratError || airError) return@Button

                ka = hitung(
                    berat.toFloat(),
                    air.toFloat(),
                    gender == radioOptions[0]
                )
                kategori = getKategori(ka)
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }
        if (ka != 0f) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Text(
                text = stringResource(R.string.ka_x, ka),
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = stringResource(kategori).uppercase(),
                style = MaterialTheme.typography.headlineLarge
            )

            Button(
                onClick = {
                    shareData(
                        context = context,
                        message = context.getString(R.string.bagikan_template,
                            berat,air,gender,ka,context.getString(kategori).uppercase())
                    )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.bagikan))
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String{
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
fun  GenderOption(label: String, isSelected: Boolean,modifier: Modifier) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String){
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    }else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

private fun hitung(berat: Float, konsumsiAir: Float, isMale: Boolean): Float {
    val kebutuhan = if (isMale) {
        (berat / 10f) * 1.0f
    } else {
        (berat / 10f) * 0.85f
    }
    return konsumsiAir / kebutuhan
}

private fun getKategori(rasio: Float): Int {
    return when {
        rasio < 0.9 -> R.string.kekurangan_air
        rasio > 1.1 -> R.string.kelebihan_air
        else -> R.string.cukup_air
    }
}


private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null){
        context.startActivity(shareIntent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Assessment1Theme {
        MainScreen(rememberNavController())
    }
}