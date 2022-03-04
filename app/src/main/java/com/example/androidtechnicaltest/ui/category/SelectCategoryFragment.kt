package com.example.androidtechnicaltest.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.androidtechnicaltest.ui.main.MainViewModel

class SelectCategoryFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = SelectCategoryFragment()
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
                    Text(text = "Select Categories",
                        modifier = Modifier.padding(top = 50.dp).align(Alignment.CenterHorizontally),
                        color = Color.Yellow
                    )
                    CategoryList(mainViewModel = mainViewModel)
                }
            }
        }
    }
}

@Composable
fun CategoryRowItem(text: String, onClick: (() -> Unit)? = null) {
    // Simple Row Composable
    Row(
        modifier = Modifier
            .size(80.dp) // Size 100 dp
            .background(Color.Black) // Background White
            .border(1.dp, Color.Yellow)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick?.invoke() }
                )
            },
        // Align Items in Center
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Text Composable which displays some
        Text(text = text, color = Color.Yellow)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryList(mainViewModel: MainViewModel) {
    val value by mainViewModel.categoryList.observeAsState()
    LazyVerticalGrid(
        // fix the item in one row to be 3.
        cells = GridCells.Fixed(3),

        contentPadding = PaddingValues(20.dp),

        ) {
        value?.let {
            itemsIndexed(it.toList()) { _, item ->
                CategoryRowItem(item) { mainViewModel.onSelectedCategoryCommand(item) }
            }
        }
    }
}
