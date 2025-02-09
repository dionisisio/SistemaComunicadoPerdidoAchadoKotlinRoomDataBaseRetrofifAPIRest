package bambi.neves.eduardo.RoomDataBase.ViewModel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bambi.neves.eduardo.Retrofite.Dados.ItensPerdidosAchados.ItemPerdidoAchadosDTO
import bambi.neves.eduardo.RoomDataBase.Enitdades.ItemPerdidoAchadoEntity
import bambi.neves.eduardo.RoomDataBase.Repositorios.ItemPerdidoAchadoRepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.sql.SQLException

class ItemPerdidoAchadoRoomViewModel(private val itemPerdidoAchadoRepositorio: ItemPerdidoAchadoRepositorio) : ViewModel() {



    fun Eliminar(itemPerdidoAchadoEntity: ItemPerdidoAchadoEntity) {
        viewModelScope.launch {
            try {
                itemPerdidoAchadoRepositorio.Eliminar(itemPerdidoAchadoEntity)
            } catch (e: IOException){}

        }
    }


    private val _listaitemroomdatabaseActual = mutableStateOf<List<ItemPerdidoAchadoEntity?>>(emptyList())
    val listaitemroomdatabaseActual: State<List<ItemPerdidoAchadoEntity?>> = _listaitemroomdatabaseActual
    fun carregarListaTodosItensPerdidoAchado() {
        viewModelScope.launch {
            try {
                val ListaitemExistente = itemPerdidoAchadoRepositorio.ObterTodosItemPerdidoAchado()
                if (ListaitemExistente.isNotEmpty()) {
                    _listaitemroomdatabaseActual.value = ListaitemExistente
                    println("lista de Itens carregado:")
                }
                else
                {
                    println("Erro de I/O ao carregar lista de Itens:")
                }
            }
            catch (e: IOException) {
                println("Erro de I/O ao carregar lista de Itens: ${e.message}")
            } catch (e: SQLException) {
                println("Erro de SQL ao carregar lista de Itens: ${e.message}")
            } catch (e: Exception) {
                println("Erro inesperado ao carregar lista de Itens: ${e.message}")
            }
        }
    }

    private val _itemroomdatabaseActual = mutableStateOf<ItemPerdidoAchadoEntity?>(null)
    val itemroomdatabaseActual: State<ItemPerdidoAchadoEntity?> = _itemroomdatabaseActual

    fun carregarItemPkItemPerdidoAchado(pkitemperdidoachado: Int) {
        viewModelScope.launch {
            try {
                val itemExistente = itemPerdidoAchadoRepositorio.obterItemPorpkitemperdidoachado(pkitemperdidoachado)
                if (itemExistente != null) {
                    _itemroomdatabaseActual.value = itemExistente
                    println("Item carregado:")
                }
                else
                {
                    println("Erro de I/O ao carregar Item:")
                }
            }
            catch (e: IOException) {
                println("Erro de I/O ao carregar Item: ${e.message}")
            } catch (e: SQLException) {
                println("Erro de SQL ao carregar Item: ${e.message}")
            } catch (e: Exception) {
                println("Erro inesperado ao carregar Item: ${e.message}")
            }
        }
    }
    suspend fun BuscarItemPkItemPerdidoAchado(pkitemperdidoachado: Int): ItemPerdidoAchadoEntity? {
        return try {
            val itemExistente = itemPerdidoAchadoRepositorio.obterItemPorpkitemperdidoachado(pkitemperdidoachado)
            if (itemExistente != null) {
                _itemroomdatabaseActual.value = itemExistente
                println("Item carregado: $itemExistente")
            } else {
                println("Item não encontrado.")
            }
            itemExistente
        } catch (e: IOException) {
            println("Erro de I/O ao carregar Item: ${e.message}")
            null
        } catch (e: SQLException) {
            println("Erro de SQL ao carregar Item: ${e.message}")
            null
        } catch (e: Exception) {
            println("Erro inesperado ao carregar Item: ${e.message}")
            null
        }
    }

    // Registra um novo usuário com e-mail e senha
    fun RegistarItemPerdidoAchado(itemPerdidoAchadoEntity: ItemPerdidoAchadoEntity) {
        if (itemPerdidoAchadoEntity.nomeitemperdidoachadoroom.isNotEmpty() && itemPerdidoAchadoEntity.pkitemperdidoachadoroom!=0) {
            viewModelScope.launch {
                try {
                    itemPerdidoAchadoRepositorio.Adicionar(itemPerdidoAchadoEntity)

                } catch (e: Exception) {

                }
            }

        }
    }

    fun ApagarItemRomm() {
              viewModelScope.launch {
                  try {
                      _itemroomdatabaseActual.value = null

                  } catch (e: Exception) {

                  }
              }


        }
}
