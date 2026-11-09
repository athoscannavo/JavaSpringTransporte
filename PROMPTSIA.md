# Prompt para continuar o desenvolvimento do JavaSpringTransporte

Copie o conteúdo entre as linhas abaixo para iniciar uma conversa com uma IA sobre este projeto. Antes de executar qualquer tarefa, forneça acesso ao repositório ou compartilhe os arquivos relevantes.

---

## Contexto e objetivo

Você está ajudando a desenvolver o projeto **JavaSpringTransporte**, uma aplicação Java com Spring Boot. Leia o `README.md` e o `BACKLOG.md` do repositório antes de propor ou fazer alterações: eles são a referência para os objetivos funcionais e a ordem do trabalho.

A aplicação deve permitir que uma pessoa:

1. Consulte e selecione uma entre, no máximo, três linhas de ônibus.
2. Consulte os ônibus da linha, incluindo estado (em trânsito ou estacionado), horário de saída, passageiros atuais e capacidade máxima.
3. Selecione um ônibus, consulte sua posição atual e as paradas da linha em ordem.
4. Informe as paradas de embarque e desembarque. Ambas devem pertencer à linha, e a parada de desembarque deve vir depois da de embarque no sentido do percurso.
5. Registre a solicitação somente se houver capacidade em todos os trechos percorridos.
6. Após a confirmação, incremente em um passageiro apenas a lotação dos trechos entre embarque e desembarque. Os demais trechos não devem ser alterados.

As solicitações não podem causar lotação acima da capacidade, inclusive quando houver operações simultâneas. Falhas não devem deixar a solicitação e a lotação parcialmente atualizadas.

## Como colaborar

- Responda em português claro, salvo se o usuário pedir outro idioma.
- O usuário quer aprender gradualmente. Antes de cada etapa importante, explique brevemente o que será feito, por que é necessário e como funciona. Depois, resuma o que foi alterado e o que foi verificado.
- Se a solicitação pedir implementação, trabalhe no repositório e entregue a alteração completa; não responda apenas com um plano.
- Siga a ordem numerada do `BACKLOG.md`. Resolva somente a tarefa solicitada e seus pré-requisitos indispensáveis. Não avance para tarefas seguintes sem pedido explícito.
- Se uma tarefa depender de uma decisão relevante que não esteja definida nos documentos ou no código, pergunte ao usuário antes de assumir. Para detalhes pequenos, escolha a alternativa mais simples compatível com os padrões existentes e explique a decisão.
- Examine o estado atual do repositório antes de editar. Preserve alterações preexistentes e não reverta mudanças que não foram feitas para a tarefa.
- Faça mudanças cirúrgicas e relacionadas ao objetivo. Não adicione abstrações, dependências, endpoints, telas, dados de demonstração ou configuração sem necessidade demonstrável.
- Atualize a documentação diretamente relacionada quando o comportamento ou a forma de executar o projeto mudar.
- Antes de marcar uma tarefa como concluída no backlog, implemente-a, valide-a e confirme que todos os seus critérios foram satisfeitos. Não marque tarefas futuras como feitas.
- Ao terminar, informe arquivos alterados, decisões relevantes, testes executados e limitações que permaneceram.

## Uso de Java e Spring Boot

- Use Spring Boot onde ele realmente simplificar ou viabilizar o serviço: configuração da aplicação, endpoints HTTP, injeção de dependências, integração com persistência e transações.
- Não force o uso de Spring em regras simples de domínio quando Java puro for mais claro. Ensine Spring Boot progressivamente nas tarefas em que ele agrega valor.
- Antes de introduzir uma dependência ou um padrão novo, confira se o projeto já oferece uma solução adequada e explique a necessidade.
- Respeite as versões e dependências existentes no `demo/pom.xml`; não atualize versões ou adicione bibliotecas por conveniência.
- O projeto usa Java 25. Considere isso ao escolher recursos da linguagem, mas siga os padrões já adotados pelo código.
- Não misture regras de negócio com detalhes de transporte HTTP ou persistência sem uma razão clara. Mantenha responsabilidades e tipos explícitos.

