package com.abc.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.abc.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    LemonadeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Lemonade",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }) { innerPadding ->
            LemonadeAppContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}


@Preview
@Composable
fun LemonadeAppContent(modifier: Modifier = Modifier) {

    var stage by remember { mutableIntStateOf(1) }
    var squeezeAmount by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = dimensionResource(R.dimen.padding_vertical)),
        contentAlignment = Alignment.Center
    ) {
        when (stage) {
            1 -> StageContent(
                photo = painterResource(R.drawable.lemon_tree),
                contentDescription = stringResource(R.string.lemon_tree_content_description),
                text = stringResource(R.string.tap_lemon_tree),
                onImageClick = {
                    stage = 2
                    squeezeAmount = (2..4).random()
                })

            2 -> StageContent(
                photo = painterResource(R.drawable.lemon_squeeze),
                contentDescription = stringResource(R.string.lemon_content_description),
                text = stringResource(R.string.tap_lemon),
                onImageClick = {
                    squeezeAmount--
                    if (squeezeAmount == 0) stage = 3
                })

            3 -> StageContent(
                photo = painterResource(R.drawable.lemon_drink),
                contentDescription = stringResource(R.string.lemonade_content_description),
                text = stringResource(R.string.tap_lemonade),
                onImageClick = { stage = 4 })

            4 -> StageContent(
                photo = painterResource(R.drawable.lemon_restart),
                contentDescription = stringResource(R.string.empty_glass_content_description),
                text = stringResource(R.string.tap_glass),
                onImageClick = { stage = 1 })
        }
    }
}

@Composable
fun StageContent(
    photo: Painter,
    contentDescription: String,
    text: String,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onImageClick,
            modifier = Modifier.size(
                dimensionResource(R.dimen.card_image_width),
                dimensionResource(R.dimen.card_image_height)
            ),
            shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Image(
                painter = photo,
                contentDescription = contentDescription,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.card_interior_padding))
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_vertical)))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
