package fr.isen.Borgna.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject

class MenuActivity : ComponentActivity() {
    private var items by mutableStateOf<List<Objet>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedCategory = intent.getStringExtra("category_name") ?: ""
        fetchData(selectedCategory)
        setContent {
            MenuAndroidERestaurantTheme {
                MenuScreen(selectedCategory, items) { item ->
                    val intent = Intent(this, DishDetailActivity::class.java).apply {
                        val itemJson = Gson().toJson(item)
                        putExtra("dishDetails", itemJson)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    private fun fetchData(category: String) {
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val param = JSONObject()
        param.put("id_shop", "1")

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, param,
            { response ->
                val result = Gson().fromJson(response.toString(), DetailActivity::class.java)
                items = result.data.find { it.namefr == category }?.items ?: emptyList()
                Log.d("MenuActivity", "result: $response")
            },
            { error ->
                Log.e("MenuActivity", "Error: $error")
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }
}

@Composable
fun MenuAndroidERestaurantTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}

@Composable
fun MenuScreen(category: String, items: List<Objet>, onItemClicked: (Objet) -> Unit) {
    Scaffold(
        topBar = { TopBar(category) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                items(items) { item ->
                    DishItem(dish = item, onItemClicked = onItemClicked)
                }
            }
        }
    }
}

@Composable
fun DishItem(dish: Objet, onItemClicked: (Objet) -> Unit) {
    val buttonColor = Color(0xFF4CAF50)

    Surface(
        color = buttonColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable { onItemClicked(dish) }
    ) {
        Text(
            text = dish.namefr ?: " no name ",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(categoryName: String) {
    CenterAlignedTopAppBar(
        title = { Text(categoryName, color = Color.Black) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFFFF0000)
        )
    )
}



