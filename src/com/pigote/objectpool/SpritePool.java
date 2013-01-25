package com.pigote.objectpool;

public class SpritePool extends ObjectPool {

	@Override
	Object create() {
		
		SpriteResource sprite = new SpriteResource();
		/*Delay to simulate time to:
		*load large resource
		*connect to database
		*other time consuming tasks
		**/
		for (float x = 0; x<15000000; x++); 
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
	
	public void borrowResource(BaseScene scene)
	{
		SpriteResource sr;
		sr = (SpriteResource) super.checkOut();
		scene.attachChild(sr.getSprite());
		sr.getSprite().animate(100);
		scene.registerTouchArea(sr.getSprite());
	}
	
	public void returnResource(SpriteResource c, BaseScene scene)
	{
		scene.detachChild(c.getSprite());
		super.checkIn(c);
	}
}
