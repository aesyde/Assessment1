package com.fahrulredho0018.assessment1.ui.screen


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fahrulredho0018.assessment1.R
import com.fahrulredho0018.assessment1.model.Logo
import com.fahrulredho0018.assessment1.ui.theme.Assessment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController){

        val data = listOf(
            Logo("Telkom", R.drawable.telkom),
            Logo("Redo", R.drawable.redo)
        )

        var index by remember { mutableIntStateOf(0) }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.tentang_aplikasi))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor =  MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        AboutContent(data[index],Modifier.padding(innerPadding)){
            index = if (index == data.size-1) 0 else index + 1
        }
        Text(
            text = stringResource(R.string.copyright),
            modifier = Modifier.padding(innerPadding).padding(16.dp)
        )
    }
}

@Composable
fun AboutContent(
    logo: Logo,
    modifier: Modifier = Modifier,
    onClick: () -> Unit) {

    Column (
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = logo.imageResid),
            contentDescription = stringResource(R.string.gambar, logo.nama),
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(132.dp)
        )
        Button(
            onClick = {onClick()},
            modifier = Modifier.fillMaxWidth(0.5f).padding(top = 24.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = stringResource(R.string.lanjut))
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AboutScreenPreview() {
    Assessment1Theme {
        AboutScreen(rememberNavController())
    }
}