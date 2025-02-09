package bambi.neves.eduardo.Paginas.ItensPerdidosAchados

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import bambi.neves.eduardo.R
import bambi.neves.eduardo.Retrofite.Dados.Usuario.UsuarioDTO
import bambi.neves.eduardo.Retrofite.ViewModel.ItemPerdidoAchado.ItemPerdidoAchadoRetrofitViewModel
import bambi.neves.eduardo.Retrofite.ViewModel.Usuario.UsuarioRetrofitViewModel
import bambi.neves.eduardo.RoomDataBase.Enitdades.ItemPerdidoAchadoEntity
import bambi.neves.eduardo.RoomDataBase.UsuarioEntity
import bambi.neves.eduardo.RoomDataBase.ViewModel.ItemPerdidoAchadoRoomViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesItemPerdidosAchados (
    navController: NavHostController,
    itemId: Int, idpagina:Int,
    itemPerdidoAchadoViewModel: ItemPerdidoAchadoRetrofitViewModel = viewModel(),
    usuarioRetrofitViewModel: UsuarioRetrofitViewModel,
    itemPerdidoAchadoRoomViewModel: ItemPerdidoAchadoRoomViewModel
) {

    var mensagemErro by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var itemroomdatabaseactual by remember {mutableStateOf<ItemPerdidoAchadoEntity?>(null)}

    val usuarioAtual = usuarioRetrofitViewModel.usuarioAtual
    val idusuario = usuarioAtual?.pkusuario

     // Buscar o item perdido/achado pelo ID
    LaunchedEffect(itemId) {
        itemPerdidoAchadoViewModel.buscarUsuarioItem(itemId)
    }

    // Observar o estado do item buscado
    val csUsuariosItensPerdidosAchadosDTO = itemPerdidoAchadoViewModel.usuarioItem.value
    var pkitemperdidoachadoroomPesquisa by remember { mutableStateOf(0) }
    var pkitemperdidoachadoroomdatabase by remember { mutableStateOf(0) }
    var totalcarremnto by remember { mutableStateOf(0) }

    pkitemperdidoachadoroomdatabase=0;

    var MostrarMensagemdeConfirmacaodeEliminacao by remember { mutableStateOf(false) }
    var tipodescricao by remember { mutableStateOf("") }
    var tipopessoa by remember { mutableStateOf("") }
    if (csUsuariosItensPerdidosAchadosDTO != null) {

        pkitemperdidoachadoroomPesquisa = csUsuariosItensPerdidosAchadosDTO.pkitemperdidoachado
        LaunchedEffect(Unit) {
                if(pkitemperdidoachadoroomPesquisa>0) {
                    itemPerdidoAchadoRoomViewModel.carregarItemPkItemPerdidoAchado( pkitemperdidoachadoroomPesquisa)
            }
        }
        if(itemPerdidoAchadoRoomViewModel.itemroomdatabaseActual.value!=null) {
            itemroomdatabaseactual = itemPerdidoAchadoRoomViewModel.itemroomdatabaseActual.value
            pkitemperdidoachadoroomdatabase = itemroomdatabaseactual?.pkitemperdidoachadoroom ?: 0
            mensagemErro =pkitemperdidoachadoroomdatabase.toString()
            showErrorDialog=true
            showErrorDialog=false
        }

        if(csUsuariosItensPerdidosAchadosDTO.verificaritemperdido) {
            tipodescricao ="Perdeu-se: "
            tipopessoa="Proprietário: "
        } else {
            tipodescricao ="Foi achado: "
            tipopessoa="Em posse de: "

        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (csUsuariosItensPerdidosAchadosDTO != null) {
                        Text(
                            text = tipodescricao + csUsuariosItensPerdidosAchadosDTO.nomeitemperdidoachado,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background
                        )

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
                    // Botão Voltar
                    FloatingActionButton(
                        onClick = {
                            if(idpagina==1) {
                                navController.navigate("homepageitemperdidoahado")
                            } else if(idpagina==2) {
                                navController.navigate("listafavoritopage")
                            }
                                  },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Voltar",
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    if (csUsuariosItensPerdidosAchadosDTO != null) {
                        if(csUsuariosItensPerdidosAchadosDTO.fkpessoaregistoitemperdidoachado == idusuario) { // Botão Excluir
                            FloatingActionButton(
                                onClick = {
                                    // Exibe a confirmação de exclusão
                                    MostrarMensagemdeConfirmacaodeEliminacao = true
                                },
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.background,
                                shape = CircleShape
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.delete),
                                    contentDescription = "Excluir",
                                    modifier = Modifier.size(50.dp)
                                )
                            }

                            // Botão Editar
                            FloatingActionButton(
                                onClick = {
                                    if (csUsuariosItensPerdidosAchadosDTO != null) {

                                        // Navega para a tela de Editar
                                        navController.navigate("editaritemperdidoachadopage/${csUsuariosItensPerdidosAchadosDTO.pkitemperdidoachado}")
                                    }
                                }, // Seleciona a anotação para edição
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.background,
                                shape = CircleShape
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = "Editar",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                if (itemroomdatabaseactual != null)
                {
                    if (pkitemperdidoachadoroomdatabase > 0) {
                        Button(
                            onClick = {
                                var itemPerdidoAchadoEntity by mutableStateOf(
                                    itemroomdatabaseactual?.let {
                                        ItemPerdidoAchadoEntity(
                                            id = it.id,
                                            nomeitemperdidoachadoroom = it.nomeitemperdidoachadoroom,
                                            descricaoitemperdidoachadoroom = itemroomdatabaseactual!!.descricaoitemperdidoachadoroom,
                                            verificaritemperdidoroom = it.verificaritemperdidoroom,
                                            pkitemperdidoachadoroom = it.pkitemperdidoachadoroom
                                        )
                                    })

                                itemPerdidoAchadoEntity?.let {
                                    if(it.id>0 && it.nomeitemperdidoachadoroom.isNotEmpty() && it.descricaoitemperdidoachadoroom.isNotEmpty()&&it.pkitemperdidoachadoroom>0) {
                                        itemPerdidoAchadoRoomViewModel.Eliminar(it)
                                        itemPerdidoAchadoRoomViewModel.ApagarItemRomm()
                                       navController.navigate("detalhesdoacaopage/${it.pkitemperdidoachadoroom}/${idpagina}")
                                    }


                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error, // Usa a cor de erro do tema
                                contentColor = MaterialTheme.colorScheme.onError // Usa a cor do texto apropriada para o fundo de erro
                            )
                        ) {
                            Text(text = "Remover dos favoritos")
                        }


                    }
                }
                else {
                    Button(
                        onClick = {

                            var itemPerdidoAchadoEntity by mutableStateOf(
                                csUsuariosItensPerdidosAchadosDTO?.let {
                                    ItemPerdidoAchadoEntity(
                                        id = 0,
                                        nomeitemperdidoachadoroom = it.nomeitemperdidoachado,
                                        descricaoitemperdidoachadoroom = it.descricaoitemperdidoachado,
                                        verificaritemperdidoroom = it.verificaritemperdido,
                                        pkitemperdidoachadoroom = it.pkitemperdidoachado
                                    )
                                })

                            itemPerdidoAchadoEntity?.let {
                                itemPerdidoAchadoRoomViewModel.RegistarItemPerdidoAchado( it )
                                navController.navigate("detalhesdoacaopage/${it.pkitemperdidoachadoroom}/${idpagina}")

                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimaryContainer, // Usa a cor de erro do tema
                            contentColor = MaterialTheme.colorScheme.primary // Usa a cor do texto apropriada para o fundo de erro
                        )
                    )
                    {
                        Text(text = "Adicionar aos favoritos")
                    }
                }



                Spacer(modifier = Modifier.height(8.dp))
                if (csUsuariosItensPerdidosAchadosDTO != null) {
                    Text(
                        text = "descição : " + csUsuariosItensPerdidosAchadosDTO.descricaoitemperdidoachado,
                        fontSize = 20.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (csUsuariosItensPerdidosAchadosDTO != null) {
                    Text(
                        text = "local : " + csUsuariosItensPerdidosAchadosDTO.localitemperdidoachado,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                if (csUsuariosItensPerdidosAchadosDTO != null) {
                    Text(
                        text = "data : " + csUsuariosItensPerdidosAchadosDTO.dataitemperdidoachado,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                if (csUsuariosItensPerdidosAchadosDTO != null) {
                    Text(
                        text = tipopessoa+   csUsuariosItensPerdidosAchadosDTO.nomeusuario,
                        fontSize = 20.sp,
                        color = Color.Blue,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (csUsuariosItensPerdidosAchadosDTO != null) {
                    Text(
                        text = "Telefone: "+csUsuariosItensPerdidosAchadosDTO.numerotelefoneusuario,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (csUsuariosItensPerdidosAchadosDTO != null) {
                    Text(
                        text = "E-mail: "+csUsuariosItensPerdidosAchadosDTO.nomeacessousuario,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (csUsuariosItensPerdidosAchadosDTO != null) {
                    Text(
                        text = "Endereço: "+csUsuariosItensPerdidosAchadosDTO.enderecousuario,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.fillMaxWidth()
                    )
                }


                Spacer(modifier = Modifier.height(32.dp))
                if (csUsuariosItensPerdidosAchadosDTO != null) {
                    Text(
                        text = "Obs: " + csUsuariosItensPerdidosAchadosDTO.observacaoentregaitemperdidoachado,
                        fontSize = 15.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
        }

        // Exibindo o AlertDialog de confirmação
        if (MostrarMensagemdeConfirmacaodeEliminacao) {
            AlertDialog(
                onDismissRequest = { MostrarMensagemdeConfirmacaodeEliminacao = false },
                title = { Text(text = "Confirmar Exclusão") },
                text = { Text(text = "Você tem certeza que deseja excluir este registo?") },
                confirmButton = {
                    TextButton(
                        onClick = {

                            if (csUsuariosItensPerdidosAchadosDTO != null) {
                                itemPerdidoAchadoViewModel.eliminarItemPerdidoAchado(
                                    csUsuariosItensPerdidosAchadosDTO.pkitemperdidoachado,
                                    onSucesso = {
                                        if (itemroomdatabaseactual != null) {
                                            if (pkitemperdidoachadoroomdatabase > 0)
                                            {

                                                        var itemPerdidoAchadoEntity by mutableStateOf(
                                                            itemroomdatabaseactual?.let {
                                                                ItemPerdidoAchadoEntity(
                                                                    id = it.id,
                                                                    nomeitemperdidoachadoroom = it.nomeitemperdidoachadoroom,
                                                                    descricaoitemperdidoachadoroom = itemroomdatabaseactual!!.descricaoitemperdidoachadoroom,
                                                                    verificaritemperdidoroom = it.verificaritemperdidoroom,
                                                                    pkitemperdidoachadoroom = it.pkitemperdidoachadoroom
                                                                )
                                                            })

                                                        itemPerdidoAchadoEntity?.let {
                                                            if (it.id > 0 && it.nomeitemperdidoachadoroom.isNotEmpty() && it.descricaoitemperdidoachadoroom.isNotEmpty() && it.pkitemperdidoachadoroom > 0) {
                                                                itemPerdidoAchadoRoomViewModel.Eliminar(it)
                                                                itemPerdidoAchadoRoomViewModel.ApagarItemRomm()
                                                            }


                                                        }



                                            }
                                        }



                                        MostrarMensagemdeConfirmacaodeEliminacao = false
                                        mensagemErro = it
                                        showErrorDialog = true
                                        navController.navigate("homepageitemperdidoahado")
                                    },
                                    onErro = {
                                        mensagemErro = it
                                        showErrorDialog = true

                                    }
                                )
                            }

                        }
                    ) {
                        Text("Sim",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 18.sp
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { MostrarMensagemdeConfirmacaodeEliminacao = false }) {
                        Text("Não",
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 18.sp
                        )
                    }
                }
            )
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
