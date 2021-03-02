package com.generic.data.core.map;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author praveen.kumar
 *         <p>
 *         {@link QueryMapImpl} QueryMapImpl is implementation class of QueryMap is Hash table based
 *         implementation of the Map interface. This implementation provides all
 *         of the optional map operations, and permits null values and the null
 *         key. (The HashMapclass is roughly equivalent to Hashtable, except
 *         that it isunsynchronized and permits nulls.) This class makes no
 *         guarantees as to the order of the map; in particular, it does not
 *         guarantee that the order will remain constant over time.
 *         </p>
 *         <br>
 *         </br>
 *         <b>Note that this implementation is not synchronized.</b>
 *         <p>
 *         If multiple threads access a QueryMap concurrently, and at least one
 *         of the threads modifies the map structurally, it must be synchronized
 *         externally. (A structural modification is any operation that adds or
 *         deletes one or more mappings; merely changing the value associated
 *         with a key that an instance already contains is not a structural
 *         modification.) This is typically accomplished by synchronizing on
 *         some object that naturally encapsulates the map.If no such object
 *         exists, the map should be "wrapped" using the
 *         Collections.synchronizedMapmethod. This is best done at creation
 *         time, to prevent accidental unsynchronized access to the map:
 *         </p>
 */
public class QueryMap extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8919287407994188315L;
	private static QueryMap queryMapInstance;

	private QueryMap() {
		// not allowed for object creation
	}

	public static QueryMap getQueryMapInsatnce() {
		if (queryMapInstance == null) {
			queryMapInstance = new QueryMap();
		}
		return queryMapInstance;
	}
	public static Map<String, Object> getMap() {
		Map<String, Object> map= QueryMap.getQueryMapInsatnce();
		map.clear();
		return map;
	}
}
