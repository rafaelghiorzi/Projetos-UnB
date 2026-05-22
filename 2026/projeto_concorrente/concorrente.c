#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <semaphore.h>
#include <time.h>
#include <unistd.h>
#include <errno.h>

/*
======================================================================================
SIMULAÇÃO DE MICROSSERVIÇOS: LOAD BALANCER, APIs E RATE LIMIT
======================================================================================

ARQUITETURA DO FLUXO:
1. USERS: Threads geram pedidos (ints) em paralelo e inserem na fila de entrada do Load Balancer.
2. LOAD BALANCER: Thread consome a fila, checa o Rate Limit via sem_trywait() e roteia pedidos
   para instâncias de API disponíveis.
   - Se houver vaga (Semáforo > 0): decrementa o token e envia o pedido para uma API.
   - Se lotado (Semáforo = 0): descarta o pedido imediatamente com HTTP 429.
3. INSTÂNCIAS DE API: Threads esperam seus pedidos usando Mutex + pthread_cond.
   Quando recebem um pedido, processam-no e chamam sem_post() para liberar o Rate Limit.
======================================================================================
*/


#define NUM_USERS 5
#define RATE_LIMIT 3
#define NUM_APIS 2
#define QUEUE_SIZE 10

// Códigos de cor ANSI para terminal
#define COLOR_USER "\033[92m"      // Verde claro
#define COLOR_LB "\033[94m"        // Azul
#define COLOR_API "\033[93m"       // Amarelo
#define COLOR_RESET "\033[0m"      // Reset

typedef struct {
    int items[QUEUE_SIZE];
    int head;
    int tail;
    int count;
} Queue;

void initializeQueue(Queue *q) {
    q->head = 0;
    q->tail = 0;
    q->count = 0;
}

bool isEmpty(Queue *q) {
    return (q->count == 0);
}

bool isFull(Queue *q) {
    return (q->count == QUEUE_SIZE);
}

void push(Queue *q, int item) {
    if (isFull(q)) {
        printf("Fila cheia!\n");
        return;
    }
    q->items[q->tail] = item;
    q->tail = (q->tail + 1) % QUEUE_SIZE;
    q->count++;
}

int pop(Queue *q) {
    if (isEmpty(q)) {
        printf("Fila vazia!\n");
        return -1;
    }
    int item = q->items[q->head];
    q->head = (q->head + 1) % QUEUE_SIZE;
    q->count--;
    return item;
}

// ============================================================================
// SINCRONIZADORES DO LOAD BALANCER
// ============================================================================
sem_t lb_semaphore;                                   // Semáforo para Rate Limit
pthread_mutex_t lb_mutex = PTHREAD_MUTEX_INITIALIZER; // Mutex da fila LB
pthread_cond_t lb_cond = PTHREAD_COND_INITIALIZER;    // Condição (aguarda novos itens)

// ============================================================================
// FILAS
// ============================================================================
Queue lb_queue;                    // Fila do Load Balancer
Queue api_queues[NUM_APIS];        // Filas para cada instância de API

// ============================================================================
// SINCRONIZADORES DAS APIs
// ============================================================================
pthread_mutex_t api_mutexes[NUM_APIS]; // Mutexes para cada fila de API
pthread_cond_t api_conds[NUM_APIS];    // Condições para cada fila de API

void initializeApiSync(void) {
    for (int i = 0; i < NUM_APIS; i++) {
        pthread_mutex_init(&api_mutexes[i], NULL);
        pthread_cond_init(&api_conds[i], NULL);
    }
}

void *user(void *id) {
    int userId = *(int *)id;

    while(true) {
        sleep(1 + rand() % 3); // Demora de 1 a 3 segundos para gerar um pedido

        pthread_mutex_lock(&lb_mutex);
        push(&lb_queue, userId);
        printf("%s[User %d] 201 CREATED - Pedido enfileirado%s\n", COLOR_USER, userId, COLOR_RESET);
        pthread_cond_signal(&lb_cond); // Acorda o Load Balancer
        pthread_mutex_unlock(&lb_mutex);
    }
    pthread_exit(0);
}

