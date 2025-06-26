"""
Universidade de Brasilia
Instituto de Ciencias Exatas
Departamento de Ciencia da Computacao
Algoritmos e Programação de Computadores - 2/2023
Turma: Prof. Carla Castanho e Prof. Frank Ned
Aluno(a): Rafael Dias Ghiorzi
Matricula: 232006144
Projeto Final - Parte 1
Descricao: < O programa é um jogo básico parecido onde você é um personagem que deve
destruir inimigos sem deixar que eles te encostem, enquanto captura tanques de combustível
para sobreviver o máximo de tempo possível. é um jogo simples que usa apenas elementos
retangulares, feito para ser intuitivo até para o menos conhecedor de jogos de computadores >
"""

import pygame
import os
import sys
from pygame.locals import *
import random
import time
import json

# -------------VARIÁVEIS----------#
displayHeight = 600
displayWidth = 1500
tamanhoPixel = 40
fps = float(50)
combustivel = 400
pontos = 0
probabilidadeX = 40
probabilidadeF = 100
probabilidadeO = 600
probabilidadeT = 250
vidaO = 10
municaoT = 5 
velocidade = 2

modoRanqueado = False
run = True

clock = pygame.time.Clock()
qntTirosJogador = []
qntTirosInimigo = []
qntInimigosX = []
qntInimigosO = []
qntInimigosT = []
qntTanquesCombustivel = []

# ------------CLASSES-------------#
class Jogador:
    def __init__(self, x, y, width, height):
        self.rect = pygame.Rect(x, y, width, height)
        self.color = (100,100,100)
        raw_image = pygame.image.load("./assets/jogador.png")
        self.image = pygame.transform.scale(raw_image, (tamanhoPixel, tamanhoPixel))
        self.speed = velocidade + 2

    def colisao(self, outro_rect):
        return self.rect.colliderect(outro_rect)

class Projeteis:
    def __init__(self, x, y, width, height):
        self.rect = pygame.Rect(x, y, width, height)
        self.color = (100,100,100)
        raw_image = pygame.image.load("./assets/tiroamigo.png")
        self.image = pygame.transform.scale(raw_image, (tamanhoPixel + 15, tamanhoPixel - 30))
        self.speed = velocidade + 1
        self.rect.x = self.rect.x + self.speed

    def colisao(self, outro_rect):
        return self.rect.colliderect(outro_rect)

class ProjeteisInimigos: #amarelo
    def __init__(self, x, y, width, height):
        self.rect = pygame.Rect(x, y, width, height)
        self.color = (100,100,100)
        raw_image = pygame.image.load("./assets/tiroinimigo.png")
        self.image = pygame.transform.scale(raw_image, (tamanhoPixel + 15, tamanhoPixel - 30))
        self.speed = velocidade + 1
        self.rect.x = self.rect.x - self.speed + 10

    def colisao(self, outro_rect):
        return self.rect.colliderect(outro_rect)

class TanquesCombustivel:
    def __init__(self, y):
        self.rect = pygame.Rect(displayWidth, y, tamanhoPixel, tamanhoPixel)
        self.color = (100,100,100)
        raw_image = pygame.image.load("./assets/fuel.png")
        self.image = pygame.transform.scale(raw_image, (tamanhoPixel, tamanhoPixel))
        self.speed = velocidade
        self.rect.x = self.rect.x - self.speed

class criaInimigosX:
    def __init__(self, y):
        self.rect = pygame.Rect(displayWidth, y, tamanhoPixel, tamanhoPixel)
        self.color = (100,100,100)
        raw_image = pygame.image.load("./assets/inimigoX.png")
        self.image = pygame.transform.scale(raw_image, (tamanhoPixel, tamanhoPixel))
        self.speed = velocidade
        self.rect.x = self.rect.x - self.speed

