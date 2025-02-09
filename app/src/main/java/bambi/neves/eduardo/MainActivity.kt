package bambi.neves.eduardo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import bambi.neves.eduardo.Paginas.ItensPerdidosAchados.ActualizarItemPerdidoAchadoPage
import bambi.neves.eduardo.Paginas.ItensPerdidosAchados.AdicionarItemPerdidoAchadoPage
import bambi.neves.eduardo.Paginas.ItensPerdidosAchados.DetalhesItemPerdidosAchados
import bambi.neves.eduardo.Paginas.RoomDataBaseMemoriaPersistente.ListaFavoritoPage
import bambi.neves.eduardo.Paginas.Usuario.AdicionarUsuarioScreen
import bambi.neves.eduardo.Paginas.Usuario.LoginScreen
import bambi.neves.eduardo.Paginas.SplashPage
import bambi.neves.eduardo.Paginas.Usuario.AtualizarUsuarioScreen
import bambi.neves.eduardo.Retrofite.Dados.ItensPerdidosAchados.ItemPerdidoAchadosDTO
import bambi.neves.eduardo.RoomDataBase.DatabaseInstance
import bambi.neves.eduardo.RoomDataBase.UsuarioRepositorio
import bambi.neves.eduardo.Retrofite.ViewModel.Usuario.UsuarioRetrofitViewModel
import bambi.neves.eduardo.RoomDataBase.Repositorios.ItemPerdidoAchadoRepositorio
import bambi.neves.eduardo.RoomDataBase.ViewModel.ItemPerdidoAchadoRoomViewModel
import bambi.neves.eduardo.ui.theme.GestorPerdidosAchadosTheme
import gestortarefas.bambi.eduardo.Frontend.Paginas.HomePageItemPerdidosAchados
import bambi.neves.eduardo.RoomDataBase.ViewModel.UsuarioRoomViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestorPerdidosAchadosTheme {
                var MostarTelaSplash by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(3000) // Aguarda 3 segundos
                    MostarTelaSplash = false
                }

                if (MostarTelaSplash) {
                    SplashPage() // Mostra a tela de splash
                } else {

// Criar a instância do banco de dados
                    var database = DatabaseInstance.getDatabase(applicationContext)

                    // Criar os repositórios
                   var usuariorepositorio = UsuarioRepositorio(database.usuarioDao())
                    var itemrepositorio = ItemPerdidoAchadoRepositorio(database.itemperdidoachadoDao())

                    // Instanciar os ViewModel
                    val usuarioroomViewModel = UsuarioRoomViewModel(usuariorepositorio)
                    val itemperdidoachadoroomViewModel = ItemPerdidoAchadoRoomViewModel(itemrepositorio)

                    usuarioroomViewModel.carregarUsuarioporID()
                    NavigationComponent(viewModel(), usuarioroomViewModel,itemperdidoachadoroomViewModel);

                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationComponent(
    usuarioretrofitViewModel: UsuarioRetrofitViewModel = viewModel(),
    usuarioRoomViewModel: UsuarioRoomViewModel,
    itemPerdidoAchadoRoomViewModel: ItemPerdidoAchadoRoomViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "loginpage") {
        //Usuario
        composable("loginpage") { LoginScreen(navController, usuarioretrofitViewModel, usuarioRoomViewModel) }
        composable("criarusuariopage") { AdicionarUsuarioScreen( navController, usuarioretrofitViewModel  )  }
        composable("criarusuariopage") { AdicionarUsuarioScreen( navController, usuarioretrofitViewModel  )  }
        composable("actualizarusuariopage") { AtualizarUsuarioScreen(navController, usuarioretrofitViewModel ) }

        // Lista Favoritos


        composable("listafavoritopage") { ListaFavoritoPage(navController, usuarioRoomViewModel,itemPerdidoAchadoRoomViewModel) }

        // Itens Perdidos e Achados
        composable("homepageitemperdidoahado") { HomePageItemPerdidosAchados(navController, viewModel(), usuarioretrofitViewModel,itemPerdidoAchadoRoomViewModel) }
        composable("adicionaritemperdidoachado") { AdicionarItemPerdidoAchadoPage(navController, usuarioretrofitViewModel ) }
        composable(
            route = "detalhesdoacaopage/{itemId}/{idpagina}",
            arguments = listOf(
                navArgument("itemId") { type = NavType.IntType },
                navArgument("idpagina") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
            val idpagina = backStackEntry.arguments?.getInt("idpagina") ?: 0
            DetalhesItemPerdidosAchados(
                navController = navController,
                itemId = itemId,
                idpagina = idpagina,
                itemPerdidoAchadoViewModel = viewModel(),
                usuarioRetrofitViewModel = usuarioretrofitViewModel,
                itemPerdidoAchadoRoomViewModel = itemPerdidoAchadoRoomViewModel
            )
        }
        composable("editaritemperdidoachadopage/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")?.toInt() ?: 0
            ActualizarItemPerdidoAchadoPage(navController,itemId = itemId, viewModel(), usuarioretrofitViewModel)
        }





    }
}
