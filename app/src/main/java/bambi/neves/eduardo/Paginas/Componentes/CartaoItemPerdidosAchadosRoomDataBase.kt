package bambi.neves.eduardo.Paginas.Componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bambi.neves.eduardo.RoomDataBase.Enitdades.ItemPerdidoAchadoEntity

@Composable
fun CartaoItemPerdidosAchadosRoomDataBase(itemPerdidoAchadoEntity: ItemPerdidoAchadoEntity, modifier: Modifier = Modifier) {
    var tipodescricao by remember { mutableStateOf("") }
    var tipopessoa by remember { mutableStateOf("") }
    if(itemPerdidoAchadoEntity.verificaritemperdidoroom) {
        tipodescricao ="Perdeu-se: "
    }
    else
    {
        tipodescricao ="Foi achado: "

    }
    Card(
    modifier = modifier
    .padding(5.dp)
    .fillMaxWidth(),
    elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = tipodescricao+ itemPerdidoAchadoEntity.nomeitemperdidoachadoroom,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Descrição:  "+ itemPerdidoAchadoEntity.descricaoitemperdidoachadoroom,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
