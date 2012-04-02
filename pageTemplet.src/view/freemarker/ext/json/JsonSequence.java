/**
 * 
 */
package view.freemarker.ext.json;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateSequenceModel;

/**
 * JSONArray Sequence data model 模板包装类
 * 
 * @author yangwm May 13, 2010 10:34:30 AM
 */
public class JsonSequence implements TemplateSequenceModel, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 9075694172766088987L;
    
    private JSONArray jsonArray;
    
    public JsonSequence(JSONArray jsonArray){
        this.jsonArray = jsonArray;
    }

    public TemplateModel get(int index) throws TemplateModelException {
        TemplateModel tm;
        try {
            Object result = this.jsonArray.get(index);
            if(result instanceof JSONArray){
                tm = new JsonSequence((JSONArray)result);
            }else if(result instanceof JSONObject){
                tm = new JsonHash((JSONObject)result);
            }else{
                tm = new SimpleScalar(this.jsonArray.getString(index));
            }
        } catch (JSONException e) {
            throw new TemplateModelException(e);
        }
        return tm;
    }

    public int size() throws TemplateModelException {
        return this.jsonArray.length();
    }

}