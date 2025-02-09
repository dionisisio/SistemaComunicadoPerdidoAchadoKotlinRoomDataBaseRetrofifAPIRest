package bambi.neves.eduardo.Retrofite.Dados.ItensPerdidosAchados

import java.time.LocalDate

data class CsUsuariosItensPerdidosAcgadosDTO (
    val pkusuario: Int,
    var nomeusuario: String,
    val nomeacessousuario: String,
    var palavrapasseusuario: String,
    var numerotelefoneusuario: String,
    var enderecousuario: String,

    val pkitemperdidoachado: Int,
    var nomeitemperdidoachado: String,
    var descricaoitemperdidoachado: String,
    var localitemperdidoachado: String,
    var dataitemperdidoachado: String, // Formato: "YYYY-MM-DD"
    var verificaritemperdido: Boolean, // Valores: "Perdido" ou "Encontrado"
    var fkpessoaregistoitemperdidoachado: Int, // FK do usuário que perdeu o item
    var observacaoentregaitemperdidoachado: String, // Observação sobre a entrega do item
    var verificarprocessoconcluidoitemperdido: Boolean
)