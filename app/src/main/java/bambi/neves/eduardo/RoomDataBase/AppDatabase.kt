package bambi.neves.eduardo.RoomDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import bambi.neves.eduardo.RoomDataBase.DAO.ItemPerdidoAchadoDAO
import bambi.neves.eduardo.RoomDataBase.Enitdades.ItemPerdidoAchadoEntity

@Database(entities = [UsuarioEntity::class, ItemPerdidoAchadoEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDAO
    abstract fun itemperdidoachadoDao(): ItemPerdidoAchadoDAO
}