class criaInimigosO: #anda e quando é morto apaga todos os inimigos da tela
    def __init__(self, y):
        self.rect = pygame.Rect(displayWidth, y, tamanhoPixel, tamanhoPixel)
        self.color = (100,100,100)
        raw_image = pygame.image.load("./assets/inimigoO.png")
        self.image = pygame.transform.scale(raw_image, (tamanhoPixel, tamanhoPixel))
        self.speed = velocidade
        self.rect.x = self.rect.x - self.speed
        self.vida = vidaO
        
class criaInimigosT: #atira e pode colidir com o jogador, tiro mais rápido
    def __init__(self, y):
        self.rect = pygame.Rect(displayWidth, y, tamanhoPixel, tamanhoPixel)
        self.color = (100,100,100)
        raw_image = pygame.image.load("./assets/inimigoT.png")
        self.image = pygame.transform.scale(raw_image, (tamanhoPixel, tamanhoPixel))
        self.speed = velocidade
        self.rect.x = self.rect.x - self.speed
        self.municao = municaoT
        self.direcao = 1
#---------------------------------#

# ------------FUNÇÕES-------------#
def Start():
    os.system('cls||clear')
    escolha = input("Window Invaders! \nBem-vindo, jogador!\nPressione enter para prosseguir...")
    if escolha == "":
        Menu()
    else:
        Start()

def Menu():
    os.system('cls||clear')
    print("1 - Jogar")
    print("2 - Configuracoes")
    print("3 - Rankings")
    print("4 - Instrucoes")
    print("5 - Sair")
    escolha = input("Escolha uma opção: ")
    
    match escolha:
        case "1": Jogo()
        case "2": Config()  # ainda a ser implementada
        case "3": Rankings()  # ainda a ser implementada
        case "4": Instrucoes()
        case "5": Sair()  # ainda a ser implementada
        case _: Menu()

def Instrucoes():
    os.system('cls||clear')
    print("Bem vindo ao Window invaders!\nSua missão é sobreviver o máximo de tempo na arena enquanto elimina inimigos que tentam de matar!\nMova-se para cima e para baixo usando 'W' e 'S' e atire usando 'X'\nExistem 4 elementos que surgem no mapa:\n- O inimigo vermelho apenas anda em sua direção e eliminá-lo você ganha 50 pontos\n- O inimigo rosa não atira e não te mata, ele tem mais vida que o inimigo normal e ao matá-lo, todos os inimigos vermelhos do mapa são eliminados\n- O inimigo vermelho escuro se move na sua direção e para cima e para baixo. Ele tem 5 munições que ele atira aleatoriamente na sua direção. Desvie do inimigo e do tiro ou atire contra ele, os dois funcionam!\n\nVocê pode ativar o modo ranqueado para salvar sua pontuação no placar ou apenas jogar casualmente.\nSeu combustível acaba gradualmente. Pegue os tanques de gasolina para repor 40 pontos de combustível por vez.\n\nBoa sorte na sua jornada!\n")
    escolha = input("Pressione 'enter' para voltar...")
    if escolha == "":
        Menu()
    else:
        Instrucoes()

def Rankings():
    os.system('cls||clear')
    print("Ranking dos 10 Melhores Jogadores:")
    
    destino = "ranking.json"

    try:
        # Tentar abrir o arquivo para leitura
        with open(destino, "r") as arquivo:
            try:
                ranking = json.load(arquivo)
            except json.JSONDecodeError:
                print("ainda não existe um rank, tente novamente após jogar uma partida ranqueada")
                time.sleep(3)
                Menu()
    except FileNotFoundError:
            print("O arquivo rank ainda não existe, tente novamente após jogar uma partida ranqueada")
            time.sleep(2)
            Menu()

    if not ranking:
        print("Ainda não há jogadores no ranking.")
        time.sleep(2)
        Menu()
    else:
        # Exibir os 10 primeiros registros do ranking
        for i, jogador in enumerate(ranking[:10], start=1):
            print(f"{i}. {jogador['nickname']}: {jogador['pontos']} pontos")

    input("Pressione Enter para voltar ao menu...")
    Menu()
#---------------------------------#

