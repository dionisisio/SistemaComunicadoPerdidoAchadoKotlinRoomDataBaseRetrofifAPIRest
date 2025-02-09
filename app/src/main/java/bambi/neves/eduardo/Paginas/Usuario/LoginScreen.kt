package bambi.neves.eduardo.Paginas.Usuario

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import bambi.neves.eduardo.R
import bambi.neves.eduardo.Retrofite.ViewModel.Usuario.UsuarioRetrofitViewModel
import bambi.neves.eduardo.RoomDataBase.ViewModel.UsuarioRoomViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, usuarioRetrofitViewModel: UsuarioRetrofitViewModel = viewModel(), usuarioRoomViewModel: UsuarioRoomViewModel) {
    // Definir as variáveis locais para e-mail, senha e mensagem de erro
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }  // Variável para controlar a visibilidade da senha
    var lembrarCredenciais by remember { mutableStateOf(true) }  // Variável para o checkbox

    var totalcarremnto by remember { mutableStateOf(0) }

    // Buscar o item perdido/achado pelo ID


    LaunchedEffect(Unit) {
        delay(1000) // Substitua 'tempoEmMilissegundos' pelo valor desejado
        if (totalcarremnto == 0) {
            val usuarioRoomDataBaseAtual = usuarioRoomViewModel.usuarioActual
            if (usuarioRoomDataBaseAtual != null) {
                email = usuarioRoomDataBaseAtual.value?.emailusuario ?: ""
                senha = usuarioRoomDataBaseAtual.value?.senhausuario ?: ""
                totalcarremnto = 1
            }
        }
    }
    // Função para validar o formato do e-mail
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Função para validar o tamanho da senha
    fun isValidPassword(senha: String): Boolean {
        return senha.length >= 6
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagem de Login
        Image(
            painter = painterResource(id = R.drawable.perdidos),
            contentDescription = "Imagem de Login",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Sist. Perdidos Achados", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Entrar")
        Spacer(modifier = Modifier.height(16.dp))

        // Campo de E-mail
        OutlinedTextField(
            value = email, // A variável de e-mail que é monitorada
            onValueChange = { novoEmail -> email = novoEmail }, // Atualiza o e-mail na variável
            label = { Text(text = "E-mail:") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            isError = !isValidEmail(email) && email.isNotEmpty(), // Marca o campo como erro se o e-mail for inválido
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (isValidEmail(email) && email.isNotEmpty()) Color.Green else Color.Red,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = if (isValidEmail(email) && email.isNotEmpty()) Color.Green else Color.Red
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Mensagem de erro do e-mail
        if (!isValidEmail(email) && email.isNotEmpty()) {
            Text(
                text = "Por favor, insira um e-mail válido",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Senha
        OutlinedTextField(
            value = senha, // A variável de senha que é monitorada
            onValueChange = { novaSenha -> senha = novaSenha }, // Atualiza a senha na variável
            label = { Text(text = "Senha") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            isError = !isValidPassword(senha) && senha.isNotEmpty(), // Marca o campo como erro se a senha for inválida
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (isValidPassword(senha) && senha.isNotEmpty()) Color.Green else Color.Red,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = if (isValidPassword(senha) && senha.isNotEmpty()) Color.Green else Color.Red
            ),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    // Usando imagens como ícones
                    val visibilityIcon = if (isPasswordVisible) {
                        painterResource(id = R.drawable.ic_visibility) // Substitua com o caminho correto da imagem
                    } else {
                        painterResource(id = R.drawable.ic_visibility_off) // Substitua com o caminho correto da imagem
                    }

                    Image(
                        painter = visibilityIcon,
                        contentDescription = if (isPasswordVisible) "Ocultar senha" else "Mostrar senha"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Mensagem de erro da senha
        if (!isValidPassword(senha) && senha.isNotEmpty()) {
            Text(
                text = "A senha deve ter no mínimo 6 caracteres",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Opção de "Lembrar Credenciais"
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = lembrarCredenciais,
                onCheckedChange = { lembrarCredenciais = it }
            )
            Text(
                text = "Lembrar as credenciais",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))
        // Link para criar conta
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start // Alinha o texto à esquerda
        ) {
            Text(
                text = "Criar Conta",
                modifier = Modifier.clickable {
                    navController.navigate("criarusuariopage")
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))


        // Botão de Login
        Button(onClick = {
            // Validação e login

            when {
                email.isEmpty() || senha.isEmpty() -> {
                    mensagemErro = "Preencha todos os campos"
                    showErrorDialog = true
                }
                !isValidEmail(email) -> {
                    mensagemErro = "Por favor, insira um e-mail válido"
                    showErrorDialog = true
                }
                !isValidPassword(senha) -> {
                    mensagemErro = "A senha deve ter no mínimo 6 caracteres"
                    showErrorDialog = true
                }
                else -> {
                    // Atualiza o estado no ViewModel
                    usuarioRetrofitViewModel.usuarioEstadoEntity.nomeusuario = email
                    usuarioRetrofitViewModel.usuarioEstadoEntity.palavrapasseusuario = senha

                    // Chama o método de login
                    usuarioRetrofitViewModel.FazerLogin { sucesso, mensagem ->
                        if (sucesso) {
                            if (lembrarCredenciais)
                            {

                                usuarioRoomViewModel.registrarUsuario(email,senha)
                                usuarioRoomViewModel.carregarUsuarioporID()
                            }
                            navController.navigate("homepageitemperdidoahado")
                        } else {
                            mensagemErro = mensagem
                            showErrorDialog = true // Exibe o diálogo de erro
                        }
                    }
                }
            }
        }) {
            Text(text = "Login")
        }


        Spacer(modifier = Modifier.height(40.dp))

        // Link para criar conta
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End // Alinha o texto à esquerda
        ) {
            Text(
                text = "Usuários Cadastrados",
                modifier = Modifier.clickable {
                    navController.navigate("listausuariopage")
                }
            )
        }

    }

    // Exibição de erro caso o login falhe
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text(text = "Erro") },
            text = { Text(mensagemErro) },
            confirmButton = {
                Button(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}
