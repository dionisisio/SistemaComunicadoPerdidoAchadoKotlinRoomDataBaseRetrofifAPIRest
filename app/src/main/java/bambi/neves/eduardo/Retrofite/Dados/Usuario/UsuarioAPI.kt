package bambi.neves.eduardo.Retrofite.Dados.Usuario

import bambi.neves.eduardo.Retrofite.Dados.RespostaAPI
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UsuarioAPI {

    @GET("usuario/listausuarios")
    fun obterListaUsuarios(): Call<List<UsuarioDTO>>


    @GET("usuario/buscarpornome")
    fun obterUsuarioPorNomeAcesso(
        @Query("nomeacessousuario") nomeAcesso: String
    ): Call<UsuarioDTO>

    @GET("usuario/buscarpornomeesenha")
    fun obterUsuarioPorNomeAcessoPlavraPasse(
        @Query("nomeacessousuario") nomeAcesso: String,
        @Query("palavrapasseusuario") senha: String
    ): Call<UsuarioDTO>

    // Método para adicionar um novo usuário
    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("usuario/adicionarusuario")
    fun adicionarUsuario(@Body usuarioDTO: UsuarioDTO): Call<RespostaAPI>


    @PUT("usuario/atualizarusuario/{id}")
    suspend fun atualizarUsuario(
        @Path("id") id: Int,
        @Body usuarioDTO: UsuarioDTO
    ): Response<RespostaAPI>
}