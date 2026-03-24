package com.haldny.dragonball.character.detail.view

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.haldny.dragonball.character.detail.domain.DragonBallCharacterDetail
import com.haldny.dragonball.character.detail.domain.OriginPlanet
import com.haldny.dragonball.design.R
import com.haldny.dragonball.design.components.DragonBallScaffold
import com.haldny.dragonball.design.screens.ErrorScreen
import com.haldny.dragonball.design.screens.LoadingScreen
import com.haldny.dragonball.design.theme.Dimens

@Composable
fun CharacterDetailScreen(
    id: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterDetailViewModel = hiltViewModel<CharacterDetailViewModel, CharacterDetailViewModel.Factory>(
        key = id.toString(),
        creationCallback = { factory -> factory.create(id = id) },
    ),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    DragonBallScaffold(
        title = context.getString(R.string.character_detail_title),
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = context.getString(R.string.content_description_back),
                )
            }
        },
    ) { paddingValues ->
        when (val ui = state) {
            UiState.Loading -> LoadingScreen(
                modifier = modifier
                    .padding(paddingValues)
                    .testTag(CharacterDetailTestTags.LOADING),
            )
            UiState.Error -> ErrorScreen(
                modifier = modifier
                    .padding(paddingValues)
                    .testTag(CharacterDetailTestTags.ERROR),
            ) {
                viewModel.getCharacterDetail(id)
            }
            is UiState.Loaded -> CharacterScreen(
                modifier = modifier
                    .padding(paddingValues)
                    .testTag(CharacterDetailTestTags.CONTENT),
                state = ui,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    modifier: Modifier = Modifier,
    state: UiState.Loaded,
) {
    val context = LocalContext.current
    val scheme = MaterialTheme.colorScheme
    var selectedTab by remember {
        mutableIntStateOf(CharacterDetailCarousel.PAGE_CHARACTER)
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = scheme.surface.copy(alpha = 0.72f),
            contentColor = scheme.primary,
            divider = { },
        ) {
            Tab(
                selected = selectedTab == CharacterDetailCarousel.PAGE_CHARACTER,
                onClick = { selectedTab = CharacterDetailCarousel.PAGE_CHARACTER },
                text = { Text(text = context.getString(R.string.detail_tab_character)) },
                selectedContentColor = scheme.primary,
                unselectedContentColor = scheme.onSurface.copy(alpha = 0.82f),
            )
            Tab(
                selected = selectedTab == CharacterDetailCarousel.PAGE_PLANET,
                onClick = { selectedTab = CharacterDetailCarousel.PAGE_PLANET },
                text = { Text(text = context.getString(R.string.detail_tab_planet)) },
                selectedContentColor = scheme.primary,
                unselectedContentColor = scheme.onSurface.copy(alpha = 0.82f),
            )
        }

        Spacer(modifier = Modifier.height(Dimens.characterDetailCarouselTabSpacing))

        Crossfade(
            targetState = selectedTab,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            label = "character_detail_carousel",
        ) { page ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(),
                border = BorderStroke(
                    width = Dimens.characterCardBorderWidth,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            scheme.primary.copy(alpha = 0.65f),
                            scheme.secondary.copy(alpha = 0.38f),
                        ),
                    ),
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = Dimens.characterDetailCarouselCardElevation,
                ),
                colors = CardDefaults.cardColors(
                    containerColor = scheme.surfaceVariant,
                    contentColor = scheme.onSurface,
                ),
            ) {
                when (page) {
                    CharacterDetailCarousel.PAGE_CHARACTER ->
                        CharacterCarouselCharacterPage(character = state.character)
                    CharacterDetailCarousel.PAGE_PLANET ->
                        CharacterCarouselPlanetPage(planet = state.character.originPlanet)
                }
            }
        }
    }
}

