package bambi.neves.eduardo.Paginas.Componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import bambi.neves.eduardo.Retrofite.Dados.ItensPerdidosAchados.CsUsuariosItensPerdidosAcgadosDTO
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CartaocsUsuarioItemPerdidosAchadosLista (csUsuariosItensPerdidosAcgadosDTO: CsUsuariosItensPerdidosAcgadosDTO, modifier: Modifier = Modifier) {
    var tipodescricao by remember { mutableStateOf("") }
    var tipopessoa by remember { mutableStateOf("") }
if(csUsuariosItensPerdidosAcgadosDTO.verificaritemperdido) {
    tipodescricao ="Perdeu-se: "
    tipopessoa="Proprietário: "
}
    else
{
    tipodescricao ="Foi achado: "
    tipopessoa="Em possee: "

}


    Card(
    modifier = modifier
    .padding(5.dp)
    .fillMaxWidth(),
    elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = tipodescricao+ csUsuariosItensPerdidosAcgadosDTO.nomeitemperdidoachado, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Descrição:  "+ csUsuariosItensPerdidosAcgadosDTO.descricaoitemperdidoachado, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "local:  "+ csUsuariosItensPerdidosAcgadosDTO.localitemperdidoachado, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "data:  "+ csUsuariosItensPerdidosAcgadosDTO.dataitemperdidoachado, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(20.dp))
            // Pessoa
            Text(text = tipopessoa+": "+ csUsuariosItensPerdidosAcgadosDTO.nomeusuario, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Telefone: "+ csUsuariosItensPerdidosAcgadosDTO.numerotelefoneusuario, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Endereço: "+ csUsuariosItensPerdidosAcgadosDTO.enderecousuario, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "Obs:  " + csUsuariosItensPerdidosAcgadosDTO.observacaoentregaitemperdidoachado, style = MaterialTheme.typography.bodySmall)
            }

        }
    }
}

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(Date())
}
