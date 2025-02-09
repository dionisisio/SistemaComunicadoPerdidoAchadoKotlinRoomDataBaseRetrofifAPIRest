package bambi.neves.eduardo.RoomDataBase.Repositorios

import bambi.neves.eduardo.RoomDataBase.DAO.ItemPerdidoAchadoDAO
import bambi.neves.eduardo.RoomDataBase.Enitdades.ItemPerdidoAchadoEntity

import kotlinx.coroutines.flow.Flow

class ItemPerdidoAchadoRepositorio(val itemperdidoachadoDAO: ItemPerdidoAchadoDAO) {

    suspend fun Adicionar(itemPerdidoAchadoEntity: ItemPerdidoAchadoEntity) {
        itemperdidoachadoDAO.AdicionarItemPerdidoAchado(itemPerdidoAchadoEntity)
    }

    suspend fun Actualizar(itemPerdidoAchadoEntity: ItemPerdidoAchadoEntity) {
        itemperdidoachadoDAO.ActualizarAdicionarItemPerdidoAchado(itemPerdidoAchadoEntity)
    }

    suspend fun Eliminar(itemPerdidoAchadoEntity: ItemPerdidoAchadoEntity) {
        itemperdidoachadoDAO.EliminarAdicionarItemPerdidoAchado(itemPerdidoAchadoEntity)
    }

    suspend fun ObterTodosItemPerdidoAchado(): List<ItemPerdidoAchadoEntity> {
        return itemperdidoachadoDAO.ObterTodosItemPerdidoAchado()
    }

    suspend fun obterItemPorpkitemperdidoachado(pkitemperdidoachado: Int): ItemPerdidoAchadoEntity? {
        return itemperdidoachadoDAO.obterItemPorpkitemperdidoachado(pkitemperdidoachado)
    }

}
