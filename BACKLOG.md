# Backlog

1. [x] Definir modelos para linhas, ônibus e paradas com identificadores estáveis.
   - Criar classes de domínio separadas para linha, ônibus e parada, com identificadores únicos e campos essenciais.
   - Escolher tipos adequados para identificadores, horários, estado e capacidade; evitar usar texto livre para valores com opções fechadas.
2. [x] Associar ônibus às linhas e ordenar as paradas de cada linha.
   - Representar a relação de cada ônibus com uma linha e cada parada com sua linha.
   - Armazenar uma ordem explícita para cada parada e garantir que a ordenação seja única dentro da linha.
3. [x] Registrar a capacidade, a lotação por trecho, o horário de saída, o estado e a posição atual de cada ônibus.
   - Definir os campos do ônibus e representar a ocupação entre cada par de paradas consecutivas.
   - Definir os valores permitidos para o estado do ônibus e representar a posição atual pelo identificador de uma parada da linha.
4. [x] Configurar persistência para manter os dados após reiniciar a aplicação.
   - Selecionar e configurar um banco de dados compatível com o projeto e a dependência de acesso a dados.
   - Criar repositórios e o esquema/tabelas para linhas, ônibus, paradas, ocupação por trecho e solicitações.
   - Configurar inicialização de dados de desenvolvimento, se necessária, sem depender de dados fictícios nos endpoints.
5. [ ] Validar os dados de domínio: limitar a três linhas e impedir lotação acima da capacidade.
   - Aplicar validações ao criar ou alterar dados, verificando limites, campos obrigatórios e relações válidas.
   - Rejeitar a criação de uma quarta linha e qualquer ocupação negativa ou superior à capacidade.
6. [ ] Criar operação para listar as linhas disponíveis.
   - Implementar um endpoint de consulta que leia as linhas persistidas e retorne identificador e informações úteis para seleção.
   - Retornar uma coleção vazia quando não houver linhas cadastradas, sem criar resultados estáticos.
7. [ ] Criar operação para listar os ônibus de uma linha com estado, horário de saída, lotação e capacidade.
   - Implementar um endpoint que receba o identificador da linha e consulte somente os ônibus associados a ela.
   - Montar uma resposta com identificador do ônibus, estado, horário, capacidade e lotação atual; se a lotação variar por trecho, indicar claramente qual trecho ou resumir conforme regra definida.
8. [ ] Tratar identificadores inválidos e linhas ou ônibus inexistentes com respostas apropriadas.
   - Validar formato e existência dos identificadores nos endpoints.
   - Usar códigos HTTP adequados e mensagens de erro consistentes, sem retornar objetos vazios como se a consulta tivesse sucesso.
9. [ ] Criar operação para consultar as paradas ordenadas de uma linha e a posição atual de um ônibus.
   - Implementar uma consulta que retorne as paradas por ordem de percurso e a posição mais recente do ônibus selecionado.
   - Confirmar que o ônibus pertence à linha solicitada antes de devolver os dados.
10. [ ] Validar que as paradas de embarque e desembarque pertençam à linha escolhida.
   - Ao receber uma solicitação, carregar as paradas informadas e conferir sua associação à linha do ônibus.
   - Rejeitar a solicitação quando uma parada não existir ou pertencer a outra linha.
11. [ ] Validar que a parada de desembarque venha depois da parada de embarque no sentido do percurso.
   - Comparar a ordem cadastrada das duas paradas na linha.
   - Aceitar somente quando o índice/ordem do desembarque for maior que o do embarque e retornar erro claro nos demais casos.
12. [ ] Criar operação para registrar uma solicitação com ônibus e paradas de embarque e desembarque.
   - Definir o formato da requisição e da resposta da solicitação.
   - Validar ônibus e paradas, aplicar as regras de percurso e só então iniciar a verificação de capacidade e o registro.
13. [ ] Verificar a capacidade disponível em todos os trechos antes de confirmar uma solicitação.
   - Identificar todos os trechos consecutivos cobertos pelo percurso entre embarque e desembarque.
   - Conferir a ocupação atual e a capacidade do ônibus em cada trecho; recusar integralmente a viagem se qualquer um estiver cheio.
14. [ ] Atualizar a lotação somente nos trechos entre embarque e desembarque.
   - Após validar disponibilidade, acrescentar exatamente um passageiro a cada trecho abrangido pela viagem.
   - Não alterar os trechos anteriores ao embarque nem posteriores ao desembarque e devolver a lotação atualizada na resposta ou consulta subsequente.
15. [ ] Garantir que falhas durante o registro não deixem a solicitação e a lotação parcialmente atualizadas.
   - Executar validação final, criação da solicitação e atualização de todos os trechos em uma única transação.
   - Configurar rollback quando qualquer gravação falhar e responder com erro sem declarar a solicitação confirmada.
16. [ ] Impedir que solicitações simultâneas façam a lotação ultrapassar a capacidade de qualquer trecho.
   - Aplicar controle transacional e de concorrência no banco durante a verificação e atualização de ocupação.
   - Escolher mecanismo apropriado, como bloqueio das linhas de ocupação afetadas ou atualização condicional atômica, e tratar conflito como indisponibilidade.
17. [ ] Criar testes para modelos, persistência, consultas, validações das paradas, capacidade por trecho e concorrência.
   - Adicionar testes unitários para regras de domínio e validações.
   - Adicionar testes de integração para repositórios e endpoints, incluindo erros e dados inexistentes.
   - Testar viagens sobrepostas e não sobrepostas, capacidade no limite e solicitações concorrentes.
18. [ ] Criar interface para selecionar linha e ônibus e consultar estado, horário, lotação e capacidade.
   - Escolher uma tecnologia de interface compatível com o repositório ou adicionar uma aplicação web separada conforme decisão do projeto.
   - Consumir os endpoints de linhas e ônibus e apresentar os campos retornados em controles de seleção e listagens.
19. [ ] Exibir na interface as paradas ordenadas e a posição atual do ônibus.
   - Ao escolher um ônibus, consultar a linha e mostrar a posição atual junto à lista ordenada de paradas.
   - Atualizar a visualização quando o usuário trocar de ônibus ou linha e tratar falhas da consulta.
20. [ ] Permitir a seleção das paradas de embarque e desembarque pela interface e apresentar erros de validação.
   - Fornecer controles para escolher as paradas exibidas, sem aceitar valores fora da linha selecionada.
   - Enviar a seleção à API e exibir as mensagens de validação recebidas quando as paradas forem incompatíveis ou estiverem na ordem errada.
21. [ ] Exibir a confirmação, a lotação atualizada ou a falta de capacidade após o envio da solicitação.
   - Enviar os dados da viagem à operação de registro e interpretar respostas de sucesso e erro.
   - Em caso de sucesso, apresentar confirmação e atualizar os dados de lotação; em caso de indisponibilidade, informar que a viagem não foi registrada.
22. [ ] Criar testes de integração para o fluxo da interface e as respostas de erro da API.
   - Automatizar o fluxo desde a seleção de linha até o registro da viagem, cobrindo sucesso e falhas de validação/capacidade.
   - Verificar a comunicação com a API e garantir que a interface não apresente confirmação para uma solicitação rejeitada.
