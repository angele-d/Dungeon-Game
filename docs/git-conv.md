# Conventions git

## Branches

On fonctionne avec des dossiers de branche: deux catégories ```feat``` ou ```fix```.

Donc à la création, on a ```feat/my-feature-branch```, ou ```fix/my-fix-branch```

Toutes les branches font référence à un ticket référençable par son numéro ```#id```

Elles sont nommées ainsi: ```#id-myfeature```

## Les commits

#### Formattage

Ils sont formalisés ainsi: ```fix #id: Body of the commit``` ou ```feat #id: Body of the commit```

Et pour le corps du commit:
```
Created classes for ...
# Note the linebreak
Any uselul mention like for example: No code refactored
```

Si un commit contient volontairement une erreur:
```
BROKEN Description...
```
Ou si il contient une grosse modification (comme un refactor par exemple)
```
REFACTOR Description...
```

#### Exemple complet

```
feat #2: BROKEN Created heroes but there is some conflicts I can't resolve
```

ou bien

```
feat #2: Added all 4 heroes classes
```

## Les Pull Requests (PR)

Elles sont formatées ainsi:

```
#1-feat The heroes are added
```

ou si la branche a été fermée:

```
#1-feat CLOSED heroes are added
```

Des pull requests sont à fairte sur lles features, ou les fix peu fiables (qui demande une révision).
Les fix ne nécéssitent donc pas forcément de pull request.

## Les tickets

Nous allons utiliser deux tickets: les ```tickets``` et les ```tâches```.

Le ticket doit être créé avant la branche.

#### Les tâches
Les tâches concernent les feature, et sont crées avant la branche (puisqu'elle y est référencée). Il faut y associer les labels correspondants. Au début de chaque journée, on passe toutes les tâches désirées en TO-DO et on assigne celles qui ne le sont pas encore.

#### Les tickets
Les tickets concernent des erreurs, des bugfix ou des correctifs. Il faut tenter de les assigner au plus vite.

Une tâche ou un ticket peuvent être déclarées non assignée