#--------------- FUNÇÕES DAS CONFIGURAÇÕES ---------------#
def Config():
    os.system('cls||clear')
    print("Selecione as configurações que deseja mudar")
    print("1 - Tabuleiro")
    print("2 - NPCs")
    print(f'3 - {"Ativar" if modoRanqueado == False else "Desativar" } modo ranqueado')
    print("4 - Voltar")
    escolha = input("Escolha uma opção: ")
        
    match escolha:
        case "1": Tabuleiro()
        case "2": NPCs()
        case "3": ModoRanqueado()
        case "4": Menu()
        case _: Config()

def Tabuleiro():
    global displayWidth, displayHeight
    os.system('cls||clear')
    if modoRanqueado == True:
        print("Você não pode alterar o tamanho do tabuleiro no modo ranqueado!\nVoltando para o menu...")
        time.sleep(3)
        Menu()
    
    print("Selecione o tamanho do tabuleiro da seguinte forma (TamanhoX TamanhoY):")
    print(f'O tamanho atual é de {displayWidth} por {displayHeight}')
    escolha = input("Digite as medidas para qual você deseja alterar:\n")
    escolha_split = escolha.split()

    if len(escolha_split) == 2:
        escolhaX, escolhaY = escolha_split
        try:
            displayWidth = int(escolhaX)
            displayHeight = int(escolhaY)
            time.sleep(0.5)
            print("Escolha salva! Voltando para as configurações...")
            time.sleep(1)
            Config()
        except Exception:
            print("Formato incorreto, tente novamente")
            time.sleep(2)
            Tabuleiro()
    else:
        print("Formato incorreto. Tente novamente.")
        time.sleep(2)
        Tabuleiro()

def NPCs():
    global probabilidadeF, probabilidadeO, probabilidadeT, probabilidadeX,vidaO, municaoT
    os.system('cls||clear')
    if modoRanqueado == True:
        print("Você não pode as propriedades dos NPCs no modo ranqueado!\nVoltando para o menu...")
        time.sleep(3)
        Menu()
        
    print("Selecione a chance de aparecer todos os respectivos personagens do jogo.")
    print("Lembrando que quanto maior o número, menor a chance de surgir um inimigo")
    escolha = input(f"Selecione a probabilidade de aparecer inimigos vermelhos (atualmente 1 em {probabilidadeX}):\n")
    try: 
        probabilidadeX = int(escolha)
    except Exception:
        print("Algo deu errado, tente novamente")
        time.sleep(1)
        NPCs()
    print("Ok! passando para o próximo NPC...")
    time.sleep(1)
    
    os.system('cls||clear')
    escolha = input(f"Ótimo, agora selecione a probabilidade de aparecer tanques de combustível (atualmente 1 em {probabilidadeF}):\n")
    try: 
        probabilidadeF = int(escolha)
    except Exception:
        print("Algo deu errado, tente novamente")
        time.sleep(1)
        NPCs()
    print("Ok! passando para o próximo NPC...")
    time.sleep(1)
    
    os.system('cls||clear')
    escolha = input(f"Ótimo, agora selecione a probabilidade de aparecer inimigos laranjas (atualmente 1 em {probabilidadeO}):\n")
    try: 
        probabilidadeO = int(escolha)
    except Exception:
        print("Algo deu errado, tente novamente")
        time.sleep(1)
        NPCs()
    print("Ok! passando para o próximo NPC...")
    time.sleep(1)
    
    os.system('cls||clear')
    escolha = input(f"Ótimo, agora selecione a probabilidade de aparecer inimigos vermelho escuro (atualmente 1 em {probabilidadeT}):\n")
    try: 
        probabilidadeX = int(escolha)
    except Exception:
        print("Algo deu errado, tente novamente")
        time.sleep(1)
        NPCs()
    print("Ok! Passando para as qualidades dos NPCs...")
    time.sleep(1)
    
    os.system('cls||clear')
    escolha = input("Qual deve ser a vida do inimigo laranja? Lembrando que cada tiro seu dá 5 de dano cada:\n")
    try:
        vidaO = int(escolha)
    except Exception:
        print("Algo deu errado, tente novamente")
        NPCs()
    print("Ok!")
    time.sleep(1)
    print("")
    escolha = input("E qual deve ser a munição do inimigo vermelho escuro? Lembrando que o padrão é de 5 tiros cada um:\n")
    try:
        municaoT = int(escolha)
    except Exception:
        print("Algo deu errado, tente novamente")
    print("Ok! Todas as configurações foram aplicadas! Voltando para as configurações...")
    time.sleep(1)
    Config()
    
