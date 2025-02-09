package bambi.neves.eduardo.RoomDataBase
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun AdicionarUsuario(usuarioEntity: UsuarioEntity)


    @Update
    suspend fun ActualizarUsuario(usuariontity: UsuarioEntity)

    @Delete
    suspend fun EliminarUsuario(usuariontity: UsuarioEntity)

    @Query("SELECT * FROM UsuarioEntity WHERE emailusuario = :email AND senhausuario = :senha LIMIT 1")
    suspend fun obterUsuarioPorEmailESenha(email: String, senha: String): UsuarioEntity?

    @Query("SELECT * FROM UsuarioEntity WHERE id = :id LIMIT 1")
    suspend fun obterUsuarioPorid(id: Int): UsuarioEntity?


    @Query("SELECT * FROM UsuarioEntity LIMIT 1")
    suspend fun obterPrimeiroUsuario(): UsuarioEntity?

}
