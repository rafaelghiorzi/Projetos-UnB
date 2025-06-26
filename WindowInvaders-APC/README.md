# Window Invaders 🚀

**Universidade de Brasília**  
**Instituto de Ciências Exatas**  
**Departamento de Ciência da Computação**  
**Algoritmos e Programação de Computadores - 2/2023**

**Desenvolvido por:** Rafael Dias Ghiorzi  
**Matrícula:** 232006144  
**Projeto Final - Parte 1**

## 📋 Descrição

Window Invaders é um jogo de sobrevivência inspirado no clássico Space Invaders, desenvolvido em Python com Pygame. O jogador controla um personagem que deve eliminar inimigos enquanto gerencia seu combustível e evita colisões. O objetivo é sobreviver o máximo de tempo possível acumulando pontos.

## 🎮 Características do Jogo

### Mecânicas Principais

- **Sistema de Combustível**: O combustível diminui gradualmente e ao atirar
- **Múltiplos Tipos de Inimigos**: Cada tipo possui comportamentos únicos
- **Sistema de Pontuação**: Ganhe pontos eliminando inimigos
- **Modo Ranqueado**: Salve suas melhores pontuações
- **Configurações Personalizáveis**: Ajuste dificuldade e tamanho da tela

### Tipos de Inimigos

| Tipo                | Cor | Comportamento                | Pontos   | Características                                       |
| ------------------- | --- | ---------------------------- | -------- | ----------------------------------------------------- |
| **Vermelho**        | 🔴  | Move-se horizontalmente      | 50       | Inimigo básico                                        |
| **Rosa/Laranja**    | 🟠  | Move-se horizontalmente      | Variável | Múltiplas vidas, elimina todos os vermelhos ao morrer |
| **Vermelho Escuro** | 🔴  | Move-se em todas as direções | -        | Atira projéteis, múltiplas munições                   |

### Itens Especiais

- **⛽ Tanques de Combustível**: Restauram 40 pontos de combustível

## 🎯 Como Jogar

### Controles

- **W**: Mover para cima
- **S**: Mover para baixo
- **A**: Mover para esquerda
- **D**: Mover para direita
- **X**: Atirar

### Objetivos

1. **Sobreviva** o máximo de tempo possível
2. **Elimine inimigos** para ganhar pontos
3. **Colete combustível** para não ficar sem energia
4. **Evite colisões** com inimigos e projéteis

### Condições de Game Over

- ❌ Combustível zerado
- ❌ Colisão com inimigo
- ❌ Atingido por projétil inimigo

## 🚀 Instalação e Execução

### Pré-requisitos

- Python 3.7 ou superior
- Pygame

### Instalação

1. Clone o repositório:

```bash
git clone <url-do-repositorio>
cd WindowInvaders-APC
```

2. Instale o Pygame:

```bash
pip install pygame
```

3. Execute o jogo:

```bash
python windows_invaders.py
```

## 📁 Estrutura do Projeto

```bash
WindowInvaders-APC/
│
├── windows_invaders.py    # Código principal do jogo
├── assets/               # Recursos gráficos
│   ├── jogador.png       # Sprite do jogador
│   ├── inimigoX.png      # Sprite inimigo vermelho
│   ├── inimigoO.png      # Sprite inimigo laranja
│   ├── inimigoT.png      # Sprite inimigo atirador
│   ├── tiroamigo.png     # Sprite projétil do jogador
│   ├── tiroinimigo.png   # Sprite projétil inimigo
│   └── fuel.png          # Sprite tanque de combustível
├── ranking.json          # Arquivo de rankings
└── README.md            # Documentação
```

## ⚙️ Configurações

### Modo Ranqueado

- **Ativação**: Menu → Configurações → Modo Ranqueado
- **Características**:
  - Configurações padronizadas para competição justa
  - Salva automaticamente no ranking
  - Requer nickname antes de jogar

### Configurações Personalizáveis (Modo Casual)

- **Tamanho do Tabuleiro**: Largura e altura customizáveis
- **Probabilidade de Spawn**: Ajuste a frequência de aparição dos elementos
- **Atributos dos NPCs**: Vida dos inimigos e munição

### Configurações Padrão

| Parâmetro           | Valor Padrão   | Descrição                   |
| ------------------- | -------------- | --------------------------- |
| Resolução           | 1500x600       | Tamanho da janela           |
| FPS                 | 50 (crescente) | Taxa de quadros             |
| Combustível Inicial | 400            | Combustível no início       |
| Velocidade          | 2              | Velocidade base dos objetos |

## 📊 Sistema de Ranking

O jogo mantém um ranking dos 10 melhores jogadores em modo ranqueado:

- Salvo automaticamente no arquivo `ranking.json`
- Ordenado por pontuação decrescente
- Exibe nickname e pontuação

## 🏗️ Arquitetura do Código

### Classes Principais

- **`Jogador`**: Controla o personagem principal
- **`Projeteis`**: Gerencia projéteis do jogador
- **`ProjeteisInimigos`**: Gerencia projéteis dos inimigos
- **`TanquesCombustivel`**: Controla itens de combustível
- **`criaInimigosX/O/T`**: Classes para diferentes tipos de inimigos

### Funcionalidades Técnicas

- **Sistema de Colisão**: Detecção precisa usando `pygame.Rect.colliderect()`
- **Spawning Probabilístico**: Aparição aleatória baseada em probabilidades
- **Persistência de Dados**: Rankings salvos em JSON
- **Interface CLI**: Menu interativo no terminal

## 🎨 Assets Gráficos

O jogo utiliza sprites PNG para todos os elementos visuais:

- Resolução padrão: 40x40 pixels
- Suporte a redimensionamento automático
- Assets organizados na pasta `./assets/`

## 🐛 Tratamento de Erros

- Validação de entrada do usuário
- Tratamento de arquivos não encontrados
- Verificação de limites da tela
- Prevenção de spawning inválido

## 🔧 Dependências

```python
import pygame      # Engine gráfica e de jogos
import os         # Operações do sistema
import sys        # Controle do sistema
import random     # Geração de números aleatórios
import time       # Controle de tempo
import json       # Manipulação de dados JSON
```

## 📈 Melhorias Futuras

- [ ] Sistema de power-ups
- [ ] Diferentes fases/níveis
- [ ] Música e efeitos sonoros
- [ ] Menu gráfico
- [ ] Multiplayer local
- [ ] Sistema de achievements

## 🎓 Contexto Acadêmico

Este projeto foi desenvolvido como trabalho final da disciplina "Algoritmos e Programação de Computadores" da Universidade de Brasília, demonstrando:

- **Programação Orientada a Objetos**: Classes e herança
- **Estruturas de Dados**: Listas, dicionários e manipulação
- **Algoritmos**: Detecção de colisão, spawning probabilístico
- **Interface Gráfica**: Implementação com Pygame
- **Persistência**: Salvamento em arquivos JSON
- **Tratamento de Eventos**: Input do usuário e eventos do sistema

## 🤝 Contribuição

Este é um projeto acadêmico, mas sugestões são bem-vindas:

1. Fork o projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📄 Licença

Projeto desenvolvido para fins acadêmicos na Universidade de Brasília.

## 📞 Contato

**Rafael Dias Ghiorzi**  
**Matrícula:** 232006144  
**Universidade de Brasília - Departamento de Ciência da Computação**

---

_Desenvolvido com 💻 e ☕ na UnB_