void *loadBalancer(void *arg) {
    (void)arg;
    while(true) {
        pthread_mutex_lock(&lb_mutex);

        // Aguarda até que haja um pedido na fila
        while (isEmpty(&lb_queue)) {
            pthread_cond_wait(&lb_cond, &lb_mutex);
        }

        int userId = pop(&lb_queue);
        pthread_mutex_unlock(&lb_mutex);

        // Tenta adquirir um token do Rate Limit
        if (sem_trywait(&lb_semaphore) == 0) {
            int startApi = rand() % NUM_APIS;
            bool routed = false;

            // Tenta encontrar uma API com espaço na fila
            for (int offset = 0; offset < NUM_APIS; offset++) {
                int apiId = (startApi + offset) % NUM_APIS;
                pthread_mutex_lock(&api_mutexes[apiId]);

                if (!isFull(&api_queues[apiId])) {
                    push(&api_queues[apiId], userId);
                    pthread_cond_signal(&api_conds[apiId]);
                    pthread_mutex_unlock(&api_mutexes[apiId]);

                    printf("%s[Load Balancer] 202 ACCEPTED - User %d roteado para API %d%s\n", 
                           COLOR_LB, userId, apiId, COLOR_RESET);
                    routed = true;
                    break;
                }
                pthread_mutex_unlock(&api_mutexes[apiId]);
            }

            if (!routed) {
                sem_post(&lb_semaphore);
                printf("%s[Load Balancer] 503 SERVICE UNAVAILABLE - User %d descartado%s\n", 
                       COLOR_LB, userId, COLOR_RESET);
            }
        } else if (errno == EAGAIN) {
            printf("%s[Load Balancer] 429 TOO MANY REQUESTS - User %d descartado%s\n", 
                   COLOR_LB, userId, COLOR_RESET);
        }
    }

    pthread_exit(0);
}

void *apiInstance(void *id) {
    int apiId = *(int *)id;

    while(true) {
        pthread_mutex_lock(&api_mutexes[apiId]);
        while (isEmpty(&api_queues[apiId])) {
            pthread_cond_wait(&api_conds[apiId], &api_mutexes[apiId]);
        }
        int userId = pop(&api_queues[apiId]);
        pthread_mutex_unlock(&api_mutexes[apiId]);

        printf("%s[API %d] 200 OK - Processando User %d%s\n", COLOR_API, apiId, userId, COLOR_RESET);
        sleep(1 + rand() % 2); // Simula o tempo de processamento
        printf("%s[API %d] 200 OK - User %d completado%s\n", COLOR_API, apiId, userId, COLOR_RESET);

        sem_post(&lb_semaphore);
    }

    pthread_exit(0);
}

int main() {
    srand(time(NULL));

    // iniciar todas as estruturas de sincronização
    sem_init(&lb_semaphore, 0, RATE_LIMIT); // Inicializa o semáforo do Load Balancer com o Rate Limit

    initializeQueue(&lb_queue); // Inicializa a fila do Load Balancer
    for (int i = 0; i < NUM_APIS; i++) {
        initializeQueue(&api_queues[i]); // Inicializa as filas das APIs
    }
    initializeApiSync(); // Inicializa os mutexes e condições das APIs

    pthread_t user_threads[NUM_USERS];
    for (int i = 0; i < NUM_USERS; i++) {
        int *userId = malloc(sizeof(int));
        *userId = i;
        pthread_create(&user_threads[i], NULL, user, (void *)userId);
    }

    pthread_t lb_thread;
    pthread_create(&lb_thread, NULL, (void *(*)(void *))loadBalancer, NULL);

    pthread_t api_threads[NUM_APIS];
    for (int i = 0; i < NUM_APIS; i++) {
        int *apiId = malloc(sizeof(int));
        *apiId = i;
        pthread_create(&api_threads[i], NULL, apiInstance, (void *)apiId);
    }

    pthread_exit(0);
    return 0;
}

