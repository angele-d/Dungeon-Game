@startuml Modèle_Conceptuel_Donnees_SGARV
skinparam linetype ortho
skinparam monochrome true

' ===================================
' 1. CLASSES PRINCIPALES
' ===================================

' Singleton
class GAMEMANAGER { 
  --
  - games : list<GAME>
}

class GAME {
  *int GameID()
  *void update()
  --
  - time : int
  - map : MAP
  - heroSquad : HeroSquad
}

class MAP {
  --
  - map : dict<Coord, TILE>
  - start : Coord
  - treasure : Coord
}

class HEROSQUAD {
  *void update()
  --
  - heroList : list<HERO>
}

' Builder
class MAPBUILDER {
    *bool addElement(coord: Coord, type: int)
    *void deleteElement()
  --
  - map : MAP
}

class MAPCREATOR {
  --
  --
  *fromNew()
  *fromSavedState()
}

class LEADERBOARD {
  --
  - games : GAMESERIALISABLE
}

class GAMESERIALISABLE {
  *void serialize()
  --
  - map : MAP
  - ai : string
  - score : int
  --
  *fromserialized()
}

class GAMECREATOR {
  *GAME createGame()
  --
}

class GAMECREATORCONTROLLER {
  --
}

' ===================================
' 2. HEROS
' ===================================

abstract class HERO {
    *void update()
    *void applyDamage(damage: int)
  --
}

class DAMAGEVISITOR {
    --
}

class HEALER {
  --
  - move : int
}

class TANK {
  --
  - move : int
}

class DWARF {
  --
  - move : int
}

class THEMEMEMAKER {
  --
  - move : int
}

' Builder
class HEROSQUADCREATOR {
  --
}

' Factory
abstract class HEROCREATOR {
  --
}

class HEALERCREATOR {
  --
}

class TANKCREATOR {
  --
}

class DWARFCREATOR {
  --
}

class THEMEMEMAKERCREATOR {
  --
}

' ===================================
' 3. CASES
' ===================================

abstract class TILE {
  --
  - interface 
}

abstract class WALL {
  *bool isPassThrough()
  --
}

class WALLSTONE {
  --
}

class WALLWOOD {
  --
}

abstract class TRAP {
  --
}

class MINE {
  --
}

class WALLTRAP {
  --
}

' ===================================
' 4. STRATEGIES
' ===================================

interface STRATEGY {
    *void move(game : GAME)
    --
}

class ASTAR {
    --
}

class DFS {
    --
}

class BFS {
    --
}

' ===================================
' 5. RELATIONS ENTRE ENTITÉS
' ===================================

GAMEMANAGER --|> GAME : GÈRE
GAME <|-- MAP : CONTIENT
GAME <|-- HEROSQUAD : ASSIGNE
HEROSQUAD --|> HERO : COMPOSE
HERO <|.. HEALER
HERO <|.. TANK
HERO <|.. DWARF
HERO <|.. THEMEMEMAKER
GAME --|> HEROSQUADCREATOR : ASSIGNE
HEROSQUADCREATOR --|> HEROCREATOR : COMPOSE
HEROCREATOR <|.. HEALERCREATOR
HEROCREATOR <|.. TANKCREATOR
HEROCREATOR <|.. DWARFCREATOR
HEROCREATOR <|.. THEMEMEMAKERCREATOR
MAPBUILDER --|> MAP : CREER
MAPCREATOR --|> MAPBUILDER : CONTROLE
GAME <|--> GAMECREATOR : CREER
GAMECREATOR <|-- GAMECREATORCONTROLLER : CONTROLE
MAPBUILDER <|-- GAMESERIALISABLE
GAMESERIALISABLE <|-- LEADERBOARD
GAMESERIALISABLE --|> GAME
DAMAGEVISITOR --|> HERO
MAP ..|> TILE
TILE --|> WALL
TILE --|> TRAP
WALLSTONE <|-- WALL
WALLWOOD <|-- WALL
TRAP --|> MINE
TRAP --|> WALLTRAP
HERO --|> STRATEGY 
STRATEGY --|> ASTAR
STRATEGY --|> BFS
STRATEGY --|> DFS

@enduml