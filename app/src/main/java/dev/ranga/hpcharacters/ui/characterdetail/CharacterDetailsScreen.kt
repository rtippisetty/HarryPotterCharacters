package dev.ranga.hpcharacters.ui.characterdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.ranga.hpcharacters.R
import dev.ranga.hpcharacters.ui.common.ImageView
import dev.ranga.hpcharacters.ui.model.UICharacterDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    onBackClick: () -> Unit,
    viewModel: CharacterDetailViewModel = hiltViewModel(),
) {
    val characterDetailState = viewModel.characterDetailState.collectAsStateWithLifecycle().value
    val displayName = rememberSaveable { mutableStateOf("Character") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(displayName.value) },
                navigationIcon = {
                    BackIcon(onBackClick = onBackClick)
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (characterDetailState) {
                is UIDetailState.Success -> {
                    displayName.value = characterDetailState.character.actor
                    CharacterDetailContent(
                        character = characterDetailState.character,
                    )
                }
                is UIDetailState.Error -> ErrorContent()
                is UIDetailState.Loading -> LoadingContent()
            }
        }
    }
}

@Composable
fun BackIcon(onBackClick: () -> Unit) {
    IconButton(onClick = onBackClick) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
    }
}

@Composable
fun LoadingContent() {
    CircularProgressIndicator()
}

@Composable
fun ErrorContent() {
    Text(text = "Error")
}

@Composable
private fun CharacterDetailContent(
    character: UICharacterDetail,
) {
    Column {
        Text(text = "Character name : " + character.name)
        Text(text = "Actor name : " + character.actor)
        Text(text = "Species : " + character.species)
        Text(text = "House : " + character.house)
        Text(text = "Date of Birth : " + character.dateOfBirth)
        Text(text = "isAlive: " + if(character.alive) "Yes" else "No")
        if(character.image.isNotEmpty()) {
            ImageView(
                imageUrl = character.image,
                name = character.name,
                modifier = Modifier.padding(64.dp)
                    .aspectRatio(1f)
            )
        }
    }
}