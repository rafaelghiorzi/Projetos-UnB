# Projeto 1 - Teoria e Aplicação de Grafos 📊

**Universidade de Brasília**  
**Instituto de Ciências Exatas**  
**Departamento de Ciência da Computação**  
**Disciplina: Teoria e Aplicação de Grafos**

## 📋 Descrição

Este projeto implementa uma análise completa de grafos utilizando a biblioteca NetworkX em Python. O objetivo é explorar as propriedades fundamentais dos grafos e aplicar algoritmos clássicos da teoria dos grafos para resolver problemas computacionais relacionados a conectividade, caminhos, fluxos e estruturas de rede.

## 🎯 Objetivos

- **Análise Estrutural**: Investigar propriedades topológicas de grafos
- **Algoritmos Clássicos**: Implementar e aplicar algoritmos fundamentais
- **Visualização**: Criar representações gráficas para análise visual
- **Aplicações Práticas**: Resolver problemas reais usando teoria dos grafos
- **Performance**: Analisar complexidade e eficiência dos algoritmos

## 🔧 Funcionalidades Implementadas

### Análise de Propriedades

- **Conectividade**: Componentes conexas e fortemente conexas
- **Centralidade**: Betweenness, Closeness, Eigenvector, PageRank
- **Métricas Básicas**: Grau, diâmetro, raio, densidade
- **Clustering**: Coeficiente de agrupamento local e global
- **Isomorfismo**: Detecção de grafos isomorfos

### Algoritmos de Caminho

- **Dijkstra**: Caminho mais curto com pesos
- **Floyd-Warshall**: Todos os pares de caminhos mais curtos
- **Bellman-Ford**: Caminho mais curto com pesos negativos
- **A\***: Busca heurística para caminhos ótimos
- **BFS/DFS**: Busca em largura e profundidade

### Algoritmos de Árvore

- **Kruskal**: Árvore geradora mínima
- **Prim**: Árvore geradora mínima
- **Árvores de Busca**: DFS e BFS trees
- **Spanning Trees**: Enumeração de árvores geradoras

### Fluxo em Redes

- **Ford-Fulkerson**: Fluxo máximo
- **Edmonds-Karp**: Implementação específica do Ford-Fulkerson
- **Dinic**: Algoritmo de fluxo máximo eficiente
- **Corte Mínimo**: Problema dual do fluxo máximo

### Problemas Clássicos

- **Coloração**: Coloração de vértices e arestas
- **Clique**: Detecção de cliques máximos
- **Conjunto Independente**: Conjuntos independentes máximos
- **Cobertura**: Cobertura mínima de vértices
- **Circuito Hamiltoniano**: Detecção e enumeração
- **Circuito Euleriano**: Detecção e construção

## 🚀 Instalação

### Pré-requisitos

```bash
Python 3.8+
pip (gerenciador de pacotes)
```

### Dependências

```bash
pip install networkx
pip install matplotlib
pip install numpy
pip install scipy
pip install pandas
pip install plotly
pip install seaborn
```

### Instalação do Projeto

```bash
git clone <url-do-repositorio>
cd Projeto-1-Grafos
pip install -r requirements.txt
```

## 💻 Uso

### Estrutura Básica

```python
import networkx as nx
import matplotlib.pyplot as plt
from src.graph_analyzer import GraphAnalyzer

# Criar analisador
analyzer = GraphAnalyzer()

# Carregar grafo
G = analyzer.load_graph('data/graph.txt')

# Análise completa
results = analyzer.full_analysis(G)

# Visualização
analyzer.plot_graph(G)
```

### Exemplos de Uso

#### Análise de Centralidade

```python
# Calcular diferentes métricas de centralidade
centrality = analyzer.calculate_centrality(G)
print(f"Betweenness: {centrality['betweenness']}")
print(f"Closeness: {centrality['closeness']}")
```

#### Algoritmos de Caminho

```python
# Caminho mais curto entre dois nós
path = analyzer.shortest_path(G, source='A', target='Z')
print(f"Caminho: {path}")

# Todos os pares de caminhos
all_paths = analyzer.all_pairs_shortest_path(G)
```

#### Fluxo Máximo

```python
# Calcular fluxo máximo entre fonte e sumidouro
max_flow = analyzer.maximum_flow(G, source='s', sink='t')
print(f"Fluxo máximo: {max_flow}")
```

## 📁 Estrutura do Projeto

```bash
Projeto-1-Grafos/
│
├── src/                     # Código fonte
│   ├── __init__.py
│   ├── graph_analyzer.py    # Classe principal de análise
│   ├── algorithms/          # Implementações de algoritmos
│   │   ├── __init__.py
│   │   ├── shortest_path.py # Algoritmos de caminho
│   │   ├── spanning_tree.py # Árvores geradoras
│   │   ├── flow.py         # Algoritmos de fluxo
│   │   ├── centrality.py   # Métricas de centralidade
│   │   └── classic.py      # Problemas clássicos
│   ├── utils/              # Utilitários
│   │   ├── __init__.py
│   │   ├── io.py          # Entrada/saída de dados
│   │   ├── visualization.py # Funções de visualização
│   │   └── metrics.py     # Cálculo de métricas
│   └── data_structures/    # Estruturas de dados customizadas
│       ├── __init__.py
│       ├── graph.py       # Implementação de grafo
│       └── heap.py        # Heap para algoritmos
│
├── data/                   # Conjuntos de dados
│   ├── small_graphs/      # Grafos pequenos para teste
│   ├── real_networks/     # Redes reais (social, transporte)
│   └── synthetic/         # Grafos sintéticos
│
├── notebooks/             # Jupyter notebooks
│   ├── analysis.ipynb    # Análise exploratória
│   ├── algorithms.ipynb  # Demonstração de algoritmos
│   └── visualization.ipynb # Visualizações avançadas
│
├── tests/                 # Testes unitários
│   ├── __init__.py
│   ├── test_algorithms.py
│   ├── test_centrality.py
│   └── test_utils.py
│
├── docs/                  # Documentação
│   ├── teoria.md         # Fundamentos teóricos
│   ├── algoritmos.md     # Descrição dos algoritmos
│   └── exemplos.md       # Exemplos de uso
│
├── results/              # Resultados e relatórios
│   ├── graphs/          # Grafos gerados
│   ├── analysis/        # Análises realizadas
│   └── benchmarks/      # Comparações de performance
│
├── requirements.txt      # Dependências
├── setup.py             # Configuração de instalação
└── README.md           # Este arquivo
```

