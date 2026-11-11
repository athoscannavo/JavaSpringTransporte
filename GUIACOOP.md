# Guia para IA: compatibilidade entre tarefas paralelas

Este arquivo complementa [`PROMPTSIA.md`](./PROMPTSIA.md) e [`BACKLOG.md`](./BACKLOG.md). Use-o como instrução adicional ao implementar uma tarefa que possa ser desenvolvida em paralelo com outras, em branches e computadores diferentes.

## Instrução principal

Implemente somente o escopo solicitado, mas escreva o código considerando que outras tarefas do backlog podem estar ausentes ou ter sido implementadas em outra branch. Não presuma que o estado do backlog corresponde ao checkout atual. Inspecione o código disponível e mantenha explícitos os pontos de integração, sem inventar comportamentos para componentes ainda inexistentes.

## Antes de alterar o código

1. Leia `README.md`, `BACKLOG.md`, `PROMPTSIA.md` e este guia; inspecione também os arquivos diretamente relacionados à tarefa.
2. Identifique a tarefa, os pré-requisitos funcionais e os tipos, métodos, dados e respostas que a implementação espera receber de outras áreas.
3. Confirme esses contratos no código. Se não existirem, escolha um contrato mínimo compatível com os padrões do projeto e descreva a suposição. Se duas alternativas afetarem significativamente a integração, peça uma decisão em vez de criar contratos incompatíveis.
4. Considere que outra branch pode conter a implementação faltante com nomes ou responsabilidades diferentes. Prefira contratos pequenos e coesos, use os modelos e convenções existentes e evite duplicar tipos, lógica ou configuração.

## Ao implementar com pré-requisitos ausentes

- Mantenha regras de domínio independentes de detalhes de HTTP, interface e banco, salvo quando a tarefa exigir explicitamente essa integração.
- Encapsule dependências externas em contratos claros e estreitos. Não replique nesses contratos entidades inteiras quando bastarem identificadores ou dados específicos.
- Se a tarefa puder ser implementada corretamente sem o pré-requisito, faça essa parte e teste-a. Deixe claro o ponto de integração e o que ainda precisa existir para o fluxo funcionar.
- Se um pré-requisito for indispensável, implemente somente o mínimo necessário quando isso estiver dentro do escopo combinado. Caso contrário, não construa uma versão paralela ou provisória: explique o bloqueio e solicite orientação.
- Não trate dados fixos, mocks, armazenamento em memória ou respostas simuladas como implementação real. Use mocks apenas em testes ou quando forem explicitamente identificados como provisórios.
- Não esconda ausência de dependências com coleções vazias, valores fictícios, sucesso aparente ou tratamento silencioso de erros.
- Preserve compatibilidade com Java, Spring Boot, dependências, estrutura de pacotes, convenções e padrões já adotados. Não atualize versões nem introduza tecnologias sem necessidade e autorização compatíveis com o escopo.

## Mapa de dependências para orientar a implementação

| Tarefas | Área e contratos relevantes |
|---|---|
| 1–3 | Modelos de linha, ônibus, paradas, ordem do percurso, posição e ocupação por trecho. São contratos fundamentais para persistência, consultas e registro. |
| 4–5 | Persistência e validação de domínio. Podem ser desenvolvidas separadamente se compartilharem os mesmos modelos e invariantes; validação não deve depender de endpoints. |
| 6–9 | Consultas de linhas, ônibus, paradas e posição. Dependem de fontes de dados e contratos de leitura; o item 8 define tratamento de identificadores inválidos e entidades ausentes e deve ser consistente entre endpoints. |
| 10–11 | Regras de associação das paradas à linha e ordem do percurso. Podem ser testadas sem API; a operação de registro deve reutilizar essas regras. |
| 12–16 | Contrato e fluxo de registro, validação de capacidade em todos os trechos, atualização seletiva, atomicidade e concorrência. Os itens 15–16 exigem integração com persistência e não devem ser considerados prontos por testes apenas em memória. |
| 17 | Testes unitários e de integração devem acompanhar cada parte. Os testes integrados só comprovam os fluxos quando dependências reais estiverem conectadas. |
| 18–21 | Interface e fluxo do usuário. Podem avançar sem servidor concluído se o contrato HTTP estiver acordado e isolado; não fixe modelos de resposta incompatíveis com a API existente. |
| 22 | Teste ponta a ponta. Depende de interface e API integradas; preparar cenários não equivale a concluir o teste. |

Uma sequência de integração provável é:

```text
1 → 2 → 3 → 4
2 → 10, 11
3 → 5
4 → 6, 7, 9
4 + 5 + 8 + 10 + 11 → 12 → 13, 14 → 15, 16
6, 7 → 18 → 19 (+9) → 20 (+10, 11, 12) → 21 (+12–16)
18–21 integrados + API → 22
```

O item 17 acompanha as áreas; o item 8 atravessa a API. Esta sequência é uma hipótese de integração, não autorização para avançar tarefas nem substituto da inspeção do código.

## Compatibilidade entre branches

Ao entregar a implementação, inclua na resposta:

- arquivos e responsabilidades alterados;
- contratos públicos novos ou alterados (assinaturas, campos, rotas, formatos e invariantes);
- dependências de outras tarefas e quais estão ausentes neste checkout;
- decisões assumidas que a outra implementação deverá respeitar ou adaptar;
- validações executadas e o que depende de integração posterior.

Evite mudanças desnecessárias em arquivos compartilhados, especialmente modelos, configurações e contratos de API. Quando a integração revelar diferenças entre branches, adapte a implementação ao código consolidado, preservando comportamento e removendo duplicações; não mantenha duas versões concorrentes do mesmo contrato.

Marque uma tarefa como concluída somente quando seus critérios estiverem implementados no código integrado e validados. Se parte do trabalho depender de outra branch, descreva-a como parcial ou pendente e não atualize o status do backlog como concluído.
