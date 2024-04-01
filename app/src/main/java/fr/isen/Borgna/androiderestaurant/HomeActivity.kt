package fr.isen.Borgna.androiderestaurant

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val selectedCategory = remember { mutableStateOf<String?>(null) }

    AndroidERestaurantTheme {
        Scaffold(
            topBar = { TopBar() },
            containerColor = Color.White
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WelcomeSection()
                MenuCategories { category ->
                    selectedCategory.value = category
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = { Text("La Trattoria", color = Color.Black) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFFFF0000)
        )
    )
}

@Composable
fun WelcomeSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            "Bienvenue chez Andrea de la Trattoria",
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        Spacer(modifier = Modifier.height(16.dp))


        Image(
            painter = painterResource(id = R.drawable.b),
            contentDescription = "Restaurant Image",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MenuCategories(onCategorySelected: (String) -> Unit) {
    val categories = listOf("Entrées", "Plats", "Desserts", "Boissons")

    Column {
        categories.forEach { category ->
            CategoryItem(name = category, onClick = {
                onCategorySelected(category)
            })
        }
    }
}

@Composable
fun CategoryItem(name: String, onClick: () -> Unit) {
    val context = LocalContext.current
    val bandeauColor = Color(0xFF4CAF50)

    Surface(
        color = bandeauColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 20.dp)
            .clickable {

                val intent = Intent(context, MenuActivity::class.java).apply {
                    putExtra("category_name", name)
                }
                context.startActivity(intent)
            }
    ) {
        Text(
            text = name,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp),
            color = Color.White
        )
    }
}

@Composable
fun AndroidERestaurantTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}
