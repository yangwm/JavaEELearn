/**
 * 
 */
package view.freemarker.ext.json;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * JSONObject Hashes data model 模板包装类
 * 
 * @author yangwm May 13, 2010 10:26:35 AM
 */
public class JsonHash implements TemplateHashModel, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3797802450071110440L;
    
    private JSONObject jsonObject;
    
    public JsonHash(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public TemplateModel get(String key) throws TemplateModelException {
        TemplateModel tm;
        try {
            if(this.jsonObject.isNull(key)){
                return null;
            }else{
                Object result = this.jsonObject.get(key);
                if(result instanceof JSONArray){
                    tm = new JsonSequence((JSONArray)result);
                }else if(result instanceof JSONObject){
                    tm = new JsonHash((JSONObject)result);
                }else{
                    tm = new SimpleScalar(this.jsonObject.getString(key));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tm;
    }

    public boolean isEmpty() throws TemplateModelException {
        return this.jsonObject == null;
    }
}

