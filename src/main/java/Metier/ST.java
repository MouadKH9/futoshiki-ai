package Metier;

/*************************************************************************
 *  Compilation:  javac ST.java
 *  Execution:    java ST
 *  
 *  Symbol table implementation using Java's java.util.TreeMap library.
 *  Does not allow duplicates.
 *
 *  % java ST
 *  128.112.136.11
 *  208.216.181.15
 *  null
 *
 *************************************************************************/

import java.util.TreeMap;
import java.util.Iterator;

public class ST<Key extends Comparable<Key>, Val> implements Iterable<Key> {
    private TreeMap<Key, Val> st;

    public ST() {
        st = new TreeMap<Key, Val>();
    }
    
    public TreeMap<Key, Val> getST()
    {
        return this.st;
    }
    
//    public void setVal(Key key, Val v)
//    {
//        st = v;
//    }
    
    public void put(Key key, Val val) {
        if (val == null) st.remove(key);
        else             st.put(key, val);
    }
    public Val get(Key key)             { return st.get(key);            }
    public Val remove(Key key)          { return st.remove(key);         }
    public boolean contains(Key key)    { return st.containsKey(key);    }
    public int size()                   { return st.size();              }
    public Iterator<Key> iterator()     { return st.keySet().iterator(); }
    
    /*private Set<String> keys = (Set<String>) st.keySet();
    public void aff()
    {
        for(String key : keys)
        {
            System.out.println("Le domaine de "+key+" est: "+st.get(key));
        }
    }*/

}
