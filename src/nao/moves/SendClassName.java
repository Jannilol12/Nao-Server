package nao.moves;

import components.json.JSONArray;

public abstract interface SendClassName {
    public String name();
    
    /**
     * @param args Arguements from the Client: ["id":"a", "value":?]
     * 			a: Einzeige Name (beim Client) und eindeutige ID zur identifizierung
     * 			b: Value der vom Client eingegeben wurde. Kann ein String, Int, Double, etc. sein
     */
    public abstract void start(JSONArray args);
    public abstract void stop();

    /**
     * ["id":"a", "type":"b", "prompt":"c"\, "min":d, "max":d, "def":d]
     * 	a: Einzeige Name (beim Client) und eindeutige ID zur identifizierung<br/>
	 *	b: [text, color, boolean, int, double] sind moegliche Input Types.<br/>
	 *	c: (optional) In z.B. TextFields werden Prompts (HintergrundText) unterstuezt<br/>
	 *	d: (optional) Sind Werte die Sliders verwenden koennen<br/>
     * @return NULL if no args used, else JSONArray (StringFormat) is returned.
     */
    public JSONArray getArgsRequest();
}
