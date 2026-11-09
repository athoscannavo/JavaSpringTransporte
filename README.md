# JavaSpringTransporte

Para contribuir, Certifique-se de documentar as alterações realizadas tanto no código quanto nos outros itens do repositório. Escreva seus pull requests de maneira que os outros possam entender com o que estamos trabalhando e rastrear erros com mais facilidade.

## Objetivo do projeto

A aplicação permitirá que o usuário consulte os ônibus disponíveis em uma linha, escolha onde embarcar e desembarcar e registre sua viagem. Ao confirmar a solicitação, o sistema atualizará a lotação do ônibus nos trechos percorridos.

## Serviços esperados

### Consultar linhas e ônibus

1. O usuário acessa a opção de consulta de linhas.
2. O sistema apresenta até três linhas disponíveis para seleção.
3. Após a escolha de uma linha, o sistema lista os ônibus associados a ela.
4. Para cada ônibus, o sistema informa:
   - se está em trânsito ou estacionado;
   - o horário de saída;
   - a quantidade atual de passageiros e a capacidade máxima.

### Consultar o percurso e escolher as paradas

1. Ao selecionar um ônibus, o usuário consulta sua posição atual e as paradas da linha.
2. As paradas são identificadas por números e apresentadas na ordem em que são percorridas.
3. O usuário informa a parada em que pretende embarcar e a parada em que pretende desembarcar.
4. O sistema aceita a seleção somente se ambas as paradas pertencerem à linha escolhida e se a parada de desembarque vier depois da parada de embarque no sentido do percurso.
5. Se a seleção for inválida, o sistema informa o motivo e não registra a solicitação.

### Registrar a viagem e atualizar a lotação

1. Antes de confirmar a solicitação, o sistema verifica se há capacidade em todos os trechos entre as paradas de embarque e desembarque.
2. Se qualquer um desses trechos estiver lotado, a solicitação não é confirmada e o sistema informa que não há capacidade disponível.
3. Se houver capacidade em todos os trechos, o sistema confirma a solicitação e acrescenta um passageiro à lotação de cada trecho percorrido.
4. A lotação dos trechos anteriores ao embarque e posteriores ao desembarque não é alterada.
5. Após a confirmação, o sistema apresenta a lotação atualizada por trecho.

## Regras de negócio

- A aplicação disponibiliza no máximo três linhas.
- Cada ônibus pertence a uma linha e possui capacidade máxima definida.
- A ordem das paradas determina o sentido do percurso.
- A parada de desembarque deve vir depois da parada de embarque.
- A lotação é controlada por trecho, pois passageiros podem embarcar e desembarcar em pontos diferentes.
- Uma solicitação só é confirmada se todos os trechos percorridos tiverem capacidade disponível.
- Cada solicitação confirmada acrescenta exatamente um passageiro aos trechos entre embarque e desembarque.

As tarefas necessárias para implementar esses serviços estão listadas em [BACKLOG.md](./BACKLOG.md).

Para orientar o uso de IA em outras conversas e manter as decisões e prioridades do projeto, consulte [PROMPTSIA.md](./PROMPTSIA.md).

## Como executar o projeto

### Pré-requisitos

- Java JDK 25 instalado.
- Git instalado (caso vá clonar o repositório).

### Executando a aplicação

1. Clone o repositório e entre na pasta do projeto.
2. Abra um terminal na pasta que contém o arquivo `mvnw`.
3. Execute o comando:

```bash
./mvnw spring-boot:run
```

4. Aguarde até que a aplicação seja iniciada.
5. Acesse `http://localhost:8080` no navegador para testar a API, utilizando os endpoints disponíveis no projeto.

**Observação:** mantenha o terminal aberto enquanto estiver utilizando a aplicação.

### Persistência local

Os dados são armazenados em um banco H2 em arquivo, no caminho `demo/data/transporte.mv.db` quando a aplicação é iniciada a partir do módulo `demo`. O esquema é atualizado pelo Hibernate na inicialização; nenhum dado de demonstração é criado automaticamente.

Para entender a configuração, as tabelas, suas colunas e relacionamentos, consulte [PERSISTENCIA.md](./PERSISTENCIA.md).

### Windows (PowerShell)

```powershell
.\mvnw.cmd spring-boot:run
```

1. Corrige o JAVA_HOME
   No terminal Git Bash do VS Code, copia e executa estes comandos:
   export JAVA_HOME="/c/Users/joaor/AppData/Local/Programs/Eclipse Adoptium/jdk-25.0.2.10-hotspot"
   export PATH="$JAVA_HOME/bin:$PATH"

Esse é o caminho que identificamos anteriormente para tua instalação do JDK 25. 2. Confirma a versão utilizada
Executa:
echo "$JAVA_HOME"
java -version
javac -version
./mvnw -version

O resultado esperado no último comando é algo semelhante a:
Java version: 25.0.2

Se ainda aparecer Java 24, não prossigas para a compilação: precisamos corrigir o ambiente primeiro. 3. Tenta iniciar novamente
Se o Maven mostrar Java 25, executa:
./mvnw clean spring-boot:run
