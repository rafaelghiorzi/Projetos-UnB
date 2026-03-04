"""
Considere para efeito deste projeto que uma determinada universidade oferece anualmente uma lista de
cinquenta (50) projetos financiados e abertos a participação de alunos, com um máximo de 80 vagas.
Cada projeto é orientado e gerenciado por professores que estabelecem as quantidades mínima e
máxima de alunos que podem ser aceitos em determinado projeto, bem como requisitos de histórico e
tempo disponível que os alunos devem possuir para serem aceitos. Esses requisitos de histórico e tempo
são pré-avaliados e cada aluno possui uma Nota agregada inteira de [3, 4, 5], sendo 3 indicando
suficiente, 4 muito boa, e 5 excelente. Neste ano, duzentos (200) alunos se candidataram aos projetos. O
ideal é que o máximo de projetos sejam realizados, mas somente se o máximo de alunos qualificados
tenham tido o interesse para tal. Para uma seleção impessoal e competitiva um algoritmo que realize um
emparelhamento estável máximo deve ser implementado. Este projeto pede a elaboração,
implementação e testes com a solução final de emparelhamento máximo estável para os dados
fornecidos. Os alunos podem indicar no máximo três (3) preferências em ordem dos projetos. Variações
do algoritmo Gale-Shapley devem ser usadas, com uma descrição textual no arquivo README do
projeto indicando qual variação/lógica foi utilizada/proposta.

O programa deve implementar uma interface com visualização colorida do grafo bipartido e das
soluções propostas de emparelhamento, ao longo da evolução com dez (10) iterações de
emparelhamento em laço, organizando as saídas até a alocação final. Um índice de preferência por
projeto deve ser calculado, bem como visualizada uma matriz final dos emparelhamentos com as ordens
de escolhas de emparelhamentos finais dos alunos.

As soluções dadas em (Abraham, Irving & Manlove, 2007) são úteis e qualquer uma pode ser
implementada com variações pertinentes. Um arquivo entradaProj2.25TAG.txt com as indicações de
código do projeto, número de vagas, requisito mínimo das vagas, bem como dos alunos com suas
preferências de projetos na ordem e suas notas indviduais, é fornecido como entrada. Uma versão
pública do artigo de (Abraham, Irving & Manlove, 2007) é fornecida para leitura.
"""

import re
import networkx as nx
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np


