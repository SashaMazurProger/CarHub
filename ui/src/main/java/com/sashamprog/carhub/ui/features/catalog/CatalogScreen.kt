@file:OptIn(ExperimentalMaterialApi::class)

package com.sashamprog.carhub.ui.features.catalog

import CreateEditCarScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.sashamprog.carhub.domain.model.Car
import com.sashamprog.carhub.ui.features.car_details.CarDetailsScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun CatalogNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "catalog_list") {
        composable("catalog_list") {
            CatalogScreen(navController)
        }
        composable(route = "carDetails") {
            val car = navController.previousBackStackEntry?.savedStateHandle?.get<Car>("car")
            car?.let {
                CarDetailsScreen(navController,it)
            }
        }
        composable(route = "editCar") {
            val car = navController.previousBackStackEntry?.savedStateHandle?.get<Car>("car")
            car?.let {
                CreateEditCarScreen(navController, it)
            }
        }
    }
}

@Composable
fun CatalogScreen(navController: NavController) {
    val viewModel: CatalogViewModel = koinViewModel()
    val catalogState by viewModel.catalogState.collectAsState()


    Scaffold(topBar = {
        TopAppBar(title = { Text("Автомобілі на продаж") })
    }) { paddingValues ->

        when (catalogState) {
            is CatalogState.Loading -> CircularProgressIndicator()
            is CatalogState.Error -> Text(text = (catalogState as CatalogState.Error).message)
            is CatalogState.Loaded -> {
                val cars = (catalogState as CatalogState.Loaded).items

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(cars) { car ->
                        CarItem(car = car, {
                            navController.currentBackStackEntry?.savedStateHandle?.set("car", car)
                            navController.navigate("carDetails")
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun CarItem(car: Car, onCarClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCarClick() },
        elevation = 4.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                car.imageUrls.take(3).forEach { imageUrl -> // Показуємо максимум 3 зображення
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Text(
                text = "${car.make} ${car.model}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6
            )
            Text(text = "Рік випуску: ${car.year}", style = MaterialTheme.typography.body1)
            Text(text = "Пробіг: ${car.mileage} км", style = MaterialTheme.typography.body1)
            Text(
                text = car.description,
                style = MaterialTheme.typography.body2,
                maxLines = 3
            )
        }
    }
}
