package bambi.neves.eduardo.RoomDataBase

import kotlinx.coroutines.flow.Flow

class UsuarioRepositorio(val usuarioDAO: UsuarioDAO) {

    suspend fun Adicionar(usuarioentity: UsuarioEntity) {
        usuarioDAO.AdicionarUsuario(usuarioentity)
    }

    suspend fun Actualizar(usuarioentity: UsuarioEntity) {
        usuarioDAO.ActualizarUsuario(usuarioentity)
    }

    suspend fun Eliminar(usuarioentity: UsuarioEntity) {
        usuarioDAO.EliminarUsuario(usuarioentity)
    }

    suspend fun ObterPrimeiroUsuario(): UsuarioEntity? {
        return usuarioDAO.obterPrimeiroUsuario()
    }


    suspend fun obterUsuarioPorEmailESenha(email: String, senha: String): UsuarioEntity? {
        return usuarioDAO.obterUsuarioPorEmailESenha(email, senha)
    }

    suspend fun obterUsuarioPorID(id: Int): UsuarioEntity? {
        return usuarioDAO.obterUsuarioPorid(id)
    }

}
