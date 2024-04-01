package fr.isen.Borgna.androiderestaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.*
import com.google.gson.Gson
import kotlinx.coroutines.delay

class DishDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dishJson = intent.getStringExtra("dishDetails")
        val dish: Objet? = Gson().fromJson(dishJson, Objet::class.java)

        setContent {
            dish?.let {
                DetailAndroidERestaurantTheme {
                    DishDetailScreenWithTopBar(dish)
                }
            }
        }
    }
}

@Composable
fun DetailAndroidERestaurantTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}

@Composable
fun DishDetailScreenWithTopBar(dish: Objet) {
    Scaffold(
        topBar = {
            TopBarDishDetail(dishName = dish.namefr ?: "Détail du Plat")
        },
    ) { innerPadding ->
        DishDetailScreen(dish, innerPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarDishDetail(dishName: String) {
    CenterAlignedTopAppBar(
        title = { Text(dishName, color = Color.Black) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFFFF0000)
        )
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DishDetailScreen(dish: Objet, innerPadding: PaddingValues) {
    val bandeauColor = Color(0xFF4CAF50)
    Column(modifier = Modifier
        .padding(innerPadding)
        .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            AutoSlidingCarousel(
                itemsCount = dish.images.size,
                itemContent = { index ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(dish.images[index])
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.height(200.dp)
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ingrédients:",
            style = MaterialTheme.typography.titleMedium
        )
        dish.ingredients.forEach { ingredient ->
            Surface(
                color = bandeauColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = ingredient.namefr ?: "Ingrédient inconnu",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Prix:",
            style = MaterialTheme.typography.titleMedium
        )
        dish.prices.forEach { price ->
            Surface(
                color = bandeauColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${price.size}: ${price.price}€",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    price.price?.let { QuantitySelector(it.toDouble()) }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 3000L,
    pagerState: PagerState = rememberPagerState(),
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    if (itemsCount > 0) {
        Box(modifier = modifier.fillMaxWidth()) {
            HorizontalPager(count = itemsCount, state = pagerState) { page ->
                itemContent(page)
            }
        }
        LaunchedEffect(pagerState) {
            while (true) {
                delay(autoSlideDuration)
                with(pagerState) {
                    val nextPage = (currentPage + 1) % pageCount
                    animateScrollToPage(nextPage)
                }
            }
        }
    } else {
        val image = painterResource(id = R.drawable.abc)
        Image(
            painter = image,
            contentDescription = "Image par défaut",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun QuantitySelector(price: Double) {
    var quantity by remember { mutableStateOf(1) }
    val totalPrice = remember(quantity) { price * quantity }
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Red,
        contentColor = Color.White
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { if (quantity > 1) quantity-- },
            contentPadding = PaddingValues(),
            colors = buttonColors
        ) {
            Text("-")
        }

        Text("$quantity x $price = $totalPrice€")

        Button(
            onClick = { quantity++ },
            contentPadding = PaddingValues(),
            colors = buttonColors
        ) {
            Text("+")
        }
    }
}