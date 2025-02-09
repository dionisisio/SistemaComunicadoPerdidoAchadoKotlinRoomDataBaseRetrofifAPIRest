package bambi.neves.eduardo.Paginas.Usuario

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import bambi.neves.eduardo.Retrofite.Dados.Usuario.UsuarioDTO
import bambi.neves.eduardo.R
import bambi.neves.eduardo.Retrofite.ViewModel.Usuario.UsuarioRetrofitViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaUsuariosScreen(navController: NavHostController, usuarioktorViewModel: UsuarioRetrofitViewModel = viewModel()) {

   usuarioktorViewModel.carregarUsuarios()
    val usuarios by usuarioktorViewModel.usuarioslista

    Scaffold(

        topBar = {
            TopAppBar(
                title = {

                        Text(
                            text = "Lista de Usuários",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background
                        )

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
                        onClick = { navController.popBackStack() },
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
                }
            }
        }


    )


    { innerPadding ->
        if (usuarios.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.Center))
            }
        } else {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(usuarios) { usuario ->
                    UsuarioItem(usuario)
                }
            }
        }
    }
}

@Composable
fun UsuarioItem(usuario: UsuarioDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nome: ${usuario.nomeusuario}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Acesso: ${usuario.nomeacessousuario}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
