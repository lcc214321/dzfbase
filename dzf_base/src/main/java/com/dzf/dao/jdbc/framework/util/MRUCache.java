package com.dzf.dao.jdbc.framework.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class MRUCache implements Map {
    private int maxCacheItems = 300;
    private Map<Object, Object> cache;
    private LinkedList<Object> mruList ;


    public MRUCache() {
        cache = new HashMap<Object, Object>(maxCacheItems);
        mruList = new LinkedList<Object>();
    }

    public MRUCache(int maxCapacity) {
        maxCacheItems = maxCapacity;
        cache = new HashMap<Object, Object>(maxCacheItems);
        mruList = new LinkedList<Object>();

    }


    private void touchItem(Object key) {
        mruList.remove(key);
        mruList.addFirst(key);

        if (cache.size() > maxCacheItems)
            cache.remove(mruList.removeLast());
    }

    // implementation of java.util.Map interface

    public int hashCode() {
        return cache.hashCode();
    }

    public boolean equals(Object obj) {
        return cache.equals(obj);
    }

    public int size() {
        return cache.size();
    }

    public Collection<Object> values() {
        return cache.values();
    }

    public Set<Object> keySet() {
        return cache.keySet();
    }

    public Set entrySet() {
        return cache.entrySet();
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    public boolean containsValue(Object v) {
        return cache.containsValue(v);
    }

    public boolean containsKey(Object k) {
        return cache.containsKey(k);
    }


    public Object put(Object key, Object value) {
      //     touchItem(key);
        return cache.put(key, value);
    }

    public Object get(Object key) {
        touchItem(key);
        return cache.get(key);
    }

    public Object remove(Object key) {
        mruList.remove(key);
        return cache.remove(key);
    }

    public void clear() {
        mruList.clear();
        cache.clear();
    }

    public void putAll(Map m) {
        Iterator i = m.entrySet().iterator();
        Map.Entry e;
        while (i.hasNext()) {
            e = (Map.Entry) i.next();
            put(e.getKey(), e.getValue());
        }
    }
}
