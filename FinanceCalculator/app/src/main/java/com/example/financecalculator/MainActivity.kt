package com.example.financecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financecalculator.ui.theme.FinanceCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanceCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White
                ) { innerPadding ->
                    FinanceCalculator(
                        //name = "Android",
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color.White)
                    )
                }
            }
        }
    }
}

@Composable
fun FinanceCalculator(modifier: Modifier = Modifier){
    var originalHomePrice by remember { mutableStateOf("") }
    var monthlyMortgagePayment by remember { mutableStateOf("") }
    //var
    //var timeRemaining by remember { mutableStateOf("") }
    var monthCountSinceFirstPurchase by remember { mutableStateOf("") }
    var outputResult by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mortgage Calculator", style = MaterialTheme.typography.headlineLarge.copy(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE)
            //color = Color.White
            ),
            modifier = Modifier.background(Color.White)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {

            //Input for the original home price
            OutlinedTextField(
                value = originalHomePrice,
                onValueChange = { originalHomePrice = it },
                label = { Text("Original Price of Home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6200EE),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF6200EE),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF6200EE),
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White

                )
            )

            //Input for monthly mortgage expense
            OutlinedTextField(
                value = monthlyMortgagePayment,
                onValueChange = { monthlyMortgagePayment = it },
                label = { Text("Monthly Mortgage Payment") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6200EE),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF6200EE),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF6200EE),
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White

                )
            )

            //Input the number of months since the original purchase of the home
            OutlinedTextField(
                value = monthCountSinceFirstPurchase,
                onValueChange = { monthCountSinceFirstPurchase = it },
                label = { Text("Months Since Original Purchase of Home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6200EE),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF6200EE),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFF6200EE),
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White

                )
            )

            //Button to Calculate Results
            Button(
                onClick = {
                    outputResult = calcRemainderPayment(
                        originalHomePrice.toDoubleOrNull() ?: 0.0,
                        monthlyMortgagePayment.toDoubleOrNull() ?: 0.0,
                        monthCountSinceFirstPurchase.toIntOrNull() ?: 0
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Calculate Results",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (outputResult.isNotEmpty()) {
                Text(
                    outputResult,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            //Spacer(modifier = Modifier.weight(1f))
        }

        Image(
            painter = painterResource(id = R.drawable.house_picture),
            contentDescription = "House Art",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Fit
        )

    }

}

fun calcRemainderPayment(originalHomePrice: Double, monthlyMortgagePayment: Double, monthCountSinceFirstPurchase: Int): String {
    if (originalHomePrice == 0.0 || monthlyMortgagePayment == 0.0 || monthCountSinceFirstPurchase < 0) {
        return "ERROR: Invalid value(s) entered"
    }

    val totalMonthsRequired = (originalHomePrice / monthlyMortgagePayment).toInt()
    val monthsOfPaymentLeft = totalMonthsRequired - monthCountSinceFirstPurchase
    val remainingBalence = monthsOfPaymentLeft * monthlyMortgagePayment
    val yearsOfPaymentLeft = monthsOfPaymentLeft / 12

    return if (monthsOfPaymentLeft > 0){
        "Remaining Time for Full Payment: $monthsOfPaymentLeft months or $yearsOfPaymentLeft years \n\nRemaining Amount for Full Payment: $${"%.2f".format(remainingBalence)}"
    }else{
        "The home is fully paid for!"
    }
}

@Preview(showBackground = true)
@Composable
fun FinanceCalculatorPreview() {
    FinanceCalculatorTheme {
        FinanceCalculator()
    }
}

//Image Source: https://www.pngkey.com/detail/u2q8a9i1o0e6q8t4_house-png-transparent-background-house-clipart/

