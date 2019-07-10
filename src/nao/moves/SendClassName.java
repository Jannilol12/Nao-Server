package nao.moves;

import com.aldebaran.qi.Application;

import components.json.JSONArray;

public abstract interface SendClassName {
    public String name();
    
    /**
     * 
     * @param application should never NULL
     * @param input Arguements from the Client: ["id":"a", "value":?]
     * 			a: Einzeige Name (beim Client) und eindeutige ID zur identifizierung
     * 			b: Value der vom Client eingegeben wurde. Kann ein String, Int, Double, etc. sein
     */
    public abstract void start(Application application, JSONArray args);
    public abstract void stop();

    /**
     * ["id":"a", "type":"b", "prompt":"c"\, "min":d, "max":d, "def":d]
     * 	a: Einzeige Name (beim Client) und eindeutige ID zur identifizierung<br/>
	 *	b: [text, color, boolean, int, double] sind mögliche Input Types.<br/>
	 *	c: (optional) In z.B. TextFields werden Prompts (HintergrundText) unterstüzt<br/>
	 *	d: (optional) Sind Werte die Sliders verwenden können<br/>
     * @return NULL if no args used, else JSONArray (StringFormat) is returned.
     */
    public JSONArray getArgsRequest();
}
