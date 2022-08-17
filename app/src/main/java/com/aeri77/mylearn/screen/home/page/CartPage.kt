package com.aeri77.mylearn.screen.home.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.aeri77.mylearn.MainViewModel
import com.aeri77.mylearn.ui.theme.*

@ExperimentalMaterial3Api
@Composable
fun CartPage(mainViewModel: MainViewModel) {
    mainViewModel.setToolbar(true)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Primary95
    ) {
        ConstraintLayout {
            val (items, checkout) = createRefs()

            LazyColumn(
                Modifier
                    .constrainAs(items) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(checkout.top)
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    }
                    .background(Secondary80),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
            ) {
                items(20) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .height(111.dp)
                            .padding(12.dp)){
                            Text(text = "12331")
                        }
                    }
                }

            }
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .constrainAs(checkout) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Rp. 200,000")
                Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(4.dp)) {
                    Text(text = "Checkout")
                }
            }
        }
    }
}