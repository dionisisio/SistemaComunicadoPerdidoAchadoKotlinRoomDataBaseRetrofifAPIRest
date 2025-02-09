package bambi.neves.eduardo.Paginas.RoomDataBaseMemoriaPersistente


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import bambi.neves.eduardo.Paginas.Componentes.CartaoItemPerdidosAchadosRoomDataBase
import bambi.neves.eduardo.Paginas.Componentes.CartaoUsuarioSessaoRoomDataBase
import bambi.neves.eduardo.R
import bambi.neves.eduardo.RoomDataBase.ViewModel.ItemPerdidoAchadoRoomViewModel
import bambi.neves.eduardo.RoomDataBase.ViewModel.UsuarioRoomViewModel

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bambi.neves.eduardo.Paginas.Componentes.CartaocsUsuarioItemPerdidosAchadosLista
import bambi.neves.eduardo.Retrofite.ViewModel.ItemPerdidoAchado.ItemPerdidoAchadoRetrofitViewModel
import bambi.neves.eduardo.Retrofite.ViewModel.Usuario.UsuarioRetrofitViewModel
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaFavoritoPage(navController: NavHostController,  usuarioRoomViewModel: UsuarioRoomViewModel,itemPerdidoAchadoRoomViewModel: ItemPerdidoAchadoRoomViewModel) {
    var id by remember { mutableStateOf(0) }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        delay(1000) // Substitua 'tempoEmMilissegundos' pelo valor desejado

        itemPerdidoAchadoRoomViewModel.carregarListaTodosItensPerdidoAchado()
    }
    val listafavoritosItemperdidosAchados by itemPerdidoAchadoRoomViewModel.listaitemroomdatabaseActual


    val usuarioRoomDataBaseAtual = usuarioRoomViewModel.usuarioActual
    if(usuarioRoomDataBaseAtual!=null)
    {
        id = usuarioRoomDataBaseAtual.value?.id ?: 0
        email = usuarioRoomDataBaseAtual.value?.emailusuario ?: ""
        senha = usuarioRoomDataBaseAtual.value?.senhausuario ?: ""
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                    text = "Dados em Cache (Usuário e Itens)",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background)
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
        Box(modifier = Modifier.padding(paddingValues))
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CartaoUsuarioSessaoRoomDataBase(email, senha)

                if (listafavoritosItemperdidosAchados.isEmpty()) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "A lista  itens perdidos e achados favoritos encontra-se vazia.",
                            color = Color.DarkGray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "" +
                                    "Lista  itens perdidos e achados favoritos",
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentPadding = PaddingValues(10.dp)
                        ) {
                            items(listafavoritosItemperdidosAchados) { itemPerdidoAchadoEntity ->
                                if (itemPerdidoAchadoEntity != null) {
                                    CartaoItemPerdidosAchadosRoomDataBase(itemPerdidoAchadoEntity,
                                        modifier = Modifier
                                            .padding(bottom = 8.dp)
                                            .clickable {
                                                navController.navigate("detalhesdoacaopage/${itemPerdidoAchadoEntity.pkitemperdidoachadoroom}/2")

                                            }
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }

    }

}




