package bambi.neves.eduardo.RoomDataBase.Enitdades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemPerdidoAchadoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var nomeitemperdidoachadoroom: String,
    var descricaoitemperdidoachadoroom: String,
    var verificaritemperdidoroom: Boolean,
    var pkitemperdidoachadoroom: Int,
)