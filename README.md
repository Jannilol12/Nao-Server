# Nao-Server
## Beschreibung
P-Seminar Projekt des Gym.-Roth

## Benutzung
### SendClassName
### start:args
\["id":"@a", "value":?\]<br/>
@a: Einzeige Name (beim Client) und eindeutige ID zur identifizierung<br/>
@b: Value der vom Client eingegeben wurde. Kann ein String, Int, Double, etc. sein<br/>

#### getArgsRequest

\["id":"@a", "type":"@b", "prompt":"@c"\, "min":@d, "max":@d, "def":@d\]<br/>
<br/>
@a: Einzeige Name (beim Client) und eindeutige ID zur identifizierung<br/>
@b: [text, color, boolean, int, double] sind mögliche Input Types.<br/>
@c: (optional) In z.B. TextFields werden Prompts (HintergrundText) unterstüzt<br/>
@d: (optional) Sind Werte die Sliders verwenden können<br/>
    
### Server Input (Main)
{"type":"@a", ...}<br/>
@a: Aktion die Ausgefuert werden sollte<br/>
...: Benötigte sonstige Werte, die die Aktion braucht



## TODO
### Bewegungen
  - [ ] Winken
  - [ ] Eye rollen
  - [x] manuelle Sprachausgabe
