# Nao-Server
## Beschreibung
P-Seminar Projekt des Gym.-Roth

## Benutzung
### SendClassName
### start
\["id":"@a", "value":?\]
@a: Einzeige Name (beim Client) und eindeutige ID zur identifizierung<br/>
@b: Value der vom Client eingegeben wurde. Kann ein String, Int, Double, etc. sein

#### getArgsRequest

\["id":"@a", "type":"@b", "prompt":"@c"\, "min":@d, "max":@d, "def":@d\]<br/>
<br/>
@a: Einzeige Name (beim Client) und eindeutige ID zur identifizierung<br/>
@b: [text, color, boolean, int, double] sind mögliche Input Types.<br/>
@c: (optional) In z.B. TextFields werden Prompts (HintergrundText) unterstüzt<br/>
@d: (optional) Sind Werte die Sliders verwenden können<br/>
    
## TODO
### Bewegungen
  - [ ] Winken
  - [ ] Eye rollen
  - [x] manuelle Sprachausgabe