def ModoRanqueado():
    global modoRanqueado, displayHeight, displayWidth, probabilidadeF, probabilidadeO, probabilidadeT, probabilidadeX, municaoT, vidaO, tamanhoPixel
    if modoRanqueado == True:
        os.system('cls||clear')
        escolha = input("Desativar modo ranqueado? (s/n):\n")
        if escolha == "s":
            displayHeight = 600
            displayWidth = 1500
            tamanhoPixel = 40
            modoRanqueado = False
            probabilidadeX = 40 # 1 até 4, 25% de chance
            probabilidadeF = 100# 10% de chance
            probabilidadeO = 600 # 1% de chance
            probabilidadeT = 250
            vidaO = 10 #cada tiro dá 5
            municaoT = 5 #quantos tiros pode dar
            
            
        elif escolha == "n":
            Nickname()
            modoRanqueado = True
            displayHeight = 500
            displayWidth = 1200
            tamanhoPixel = 40
            probabilidadeX = 60 # 1 até 4, 25% de chance
            probabilidadeF = 150 # 10% de chance
            probabilidadeO = 500 # 1% de chance
            probabilidadeT = 250  # 4% de chance
            municaoT = 7
            vidaO = 15              
        else:
            ModoRanqueado()
        print("Salvando sua escolha e voltando para o menu...")
        time.sleep(1)    
        Menu()
        
    if modoRanqueado == False:
        os.system('cls||clear')
        escolha = input("Ativar modo ranqueado? (s/n)\n")
        if escolha == "s":
            Nickname()
            modoRanqueado = True
            displayHeight = 500
            displayWidth = 1200
            tamanhoPixel = 40
            probabilidadeX = 40 # 1 até 4, 25% de chance
            probabilidadeF = 150 # 10% de chance
            probabilidadeO = 700 # 1% de chance
            probabilidadeT = 250  # 4% de chance
            municaoT = 7
            vidaO = 15
        elif escolha == "n":
            displayHeight = 500
            displayWidth = 1200
            tamanhoPixel = 40
            modoRanqueado = False
            probabilidadeX = 60 # 1 até 4, 25% de chance
            probabilidadeF = 100# 10% de chance
            probabilidadeO = 500 # 1% de chance
            probabilidadeT = 400
            vidaO = 10 #cada tiro dá 5
            municaoT = 5 #quantos tiros pode dar
        else:
            ModoRanqueado()
        print("Salvando sua escolha e voltando para o menu...")
        time.sleep(1)
        Menu()
#---------------------------------------------------------#

#--------------- FUNÇÕES DE FUNCIONAMENTO DO JOGO --------#
def ResetGame():
    global run, combustivel, pontos, fps, clock, jogador, janela, qntTirosJogador, qntInimigos, tiro, qntInimigos, probabilidadeX, probabilidadeF, probabilidadeO, probabilidadeT, unidadeCombustivel, qntTanquesCombustivel

    # reinicia todas as variáveis globais #
    global displayHeight, displayWidth, tamanhoPixel, fps, combustivel, probabilidadeX, velocidade, pontos, run, clock, qntTirosJogador, qntInimigos, qntTanquesCombustivel, municaoT, vidaO
    displayHeight = displayHeight
    displayWidth = displayWidth
    tamanhoPixel = tamanhoPixel
    fps = float(50)
    combustivel = 400
    velocidade = 2
    pontos = 0
    run = True
    clock = pygame.time.Clock()
    qntTirosJogador = []
    qntInimigos = []
    qntTanquesCombustivel = []
    probabilidadeX = probabilidadeX
    probabilidadeF = probabilidadeF
    probabilidadeO = probabilidadeO
    probabilidadeT = probabilidadeT
    municaoT = 5  # Valor padrão
    vidaO = vidaO

    # reinicia objetos específicos 
    jogador = Jogador(5, (displayHeight / 2), tamanhoPixel, tamanhoPixel)

    # reinicia a janela
    janela = pygame.display.set_mode((displayWidth, displayHeight))

    # reinicialize o jogo
    pygame.init()
    pygame.font.init()
    pygame.display.set_caption("Window Invaders")
    font = pygame.font.Font(None, 24)

