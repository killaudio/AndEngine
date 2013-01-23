package com.pigote.objectpool;

public class SpritePool extends ObjectPool {

	@Override
	Object create() {
		SpriteResource sprite = new SpriteResource();
		return sprite;
	}

	@Override
	boolean validate(Object o) {
		return (((SpriteResource)o).isValid());
	}

	@Override
	void expire(Object o) {
		o = null;		
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
