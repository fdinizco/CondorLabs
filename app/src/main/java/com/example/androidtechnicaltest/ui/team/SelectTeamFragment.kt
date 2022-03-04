package com.example.androidtechnicaltest.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.compose.rememberImagePainter
import com.example.androidtechnicaltest.ui.main.MainViewModel
import com.example.androidtechnicaltest.util.EMPTY_STRING

class SelectTeamFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = SelectTeamFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Column  (
                    Modifier.background(Color.Black)
                ){
                    Text(text = "Select Team",
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .align(Alignment.CenterHorizontally),
                        color = Color.Yellow
                    )
                    TeamList(mainViewModel = mainViewModel)
                }
            }
        }
    }
}

@Composable
fun TeamCard(text: String, onClick: (() -> Unit)? = null, logoURL: String = EMPTY_STRING) {
    Card(
        backgroundColor = Color.Black,
        modifier = Modifier
            .padding(10.dp)
            .width(180.dp)
            .height(180.dp)
            .border(1.dp, Color.Yellow, RoundedCornerShape(20.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick?.invoke() }
                )
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Row(modifier = Modifier.padding(top = 10.dp)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = rememberImagePainter(logoURL),
                        contentDescription = null,
                        modifier = Modifier.weight(3f, fill = true).align(CenterHorizontally)
                    )
                    Spacer(
                        modifier = Modifier
                            .width(1.dp)
                            .height(10.dp)
                    )
                    Text(
                        text = text,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 10.sp
                        ),
                        modifier = Modifier.weight(1f).align(CenterHorizontally)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TeamList(mainViewModel: MainViewModel) {
    val value by mainViewModel.teamList.observeAsState()
    LazyVerticalGrid(
        // fix the item in one row to be 2.
        cells = GridCells.Fixed(2),

        contentPadding = PaddingValues(20.dp),

        ) {
        value?.let {
            itemsIndexed(it.toList()) { _, item ->
                TeamCard(item.strTeam.orEmpty(), logoURL = item.strTeamBadge.orEmpty(), onClick = { mainViewModel.onSelectedTeam(item.strTeam.orEmpty()) })
            }
        }
    }
}