## 📚 Conceitos Teóricos Abordados

### Teoria Básica

- **Definições**: Vértices, arestas, graus, caminhos
- **Tipos de Grafos**: Direcionados, não-direcionados, ponderados
- **Representações**: Matriz de adjacência, lista de adjacência
- **Isomorfismo**: Equivalência estrutural entre grafos

### Conectividade

- **Componentes Conexas**: Subgrafos maximais conexos
- **Pontes e Articulações**: Elementos críticos para conectividade
- **Conectividade k**: Robustez da conexão
- **Fluxo e Corte**: Capacidade de transferência

### Centralidade

- **Grau**: Número de conexões diretas
- **Proximidade**: Distância média para outros nós
- **Intermediação**: Controle sobre caminhos
- **Autovetor**: Influência baseada em conexões importantes

## 🧪 Testes e Validação

### Conjuntos de Teste

- **Grafos Pequenos**: Verificação manual de resultados
- **Casos Extremos**: Grafos completos, árvores, caminhos
- **Redes Reais**: Validação em dados do mundo real
- **Benchmark**: Comparação com implementações conhecidas

### Métricas de Avaliação

- **Corretude**: Verificação dos resultados
- **Performance**: Tempo de execução e uso de memória
- **Escalabilidade**: Comportamento com grafos grandes
- **Robustez**: Tratamento de casos especiais

## 📊 Datasets Utilizados

### Redes Sociais

- **Karate Club**: Rede clássica de Zachary
- **Facebook**: Rede de amizades
- **Twitter**: Rede de seguidores

### Redes de Transporte

- **Malha Rodoviária**: Conexões entre cidades
- **Rede Aérea**: Rotas de companhias aéreas
- **Transporte Público**: Linhas de metrô/ônibus

### Redes Biológicas

- **Proteínas**: Interações proteína-proteína
- **Genes**: Redes de regulação gênica
- **Neurônios**: Conectomas cerebrais

## 📈 Análises Realizadas

### Estudo Comparativo

- Performance de algoritmos de caminho mais curto
- Eficiência de diferentes representações de grafo
- Escalabilidade com tamanho da rede

### Análise de Redes Reais

- Propriedades de mundo pequeno
- Distribuição de graus
- Estrutura de comunidades

### Visualizações

- Layouts baseados em força
- Heatmaps de centralidade
- Análise temporal de redes dinâmicas

## 🔬 Algoritmos Implementados

| Categoria    | Algoritmo      | Complexidade     | Aplicação             |
| ------------ | -------------- | ---------------- | --------------------- |
| Caminho      | Dijkstra       | O((V + E) log V) | GPS, Roteamento       |
| Caminho      | Floyd-Warshall | O(V³)            | Análise de distâncias |
| Árvore       | Kruskal        | O(E log E)       | Redes de distribuição |
| Fluxo        | Ford-Fulkerson | O(E \* f)        | Redes de transporte   |
| Centralidade | PageRank       | O(V + E)         | Ranking web           |
| Coloração    | Greedy         | O(V + E)         | Agendamento           |

## 🎓 Contexto Acadêmico

Este projeto demonstra a aplicação prática de conceitos fundamentais da teoria dos grafos:

- **Modelagem**: Representação de problemas reais como grafos
- **Algoritmos**: Implementação eficiente de soluções clássicas
- **Análise**: Interpretação de resultados e métricas
- **Otimização**: Considerações de performance e escalabilidade
- **Visualização**: Comunicação efetiva de resultados

## 🤝 Contribuições

Para contribuir com o projeto:

1. Fork o repositório
2. Crie uma branch para sua feature
3. Implemente testes para novas funcionalidades
4. Mantenha a documentação atualizada
5. Submit um Pull Request

## 📖 Referências

### Livros

- "Introduction to Algorithms" - Cormen, Leiserson, Rivest, Stein
- "Networks" - Newman
- "Graph Theory" - Diestel

### Papers

- "A Fast Algorithm for Finding Dominators in a Flowgraph" - Lengauer & Tarjan
- "The PageRank Citation Ranking" - Page et al.
- "Community Structure in Social and Biological Networks" - Girvan & Newman

### Documentação

- [NetworkX Documentation](https://networkx.org/)
- [Matplotlib Documentation](https://matplotlib.org/)
- [NumPy Documentation](https://numpy.org/)

## 📄 Licença

Projeto desenvolvido para fins acadêmicos na Universidade de Brasília.

---

**Disciplina:** Teoria e Aplicação de Grafos  
**Universidade de Brasília - Departamento de Ciência da Computação**  
_Desenvolvido com 🧠 e 📊 na UnB_
