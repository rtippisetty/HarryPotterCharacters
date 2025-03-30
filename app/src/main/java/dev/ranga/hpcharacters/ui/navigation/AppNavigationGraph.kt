package dev.ranga.hpcharacters.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.ranga.hpcharacters.ui.characterdetail.CharacterDetailScreen
import dev.ranga.hpcharacters.ui.characterlist.CharactersListScreen
import kotlinx.serialization.Serializable

@Serializable
object CharactersListHome

@Serializable
data class CharacterDetail(val id: String)

fun NavGraphBuilder.homeScreen(
    navigateToCharacterDetail: (String) -> Unit
) {
    composable<CharactersListHome> {
        CharactersListScreen(
            onCharacterClick = navigateToCharacterDetail
        )
    }
}

fun NavGraphBuilder.characterDetailScreen(
    navigateBack: () -> Unit
) {
    composable<CharacterDetail> {
        CharacterDetailScreen(
            onBackClick = navigateBack
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = CharactersListHome) {
        homeScreen(
            navigateToCharacterDetail = { characterId ->
                navController.navigate(CharacterDetail(characterId))
            }
        )

        characterDetailScreen(
            navigateBack = navController::popBackStack
        )
    }
}