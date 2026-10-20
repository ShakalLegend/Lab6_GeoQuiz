package com.example.lab6_geoquiz

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab6_geoquiz.ui.theme.Lab6_GeoQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab6_GeoQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    onScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Question(
    val quest: String,
    val answer: Boolean,
)

@Composable
fun onScreen(modifier: Modifier = Modifier) {
    var questions = listOf(
        Question("Canberra is the capital of Australia.",true),
        Question("The Pacific Ocean is larger than the Atlantic Ocean.",true),
        Question("The Suez Canal connects the Red Sea and the Indian Ocean.",false),
        Question("The source of the Nile River is in Egypt.",false),
        Question("The Amazon River is the longest river in the Americas.",true),
        Question("Lake Baikal is the world's oldest and deepest freshwater lake.",true),
    )
    var round by remember { mutableStateOf(0) }
    var question by remember { mutableStateOf(questions[round].quest)}
    var correct by remember { mutableStateOf(0) }
    var canGetAnswer by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()){
        Column() {
            Text(question, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                textAlign = TextAlign.Center
            )
            if (canGetAnswer){
                Row(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement
                        .SpaceBetween
                ) {
                    Button(onClick = {
                        if (false == questions[round].answer) {
                            correct += 1
                        }
                        canGetAnswer = false
                    }, modifier = Modifier.padding(10.dp)) {Text("False")}
                    Button(onClick = {
                        if (true == questions[round].answer) {
                            correct += 1
                        }
                        canGetAnswer = false
                    }, modifier = Modifier.padding(10.dp)) {Text("True") }
                }
            }
            if (!canGetAnswer) {
                Button(onClick = {
                    if (round < 5) {
                        round+=1
                        question = questions[round].quest
                        canGetAnswer = true
                    }
                    else {
                        showDialog = true
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), ) {if (round < 5) Text("Next") else Text("Итоги")}
            }
            Text(round.toString() + " раунд")
            Text(correct.toString() + " правильных ответов")
            Text(canGetAnswer.toString() + " правильных ответов")
            Text(showDialog.toString() + " правильных ответов")
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Результаты") },
                    text = { Text(correct.toString() + " правильных ответов") },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            correct = 0
                            round = 0
                            canGetAnswer = true
                        })
                        {
                            Text("Заново")
                        }
                    }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab6_GeoQuizTheme {
        onScreen()
    }
}