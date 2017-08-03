Module
======

| Name                       | Package                                     | Artifact Id                 |
|----------------------------|---------------------------------------------|-----------------------------| 
| DungeonGames               | de.bergwerklabs.dungenfighters.dungeongames | dungeon-games               |
| DungeonUploader            | de.bergwerklabs.dungeonfighters.uploader    | dungeon-uploader            |
| Game                       | de.bergwerklabs.dungeonfighters             | dungeon-fighters-game       |

Das Modul `DungeonGames` enthält wiederum andere Module, welche die einzelnen Minigames darstellen.

| Name              | Package                                                 | Artifact Id          |
|-------------------|---------------------------------------------------------|----------------------| 
| DungeonDropper    | de.bergwerklabs.dungeonfighters.dungeongames.dropper    | dungeon-dropper      |
| DungeonJumpAndRun | de.bergwerklabs.dungeonfighters.dungeongames.jumpandrun | dungeon-jump-and-run |
| DungeonMinefield  | de.bergwerklabs.dungeonfighters.dungeongames.minefield  | dungeon-minefield    |


Code Konventionen
=================

Aufbau einer Klasse
-------------------
Diese Vorgabe muss nicht zwingen umgesetzt werden, dennoch sollte zumindest ein Wenig darauf geachtet werden.
```
public class MyClass {
    
    {getter}
    
    {setter}

    {öffentliche statische Variablen}

    {öffentliche variablen}

    {private statische Variablen}
    
    {protected Variablen}
    
    {private Variablen}
    
    {öffentliche statische Methoden}
    
    {private statische Methoden}
    
    {konstruktoren}
    
    {öffentliche überschriebene Methoden}
    
    {öffentliche Instanzmethoden}
    
    {package private Instanzmethoden}
    
    {private überschriebene Methoden}
    
    {private InstanzMethoden}
}
``` 
Die Methoden sollten zu dem nach Datentypen sortiert sein. Zwischen jedem Getter muss immer eine Zeile freibleiben, dies gilt auch für Setter. Getter und Setter sind wie Methoden, nach dem Rückgabewert zu sortieren. Ebenfals gelten für sie die selbe Reihen folge wie bei Methoden.

```
{öffentliche überschriebene getter/setter}
    
{öffentliche getter/setter}
    
{package private getter/setter}
    
{private überschriebene getter/setter}
    
{private getter/setter}
```

Variablen
---------
Auf Instanzvariablen soll immer über das this Keyword zugegriffen werden. Dies gilt auch für Instanzmethoden.

Benennung der Variablen
-----------------------
Hier gelten die Java-Konventionen. Wobei bei eine Ausnahme erfolgt:
Bei Akronymen wie z. B. XML oder JSON werden die Klassen-Namen nicht in all-caps geschrieben, sondern nur der Anfangsbuchstabe.

**Richtig**
```JAVA
private XmlWriter writer = new XmlWriter();
private JsonObject jsonObject;
```

**Falsch**
```JAVA
private XMLWriter writer = new XMLWriter();
private JSONObject jsonObject;
```

Layout-Konventionen
-------------------
```JAVA
public class MyClass {
 
    public void myMethod(boolean bool) {
        if (bool) {
            System.out.println(bool);
        }
        else {
            System.out.println(!bool);
        }
    }
}
```
Der obige Code zeigt, wie die Klammern gesetzt werden müssen. Zudem muss die erste Zeile nach der Klassendeklaration ebenfalls frei bleiben.

Kommentare
----------

### Normale Kommentare
Kommentare haben die selbe Einrückung, wie umgebende Code. Kommentar-Styles, die erlaubt sind:

```JAVA
/*                               // Das ist auch
 * Das ist ein Kommentar         // ein Kommentar    
 */
```
Jede andere Form von Kommentaren sollte nicht verwendet werden. Zudem sollte, wenn möglich, ausschließlich in Englisch kommentiert werden.

### JavaDoc-Kommentare
Javadoc-Kommentare sollte so aussehen:
```JAVA
/**
 * Das ist der Kommentar
 *
 * @param integer Das ist ein Parameter
 */
public void func(int integer) {}
```
Zwischen der Auflistung der @param Notationen und der Beschreibung der Methode.

Klassen sollten wie folgt Kommentiert sein:
```JAVA
/**
 * Das ist eine Klasse
 * <p> 
 * Sie tut Dinge.
 *
 * @author {author}
 */
public class MyClass {}
```
Zwischen der Beschreibung und @author muss immer eine Zeile frei bleiben. Ein neuer Paragraph wird immer mit eine einzelnen `<p>` begonnen und steht immer alleine in einer Zeile, dies gilt ebenfalls für Methoden-Beschreibungen.
