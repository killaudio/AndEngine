package com.pigote.baseAnd;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Hold extends Sprite {
	private FixtureDef fixture_def = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
	private final int goodness;
	private final Body body;
	
	public Hold(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vbom, int goodness, PhysicsWorld physicsWorld) {
		super(pX, pY, pTextureRegion, vbom);
		this.goodness = goodness;
		fixture_def.isSensor = true;
		this.body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.StaticBody, fixture_def);
        body.setUserData("hold");
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false));
	}

	public int getGoodness() {
		return goodness;
	}

}