def Tiro():
    global linha, tiro, combustivel, qntTirosJogador

    linha = jogador.rect.right
    combustivel -= 3
    tiro = Projeteis(linha, jogador.rect.y + 15, tamanhoPixel + 3, tamanhoPixel - 30)
    qntTirosJogador.append(tiro)

def InimigoX():
    inimigoX = criaInimigosX(random.randint(30, displayHeight))
    qntInimigosX.append(inimigoX)

def InimigoO():
    inimigoO = criaInimigosO(random.randint(30, displayHeight))
    qntInimigosO.append(inimigoO)
    
def InimigoT():
    inimigoT = criaInimigosT(random.randint(30, displayHeight))
    qntInimigosT.append(inimigoT)

def Combustivel():
    unidadadeCombustivel = TanquesCombustivel(random.randint(30, displayHeight))
    qntTanquesCombustivel.append(unidadadeCombustivel)

def Nickname():
    global nickname
    os.system('cls||clear')
    nickname = input("Antes do jogo ranqueado começar, escreva seu nickname para que possamos salvar sua pontuação:\nOBS: o nome deve conter entre 1 e 10 caracteres:\n")
    if len(nickname) < 1 or len(nickname) > 10:
        print("nome inválido, tente novamente!")
        time.sleep(2)
        Nickname()
        return
#---------------------------------------------------------#

