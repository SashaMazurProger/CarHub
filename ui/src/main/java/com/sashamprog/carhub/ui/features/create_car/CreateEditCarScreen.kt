import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.sashamprog.carhub.domain.model.Car

@Composable
fun CreateEditCarScreen(
    navController: NavController,
    car: Car? = null // Pass the car object if editing
) {
    var make by remember { mutableStateOf(car?.make ?: "") }
    var model by remember { mutableStateOf(car?.model ?: "") }
    var year by remember { mutableStateOf(car?.year?.toString() ?: "") }
    var mileage by remember { mutableStateOf(car?.mileage?.toString() ?: "") }
    var description by remember { mutableStateOf(car?.description ?: "") }
    var imageUrls by remember { mutableStateOf(car?.imageUrls?.toMutableList() ?: mutableListOf()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = make,
            onValueChange = { make = it },
            label = { Text("Make") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("Model") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Year") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = mileage,
            onValueChange = { mileage = it },
            label = { Text("Mileage") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Images", style = MaterialTheme.typography.titleMedium)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items = imageUrls) { url ->
                Box(modifier = Modifier.size(100.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(url),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    )
                    IconButton(
                        onClick = { imageUrls.remove(url) },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Image")
                    }
                }
            }
            item {
                IconButton(
                    onClick = {
                        // Logic to attach an image (e.g., open image picker)
                        imageUrls.add("https://your-placeholder-image-url.com")


                    },
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Image")
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                // Handle save logic here
                val newCar = Car(
                    make = make,
                    model = model,
                    year = year.toIntOrNull() ?: 0,
                    mileage = mileage.toIntOrNull() ?: 0,
                    description = description,
                    imageUrls = imageUrls
                )
                // Navigate back after saving
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (car == null) "Add Car" else "Save Changes")
        }
    }
}

