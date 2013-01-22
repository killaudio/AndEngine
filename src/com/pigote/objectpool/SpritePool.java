package com.pigote.objectpool;

public class SpritePool extends ObjectPool {

	@Override
	Object create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	boolean validate(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	void expire(Object o) {
		// TODO Auto-generated method stub
		
	}
	
	public SpritePool(){
		//initialize a couple of objects, etc
	}
	
	public SpriteResource borrowResource()
	{
	   return((SpriteResource) super.checkOut());
	}
	public void returnResource(SpriteResource c)
	{
	   super.checkIn(c);
	}
}