@Composable
private fun CharacterCarouselCharacterPage(character: DragonBallCharacterDetail) {
    val context = LocalContext.current
    val scheme = MaterialTheme.colorScheme
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(Dimens.screenPadding),
        horizontalAlignment = Alignment.Start,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.characterImageHeight),
            model = character.image,
            contentDescription = character.name,
            contentScale = ContentScale.Fit,
            alignment = Alignment.TopCenter,
        )

        Spacer(modifier = Modifier.height(Dimens.screenPadding))

        Text(
            text = character.name,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineLarge,
            color = scheme.onSurface,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(Dimens.characterDetailCarouselTabSpacing))
        val labelColor = scheme.onSurface.copy(alpha = 0.92f)
        val valueColor = scheme.onSurfaceVariant
        CharacterDetailLabeledRow(
            label = context.getString(R.string.detail_label_ki),
            value = character.ki,
            labelColor = labelColor,
            valueColor = valueColor,
        )
        CharacterDetailLabeledRow(
            label = context.getString(R.string.detail_label_max_ki),
            value = character.maxKi,
            labelColor = labelColor,
            valueColor = valueColor,
        )
        CharacterDetailLabeledRow(
            label = context.getString(R.string.detail_label_gender),
            value = character.gender?.value.orEmpty(),
            labelColor = labelColor,
            valueColor = valueColor,
        )
        CharacterDetailLabeledRow(
            label = context.getString(R.string.detail_label_race),
            value = character.race?.value.orEmpty(),
            labelColor = labelColor,
            valueColor = valueColor,
        )

        if (character.description.isNotBlank()) {
            Spacer(modifier = Modifier.height(Dimens.screenPadding))
            Text(
                text = context.getString(R.string.detail_label_description),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = scheme.onSurface,
                textAlign = TextAlign.Start,
            )
            Text(
                text = character.description,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge,
                color = scheme.onSurface,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Composable
private fun CharacterCarouselPlanetPage(planet: OriginPlanet?) {
    val context = LocalContext.current
    val scheme = MaterialTheme.colorScheme
    if (planet == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.screenPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = context.getString(R.string.detail_planet_unavailable),
                style = MaterialTheme.typography.bodyLarge,
                color = scheme.onSurface,
                textAlign = TextAlign.Center,
            )
        }
    } else {
        val scroll = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(Dimens.screenPadding),
            horizontalAlignment = Alignment.Start,
        ) {
            if (planet.image.isNotBlank()) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.characterImageHeight),
                    model = planet.image,
                    contentDescription = planet.name,
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.TopCenter,
                )
                Spacer(modifier = Modifier.height(Dimens.screenPadding))
            }

            Text(
                text = planet.name.ifBlank { context.getString(R.string.detail_tab_planet) },
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineLarge,
                color = scheme.onSurface,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(Dimens.characterDetailCarouselTabSpacing))
            CharacterDetailLabeledRow(
                label = context.getString(R.string.detail_label_destroyed),
                value = if (planet.isDestroyed) {
                    context.getString(R.string.detail_yes)
                } else {
                    context.getString(R.string.detail_no)
                },
                labelColor = scheme.onSurface.copy(alpha = 0.92f),
                valueColor = scheme.onSurfaceVariant,
            )
            if (planet.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(Dimens.screenPadding))
                Text(
                    text = context.getString(R.string.detail_label_description),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    color = scheme.onSurface,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = planet.description,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = scheme.onSurface,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}

/**
 * Fixed-width label column (start-aligned like the title) + ": " + value; same [bodyLarge] style,
 * label [FontWeight.Bold]. Separate [Text] nodes preserve RTL/bidi for mixed-direction content.
 */
@Composable
private fun CharacterDetailLabeledRow(
    label: String,
    value: String,
    labelColor: Color,
    valueColor: Color,
) {
    if (value.isBlank()) return
    val body = MaterialTheme.typography.bodyLarge
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = label,
            style = body.copy(fontWeight = FontWeight.Bold),
            color = labelColor,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = ": ",
            style = body,
            color = valueColor,
        )
        Text(
            text = value,
            modifier = Modifier.weight(1f, fill = false),
            style = body,
            color = valueColor,
        )
    }
}