#------------------ MAIN -----------------------------#
def Jogo():
    global run, combustivel, pontos, fps, clock, jogador, janela, qntTirosJogador, qntInimigos, tiro, qntInimigos, unidadeCombustivel, qntTanquesCombustivel, modoRanqueado, nickname

    
    os.system('cls||clear')
    print("Boa sorte")
    time.sleep(1)
    
    ResetGame()
    
    #inicia a tela  
    janela = pygame.display.set_mode((displayWidth, displayHeight))
    #inicia jogador
    jogador = Jogador(5, 50, tamanhoPixel, tamanhoPixel)
    #Configurações iniciais
    pygame.init()
    pygame.font.init()
    pygame.display.set_caption("Window Invaders")
    font = pygame.font.Font(None, 24)


    while run:

        # imprime combustivel e pontuação no topo da tela e modo ranqueado se estiver ativo#
        if combustivel < 150:
            nivelCombustivel = font.render(f'Combustível: {combustivel}', True, (200, 0,00))
            janela.blit(nivelCombustivel, (10, 10))
        else:
            nivelCombustivel = font.render(f'Combustível: {combustivel}', True, (255,255,255))
            janela.blit(nivelCombustivel, (10, 10)) 
            
        pontuacao = font.render(f'Pontuação: {pontos}', True, (255, 255, 255))       
        janela.blit(pontuacao, (displayWidth - 140 , 10))
        
        if modoRanqueado == True:
            avisoRanqueado = font.render(f'Modo Ranqueado', True, (255, 255, 255))
            janela.blit(avisoRanqueado, ((displayWidth/2) - 100, 10))
        #-------------------------------------------------#
        
        # evento de fechar a janela #
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False
                pygame.quit()
                if modoRanqueado == True:
                    registerMatch(nickname, pontos)
                GameoverQuit()     
        #---------------------------#
        
        
        # ações possíveis do jogador #
            elif event.type == pygame.KEYDOWN:
                if event.key==120:  #ascii pra 'X'
                    Tiro()

        key = pygame.key.get_pressed()
        if key[pygame.K_w] == True:
            jogador.rect.y = jogador.rect.y - jogador.speed

        elif key[pygame.K_s] == True:
            jogador.rect.y = jogador.rect.y + jogador.speed
            
        elif key[pygame.K_d] == True:
            jogador.rect.x = jogador.rect.x + jogador.speed
        
        elif key[pygame.K_a] == True:
            jogador.rect.x = jogador.rect.x - jogador.speed - 1
        #----------------------------#
        
        # Cria tipos de inimigos e tanques de combustivel # (0 a 25 é fácil, 25 a 60 é média dificuldade, 60 a 100 é difícil)
        chance = random.randint(0, probabilidadeX)
        if chance == 1:
            InimigoX()
            
        chance = random.randint(0, probabilidadeF)
        if chance == 1:
            Combustivel()
            
        chance = random.randint(0, probabilidadeO)
        if chance == 1:
            InimigoO()
            
        chance = random.randint(0, probabilidadeT)
        if chance == 1:
            InimigoT()
        
        
        for inimigo in qntInimigosT:    
            chance = random.randint(0, 500)
            if chance == 1:
                if inimigo.municao >= 0:
                    linhaInimigo = inimigo.rect.x - 65
                    colunaInimigo = inimigo.rect.y + 15
                    tiro = ProjeteisInimigos(linhaInimigo, colunaInimigo, tamanhoPixel + 3, tamanhoPixel - 30)
                    qntTirosInimigo.append(tiro)
                inimigo.municao -= 1
        #----------------------------------------#
        
        # Detecta colisões #
        for inimigo in qntInimigosX:
            if jogador.colisao(inimigo.rect):
                run = False
                pygame.quit()
                if modoRanqueado == True:
                    registerMatch(nickname, pontos)
                GameoverMorte()   
                
        for inimigo in qntInimigosT:
            if jogador.colisao(inimigo.rect):
                run = False
                pygame.quit()
                if modoRanqueado == True:
                    registerMatch(nickname, pontos)
                GameoverMorte()
        
        for unidadeCombustivel in qntTanquesCombustivel:
            if jogador.colisao(unidadeCombustivel.rect):
                qntTanquesCombustivel.remove(unidadeCombustivel)
                combustivel += 40 
                
        for tiro in qntTirosInimigo:
            if jogador.colisao(tiro.rect):  
                run = False
                pygame.quit()
                if modoRanqueado == True:
                    registerMatch(nickname, pontos)
                GameOverTiro()
                
        for tiro in qntTirosJogador:
            for inimigo in qntInimigosX:
                if tiro.colisao(inimigo.rect):
                    qntInimigosX.remove(inimigo)
                    qntTirosJogador.remove(tiro)
                    pontos += 50
                    
        for tiro in qntTirosJogador:
            for inimigo in qntInimigosO:
                if tiro.colisao(inimigo.rect):
                    inimigo.vida -= 5
                    if inimigo.vida <= 0:
                        qntInimigosO.remove(inimigo)
                        pontos += ((len(qntInimigosX)) * 10)
                        qntInimigosX.clear()
                    qntTirosJogador.remove(tiro)
                    break
                        
        for tiro in qntTirosJogador:
            for inimigo in qntInimigosT:
                if tiro.colisao(inimigo.rect):
                    qntInimigosT.remove(inimigo)
                    qntTirosJogador.remove(tiro)
                    
        for inimigo in qntInimigosT:
            if jogador.colisao(inimigo.rect):
                run = False
                pygame.quit()
                if modoRanqueado == True:
                    registerMatch(nickname, pontos)
                GameoverMorte()  
                
        for tiro in qntTirosJogador:
            for tiroInimigo in qntTirosInimigo:
                if tiro.colisao(tiroInimigo.rect):
                    qntTirosInimigo.remove(tiroInimigo)
                    qntTirosJogador.remove(tiro)
                 
        #-----não deixa o jogador e inimigos sairem da tela----#
        if jogador.rect.top <= 30:
            jogador.rect.top = 30
        if jogador.rect.bottom >= displayHeight:
            jogador.rect.bottom = displayHeight
            
        for inimigo in qntInimigosT:
            if inimigo.rect.top <= 30:
                inimigo.rect.y = 30
            if inimigo.rect.bottom >= displayHeight:
                inimigo.rect.bottom = displayHeight
                
        for inimigo in qntInimigosO:
            if inimigo.rect.y <= 30:
                inimigo.rect.top = 30
            if inimigo.rect.bottom >= displayHeight:
                inimigo.rect.bottom = displayHeight      
                
        for inimigo in qntInimigosX:
            if inimigo.rect.top <= 30:
                inimigo.rect.y = 30
            if inimigo.rect.bottom >= displayHeight:
                inimigo.rect.bottom = displayHeight  
        #-----------------------------------------#
        
        

        # atualiza a tela, inimigos, projeteis, personagem e combustiveis #
        chance = random.randint(0, 7)                             
        if chance == 3:
            combustivel -= 1
        
        pygame.draw.rect(janela, (100,100,100), jogador)
        janela.blit(jogador.image, jogador.rect)
        
        for inimigo in qntInimigosX:
            pygame.draw.rect(janela, (100,100,100), inimigo)
            janela.blit(inimigo.image, inimigo.rect)
            inimigo.rect.x = inimigo.rect.x - inimigo.speed
        
        for inimigo in qntInimigosO:
            pygame.draw.rect(janela, (100,100,100), inimigo)
            janela.blit(inimigo.image, inimigo.rect)
            inimigo.rect.x = inimigo.rect.x - inimigo.speed        

        for inimigo in qntInimigosT:
            pygame.draw.rect(janela, (100,100,100), inimigo)
            janela.blit(inimigo.image, inimigo.rect)
            inimigo.rect.x = inimigo.rect.x - inimigo.speed
            inimigo.rect.y += (inimigo.speed * inimigo.direcao) * 0.75
            
            chance = random.randint(0,50)
            if chance == 1:
                inimigo.direcao = -1   
            if chance == 2:
                inimigo.direcao = 1
                    
        for tiro in qntTirosJogador:
            pygame.draw.rect(janela, (100,100,100), tiro)
            janela.blit(tiro.image, tiro.rect)
            tiro.rect.x = tiro.rect.x + tiro.speed + 3
            
        for tiro in qntTirosInimigo:
            pygame.draw.rect(janela, (100,100,100), tiro)
            janela.blit(tiro.image, tiro.rect)
            tiro.rect.x = tiro.rect.x - tiro.speed - 2
        
        for unidadeCombustivel in qntTanquesCombustivel:
            pygame.draw.rect(janela, (100,100,100), unidadeCombustivel)
            janela.blit(unidadeCombustivel.image, unidadeCombustivel.rect)
            unidadeCombustivel.rect.x = unidadeCombustivel.rect.x - unidadeCombustivel.speed
            
        pygame.display.flip()
        janela.fill(pygame.Color(100,100,100))
        fps = float(fps) + float(0.01)
        clock.tick(fps)   
        #-----------------#
        
        # Checa a quantidade de combustível #
        if combustivel <= 0:
            pygame.quit()
            if modoRanqueado == True:
                registerMatch(nickname, pontos)
            GameoverCombustivel()
        #-----------------------------------#
