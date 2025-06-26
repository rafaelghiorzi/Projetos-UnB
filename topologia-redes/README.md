# Simulador de Topologia de Redes

Projeto de redes de computadores 2024.2 - Universidade de Brasília (UnB)

## 📋 Descrição

Este projeto implementa um simulador de topologia de redes que permite criar, visualizar e gerenciar uma rede hierárquica com roteadores, comutadores e hosts. O simulador oferece funcionalidades para executar comandos de rede como `ping` e `traceroute`, além de visualizar a topologia graficamente.

## 🏗️ Arquitetura da Rede

A topologia implementada segue uma estrutura hierárquica de três camadas:

```bash
                    [C1] - Roteador Core
                   /              \
              [A1] - Roteador     [A2] - Roteador
             /    \              /    \
        [E1]      [E2]      [E3]      [E4] - Comutadores
       /           |         |           \
   Hosts        Hosts     Hosts        Hosts
(24 hosts)   (24 hosts) (15 hosts)   (15 hosts)
```

### Especificações das Subredes

- **E1 (172.16.1.0/27)**: Capacidade para 30 hosts (24 utilizados)
- **E2 (172.16.1.32/27)**: Capacidade para 30 hosts (24 utilizados)
- **E3 (172.16.2.0/27)**: Capacidade para 30 hosts (15 utilizados)
- **E4 (172.16.2.32/27)**: Capacidade para 30 hosts (15 utilizados)
- **Backbone (172.16.0.0/30)**: Interconexão entre roteadores

### Tipos de Enlaces

- **Fibra Óptica**: Conexões backbone (10Gbps) e agregação (1Gbps)
- **Par Trançado**: Conexões de borda para hosts (200Mbps)

## 🚀 Instalação

### Pré-requisitos

- Python 3.8 ou superior
- pip (gerenciador de pacotes Python)

### Configuração do Ambiente

1. Clone o repositório:

```bash
git clone <url-do-repositorio>
cd topologia-redes
```

2. Crie um ambiente virtual (recomendado):

```bash
python -m venv .venv
source .venv/bin/activate  # Linux/Mac
# ou
.venv\Scripts\activate     # Windows
```

3. Instale as dependências:

```bash
pip install -r requirements.txt
```

## 💻 Uso

### Execução do CLI

Para iniciar o simulador:

```bash
python cli.py
```

### Menu Principal

O CLI oferece as seguintes opções:

1. **Adicionar Host**: Adiciona um novo host a uma subrede existente
2. **Executar Ping**: Simula um comando ping entre dois IPs
3. **Executar Traceroute**: Mostra o caminho entre dois dispositivos
4. **Plotar Rede**: Exibe a topologia graficamente
5. **Listar Dispositivos**: Lista todos os dispositivos da rede
6. **Mostrar Tabelas de Roteamento**: Exibe as tabelas de roteamento dos roteadores
7. **Sair**: Encerra o programa

### Exemplos de Uso

#### Ping entre dispositivos

```bash
IP de origem: 172.16.1.2
IP de destino: 172.16.2.2
```

#### Traceroute

```bash
IP de origem: 172.16.1.2
IP de destino: 172.16.2.34
```

#### Adicionar novo host

```bash
Nome do host: h25
IP do host com máscara: 172.16.1.25/27
```

## 🔧 Funcionalidades

### Algoritmos Implementados

- **Dijkstra (BFS)**: Para cálculo de rotas mais curtas
- **Tabelas de Roteamento**: Geração automática baseada no algoritmo de Dijkstra
- **Validação de IP**: Verificação de endereços IP válidos e disponibilidade de subrede

### Recursos de Visualização

- **Gráfico Interativo**: Visualização da topologia usando NetworkX e Matplotlib
- **Cores por Tipo**:
  - 🔵 Azul: Roteadores (quadrados)
  - 🟠 Laranja: Comutadores (diamantes)
  - 🟢 Verde: Hosts (círculos)
- **Informações de Enlaces**: Tipo, capacidade e justificativa

### Validações

- **Capacidade de Subrede**: Verificação automática do limite de hosts
- **Unicidade de IP**: Prevenção de IPs duplicados
- **Conectividade**: Verificação de conectividade antes da adição de hosts

## 📁 Estrutura do Projeto

```bash
topologia-redes/
│
├── cli.py              # Interface de linha de comando
├── topologia.py        # Classes principais (Dispositivo, Rede)
├── requirements.txt    # Dependências do projeto
├── README.md          # Documentação
└── .gitignore         # Arquivos ignorados pelo Git
```

## 🏷️ Classes Principais

### `Dispositivo`

Representa um dispositivo de rede (host, roteador ou comutador).

**Atributos:**

- `nome`: Identificador do dispositivo
- `ip`: Endereço IP com máscara
- `tipo`: Categoria do dispositivo
- `vizinhos`: Dicionário de dispositivos conectados
- `tabela_roteamento`: Tabela de rotas (apenas roteadores)

### `Rede`

Gerencia a topologia completa da rede.

**Métodos principais:**

- `adicionar_host()`: Adiciona um novo host
- `ping()`: Simula comando ping
- `traceroute()`: Simula comando traceroute
- `plotar_rede()`: Visualiza a topologia
- `mostrar_tabelas_roteamento()`: Exibe tabelas de roteamento

## 🧪 Testes

O projeto inclui validações automáticas para:

- Limites de capacidade de subrede
- Conectividade entre dispositivos
- Validação de endereços IP
- Cálculo de rotas ótimas

## 🤝 Contribuição

Este é um projeto acadêmico. Para contribuições:

1. Faça um fork do projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📄 Licença

Projeto desenvolvido para fins acadêmicos na Universidade de Brasília (UnB).

## 👥 Autores

- Projeto de Redes de Computadores 2024.2
- Universidade de Brasília (UnB)

## 📚 Referências

- Algoritmo de Dijkstra para roteamento
- RFC 791 - Internet Protocol
- Conceitos de redes hierárquicas
