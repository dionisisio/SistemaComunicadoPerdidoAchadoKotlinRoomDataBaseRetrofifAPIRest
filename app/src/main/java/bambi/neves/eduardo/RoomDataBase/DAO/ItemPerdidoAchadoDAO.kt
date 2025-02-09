package bambi.neves.eduardo.RoomDataBase.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import bambi.neves.eduardo.RoomDataBase.Enitdades.ItemPerdidoAchadoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemPerdidoAchadoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun AdicionarItemPerdidoAchado(itemPerdidoAchadoEntity: ItemPerdidoAchadoEntity)


    @Update
    suspend fun ActualizarAdicionarItemPerdidoAchado(itemPerdidoAchadoEntity: ItemPerdidoAchadoEntity)

    @Delete
    suspend fun EliminarAdicionarItemPerdidoAchado(itemPerdidoAchadoEntity: ItemPerdidoAchadoEntity)

    @Query("SELECT * FROM ItemPerdidoAchadoEntity WHERE pkitemperdidoachadoroom = :pkitemperdidoachadoroom LIMIT 1")
    suspend fun obterItemPorpkitemperdidoachado(pkitemperdidoachadoroom: Int): ItemPerdidoAchadoEntity?

    @Query("SELECT * FROM ItemPerdidoAchadoEntity")
    suspend fun ObterTodosItemPerdidoAchado(): List<ItemPerdidoAchadoEntity>
}