/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingstuff;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *All Rights Reserved
 * @author TokenAtKenz
 */

public class KurlParams extends ArrayList{
    public KurlParams(){
        super();
    }
    
    
    public void set(String key,Object value){
        KeyValuePair kvp; 
        boolean updated = false;
        Iterator list = iterator();
        while(list.hasNext()){
            kvp = (KeyValuePair)list.next();
            if(kvp.key.equals(key)){
                kvp.value = value;
                updated = true;
            }
        }
        if(!updated){
            kvp = new KeyValuePair(key,value);
            add(kvp);
        }
    }
    
    public Object get(String key){
        Iterator list = iterator();
        Object ret = null;
        KeyValuePair kvp;
        while(list.hasNext()){
            kvp = (KeyValuePair)list.next();
            if(kvp.key.equals(key)){ret = kvp.value;}
        }
        return ret;
    }
    

    public class KeyValuePair implements Comparable{
       public String key;
       public Object value;
       public KeyValuePair(String key,Object value){
                this.key = key;
                this.value = value;}
    @Override
    public int compareTo(Object o) {
        KeyValuePair kvp = (KeyValuePair) o;
        return (int)((double)this.value-(double)kvp.value ) ;
        }
    @Override
    public String toString(){
        String s = key + " ["+value+"]";
        return s;
    }
}
 
}
