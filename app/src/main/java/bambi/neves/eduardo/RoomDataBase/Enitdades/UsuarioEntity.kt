package bambi.neves.eduardo.RoomDataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var emailusuario: String,
    var senhausuario: String
)