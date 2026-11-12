package com.example.countercompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countercompose.ui.theme.CounterComposeTheme

// Stateful: menghubungkan UI dengan ViewModel
@Composable
fun CounterApp(
    modifier: Modifier = Modifier,
    viewModel: CounterViewModel = viewModel()
) {
    val counter by viewModel.counter
    CounterScreen(
        counter = counter,
        onIncrement = { viewModel.increment() },
        onDecrement = { viewModel.decrement() },
        onReset = { viewModel.reset() },
        modifier = modifier
    )
}

// Stateless untuk counter. State nama dipegang di sini (challenge)
@Composable
fun CounterScreen(
    counter: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    // nameInput = isi form yang sedang diketik
    // savedName = nama yang sudah ditekan SIMPAN (yang ditampilkan)
    var nameInput by rememberSaveable { mutableStateOf("") }
    var savedName by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Counter App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Tampil di bawah judul hanya jika nama sudah disimpan
        if (savedName.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Nama: $savedName",
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        CounterDisplay(
            value = counter,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CounterButtons(
            onDecrement = onDecrement,
            onIncrement = onIncrement,
            onReset = onReset
        )

        // Form input nama di bawah tombol RESET
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Nama") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Tombol SIMPAN di bawah form nama
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { savedName = nameInput },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SIMPAN")
        }
    }
}

@Composable
fun CounterDisplay(value: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(
            color = Color(0xFFF5F5F5),
            shape = RoundedCornerShape(16.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Nilai Counter",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )
        Text(
            text = value.toString(),
            fontSize = 120.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CounterButtons(
    onDecrement: () -> Unit = {},
    onIncrement: () -> Unit = {},
    onReset: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onDecrement,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336)
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_remove),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text("KURANG")
            }
            Button(
                onClick = onIncrement,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text("TAMBAH")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onReset,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_refresh),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            Text("RESET")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CounterScreenPreview() {
    CounterComposeTheme {
        CounterScreen(
            counter = 7,
            onIncrement = {},
            onDecrement = {},
            onReset = {}
        )
    }
}