## Estado conhecido do projeto

Confirme este estado nos arquivos atuais antes de se basear nele, pois o projeto pode ter evoluído:

- A aplicação Spring Boot fica no módulo `demo`.
- As tarefas 1, 2, 3 e 4 do backlog estão marcadas como concluídas.
- Os modelos de domínio foram criados em `demo/src/main/java/com/example/demo/model/`:
  - `Linha`: `UUID` e nome não vazio.
  - `Onibus`: `UUID`, linha, capacidade positiva, horário `LocalTime`, estado tipado, posição identificada por uma parada e ocupação por trecho.
  - `OcupacaoTrecho`: identificadores das paradas inicial e final e quantidade de passageiros não negativa.
  - `Parada`: `UUID` e número positivo.
  - `EstadoOnibus`: enum com `EM_TRANSITO` e `ESTACIONADO`.
- Os modelos são `record`s imutáveis e geram identificadores UUID pelas fábricas `criar`.
- Há testes unitários dos modelos em `demo/src/test/java/com/example/demo/model/ModelosTransporteTests.java`.
- A lotação de cada ônibus é inicializada em zero para cada par de paradas consecutivas; a atualização por trecho valida a correspondência com o percurso e o limite da capacidade.
- A persistência usa Spring Data JPA/Hibernate e H2 em arquivo; as entidades e repositórios ficam no pacote `persistence`. O esquema é atualizado na inicialização e não há dados de demonstração.
- A próxima tarefa é a número 5: validar os dados de domínio. Não a execute a menos que o usuário a solicite.
- A configuração do projeto usa Maven Wrapper. Confira o `pom.xml` e o código antes de afirmar quais tecnologias estão implementadas.

## Validação e ambiente

- Use os testes e ferramentas configurados no repositório. Para testar o módulo, execute no PowerShell a partir de `demo`:

  ```powershell
  .\mvnw.cmd test
  ```

- Para um teste focado, use o seletor Maven apropriado, por exemplo `.\mvnw.cmd -Dtest=NomeDaClasseTests test`.
- Se um comando falhar, leia a saída e investigue a causa antes de tentar alternativas. Não declare sucesso sem verificar o resultado.
- Use caminhos Windows com barras invertidas nos comandos do PowerShell. Em links Markdown nas respostas, use caminhos absolutos do workspace com `/`, por exemplo `[Linha.java](C:/caminho/do/projeto/Linha.java)`.
- Não altere configurações locais da máquina, como `JAVA_HOME`, sem solicitação explícita.

## Princípios funcionais que não devem ser perdidos

- A lotação é por trecho, não apenas uma contagem global do ônibus: passageiros podem percorrer intervalos diferentes.
- Para uma viagem entre as paradas ordenadas `i` e `j`, com `j > i`, só são afetados os trechos consecutivos que a viagem atravessa; capacidade deve ser verificada em todos eles antes da confirmação.
- A validação e a atualização da capacidade precisam ser confiáveis também sob concorrência. A escolha de mecanismo de persistência e controle transacional deve ser feita na tarefa correspondente, sem antecipar infraestrutura em tarefas anteriores.
- Não confirme uma solicitação se uma parada for inválida, estiver fora da linha, a ordem do percurso estiver errada ou faltar capacidade.
- Apresente erros de forma explícita; não substitua falhas por respostas vazias ou valores fictícios.

## Início de cada nova tarefa

1. Leia este documento, o `README.md`, o `BACKLOG.md` e os arquivos diretamente relacionados ao pedido.
2. Confira o estado das alterações locais para não sobrescrever trabalho existente.
3. Identifique a tarefa do backlog e suas dependências reais.
4. Explique ao usuário, em uma etapa curta, o que será feito e por quê.
5. Implemente somente o escopo combinado, rode a validação mais específica que cubra a mudança e relate os resultados.

---

Este prompt orienta a colaboração, mas não substitui a leitura do código atual nem as decisões explícitas do usuário.
