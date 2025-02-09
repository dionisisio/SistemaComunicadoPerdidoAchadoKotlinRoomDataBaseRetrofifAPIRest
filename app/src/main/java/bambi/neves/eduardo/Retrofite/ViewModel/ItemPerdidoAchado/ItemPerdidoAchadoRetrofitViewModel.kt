package bambi.neves.eduardo.Retrofite.ViewModel.ItemPerdidoAchado

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bambi.neves.eduardo.Retrofite.Dados.ItensPerdidosAchados.CsUsuariosItensPerdidosAcgadosDTO
import bambi.neves.eduardo.Retrofite.Dados.ItensPerdidosAchados.ItemPerdidoAchadoAPI
import bambi.neves.eduardo.Retrofite.Dados.ItensPerdidosAchados.ItemPerdidoAchadosDTO
import bambi.neves.eduardo.Retrofite.Dados.RespostaAPI
import bambi.neves.eduardo.Retrofite.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class ItemPerdidoAchadoRetrofitViewModel : ViewModel()
{
    private val itemPerdidoAchadoServiceRetrofit = RetrofitInstance.createService(
        ItemPerdidoAchadoAPI::class.java)

    private val _itemperdidoachadolista = mutableStateOf<List<ItemPerdidoAchadosDTO>>(emptyList())
    val itemperdidoachadolista: State<List<ItemPerdidoAchadosDTO>> = _itemperdidoachadolista


    private val _csusuariositemperdidoachadolista = mutableStateOf<List<CsUsuariosItensPerdidosAcgadosDTO>>(emptyList())
    val csusarioitemperdidoachadolista: State<List<CsUsuariosItensPerdidosAcgadosDTO>> = _csusuariositemperdidoachadolista

    private val _usuarioItem = mutableStateOf<CsUsuariosItensPerdidosAcgadosDTO?>(null)
    val usuarioItem: State<CsUsuariosItensPerdidosAcgadosDTO?> = _usuarioItem

    fun buscarUsuarioItem(pkitemperdidoachado: Int) {
        itemPerdidoAchadoServiceRetrofit.obterUsuarioItemPKitem(pkitemperdidoachado)
            .enqueue(object : Callback<CsUsuariosItensPerdidosAcgadosDTO> {
                override fun onResponse(
                    call: Call<CsUsuariosItensPerdidosAcgadosDTO>,
                    response: Response<CsUsuariosItensPerdidosAcgadosDTO>
                ) {
                    if (response.isSuccessful) {
                        _usuarioItem.value = response.body()
                    } else {
                        // Trate o caso de resposta não bem-sucedida, se necessário
                    }
                }

                override fun onFailure(call: Call<CsUsuariosItensPerdidosAcgadosDTO>, t: Throwable) {
                    // Trate falhas de rede ou outros erros, se necessário
                }
            })
    }

    private val _itemPerdidoAchado = mutableStateOf<ItemPerdidoAchadosDTO?>(null)
    val itemPerdidoAchado: State<ItemPerdidoAchadosDTO?> = _itemPerdidoAchado

    fun buscaritemperdidoachadopk(pkitemperdidoachado: Int) {
        itemPerdidoAchadoServiceRetrofit.obterItemPerdidoAchadoPK(pkitemperdidoachado)
            .enqueue(object : Callback<ItemPerdidoAchadosDTO> {
                override fun onResponse(
                    call: Call<ItemPerdidoAchadosDTO>,
                    response: Response<ItemPerdidoAchadosDTO>
                ) {
                    if (response.isSuccessful) {
                        _itemPerdidoAchado.value = response.body()
                    } else {
                        // Trate o caso de resposta não bem-sucedida, se necessário
                    }
                }

                override fun onFailure(call: Call<ItemPerdidoAchadosDTO>, t: Throwable) {
                    // Trate falhas de rede ou outros erros, se necessário
                }
            })
    }

    // Estado do usuário atual
    var itemperdidoachadoEstadoEntity by mutableStateOf(
        ItemPerdidoAchadosDTO(
            pkitemperdidoachado = 0,
            nomeitemperdidoachado = "",
            descricaoitemperdidoachado = "",
            localitemperdidoachado = "",
            dataitemperdidoachado = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            verificaritemperdido = false,
            fkpessoaregistoitemperdidoachado = 0,
            observacaoentregaitemperdidoachado = "",
            verificarprocessoconcluidoitemperdido = false
        )
    )

    private val _erro = mutableStateOf<String?>(null)
    val erro: State<String?> = _erro


    fun carregarcsListaUsuarioItensPerdidosAchados() {
        itemPerdidoAchadoServiceRetrofit.obterListaUsuariosItensPerdidosAchados().enqueue(object : Callback<List<CsUsuariosItensPerdidosAcgadosDTO>> {
            override fun onResponse(call: Call<List<CsUsuariosItensPerdidosAcgadosDTO>>, response: Response<List<CsUsuariosItensPerdidosAcgadosDTO>>) {
                if (response.isSuccessful) {
                    _csusuariositemperdidoachadolista.value = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<CsUsuariosItensPerdidosAcgadosDTO>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    fun carregarListaItensPerdidosAchados() {
        itemPerdidoAchadoServiceRetrofit.obterListaItensPerdidosAchados().enqueue(object : Callback<List<ItemPerdidoAchadosDTO>> {
            override fun onResponse(call: Call<List<ItemPerdidoAchadosDTO>>, response: Response<List<ItemPerdidoAchadosDTO>>) {
                if (response.isSuccessful) {
                    _itemperdidoachadolista.value = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<ItemPerdidoAchadosDTO>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }



    // Função para registrar um novo usuário com e-mail e senha
    fun RegistarItemPerdidoAchado(onResult: (Boolean, String) -> Unit) {

        if (itemperdidoachadoEstadoEntity.nomeitemperdidoachado.isNotEmpty() && itemperdidoachadoEstadoEntity.fkpessoaregistoitemperdidoachado!=0) {
            viewModelScope.launch {

                    itemPerdidoAchadoServiceRetrofit.adicionarItemPerdidoAchado(itemperdidoachadoEstadoEntity).enqueue(object : Callback<RespostaAPI> {
                        override fun onResponse(call: Call<RespostaAPI>, response: Response<RespostaAPI>) {
                            if (response.isSuccessful) {
                                val resposta = response.body()
                                onResult(true, resposta?.mensagem ?: "item registado com sucesso.")
                            } else {
                                onResult(false, "Erro ao registar item: ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<RespostaAPI>, t: Throwable) {
                            onResult(false, "Erro ao registar: ${t.message}")
                        }
                    })
            }
        } else {
            onResult(false, "Todos os campos devem ser preenchidos")
        }
    }

    fun eliminarItemPerdidoAchado(
        id: Int,
        onSucesso: (String) -> Unit,
        onErro: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = itemPerdidoAchadoServiceRetrofit.eliminarItemPerdidoAchado(id)
                if (response.isSuccessful) {
                    val mensagem = response.body() ?: "Item removido com sucesso!"
                    onSucesso(mensagem)
                } else {
                    onErro("Erro ao remover o item. Código: ${response.code()}")
                }
            } catch (e: Exception) {
                onErro("Erro ao conectar ao servidor: ${e.localizedMessage}")
            }
        }
    }

    fun actualizarItemPerdidoAchado(
        id: Int,
        itemPerdidoAchadosDTO: ItemPerdidoAchadosDTO,
        onSucesso: (String) -> Unit,
        onErro: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = itemPerdidoAchadoServiceRetrofit.atualizarItemPerdidoAchado(id, itemPerdidoAchadosDTO)
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



    // Função para fazer logout, removendo o usuário logado da sessão
    fun LimparitemActual() {
        _itemPerdidoAchado.value = null // Remove a sessão de usuário logado
    }
}
