package bambi.neves.eduardo.Paginas.Usuario

import android.util.Patterns
import androidx.compose.foundation.Image
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
import bambi.neves.eduardo.Retrofite.Dados.Usuario.UsuarioDTO
import bambi.neves.eduardo.R
import bambi.neves.eduardo.Retrofite.ViewModel.Usuario.UsuarioRetrofitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtualizarUsuarioScreen(navController: NavHostController, usuarioRetrofitViewModel: UsuarioRetrofitViewModel = viewModel()) {
    // Definir as variáveis locais para nome, e-mail, senha e confirmação de senha
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }


    val usuarioAtual = usuarioRetrofitViewModel.usuarioAtual
    var idusuario by remember { mutableStateOf(0) }

    if (usuarioAtual != null) {
        idusuario= usuarioAtual.pkusuario
        nome=usuarioAtual.nomeusuario
        email = usuarioAtual.nomeacessousuario
        telefone=usuarioAtual.numerotelefoneusuario
        endereco=usuarioAtual.enderecousuario

    }


    // Função para validar o formato do e-mail
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Função para validar o tamanho da senha
    fun isValidPassword(senha: String): Boolean {
        return senha.length >= 6
    }

    // Função para validar se as senhas coincidem
    fun arePasswordsMatching(): Boolean {
        return senha == confirmarSenha
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagem de Criação de Conta
        Image(
            painter = painterResource(id = R.drawable.perfil),
            contentDescription = "Imagem de Criação de Conta",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Actualizar Conta", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        // Campo de Nome
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text(text = "Nome:") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Telefone
        OutlinedTextField(
            value = telefone,
            onValueChange = { telefone = it },
            label = { Text(text = "Telefone:") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Endereço
        OutlinedTextField(
            value = endereco,
            onValueChange = { endereco = it },
            label = { Text(text = "Endereço:") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de E-mail
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "E-mail:") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            isError = !isValidEmail(email) && email.isNotEmpty(),
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
            value = senha,
            onValueChange = { senha = it },
            label = { Text(text = "Senha") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            isError = !isValidPassword(senha) && senha.isNotEmpty(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (isValidPassword(senha) && senha.isNotEmpty()) Color.Green else Color.Red,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = if (isValidPassword(senha) && senha.isNotEmpty()) Color.Green else Color.Red
            ),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    val visibilityIcon = if (isPasswordVisible) {
                        painterResource(id = R.drawable.ic_visibility)
                    } else {
                        painterResource(id = R.drawable.ic_visibility_off)
                    }
                    Image(painter = visibilityIcon, contentDescription = "Mostrar/Ocultar Senha")
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

        // Campo de Confirmar Senha
        OutlinedTextField(
            value = confirmarSenha,
            onValueChange = { confirmarSenha = it },
            label = { Text(text = "Confirmar Senha") },
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            isError = !arePasswordsMatching() && confirmarSenha.isNotEmpty(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (arePasswordsMatching() && confirmarSenha.isNotEmpty()) Color.Green else Color.Red,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = if (arePasswordsMatching() && confirmarSenha.isNotEmpty()) Color.Green else Color.Red
            ),
            trailingIcon = {
                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    val visibilityIcon = if (isConfirmPasswordVisible) {
                        painterResource(id = R.drawable.ic_visibility)
                    } else {
                        painterResource(id = R.drawable.ic_visibility_off)
                    }
                    Image(painter = visibilityIcon, contentDescription = "Mostrar/Ocultar Confirmar Senha")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Mensagem de erro de confirmação de senha
        if (!arePasswordsMatching() && confirmarSenha.isNotEmpty()) {
            Text(
                text = "As senhas não coincidem",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally), // Alinha a linha no canto inferior direito
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Espaçamento entre os botões
            verticalAlignment = Alignment.CenterVertically // Alinha os botões verticalmente no centro da linha
        ) {
            // Botão de Criar Conta
            Button(onClick = {
                // Validação para criar a conta
                when {
                    nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty() -> {
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

                    !arePasswordsMatching() -> {
                        mensagemErro = "As senhas não coincidem"
                        showErrorDialog = true
                    }

                    else -> {

                        val usuarioAtualizado = UsuarioDTO(
                            pkusuario = idusuario,
                            nomeusuario = nome,
                            nomeacessousuario = email,
                            palavrapasseusuario = senha, // Pode ser mantido ou atualizado
                            numerotelefoneusuario = telefone,
                            enderecousuario = endereco
                        )

                        usuarioRetrofitViewModel.actualizarUsuario(
                            idusuario, usuarioAtualizado,
                            onSucesso = {
                                mensagemErro = it
                                showErrorDialog = true
                                navController.navigate("homepageitemperdidoahado")
                            },
                            onErro = {
                                mensagemErro = it
                                showErrorDialog = true

                            }
                        )

                    }
                }
            }) {
                Text(text = "Actualizar")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {

                    navController.popBackStack()


                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error, // Usa a cor de erro do tema
                    contentColor = MaterialTheme.colorScheme.onError // Usa a cor do texto apropriada para o fundo de erro
                )
            ) {
                Text(text = "Cancelar")
            }
        }

        // Exibição de erro caso a criação de conta falhe
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text(text = "Alerta") },
                text = { Text(mensagemErro) },
                confirmButton = {
                    Button(onClick = { showErrorDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