#-----------------------------------------------------#

#------------------ FINALIZAÇÃO DA RODADA E SALVAMENTO DOS DADOS -------------------#
def registerMatch(nickname, pontos):

    arquivoRank = "ranking.json"

    try:
        with open(arquivoRank, "r") as arquivo:
            try:
                ranking = json.load(arquivo)
            except json.JSONDecodeError:
                with open(arquivoRank, "w") as arquivo:
                    ranking = [{"nickname": nickname, "pontos" : pontos}]
                    json.dump(ranking, arquivo)
    except FileNotFoundError:
        print("arquivo de ranks não encontrado!")
        time.sleep(2)
        Menu()

    ranking.append({"nickname": nickname, "pontos": pontos})

    ranking = sorted(ranking, key=lambda x: x["pontos"], reverse=True)

    ranking = ranking[:10]

    with open(arquivoRank, "w") as arquivo:
        json.dump(ranking, arquivo)

    print("Partida registrada no ranking com sucesso!")
def GameOverTiro():
    global run, combustivel, pontos, fps, qntTanquesCombustivel, qntTirosJogador, qntInimigos, nickname

    os.system('cls||clear')
    print("Você levou um tiro! Não deixe acontecer novamente...")
    print(f'Sua pontuação foi de {pontos} ponto(s), parabéns!')
    print("1 - Reiniciar\n2 - Voltar para o menu\n3 - Ver os rankings")
    combustivel = 400
    pontos = 0
    fps = 50
    qntInimigosX.clear()
    qntInimigosO.clear()
    qntInimigosT.clear()
    qntTirosInimigo.clear()
    qntTirosJogador.clear()
    qntTanquesCombustivel.clear()

    escolha = input("Escolha uma opção: ")
    match escolha:
        case "1":         
            run = True
            Jogo()
        case "2": 
            run = True
            Menu()
        case "3":
            run = True
            Rankings() 
        case _:
            GameOverTiro()
