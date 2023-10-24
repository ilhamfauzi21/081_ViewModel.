package com.example.myapplication5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication5.R
import com.example.myapplication5.ui.theme.MyApplication5Theme
import com.example.pertemuan5.Data.DataForm
import com.example.myapplication5.Data.Datasource
import com.example.myapplication5.Data.Datasource.jenis
import com.example.myapplication5.ui.theme.MyApplication5Theme
import com.example.pertemuan5.CobaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TampilanLayout()
                }
            }
        }
    }
}

@Composable
fun TampilanLayout(
    modifier: Modifier =Modifier
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ){
            TampilanForm()
        }
    }
}
@OptIn(ExperimentalMaterial3Api ::class)
@Composable
fun TampilanForm(cobaViewModel: CobaViewModel = viewModel()){
    var textNama by remember{ mutableStateOf("") }
    var textTlp by remember{ mutableStateOf("") }
    var textAlt by remember{ mutableStateOf("") }

    val context = LocalContext.current
    val dataclass : DataForm
    val uiState by cobaViewModel.uiState.collectAsState()
    dataclass = uiState;

    OutlinedTextField(
        value = textNama,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Nama Lengkap")},
        onValueChange ={
            textNama = it
        }
    )
    OutlinedTextField(
        value = textTlp,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Telpon")},
        onValueChange ={
            textTlp = it
        }
    )
    OutlinedTextField(
        value = textAlt,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Alamatnya")},
        onValueChange ={
            textAlt = it
        }
    )


    SelectJK(options = jenis.map { id -> context.resources.getString(id) },
        onSelectionChanged = {
            cobaViewModel.setJenisK(it)
        })
    Button(modifier = Modifier.fillMaxWidth(),
        onClick = {
            cobaViewModel.readData(textNama,textTlp,textAlt,dataclass.sex)
        }
    ) {
        Text(
            text =stringResource(R.string.submit),
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(100.dp))
    Texthasil(
        namanya = cobaViewModel.namaUsr,
        telponnya =cobaViewModel.noTlp ,
        alamatnya = cobaViewModel.alamatUsr,
        jenisnya = cobaViewModel.jenisKL


    )
}

@Composable
fun SelectJK(
    options: List<String>,
    onSelectionChanged:(String) -> Unit ={}
){
    var selectedValue by rememberSaveable{ mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        options.forEach { item ->
            Row (
                modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                )
                Text(item)
            }
        }
    }
}
@Composable
fun Texthasil(namanya:String,alamatnya: String, telponnya:String,jenisnya:String){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text ="Nama :" + namanya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )
        Text(
            text ="Telepon :" + telponnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(
            text ="Alamatnya :" + alamatnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(
            text ="Jenis Kelamin :" + jenisnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp))

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplication5Theme {
        TampilanLayout()
}
}