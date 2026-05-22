# Projeto Concorrente

Simulação simples de microserviços com `pthread` e `semaphore` em C.

O programa `concorrente.c` modela um fluxo de requisições onde:

- múltiplos usuários geram pedidos concorrentes;
- um Load Balancer recebe os pedidos e aplica `rate limit`;
- instâncias de API processam os pedidos se houver espaço disponível.

## Arquitetura

1. **Users**: threads que geram pedidos periódicos e os enfileiram no Load Balancer.
2. **Load Balancer**: consome a fila de entrada, tenta adquirir um token do rate limit via `sem_trywait()` e roteia o pedido para uma das APIs.
   - se houver token disponível, o pedido é encaminhado para uma instância de API com fila livre;
   - se não houver token, retorna `429 TOO MANY REQUESTS` e descarta o pedido;
   - se as APIs estiverem cheias, libera o token e retorna `503 SERVICE UNAVAILABLE`.
3. **APIs**: cada instância aguarda pedidos em sua fila, processa o pedido e libera o token de rate limit com `sem_post()`.

## Componentes principais

- `NUM_USERS`: número de threads de usuário gerando pedidos.
- `RATE_LIMIT`: número de tokens no semáforo do Load Balancer.
- `NUM_APIS`: quantidade de instâncias de API.
- `QUEUE_SIZE`: tamanho máximo das filas internas.

## Como compilar

Execute:

```bash
gcc concorrente.c -o concorrente -lpthread
```

## Como executar

```bash
./concorrente
```

O programa roda continuamente, imprimindo no terminal eventos de criação, aceitação, descarte e processamento de pedidos.

## Detalhes de implementação

- usa `pthread_mutex_t` e `pthread_cond_t` para sincronizar acesso às filas do Load Balancer e das APIs;
- usa `sem_t` para implementar o rate limit do Load Balancer;
- cada fila é um buffer circular fixo com `push()` e `pop()`;
- threads de usuário dormem entre 1 e 3 segundos antes de enviar um novo pedido;
- cada API processa pedidos com tempo aleatório de 1 a 2 segundos.

## Observações

- o programa não faz tratamento de término; ele continua rodando até ser interrompido (`Ctrl+C`).
- como as threads de usuário usam IDs alocados dinamicamente, o programa poderia ser aprimorado para liberar essas alocações.

## Licença

Este projeto é um exemplo educacional de concorrência em C.