def GameoverMorte():
    global run, combustivel, pontos, fps, qntTanquesCombustivel, qntTirosJogador, qntInimigos, nickname

    os.system('cls||clear')
    print("O inimigo chegou até você! Não deixe acontecer novamente...")
    print(f'Sua pontuação foi de {pontos} ponto(s), parabéns!')
    print("1 - Reiniciar\n2 - Voltar para o menu\n3 - Ver os rankings")
    combustivel = 400
    pontos = 0
    fps = 50
    qntInimigosX.clear()
    qntInimigosO.clear()
    qntInimigosT.clear()
    qntTirosInimigo.clear()
    qntTirosJogador.clear()
    qntTanquesCombustivel.clear()

    escolha = input("Escolha uma opção: ")
    match escolha:
        case "1":         
            run = True
            Jogo()
        case "2": 
            run = True
            Menu()
        case "3":
            run = True
            Rankings() 
        case _:
            GameoverMorte()        
def GameoverCombustivel():
    global run, combustivel, pontos, fps, qntTanquesCombustivel, qntTirosJogador, qntInimigos
    
    os.system('cls||clear')
    
    print("Seu combustível acabou! Preste mais atenção na próxima vez...")
    print(f'Sua pontuação foi de {pontos} ponto(s), parabéns!')
    print("1 - Reiniciar\n2 - Voltar para o menu\n3 - Ver os rankings")
    combustivel = 400
    pontos = 0
    fps = 50
    qntInimigos.clear()
    qntTirosJogador.clear()
    qntTanquesCombustivel.clear()
    
    escolha = input("Escolha uma opção: ")
    match escolha:
        case "1":      
            run = True
            Jogo()
        case "2": 
            run = True
            Menu()
        case "3":
            run = True
            Rankings()    
        case _:
            GameoverCombustivel()    
def GameoverQuit():
    global run, combustivel, pontos, fps, qntTanquesCombustivel, qntTirosJogador, qntInimigos
    
    os.system('cls||clear')
    
    print("Não desista tão fácil assim da missão!")
    print(f'Sua pontuação foi de {pontos} ponto(s), parabéns!')
    print("1 - Reiniciar\n2 - Voltar para o menu\n3 - Ver os rankings")
    combustivel = 400
    pontos = 0
    fps = float(50)
    qntInimigos.clear()
    qntTirosJogador.clear()
    qntTanquesCombustivel.clear()
    
    escolha = input("Escolha uma opção: ")
    match escolha:
        case "1":        
            run = True
            Jogo()
        case "2": 
            run = True
            Menu()
        case "3":
            run = True
            Rankings() 
        case _:
            GameoverQuit()           
def Sair():
    os.system('cls||clear')
    sys.exit()
#-----------------------------------------------------------------------------------#



#inicia tudo#
Start()
#-----------#
