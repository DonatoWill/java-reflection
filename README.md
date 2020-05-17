# Reflection

Essa API foi desenvolvida com o intuito de estudar alguns conceitos importantes utilizados por diversos frameworks, e que muitas vezes até esquecemos pela facilidade e agilidade que nos proporcionam mas quesão importantes conhecimentos para todo desenvolvedor.

Nessa aplicação tentei replicar de forma simples (bem simples) de como é feita a persistência no banco de dados por alguns frameworks como Hibernate (muito requisitado pelas empresas)

## Afinal o que é Reflection?

Reflection é uma API do Java introduzida na versão 1, essa API permite modificar e extrair o conteúdo de um objeto em tempo de execução
sem conhecimento prévio da sua estrutura ou até mesmo da classe desse objeto no tempo de compilação. 
Olhando essa definição muitos já lembram de frameworks que utilizam muito essa API, imagine que ao desenvolver um plugin 
você tivesse que refatorar seu projeto para se adaptar à ele, com certeza seria inviável e essa API veio para resolver isso.

Abaixo alguns exemplos de frameworks que o utilizam:
- Hibernate, EclipseLink ORMs utilizados para persistência de dados no banco;
- JAX-B, JSON-B utilizados para mapear os objetos para XML ou JSON;
- Spring utilizando para injeção de dependências;
- JUnit, TestNG, Mockito para realização de testes.

E muitos outros mais..

###Pontos negativos do seu uso
Como toda API devemos nos atentar onde realmente fará sentido aplicar o uso dessa API. Abaixo são listados alguns pontos 
negativos:
- Redução de desempenho em alguns casos;
- Problemas com segurança;
- Exposição da estrutura interna das classes/objetos

### Pontos positivos do seu uso
Além dos riscos, temos diversos benefícios que podem ser aproveitados com seu uso correto. Abaixo listo alguns deles:
- Facilidade de manutenção
- Minimização de erros
- Agilidade no desenvolvimento
- Padronização
- Reutilização de código

Cabe a nós desenvolvedores analisarmos os trade-offs de seu uso.

## Estrutura da API
Nesta API utilizei uma estrutura básica para demonstrar a aplicação de Reflection e outros conceitos para persistêcia de
dados em uma base H2.

- model
    - Classe Person é o nosso modelo/entidade que será persistido no banco, em que criamos uma tabela com o mesmo nome e
     atributos.
- annotation
    - Pacote que inclui as annotations @Column e @PrimaryKey muito utilizadas pelo Hibernante e outros ORMs.
- orm
    - Pacote que contém nosso gerenciador, aqui criamos os métodos de persistência e consulta no banco de dados, nele 
    utilizamos reflection para  

 - util
    - MetaModelo, onde conseguimos retirar informações do modelo que queremos persistir no banco
     de dados, identificando chave primária e colunas, através das respectivas annotations utilizadas nos atributos de um 
     objeto T, conhecido somente em tempo de execução. 
     
    - EntityMaanger modela a escrita e leitura das instÂncias de T para qualquer tipo de armazenamento ou mídia, sem conhecer
    o que é T no tempo de compilação, é uma replica (MUITO) simplificada do EntityManager que utilizamos em alguns frameworks,
    mas, que nos dá um pouco da dimensão de sua complexidade.
    
    - ColumnField e PrimaryKeyField são utilizados para extrair os dados do objeto no metamodelo.
    
  - ReflectionApplication 
    - É a classe principal onde é executado um teste simples de escrita e leitura dos dados através da estrutura descrita 
    acima.