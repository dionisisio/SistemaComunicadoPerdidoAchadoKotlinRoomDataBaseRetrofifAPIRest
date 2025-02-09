# Sistema de Comunicado de Itens Perdidos e Achados

O sistema de comunicado de itens perdidos e achados foi desenvolvido utilizando Kotlin e Jetpack Compose, proporcionando uma interface moderna e fluída para os utilizadores. A aplicação conta com a integração de um banco de dados local através do Room Database, que é responsável pelo armazenamento e persistência das sessões de utilizadores, bem como pela gestão da lista de itens favoritos.

Para a comunicação com o servidor e obtenção de dados, a aplicação faz uso da API RESTful através da biblioteca Retrofit, que facilita a integração com o servidor desenvolvido em ASP.NET. O servidor é responsável por fornecer a autenticação dos utilizadores através de email e senha, utilizando técnicas de segurança adequadas para proteger as credenciais.

A base de dados do servidor é implementada em PostgreSQL, que armazena informações sobre os itens perdidos e achados, permitindo realizar operações de CRUD (Criar, Ler, Actualizar e Apagar) para a gestão dos itens. O sistema permite que os utilizadores registem novos itens perdidos ou encontrados, pesquisem itens já registados e adicione os itens favoritos, utilizando o Room Database para persistir os dados de cada sessão.

Além disso, o servidor também gerencia as permissões de acesso, garantindo que apenas utilizadores autenticados possam interagir com as funcionalidades de CRUD e com a lista de itens favoritos.

Este sistema é altamente escalável e eficiente, integrando tecnologias modernas e práticas recomendadas para o desenvolvimento de aplicações móveis com Kotlin, garantindo uma experiência de utilizador intuitiva e funcional.
