<div id="top"></div>

[![LinkedIn][linkedin-shield]][linkedin-url]

  <h2 align="center">Desafio Pub Future </h2>
  <p align="center"> Projeto criado para o desafio Pub Future de finanças pessoais </p
    <br /> <br />
 



<details>
  <summary>Sumário</summary>
  <ol>
    <li>
      <a href="#sobre-o-projeto">Sobre o projeto</a>
      <ul>
        <li><a href="#ferramentas-utilizadas">Ferramentas utilizadas</a></li>
      </ul>
    </li>
    <li>
      <a href="#começando">Começando</a>
      <ul>
        <li><a href="#requisitos">Requisitos</a></li>
        <li><a href="#instalar">Instalar</a></li>
      </ul>
    </li>
    <li><a href="#uso">Uso</a></li>
	  <li><a href="#agradecimentos">Agradecimentos <a/></li>
  </ol>
</details>



## Sobre o projeto


Este projeto foi criado para o desafio da [PubFuture](http://pubfuture.com.br/) ,  o desafio é implementar uma solução que auxilie no controle das finanças pessoais. para isso, o projeto conta com "contas", "receitas" e "despesas", que se relacionam e efetuam cálculos como saldo da conta ou filtros como despesas por período.

Eu optei por utilizar uma arquitetura de **microsserviços** devido a pontualidade e tamanho do projeto, seguindo as especificações de uma **API REST/RESTFUL**. a estrutura geral do projeto também está de acordo com o layout proposto na [documentação de uma aplicação utilizando Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.structuring-your-code) . Por fim, utilizei alguns **DTO**'s para não haver problemas com dados redundantes em diferentes operações nos endpoints da API.



### Ferramentas utilizadas

* Java SE 11
* Maven 
* Spring Boot 2.5.2, Spring Data, Spring Validation, Spring Web, Spring Test ( Melhorias diversas como injeção de dependências e inversão de controle)
* H2 (Banco em memória) / PostgreSQL ( Banco relacional )
* Project Lombok ( Diminuir código repetitivo )
* Swagger / Swagger UI 2.9.2 ( Documentação API / Endpoints )
* ModelMapper 2.4.4 ( Mapeamento DTO's  / Modelos)
* JPA / Hibernate ( Mapeamento Objeto-Relacional )
* JUnit 5 ( Testes Unitários ) / MockMvc ( Testes Integração )



## Começando

Eu utilizei o **banco em memória H2** para maior comodidade de testes bem como para rodar a aplicação.  **Há o postgreSQL no projeto**, e para alterar de um outro, basta remover os comentários das linhas no [application-properties] e no [pom].
No geral, para testes gerais da aplicação, devido ao uso do Maven e do Spring Boot, não se faz necessário nem mesmo uma IDE, isso porque o projeto conta com um wrapper do maven, que torna possivel executar comandos do maven, sem nem mesmo ter ele instalado.



### Requisitos
* Na configuração atual do projeto, não há requisito algum, em especial o projeto deve rodar tranquilamente por linha de comando, a aplicação bem como os testes, de outra forma, há alguns adendos:
* 
  * Caso for mudar para o PostgreSQL, então o projeto vai procurar pelas configurações descritas no [application-properties].  
  
  * O projeto pode rodar em basicamente qualquer IDE que suporte Java, porém por ter utilizado o Project Lombok algumas IDE's podem apontar erros no projeto, para isso basta instalar o plugin do [Lombok]
  
   

### Instalar

1. Clone este repositório
   ```sh
   git clone https://github.com/jonahanzen/desafiopubfuture.git
   ```
   
#### Se rodar usando o Maven

2.  Abra o terminal na pasta do projeto e execute o comando
   ```sh
   mvnw spring-boot:run
   ```

3. Acesse o link para testar os endpoints e uso geral do projeto
 ```sh
 http://localhost:8080/swagger-ui.html#/
  ```
#### Se rodar em alguma IDE e utilizando o PostgreSQL

2.  Instalar o plugin do Lombok
```sh
	https://projectlombok.org/
```
3.  em [application-properties] alterar as configurações para o postgresql

[Application-Properties]

4.  no [pom] alterar as dependencias para o postgresql

[Pom]

5. Se necessário, atualizar o projeto, e então rodar, e então acessar o link para testar os endpoints e uso geral do projeto (  **OU** use o Postman e/ou outra ferramenta para testar os endpoints)
```sh
 http://localhost:8080/swagger-ui.html#/
  ```




## Uso

### Testes

 Os testes pelo maven podem ser rodados pelo próprio terminal, localize a pasta onde o repositório foi clonado, abra o terminal e execute ( o comando vai testar todas as classes (de teste) automáticamente) . Do contrário, abra o projeto em uma IDE de sua preferência e execute os testes por lá, o resultado deve ser o mesmo.

```sh
mvnw test
```

![Testes Maven][imagem-teste]

### Aplicação 

Visão geral do projeto no Swagger

![Swagger-geral][imagem-swagger-geral]

Endpoints da Conta

![Endpoints-Conta][imagem-conta-controller]

Endpoins de Despesa

![Endpoints-Despesa][imagem-despesa-controller]

Endpoints Receita

![Endpoints-Receita][imagem-receita-controller]

<br/>
	
### Agradecimentos

Agradeço pela oportunidade do desafio e também agradeço a existência do Spring Boot.  :grin: 

Links e sites gerais que foram utilizados para consulta de documentação e/ou dúvidas e/ou que sirvam de alguma ajuda.


<details>
  <summary>Consulta de documentação</summary>
  <ul>
    <li> https://www.baeldung.com/integration-testing-in-spring </li>
	<li> https://github.com/json-path/JsonPath </li>
	<li> https://www.baeldung.com/jackson-object-mapper-tutorial </li>
	<li> https://www.baeldung.com/maven-wrapper </li>
	<li> https://mkyong.com/maven/how-to-run-unit-test-with-maven/ </li>
	<li> https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.structuring-your-code </li>
	<li> https://www.baeldung.com/junit-5 </li>
	<li> https://www.baeldung.com/java-modelmapper </li>
	<li> https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation </li>
	<li> https://stackoverflow.com/questions/51637103/how-do-i-return-sum-from-jpa-query-using-hibernate-and-spring-boot </li>
	<li> https://qastack.com.br/programming/12505141/only-using-jsonignore-during-serialization-but-not-deserialization </li>
	<li> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/result/MockMvcResultMatchers.html </li>
	</ul>
</details>

<details>
<summary> Gerais </summary>
<ul>
<li> https://stackedit.io/ </li>
<li> https://github.com/othneildrew/Best-README-Template/blob/master/README.md </li>
</ul>

	
	





<!-- Imagens -->
[imagem-teste]: images/tests.png
[imagem-conta-controller]: images/conta-controller.png
[imagem-despesa-controller]: images/despesa-controller.png
[imagem-receita-controller]: images/receita-controller.png
[imagem-swagger-geral]: images/swagger-screenshot.png



<!-- Links -->
[test]: src/test/java/br/com
[lombok]: https://projectlombok.org
[pom]: pom.xml
[application-properties]: src/main/resources
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://br.linkedin.com/in/jonathanhflores



