package bambi.neves.eduardo.Retrofite.Dados.ItensPerdidosAchados
import bambi.neves.eduardo.Retrofite.Dados.RespostaAPI
import bambi.neves.eduardo.Retrofite.Dados.Usuario.UsuarioDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemPerdidoAchadoAPI {


    @GET("ItemPerdidoAchado/listaItensperdidosachado")
    fun obterListaItensPerdidosAchados(): Call<List<ItemPerdidoAchadosDTO>>

    @GET("ItemPerdidoAchado/listaitensperdidosachadosporusuario")
    fun obterListaUsuariosItensPerdidosAchados(): Call<List<CsUsuariosItensPerdidosAcgadosDTO>>

    @GET("ItemPerdidoAchado/buscarpkitemperdidoachado")
    fun obterItemPerdidoAchadoPK(
        @Query("pkitemperdidoachado") pkitemperdidoachado: Int
    ): Call<ItemPerdidoAchadosDTO>

    @GET("ItemPerdidoAchado/buscarusuariopkitemperdidoachado")
    fun obterUsuarioItemPKitem(
        @Query("pkitemperdidoachado") pkitemperdidoachado: Int
    ): Call<CsUsuariosItensPerdidosAcgadosDTO>

    // Método para adicionar um novo usuário
    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("ItemPerdidoAchado/adicionaritemperdidoachado")
    fun adicionarItemPerdidoAchado(@Body itemPerdidoAchadosDTO: ItemPerdidoAchadosDTO): Call<RespostaAPI>


    @PUT("ItemPerdidoAchado/atualizaritem/{id}")
    suspend fun atualizarItemPerdidoAchado(
        @Path("id") id: Int,
        @Body itemPerdidoAchadosDTO: ItemPerdidoAchadosDTO
    ): Response<RespostaAPI>


    // Novo método para excluir um item pelo ID
    @Headers("Accept: application/json")
    @DELETE("ItemPerdidoAchado/eliminarregisto/{id}")
    suspend fun eliminarItemPerdidoAchado(@Path("id") id: Int): Response<String>
}