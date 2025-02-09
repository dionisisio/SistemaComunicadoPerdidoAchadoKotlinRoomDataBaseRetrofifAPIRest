package gestortarefas.bambi.eduardo.Frontend.Paginas

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import bambi.neves.eduardo.Paginas.Componentes.CartaocsUsuarioItemPerdidosAchadosLista
import bambi.neves.eduardo.R
import bambi.neves.eduardo.Retrofite.ViewModel.ItemPerdidoAchado.ItemPerdidoAchadoRetrofitViewModel
import bambi.neves.eduardo.Retrofite.ViewModel.Usuario.UsuarioRetrofitViewModel
import bambi.neves.eduardo.RoomDataBase.ViewModel.ItemPerdidoAchadoRoomViewModel
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageItemPerdidosAchados(navController: NavHostController,
                                itemPerdidoAchadoRetrofitViewModel: ItemPerdidoAchadoRetrofitViewModel,
                                usuarioRetrofitViewModel: UsuarioRetrofitViewModel,
                                itemPerdidoAchadoRoomViewModel: ItemPerdidoAchadoRoomViewModel
) {
    var mensagemErro by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }



    var inputusuarioitem by remember { mutableStateOf("") }
    val usuarioAtual = usuarioRetrofitViewModel.usuarioAtual
    var idusuario by remember { mutableStateOf(0) }

    if (usuarioAtual != null) {
        idusuario= usuarioAtual.pkusuario

    }
    itemPerdidoAchadoRetrofitViewModel.carregarcsListaUsuarioItensPerdidosAchados()
    val listausuariositem by itemPerdidoAchadoRetrofitViewModel.csusarioitemperdidoachadolista




    val filteredList = listausuariositem.filter { usuarioitem ->
                usuarioitem.nomeusuario.contains(inputusuarioitem, ignoreCase = true) ||
                usuarioitem.nomeitemperdidoachado.contains(inputusuarioitem, ignoreCase = true) ||
                usuarioitem.descricaoitemperdidoachado.contains(inputusuarioitem, ignoreCase = true) ||
                usuarioitem.observacaoentregaitemperdidoachado.contains(inputusuarioitem, ignoreCase = true) ||
                usuarioitem.dataitemperdidoachado.contains(inputusuarioitem, ignoreCase = true)
    }


    val sortedList = filteredList.sortedByDescending { usuarioitem ->
        val date = usuarioitem.dataitemperdidoachado.split("/").takeIf { it.size == 3 }?.let {
            Calendar.getInstance().apply {
                set(
                    it[2].toInt(),
                    it[1].toInt() - 1,
                    it[0].toInt()
                ) // Convertendo para um objeto Date
            }
        }
        date?.time ?: Date()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Itens Perdidos e Achados",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background
                        )

                        if (usuarioAtual != null) {
                            Text(
                                text = "Usuário logado: ${usuarioAtual.nomeusuario}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                },
                actions = {
                    Row( modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.Top), // Alinha a linha no canto inferior direito
                        horizontalArrangement = Arrangement.spacedBy(2.dp), // Espaçamento entre os botões
                        verticalAlignment = Alignment.Top // Alinha os botões verticalmente no centro da linha
                    ) {

                        IconButton(onClick = {

                            navController.navigate("actualizarusuariopage") { // Substitua pelo nome correto da sua tela de login
                                popUpTo("homePage") {
                                    inclusive = true
                                } // Fecha a página de home ao fazer logout
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.setting), // Coloque o ícone de logout adequado
                                contentDescription = "Configuração",
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        IconButton(onClick = {
                            usuarioRetrofitViewModel.FazerLogout()
                            navController.navigate("loginpage") { // Substitua pelo nome correto da sua tela de login
                                popUpTo("homePage") {
                                    inclusive = true
                                } // Fecha a página de home ao fazer logout
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.logout2), // Coloque o ícone de logout adequado
                                contentDescription = "Logout",
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp) // Espaçamento para evitar sobreposição com a barra de navegação
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.BottomEnd), // Alinha a linha no canto inferior direito
                    horizontalArrangement = Arrangement.spacedBy(8.dp), // Espaçamento entre os botões
                    verticalAlignment = Alignment.CenterVertically // Alinha os botões verticalmente no centro da linha
                ) {
                    FloatingActionButton(
                        onClick = { navController.navigate("adicionaritemperdidoachado") },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.adicionar),
                            contentDescription = "Adicionar",
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    FloatingActionButton(
                        onClick = { navController.navigate("listafavoritopage") },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.favorite),
                            contentDescription = "Favoritos",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            TextField(
                value = inputusuarioitem,
                onValueChange = { inputusuarioitem = it },
                label = { Text("Pesquisar") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onTertiary, // Cor de fundo
                    focusedLabelColor = Color.Gray, // Cor do label quando em foco (Cinza)
                    unfocusedLabelColor = Color.Gray, // Cor do label quando não em foco (Cinza)
                    focusedIndicatorColor = MaterialTheme.colorScheme.primaryContainer, // Cor da linha de indicação quando em foco
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f), // Cor da linha de indicação quando não em foco
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                )
            )

            if (sortedList.isEmpty()) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Lista vazia",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(sortedList) { usuarioitem ->
                        CartaocsUsuarioItemPerdidosAchadosLista(usuarioitem,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .clickable {
                                    itemPerdidoAchadoRoomViewModel.ApagarItemRomm()
                                    navController.navigate("detalhesdoacaopage/${usuarioitem.pkitemperdidoachado}/1")

                                }
                        )
                    }
                }
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

