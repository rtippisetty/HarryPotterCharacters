package dev.ranga.hpcharacters.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.ranga.hpcharacters.ui.characterlist.TableCell
import dev.ranga.hpcharacters.ui.model.UICharacter

@Composable
fun TableRow(
    character: UICharacter,
    onCharacterClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onCharacterClick(character.id) }
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TableCell(character.name, modifier = Modifier.weight(1f))
        TableCell(character.actor, modifier = Modifier.weight(1f))
        TableCell(character.species, modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    character.houseColor.copy(alpha = 0.6f)
                )
                .weight(1f)
        ) {
            TableCell(
                text = character.house,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}