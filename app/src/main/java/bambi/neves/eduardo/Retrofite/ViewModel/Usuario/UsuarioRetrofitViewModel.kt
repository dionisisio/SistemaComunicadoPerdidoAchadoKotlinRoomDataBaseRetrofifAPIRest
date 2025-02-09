package bambi.neves.eduardo.Retrofite.ViewModel.Usuario


import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bambi.neves.eduardo.Retrofite.Dados.RespostaAPI
import bambi.neves.eduardo.Retrofite.Dados.Usuario.UsuarioAPI
import bambi.neves.eduardo.Retrofite.Dados.Usuario.UsuarioDTO
import bambi.neves.eduardo.Retrofite.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class UsuarioRetrofitViewModel : ViewModel() {


    private val usuarioServiceRetrofit = RetrofitInstance.createService(UsuarioAPI::class.java)

    private val _usuarioslista = mutableStateOf<List<UsuarioDTO>>(emptyList())
    val usuarioslista: State<List<UsuarioDTO>> = _usuarioslista

    // Em UsuarioViewModel
    // Estado do usuário atual (sessão global)
    var usuarioAtual: UsuarioDTO? by mutableStateOf(null) // Usuário logado, ou null caso não tenha sessão


    // Estado do usuário atual
    var usuarioEstadoEntity by mutableStateOf(UsuarioDTO(pkusuario = 0, nomeusuario = "", nomeacessousuario = "", palavrapasseusuario = "", numerotelefoneusuario = "", enderecousuario = ""))


    private val _erro = mutableStateOf<String?>(null)
    val erro: State<String?> = _erro



    fun carregarUsuarios() {
        usuarioServiceRetrofit.obterListaUsuarios().enqueue(object : Callback<List<UsuarioDTO>> {
            override fun onResponse(call: Call<List<UsuarioDTO>>, response: Response<List<UsuarioDTO>>) {
                if (response.isSuccessful) {
                    _usuarioslista.value = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<UsuarioDTO>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    fun buscarUsuario(nomeAcesso: String, palavrapasse: String) {
        viewModelScope.launch {
            try {
                // Usando a interface consolidada para buscar o usuário
                val response = usuarioServiceRetrofit.obterUsuarioPorNomeAcessoPlavraPasse(nomeAcesso, palavrapasse).awaitResponse()
                if (response.isSuccessful) {
                  usuarioAtual = response.body()
                } else {
                    _erro.value = "Usuário não encontrado"
                }
            } catch (e: Exception) {
                _erro.value = "Erro ao buscar usuário"
            }
        }
    }

    // Função para registrar um novo usuário com e-mail e senha
    fun RegistarUsuario(onResult: (Boolean, String) -> Unit) {

        if (usuarioEstadoEntity.nomeacessousuario.isNotEmpty() && usuarioEstadoEntity.palavrapasseusuario.isNotEmpty() && usuarioEstadoEntity.nomeusuario.isNotEmpty()) {
            viewModelScope.launch {
                // Verifica se o usuário já existe
                val response = usuarioServiceRetrofit.obterUsuarioPorNomeAcesso(usuarioEstadoEntity.nomeacessousuario).awaitResponse()
                if (!response.isSuccessful)
                {

                    usuarioServiceRetrofit.adicionarUsuario(usuarioEstadoEntity).enqueue(object : Callback<RespostaAPI> {
                        override fun onResponse(call: Call<RespostaAPI>, response: Response<RespostaAPI>) {
                            if (response.isSuccessful) {
                                val resposta = response.body()
                                onResult(true, resposta?.mensagem ?: "Usuário registado com sucesso.")
                            } else {
                                onResult(false, "Erro ao criar usuário: ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<RespostaAPI>, t: Throwable) {
                            onResult(false, "Erro ao registar: ${t.message}")
                        }
                    })

                }
                else
                {
                    onResult(false, "E-mail já cadastrado")
                }


                    }
        } else {
            onResult(false, "Todos os campos devem ser preenchidos")
        }
    }


    fun actualizarUsuario(
        id: Int,
        usuarioDTO: UsuarioDTO,
        onSucesso: (String) -> Unit,
        onErro: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = usuarioServiceRetrofit.atualizarUsuario(id, usuarioDTO)
                if (response.isSuccessful) {
                    val resposta = response.body()
                    onSucesso(resposta?.mensagem ?: "Usuário atualizado com sucesso!")
                } else {
                    onErro("Erro ao atualizar o usuário. Código: ${response.code()}")
                }
            } catch (e: Exception) {
                onErro("Erro ao conectar ao servidor: ${e.localizedMessage}")
            }
        }
    }



    fun FazerLogin(onResult: (Boolean, String) -> Unit) {
        val email = usuarioEstadoEntity.nomeusuario
        val senha = usuarioEstadoEntity.palavrapasseusuario

        if (email.isNotEmpty() && senha.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    val response = usuarioServiceRetrofit.obterUsuarioPorNomeAcessoPlavraPasse(email, senha).awaitResponse()

                    if (response.isSuccessful) {
                        usuarioAtual = response.body()
                        onResult(true, "Login realizado com sucesso!")
                    } else {
                        val erroMsg = response.errorBody()?.string() ?: "Credenciais inválidas"
                        onResult(false, erroMsg)
                    }
                } catch (e: Exception) {
                    onResult(false, "Erro de conexão: ${e.localizedMessage}")
                }
            }
        } else {
            onResult(false, "E-mail ou senha não informados")
        }
    }

    // Função para fazer logout, removendo o usuário logado da sessão
    fun FazerLogout() {
        usuarioAtual = null // Remove a sessão de usuário logado
    }
}
