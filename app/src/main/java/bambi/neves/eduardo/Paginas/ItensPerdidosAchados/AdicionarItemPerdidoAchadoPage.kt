package bambi.neves.eduardo.Paginas.ItensPerdidosAchados

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import bambi.neves.eduardo.R
import bambi.neves.eduardo.Retrofite.ViewModel.ItemPerdidoAchado.ItemPerdidoAchadoRetrofitViewModel
import bambi.neves.eduardo.Retrofite.ViewModel.Usuario.UsuarioRetrofitViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarItemPerdidoAchadoPage (navController: NavHostController, usuarioRetrofitViewModel: UsuarioRetrofitViewModel) {

   val itemperdidoachadoRetrofitViewModel: ItemPerdidoAchadoRetrofitViewModel = viewModel()
    var nomeitemperdidoachado by remember { mutableStateOf("") }
    var descricaoitemperdidoachado by remember { mutableStateOf("") }
    var localitemperdidoachado by remember { mutableStateOf("") }
    var dataitemperdidoachado by remember { mutableStateOf("") }
    var verificaritemperdido by remember { mutableStateOf(false) }
    var observacaoentregaitemperdidoachado by remember { mutableStateOf("") }
    var verificarprocessoconcluidoitemperdido by remember { mutableStateOf(false) }


    dataitemperdidoachado = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    verificarprocessoconcluidoitemperdido=false

    var nomeitemValido by remember { mutableStateOf(true) }
    var descricaoitemValido by remember { mutableStateOf(true) }



    val usuarioAtual = usuarioRetrofitViewModel.usuarioAtual
    var idusuario by remember { mutableStateOf(0) }
    var mensagemErro by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    if (usuarioAtual != null) {
        idusuario = usuarioAtual.pkusuario
    }

    // Estado para controlar o DatePickerDialog
    var MostarDatePicker by remember { mutableStateOf(false) }
    val calendario = Calendar.getInstance()

    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Adicionar Item Perdido/Achado",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("homepageitemperdidoahado")
                          },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Adicionar",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top, // Itens alinhados ao topo
            horizontalAlignment = Alignment.Start
        ) {


            OutlinedTextField(
                value = nomeitemperdidoachado,
                onValueChange = { nomeitemperdidoachado = it },
                label = { Text("Nome do Item") },
                placeholder = { Text("Inserir o nome do item") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                isError = !nomeitemValido,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )


            OutlinedTextField(
                value = descricaoitemperdidoachado,
                onValueChange = { descricaoitemperdidoachado = it },
                label = { Text("Descrição") },
                placeholder = { Text("Descrição do item") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                isError = !descricaoitemValido,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = verificaritemperdido,
                    onCheckedChange = { verificaritemperdido = it },

                )
                Text(text = "Item Perdido?/Achado?",
                    color = Color.Black)
            }
            OutlinedTextField(
                value = localitemperdidoachado,
                onValueChange = { localitemperdidoachado = it },
                label = { Text("Local") },
                placeholder = { Text("Indica o local") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Data: $dataitemperdidoachado",
                    color = Color.Black,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
                )

                Button(
                    onClick = { MostarDatePicker = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Definir")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = observacaoentregaitemperdidoachado,
                onValueChange = { observacaoentregaitemperdidoachado = it },
                label = { Text("Observação") },
                placeholder = { Text("Coloca uma observação") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp), maxLines = 5,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Default
                )
            )


            Spacer(modifier = Modifier.height(16.dp))

            FloatingActionButton(
                onClick = {
                    if (nomeitemperdidoachado.isEmpty() || descricaoitemperdidoachado.isEmpty() || dataitemperdidoachado.isEmpty()) {
                        mensagemErro = "Os campos nome do item, descrição e data devem ser preenchidos!"
                        showErrorDialog = true
                    } else {

                        val dataAtual = dateFormatter.format(Date())


                        if ((dataitemperdidoachado.isNotEmpty() && dataitemperdidoachado > dataAtual)) {
                            mensagemErro = " a Data  não podem ser superior ao momento atual."
                            showErrorDialog = true
                        } else {

                            val dataAtual =
                                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                            itemperdidoachadoRetrofitViewModel.itemperdidoachadoEstadoEntity =
                                itemperdidoachadoRetrofitViewModel.itemperdidoachadoEstadoEntity.copy(
                                    pkitemperdidoachado = 0,
                                    fkpessoaregistoitemperdidoachado = idusuario,
                                    nomeitemperdidoachado = nomeitemperdidoachado,
                                    descricaoitemperdidoachado = descricaoitemperdidoachado,
                                    localitemperdidoachado = localitemperdidoachado,
                                    dataitemperdidoachado = dataitemperdidoachado,
                                    verificaritemperdido = verificaritemperdido,
                                    observacaoentregaitemperdidoachado = observacaoentregaitemperdidoachado,
                                    verificarprocessoconcluidoitemperdido = verificarprocessoconcluidoitemperdido

                                )
                            itemperdidoachadoRetrofitViewModel.RegistarItemPerdidoAchado { sucesso, mensagem ->
                                if (sucesso) {
                                    mensagemErro = mensagem
                                    showErrorDialog = true
                                    navController.navigate("homepageitemperdidoahado")
                                } else {
                                    mensagemErro = mensagem
                                    showErrorDialog = true
                                }
                            }
                        }
                    }

                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
                    .width(200.dp)
                    .height(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Adicionar",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }
            }


            if (MostarDatePicker) {
                DatePickerDialog(
                    LocalContext.current,
                    { _, year, month, dayOfMonth ->
                        dataitemperdidoachado = dateFormatter.format(Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }.time)
                    },
                    calendario.get(Calendar.YEAR),
                    calendario.get(Calendar.MONTH),
                    calendario.get(Calendar.DAY_OF_MONTH)
                ).show()
                MostarDatePicker = false
            }
        }
    }

// Exibição de erro caso a criação de conta falhe
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text(text = "Alerta") },
            text = { Text(mensagemErro) },
            confirmButton = {
                Button(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}
