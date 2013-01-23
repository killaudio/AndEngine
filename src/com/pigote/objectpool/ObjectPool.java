package com.pigote.objectpool;

import java.util.Enumeration;
import java.util.Hashtable;

public abstract class ObjectPool {
	 //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	private long expirationTime;   
	private Hashtable locked, unlocked;     
	private final char maxObjects; 
		
    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------
    
	public ObjectPool()
	{
		expirationTime = 30000; // 30 seconds
		locked = new Hashtable();         
		unlocked = new Hashtable();
		maxObjects = 20;
	}
    
    //---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------

	abstract Object create();
	abstract boolean validate(Object o);
	abstract void expire(Object o);

	//---------------------------------------------
    // METHODS
    //---------------------------------------------

	synchronized Object checkOut(){
		
		long now = System.currentTimeMillis();
		Object o;        
		if(unlocked.size() > 0)
		{
			Enumeration e = unlocked.keys();  
			while(e.hasMoreElements())
			{
				o = e.nextElement();           
				if((now - ((Long)unlocked.get(o)).longValue()) > expirationTime)
				{
					// object has expired
					unlocked.remove(o);
					expire(o);
					o = null;
				}
				else
				{
					if( validate(o))
					{
			       		unlocked.remove(o);
			     		locked.put(o, new Long(now));                
			     		return(o);
					}
					else
					{
			            // object failed validation
			            unlocked.remove(o);
			            expire(o);
			            o = null;
					}
				}
			}
		}
		// no objects available
		// create object if maxObjects hasn't been reached
		//if((unlocked.size()+locked.size()) > maxObjects) {
			o = create();        
			locked.put(o, new Long(now)); 
		//} else {
		//	o=null;
		//}
		return(o);
	}
	
	synchronized void checkIn(Object o){
		locked.remove(o);
		unlocked.put(o, new Long(System.currentTimeMillis()));
	}
    
}
