package com.example.androidtechnicaltest.ui.team

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.compose.rememberImagePainter
import com.example.androidtechnicaltest.R
import com.example.androidtechnicaltest.extension.ifNullOrEmpty
import com.example.androidtechnicaltest.ui.main.MainViewModel
import com.example.androidtechnicaltest.util.EMPTY_STRING

class TeamDetailFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = TeamDetailFragment()
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
                   TeamInformation(mainViewModel = mainViewModel, requireActivity())
                }
            }
        }
    }
}

@Composable
fun TeamInformation(mainViewModel: MainViewModel, context: Context) {
    val value by mainViewModel.selectedTeam.observeAsState()
    val bannerImage = value?.strTeamLogo.ifNullOrEmpty(value?.strTeamBadge.orEmpty())
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(modifier = Modifier.padding(top = 10.dp)) {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)) {
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(50.dp)
                )
                Image(
                    painter = rememberImagePainter(bannerImage),
                    contentDescription = null,
                    modifier = Modifier
                        .height(128.dp)
                        .align(CenterHorizontally)
                )
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(70.dp)
                )
                Text(
                    text = "Description: ",
                    textAlign = TextAlign.Left,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    ),
                )
                Text(
                    text = value?.strDescriptionEN.orEmpty(),
                    textAlign = TextAlign.Left,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp
                    ),
                )
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(10.dp)
                )
                Text(
                    text = "Foundation Year: " + value?.intFormedYear.orEmpty(),
                    textAlign = TextAlign.Left,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    ),
                )
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(20.dp)
                )
                TeamCard(
                    text = EMPTY_STRING,
                    onClick = null,
                    logoURL = value?.strTeamJersey.orEmpty()
                )
                Row(
                    modifier = Modifier.align(CenterHorizontally).fillMaxWidth()
                ) {
                    SocialIcon(
                        onClick = { openLink(context, value?.strInstagram.orEmpty())},
                        image = R.drawable.ic_instagram_icon
                    )
                    SocialIcon(
                        onClick = { openLink(context, value?.strFacebook.orEmpty())},
                        image = R.drawable.ic_facebook_icon
                    )
                    SocialIcon(
                        onClick = { openLink(context, value?.strTwitter.orEmpty())},
                        image = R.drawable.ic_twitter_icon
                    )
                    SocialIcon(
                        onClick = { openLink(context, value?.strWebsite.orEmpty())},
                        image = R.drawable.ic_website_icon
                    )
                }
            }
        }
    }
}

@Composable
fun SocialIcon(onClick: (() -> Unit)? = null, image: Int = Int.MIN_VALUE) {
    Image(
        painter =  painterResource(image),
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        colorFilter = ColorFilter.tint(
            Color(0xFFF8DE7E),
            BlendMode.ColorBurn
        ),
        modifier = Modifier
            .height(48.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick?.invoke() }
                )
            },
    )
}

fun openLink(context: Context, link: String){

    var webpage = Uri.parse(link)
    if (!link.startsWith("http://") && !link.startsWith("https://")) {
        webpage = Uri.parse("http://$link");
    }
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    context.startActivity(intent)
}

