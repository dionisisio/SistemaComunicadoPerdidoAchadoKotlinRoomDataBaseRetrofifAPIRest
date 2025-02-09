package bambi.neves.eduardo.Retrofite.Dados.Usuario

data class UsuarioDTO(
    val pkusuario: Int,
    var nomeusuario: String,
    val nomeacessousuario: String,
    var palavrapasseusuario: String,
    var numerotelefoneusuario: String,
    var enderecousuario: String
)