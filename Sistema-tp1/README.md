# Sistema de Gerenciamento de Academia

Um sistema completo para gerenciamento de academia desenvolvido em Java com interface gráfica Swing, permitindo o controle de alunos, professores, aulas e reservas.

## 📋 Funcionalidades

### Para Alunos

- Cadastro e login de alunos
- Diferentes planos de assinatura (Normal, Gold, Platinum)
- Sistema de reservas de aulas
- Perfil personalizado com informações pessoais
- Limite de reservas baseado no plano contratado

### Para Professores

- Cadastro e login de professores
- Criação e gerenciamento de aulas
- Especialidades variadas (Calistenia, Yoga, Funcional, Crossfit, Musculação, Pilates, Luta, Dança)
- Geração de relatórios em PDF
- Perfil com informações profissionais

### Para Administradores

- Painel administrativo completo
- Gerenciamento de usuários
- Controle de aulas e reservas
- Relatórios gerenciais

## 🏗️ Estrutura do Projeto

```bash
src/
├── classes/           # Classes principais do sistema
│   ├── Aluno.java     # Classe para alunos
│   ├── Professor.java # Classe para professores
│   ├── Aula.java      # Classe para aulas
│   ├── Reserva.java   # Classe para reservas
│   ├── Pessoa.java    # Classe base para pessoas
│   └── ...
├── enums/             # Enumerações
│   ├── Planos.java    # Tipos de planos
│   ├── Especialidade.java # Especialidades dos professores
│   ├── Status.java    # Status das reservas
│   └── Unidade.java   # Unidades da academia
├── telas/             # Interfaces gráficas (Swing)
│   ├── TelaInicial.java
│   ├── CadastroAluno.java
│   ├── CadastroProfessor.java
│   ├── LoginAluno.java
│   ├── LoginProfessor.java
│   └── ...
└── resources/         # Recursos (imagens, ícones, etc.)
```

## 🚀 Como Executar

### Pré-requisitos

- Java JDK 8 ou superior
- NetBeans IDE (recomendado)
- Bibliotecas para geração de PDF (iText)

### Passos para Execução

1. **Clone o repositório**

   ```bash
   git clone [URL_DO_REPOSITÓRIO]
   cd Sistema-tp1
   ```

2. **Abrir no NetBeans**

   - Abra o NetBeans IDE
   - File → Open Project
   - Selecione a pasta do projeto

3. **Compilar e Executar**
   - Clique com o botão direito no projeto
   - Selecione "Clean and Build"
   - Clique em "Run" ou pressione F6

### Executar via linha de comando

```bash
# Compilar
ant clean
ant compile

# Executar
ant run
```

## 💡 Principais Características Técnicas

- **Arquitetura**: Orientação a objetos com herança e polimorfismo
- **Interface**: Java Swing com formulários (.form)
- **Autenticação**: Sistema de login diferenciado para alunos e professores
- **Relatórios**: Geração de PDFs usando biblioteca iText
- **Persistência**: Armazenamento em listas (ArrayList)

## 📊 Planos Disponíveis

| Plano    | Limite de Reservas | Características     |
| -------- | ------------------ | ------------------- |
| Normal   | 2 reservas         | Plano básico        |
| Gold     | 4 reservas         | Plano intermediário |
| Platinum | 6 reservas         | Plano premium       |

## 🏃‍♀️ Especialidades Oferecidas

- Calistenia
- Yoga
- Funcional
- Crossfit
- Musculação
- Pilates
- Luta
- Dança
- Geral

## 👥 Contribuidores

- Rafael
- Lucca

## 📝 Licença

Este projeto foi desenvolvido como trabalho acadêmico para a Universidade de Brasília (UnB).

## 🔧 Melhorias Futuras

- [ ] Integração com banco de dados
- [ ] Sistema de pagamentos
- [ ] Notificações por email
- [ ] Aplicativo móvel
- [ ] Dashboard com estatísticas
- [ ] Sistema de avaliação de aulas
