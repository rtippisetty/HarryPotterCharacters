package dev.ranga.hpcharacters.ui.characterlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.ranga.hpcharacters.ui.common.SearchBar
import dev.ranga.hpcharacters.ui.common.TableRow
import dev.ranga.hpcharacters.ui.model.UICharacter
import dev.ranga.hpcharacters.ui.theme.GryffindorHouseColor
import dev.ranga.hpcharacters.ui.theme.RavenclawHouseColor

@Composable
fun CharactersListScreen(
    viewModel: CharactersListViewModel = hiltViewModel(),
    onCharacterClick: (String) -> Unit
) {
    val characters = viewModel.characters.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value

    var searchQuery by remember { mutableStateOf("") }

    CharactersListContent(
        characters = characters,
        isLoading = isLoading,
        onCharacterClick = onCharacterClick,
        searchQuery = searchQuery,
        onSearchQueryChange = { query ->
            searchQuery = query
            viewModel.searchCharacters(query)
        },
        onSearchClick = {
            viewModel.searchCharacters(searchQuery)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharactersListContent(
    characters: List<UICharacter>,
    isLoading: Boolean,
    onCharacterClick: (String) -> Unit,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Harry Potter Cast",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            SearchBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onSearchClick = onSearchClick,
                onClearClick = {
                    onSearchQueryChange("")
                }
            )
            TableHeader()
            if (isLoading) {
                Text("Loading...")
            }
            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
            if (characters.isEmpty()) {
                Text(text = "No characters found")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = characters,
                        key = { character -> character.id }
                    ) { character ->
                        TableRow(character = character, onCharacterClick = onCharacterClick)
                        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TableCell("Name", true, modifier = Modifier.weight(1f))
        TableCell("Actor", true, modifier = Modifier.weight(1f))
        TableCell("Species", true, modifier = Modifier.weight(1f))
        TableCell("House", true, modifier = Modifier.weight(1f))
    }
}

@Composable
fun TableCell(text: String, isHeader: Boolean = false, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier.padding(8.dp),
        fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
        fontSize = if (isHeader) 18.sp else 16.sp
    )
}

@Preview
@Composable
private fun CharacterListItemPreview() {
    TableRow(
        character = UICharacter(
            id = "1",
            name = "Harry Potter",
            actor = "Daniel Radcliffe",
            species = "Human",
            house = "Gryffindor",
            houseColor = GryffindorHouseColor
        ),
        {}
    )
}

@Preview
@Composable
private fun CharactersListScreenPreview() {
    CharactersListContent(
        characters = listOf(
            UICharacter(
                id = "1",
                name = "Harry Potter",
                actor = "Daniel Radcliffe",
                species = "Human",
                house = "Gryffindor",
                houseColor = GryffindorHouseColor
            ),
            UICharacter(
                id = "2",
                name = "Hermione Granger",
                actor = "Emma Watson",
                species = "Human",
                house = "Gryffindor",
                houseColor = RavenclawHouseColor
            )
        ),
        isLoading = false,
        onCharacterClick = {}
    )
}