class Emparelhamento:
    """
    Classe para implementar o algoritmo de emparelhamento estável máximo.
    Utiliza uma variação do algoritmo de Gale-Shapley e possui algumas
    funções auxiliares para manipulação de dados e visualização de grafos.
    """

    def __init__(self) -> None:
        self.grafo = nx.Graph()
        self.alunos = {}
        self.projetos = {}

    def organizar_dados(self, alunos_txt: str = "alunos.txt", projetos_txt: str = "projetos.txt") -> None:
        """
        Função para extrair os dados de entrada e atualizar os dicionários de alunos e projetos.
        
        Args:
            alunos (str): caminho para o txt dos alunos
            projetos (str): caminho para o txt dos projetos
        """
        try:
            # Lendo o arquivo de projetos
            with open(projetos_txt, encoding="utf-8") as f:
                linhas = f.readlines()
            for linha in linhas:
                linha = linha.strip()
                if linha or not linha.startswith("//"):
                    # Aplica um regex para extrair os dados
                    match = re.match(r"\((P\d+),\s*(\d+),\s*(\d+)\)", linha)
                    if match:
                        codigo, vagas, nota_min = match.groups()
                        # Atualiza o dicionário com as informações necessárias
                        self.projetos[codigo] = {"vagas": int(vagas), "nota_min": int(nota_min)}

            # Lendo o arquivo de alunos
            with open(alunos_txt, encoding="utf-8") as f:
                linhas = f.readlines()
            for linha in linhas:
                linha = linha.strip()
                if linha or not linha.startswith("//"):
                    # Aplica outro regex para extrair os dados
                    match = re.match(r"\((A\d+)\):\(([^)]+)\)\s*\((\d+)\)", linha)
                    if match:
                        codigo, prefs, nota = match.groups()
                        preferencias = [pref.strip() for pref in prefs.split(",")]
                        self.alunos[codigo] = {"preferencias": preferencias, "nota": int(nota)}

            print("Dados organizados com sucesso!")

        except Exception as e:
            print(f"Algo deu errado! {e}")

    def exibir_grafo(self, titulo: str = "Grafo Bipartido Inicial") -> None:
        """
        Exibe um grafo bipartido com rótulos
        
        Args:
            titulo (str): Título do grafo a ser exibido
        """
        if not self.grafo.nodes():
            print("O grafo fornecido para visualização está vazio.")
            return
        # Identifica os conjuntos bipartidos com base no atributo 'bipartite'
        set_alunos = {n for n, d in self.grafo.nodes(data=True) if d.get("bipartite") == 0}
        set_projetos = {n for n, d in self.grafo.nodes(data=True) if d.get("bipartite") == 1}

        # Se a identificação bipartida falhar, tentamos inferir com base nos nomes ou total de nós.
        if not set_alunos and not set_projetos:
            set_alunos = {n for n in self.grafo.nodes() if n.startswith('A')}
            set_projetos = {n for n in self.grafo.nodes() if n.startswith('P')}
            if not set_alunos or not set_projetos:
                print("Aviso: Não foi possível identificar as partições bipartidas (bipartite=0/1 ou 'A'/'P' no nome).")
                print("O layout bipartido pode não ser aplicado corretamente.")
                # Se ainda assim não der, use um layout genérico
                pos = nx.spring_layout(self.grafo)
            else:
                # Caso os atributos não existam, mas os nomes sigam o padrão 'A'/'P'
                pos = nx.bipartite_layout(self.grafo, set_alunos)
        else:
            # Caso os atributos estejam corretos, use o layout bipartido
            pos = nx.bipartite_layout(self.grafo, set_alunos)
        # Determina o espaçamento vertical e a altura da figura de forma dinâmica
        # para melhor acomodar a quantidade de nós.
        num_max_nodes_side = max(len(set_alunos), len(set_projetos))
        espacamento_vertical = 1.0 # Espaçamento base entre os nós
        
        # Ajusta o node_size e font_size com base no número de nós
        if num_max_nodes_side > 100:
            node_size = 150
            font_size = 5
        elif num_max_nodes_side > 50:
            node_size = 250
            font_size = 6
        else:
            node_size = 400
            font_size = 7

        altura_figura = max(10, num_max_nodes_side * espacamento_vertical * 0.2) 
        plt.figure(figsize=(15, altura_figura)) # Aumentei um pouco a largura para rótulos
        # Desenha os nós dos alunos
        nx.draw_networkx_nodes(self.grafo, pos, nodelist=list(set_alunos), node_color="#87CEFA", # Azul claro
                            node_size=node_size, edgecolors='black', label="Alunos", alpha=0.9)
        # Desenha os nós dos projetos
        nx.draw_networkx_nodes(self.grafo, pos, nodelist=list(set_projetos), node_color="#98FB98", # Verde claro
                            node_size=node_size, edgecolors='black', label="Projetos", alpha=0.9)
        # Desenha as arestas
        nx.draw_networkx_edges(self.grafo, pos, width=0.8, alpha=0.5, edge_color="gray")
        # Desenha os rótulos dos nós
        nx.draw_networkx_labels(self.grafo, pos, font_size=font_size, font_weight='bold')
        plt.title(titulo, fontsize=16, pad=20) # Título maior e com mais espaçamento
        plt.axis("off") # Desliga os eixos
        plt.tight_layout() # Ajusta o layout para evitar sobreposição
        plt.show() # Exibe a janela do grafo

    def Emparelhamento_final(self)-> None:
        # Matriz de emparelhamento 
        # Ordenar os alunos e projetos para fixar a ordem da matriz
        alunos_ordenados = list(self.alunos.keys())
        projetos_ordenados = list(self.projetos.keys())

        # Cria matriz vazia 
        matriz_final = [['' for _ in projetos_ordenados] for _ in alunos_ordenados]

        # Preenche com '*' onde há emparelhamento 
        for aluno, projeto in self.grafo.edges():
            if aluno in self.alunos and projeto in self.projetos:
                i = alunos_ordenados.index(aluno)
                j = projetos_ordenados.index(projeto)
                matriz_final[i][j] = '*'

        # Converte para DataFrame e salva no csv
        df = pd.DataFrame(matriz_final, index=alunos_ordenados, columns=projetos_ordenados)
        df.to_csv("matriz_emparelhamento_final.csv")

    def exibir_escolhas(self) -> None:
        """
        Exibe as turmas e projetos emparelhados, mostrando o projeto e os alunos emparelhados com ele,
        """
        print("\nEmparelhamento final:")
        for projeto in self.projetos.keys():
            alunos_no_projeto = [aluno for aluno, proj in self.grafo.edges() if proj == projeto]
            if alunos_no_projeto:
                print(f"Projeto {projeto}: Alunos emparelhados: {', '.join(alunos_no_projeto)}")
            else:
                print(f"Projeto {projeto}: Nenhum aluno emparelhado.")

    def gale_shapley(self, iteracoes: int) -> None:
        """
        Implementa a variação do algoritmo de Gale-Shapley para encontrar um emparelhamento estável máximo.

        Args:
            iteracoes (int): Número de iterações para o emparelhamento
        """

        # Inicializa as estruturas necessárias
        alunos_disponiveis = list(self.alunos.keys())
        # Os projetos com vagas ocupadas
        vagas_ocupadas = {projeto: [] for projeto in self.projetos.keys()}
        # As propostas já feitas pelos alunos
        propostas = {aluno: [] for aluno in self.alunos.keys()}

        for i in range(iteracoes):
            print(f"Iteração {i + 1} de {iteracoes}")

            # Condições de parada
            # 1. Se não tiver alunos disponíveis para serem emparelhados
            if not alunos_disponiveis:
                print("Emparelhamento máximo alcançado: todos os alunos foram processados")
                self.exibir_grafo(titulo="Grafo Bipartido - Emparelhamento Final")
                break

            # 2. Se todos os alunos disponíveis não tiverem mais preferências
            alunos_sem_opcoes = []
            for aluno in alunos_disponiveis:
                preferencias_validas = [pref for pref in self.alunos[aluno]['preferencias']
                                        if pref in self.projetos and pref not in propostas[aluno]]
                if not preferencias_validas:
                    alunos_sem_opcoes.append(aluno)

            if len(alunos_disponiveis) == len(alunos_sem_opcoes):
                print("Emparelhamento máximo alcançado: nenhum aluno pode fazer mais propostas")
                print(f"Quantidade de iterações: {i + 1}")
                self.exibir_grafo(titulo="Grafo Bipartido - Emparelhamento Final")
                break

            # Pega um aluno para tentar o emparelhamento
            aluno = alunos_disponiveis.pop(0)
            # Informações do aluno
            nota_aluno = self.alunos[aluno]['nota']
            pref_aluno = self.alunos[aluno]['preferencias']

            # Encontrar o próximo projeto na lista de preferênias do aluno
            projeto_escolhido = None
            for projeto in pref_aluno:
                # Se o aluno ainda não tiver feito proposta pra esse projeto
                if projeto not in propostas[aluno] and projeto in self.projetos:
                    # A nota do aluno tem que ser necessária
                    if nota_aluno >= self.projetos[projeto]['nota_min']:
                        projeto_escolhido = projeto
                        break

            # Se existe um projeto escolhido, adiciona ele no emparelhamento
            if projeto_escolhido:
                # Atualiza as propostas feitas do aluno
                propostas[aluno].append(projeto_escolhido)
                # Pega as quantidade de vagas do projeto escolhido
                qtd_vagas = self.projetos[projeto_escolhido]['vagas']

                # Checa se existe vagas no projeto
                if len(vagas_ocupadas[projeto_escolhido]) < qtd_vagas:
                    # Se tem vaga, coloca o aluno na vaga
                    vagas_ocupadas[projeto_escolhido].append(aluno)
                    # Adiciona uma conexão no grafo de emparelhamento
                    self.grafo.add_edge(aluno, projeto_escolhido, nota=nota_aluno)
                    print(f"Aluno {aluno} emparelhado com o projeto {projeto_escolhido}")

                else:
                    # Se o projeto está cheio, verifica se o aluno tem preferência
                    alunos_no_projeto = vagas_ocupadas[projeto_escolhido]
                    # Pega o aluno com menor nota dentro do projeto
                    aluno_menor_nota = min(alunos_no_projeto, key=lambda a: (self.alunos[a]["nota"], a))

                    nota_menor = self.alunos[aluno_menor_nota]["nota"]
                    if nota_aluno > nota_menor:
                        # Remove o aluno com menor nota do projeto
                        vagas_ocupadas[projeto_escolhido].remove(aluno_menor_nota)
                        vagas_ocupadas[projeto_escolhido].append(aluno)

                        # Remove a conexão entre o aluno anterior e adiciona uma nova
                        self.grafo.remove_edge(aluno_menor_nota, projeto_escolhido)
                        self.grafo.add_edge(aluno, projeto_escolhido, nota=nota_aluno)
                        
                        print(f"Aluno {aluno} emparelhado com o projeto {projeto_escolhido}, substituindo {aluno_menor_nota}.")

                        preferencias_restantes = [pref for pref in self.alunos[aluno_menor_nota]['preferencias']
                                                  if pref in self.projetos and pref not in propostas[aluno_menor_nota]]
                        if preferencias_restantes:
                            alunos_disponiveis.append(aluno_menor_nota)
                    else:
                        # Se o aluno não é melhor, volta para a lista de disponíveis
                        alunos_disponiveis.append(aluno)
                        print(f"Aluno {aluno} não foi emparelhado com o projeto {projeto_escolhido}, pois não é melhor que os já emparelhados.")
            else:
                print(f"Aluno {aluno} não escolheu nenhum projeto válido nesta iteração.")

            if i % 100 == 0:
                self.exibir_grafo(titulo=f"Grafo Bipartido - Iteração {i}")

        print("Emparelhamento finalizado.")
        self.exibir_escolhas()


if __name__ == "__main__":
    emparelhamento = Emparelhamento()
    emparelhamento.organizar_dados()
    emparelhamento.gale_shapley(iteracoes=1000)
    emparelhamento.Emparelhamento_final()

    # Exibe o número de nós e arestas no grafo
    print(f"Número de nós: {len(emparelhamento.grafo.nodes())}")
    print(f"Número de arestas: {len(emparelhamento.grafo.edges())}")
