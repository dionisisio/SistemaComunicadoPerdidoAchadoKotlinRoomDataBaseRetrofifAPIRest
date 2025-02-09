package bambi.neves.eduardo.RoomDataBase.ViewModel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bambi.neves.eduardo.Retrofite.Dados.ItensPerdidosAchados.ItemPerdidoAchadosDTO
import bambi.neves.eduardo.RoomDataBase.UsuarioEntity
import bambi.neves.eduardo.RoomDataBase.UsuarioRepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.sql.SQLException

class UsuarioRoomViewModel(private val usuarioRepositorio: UsuarioRepositorio) : ViewModel() {

    // Usuário logado ou null caso não tenha sessão
    var usuarioAtual: UsuarioEntity? by mutableStateOf(null)
        private set

    // Estado do usuário atual
    var usuarioEstadoEntity by mutableStateOf(
        UsuarioEntity(id = 0, emailusuario = "", senhausuario = "")
    )
        private set


    private val _usuarioActual = mutableStateOf<UsuarioEntity?>(null)
    val usuarioActual: State<UsuarioEntity?> = _usuarioActual
    // Carrega o primeiro usuário salvo no banco
    fun carregarUsuarioporID() {
        viewModelScope.launch {
            try {
                val usuarioExistente = usuarioRepositorio.obterUsuarioPorID(1)
                if (usuarioExistente != null) {
                    _usuarioActual.value = usuarioExistente
                    println("Usuário carregado:")
                }
                else
                {
                    println("Erro de I/O ao carregar usuário:")
                }
            }
            catch (e: IOException) {
                println("Erro de I/O ao carregar usuário: ${e.message}")
            } catch (e: SQLException) {
                println("Erro de SQL ao carregar usuário: ${e.message}")
            } catch (e: Exception) {
                println("Erro inesperado ao carregar usuário: ${e.message}")
            }
        }
    }

    // Registra um novo usuário com e-mail e senha
    fun registrarUsuario(email: String, senha: String) {
        if (email.isNotEmpty() && senha.isNotEmpty()) {

            var usuarioAtuals: UsuarioEntity? by mutableStateOf(null)


            viewModelScope.launch {
                 try {
                    val usuarioExistente = usuarioRepositorio.obterUsuarioPorEmailESenha(email, senha)
                    if (usuarioExistente == null) {
                        val novoUsuario = UsuarioEntity(id = 0, emailusuario = email, senhausuario = senha)
                        usuarioRepositorio.Adicionar(novoUsuario)
                            } else
                            {
                        println("Usuário encontrado: ${usuarioExistente.emailusuario}")
                                 }
                } catch (e: Exception) {

                }
            }

        }
    }
}
