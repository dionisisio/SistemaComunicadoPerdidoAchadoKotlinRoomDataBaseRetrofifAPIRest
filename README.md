# Sistema de Gerenciamentos de Comunicados de Itens Perdidos e Achados

O sistema de comunicado de itens perdidos e achados foi desenvolvido utilizando Kotlin e Jetpack Compose, proporcionando uma interface moderna e fluída para os utilizadores. A aplicação conta com a integração de um banco de dados local através do Room Database, que é responsável pelo armazenamento e persistência das sessões de utilizadores, bem como pela gestão da lista de itens favoritos.

Para a comunicação com o servidor e obtenção de dados, a aplicação faz uso da API RESTful através da biblioteca Retrofit, que facilita a integração com o servidor desenvolvido em ASP.NET. O servidor é responsável por fornecer a autenticação dos utilizadores através de email e senha, utilizando técnicas de segurança adequadas para proteger as credenciais.

A base de dados do servidor é implementada em PostgreSQL, que armazena informações sobre os itens perdidos e achados, permitindo realizar operações de CRUD (Criar, Ler, Actualizar e Apagar) para a gestão dos itens. O sistema permite que os utilizadores registem novos itens perdidos ou encontrados, pesquisem itens já registados e adicione os itens favoritos, utilizando o Room Database para persistir os dados de cada sessão.

Além disso, o servidor também gerencia as permissões de acesso, garantindo que apenas utilizadores autenticados possam interagir com as funcionalidades de CRUD e com a lista de itens favoritos.

Este sistema é altamente escalável e eficiente, integrando tecnologias modernas e práticas recomendadas para o desenvolvimento de aplicações móveis com Kotlin, garantindo uma experiência de utilizador intuitiva e funcional.

#Princípio de Funcionamento 

O sistema contempla uma tela splash que roda por 3 segundos seguido de uma tela de login.

o login é efectuado com e-mail e senha validadas no frontend que verifica o formato correcto do e-mail e uma senha com o mínimo de 4 caracteres. No lado do servidor o e-mail e a senha são novamente verificados antes de retornar toda informação da sessão.

L sistema contempla uma tela Home que lista todos itens perdidos ou achados que ainda não foram recuperados.

No home contém também os botões adicionar item perdido/achado e lista de favoritos.

Ao clicar em cada item, abre a página de detalhoes, onde é possivel editar o item, eliminar ou adicionar/remover dos favoritos.


para além disso, o usuário pode actualizar os seus dados de login e contactos, bem como pode criar outra conta.

A comunicação entre o frontend e o backend é feita via https, utilizando certificado TLS.



