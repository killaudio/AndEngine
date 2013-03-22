package com.pigote.baseAnd;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class Climber {
	
	//Variables
    public Sprite m_HeadSprite;
    public Sprite m_Torso1Sprite;
    public Sprite m_Torso2Sprite;
    public Sprite m_Torso3Sprite;
    public Sprite m_UpperArmLeftSprite;
    public Sprite m_UpperArmRightSprite;
    public Sprite m_LowerArmLeftSprite;
    public Sprite m_LowerArmRightSprite;
    public Sprite m_UpperLegLeftSprite;
    public Sprite m_UpperLegRightSprite;
    public Sprite m_LowerLegLeftSprite;
    public Sprite m_LowerLegRightSprite;
	
	public Climber(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld, Scene scene) {
		createRagdoll(vbo, physicsWorld, pX, pY);
		attachToScene(scene);
		scene.registerUpdateHandler(physicsWorld);	
	}
	
    private void attachToScene(Scene pScene) {
    	
    	pScene.registerTouchArea(m_HeadSprite);
    	pScene.registerTouchArea(m_Torso1Sprite);
    	pScene.registerTouchArea(m_Torso1Sprite);
    	pScene.registerTouchArea(m_Torso3Sprite);
    	pScene.registerTouchArea(m_UpperArmLeftSprite);
    	pScene.registerTouchArea(m_UpperArmRightSprite);         
    	pScene.registerTouchArea(m_LowerArmLeftSprite);
    	pScene.registerTouchArea(m_LowerArmRightSprite);         
    	pScene.registerTouchArea(m_UpperLegLeftSprite);
    	pScene.registerTouchArea(m_UpperLegRightSprite);         
    	pScene.registerTouchArea(m_LowerLegLeftSprite);
    	pScene.registerTouchArea(m_LowerLegRightSprite);
    	            
    	pScene.attachChild(m_HeadSprite);
    	pScene.attachChild(m_Torso1Sprite);
    	pScene.attachChild(m_Torso2Sprite);
    	pScene.attachChild(m_Torso3Sprite);
    	pScene.attachChild(m_UpperArmLeftSprite);
    	pScene.attachChild(m_UpperArmRightSprite);
    	pScene.attachChild(m_LowerArmLeftSprite);
    	pScene.attachChild(m_LowerArmRightSprite);
    	pScene.attachChild(m_UpperLegLeftSprite);
    	pScene.attachChild(m_UpperLegRightSprite);
    	pScene.attachChild(m_LowerLegLeftSprite);
    	pScene.attachChild(m_LowerLegRightSprite);
	}

	private Vector2 getLeftPoint(Sprite sprite, Body body) {
        float w = sprite.getWidth() * 0.5f / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
        float h = sprite.getHeight() * 0.5f / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
        Vector2 pnt = body.getPosition();
        pnt.x -= w;
        pnt.y -= h;
        return pnt;
   }

   private Vector2 getRightPoint(Sprite sprite, Body body) {
   	float w = sprite.getWidth() * 0.5f / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
   	float h = sprite.getHeight() * 0.5f / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
   	Vector2 pnt = body.getPosition();
   	pnt.x += w;
   	pnt.y += h;
   	return pnt;
   }

   private Vector2 getTopPoint(Sprite sprite, Body body) {
   	float h = sprite.getHeight() * 0.5f / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
   	Vector2 pnt = body.getPosition();
   	pnt.y -= h;
   	return pnt;
   }

   private Vector2 getBottomPoint(Sprite sprite, Body body) {
   	float h = sprite.getHeight() * 0.5f / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
   	Vector2 pnt = body.getPosition();
   	pnt.y += h;
   	return pnt;
   }

   private void createRagdoll(VertexBufferObjectManager vbom, PhysicsWorld m_PhysicsWorld, float px, float py) {
	
	ResourcesManager rm = ResourcesManager.getInstance();
	   
   	m_HeadSprite = new Sprite(0, 0, rm.m_Head, vbom);
   	m_Torso1Sprite = new Sprite(0, 0, rm.m_Torso1, vbom);
   	m_Torso2Sprite = new Sprite(0, 0, rm.m_Torso2, vbom);
   	m_Torso3Sprite = new Sprite(0, 0, rm.m_Torso3, vbom);
   	m_UpperArmLeftSprite = new Sprite(0, 0, rm.m_UpperArmLeft, vbom);
   	m_UpperArmRightSprite = new Sprite(0, 0, rm.m_UpperArmRight, vbom);
   	m_LowerArmLeftSprite = new Sprite(0, 0, rm.m_LowerArmLeft, vbom);
   	m_LowerArmRightSprite = new Sprite(0, 0, rm.m_LowerArmRight, vbom);
   	m_UpperLegLeftSprite = new Sprite(0, 0, rm.m_UpperLegLeft, vbom);
   	m_UpperLegRightSprite = new Sprite(0, 0, rm.m_UpperLegRight, vbom);
   	m_LowerLegLeftSprite = new Sprite(0, 0, rm.m_LowerLegLeft, vbom);
   	m_LowerLegRightSprite = new Sprite(0, 0, rm.m_LowerLegRight, vbom);

   	RevoluteJointDef jd = new RevoluteJointDef();
   	FixtureDef fixtureDef = new FixtureDef();

   	// BODIES
   	
   		//for head only
	    	fixtureDef.density = 1.0f;
	    	fixtureDef.friction = 0.1f;
	    	fixtureDef.restitution = 0.2f;
   	
   	// Head
   	m_HeadSprite.setPosition(px, py);
   	Body head = PhysicsFactory.createCircleBody(m_PhysicsWorld, m_HeadSprite, BodyType.DynamicBody, fixtureDef);                
   	m_HeadSprite.setUserData(head);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_HeadSprite, head));

	    	//for rest of body
	    	fixtureDef.density = 1.0f;
	    	fixtureDef.friction = 0.0f;
	    	fixtureDef.restitution = 1.0f;
   	
   	// Torso1    	
   	m_Torso1Sprite.setPosition(px, py + m_HeadSprite.getHeight());
   	Body torso1 = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_Torso1Sprite, BodyType.DynamicBody, fixtureDef);
   	m_Torso1Sprite.setUserData(torso1);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_Torso1Sprite, torso1));

   	// Torso2
   	m_Torso2Sprite.setPosition(px, py + 98);
   	Body torso2 = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_Torso2Sprite, BodyType.DynamicBody, fixtureDef);
   	m_Torso2Sprite.setUserData(torso2);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector( m_Torso2Sprite, torso2));

   	// Torso3
   	m_Torso3Sprite.setPosition(px, py + 133);
   	Body torso3 = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_Torso3Sprite, BodyType.DynamicBody, fixtureDef);
   	m_Torso3Sprite.setUserData(torso3);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_Torso3Sprite, torso3));

   	// UpperArm

   	// L
   	m_UpperArmLeftSprite.setPosition(px - (m_Torso1Sprite.getWidth()/2 + m_UpperArmLeftSprite.getWidth()/2),
   									py + m_HeadSprite.getHeight());
   	Body upperArmL = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_UpperArmLeftSprite, BodyType.DynamicBody, fixtureDef);
   	m_UpperArmLeftSprite.setUserData(upperArmL);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_UpperArmLeftSprite, upperArmL));

   	// R
   	m_UpperArmRightSprite.setPosition(px + m_Torso1Sprite.getWidth()/2 + m_UpperArmLeftSprite.getWidth()/2,
   									py + m_HeadSprite.getHeight());
   	Body upperArmR = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_UpperArmRightSprite, BodyType.DynamicBody, fixtureDef);
   	m_UpperArmRightSprite.setUserData(upperArmR);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_UpperArmRightSprite, upperArmR));

   	// LowerArm

   	// L
   	m_LowerArmLeftSprite.setPosition(px - 173.75f - 5f, py + 62.5f);
   	Body lowerArmL = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_LowerArmLeftSprite, BodyType.DynamicBody, fixtureDef);
   	m_LowerArmLeftSprite.setUserData(lowerArmL);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_LowerArmLeftSprite, lowerArmL));

   	// R
   	m_LowerArmRightSprite.setPosition(px + 146.25f + 5f, py + 62.5f);
   	Body lowerArmR = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_LowerArmRightSprite, BodyType.DynamicBody, fixtureDef);
   	m_LowerArmRightSprite.setUserData(lowerArmR);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_LowerArmRightSprite, lowerArmR));

   	// UpperLeg

   	// L
   	m_UpperLegLeftSprite.setPosition(px - m_UpperLegLeftSprite.getWidth()/2, py + 212.5f);
   	Body upperLegL = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_UpperLegLeftSprite, BodyType.DynamicBody, fixtureDef);
   	m_UpperLegLeftSprite.setUserData(upperLegL);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_UpperLegLeftSprite, upperLegL));

   	// R
   	m_UpperLegRightSprite.setPosition(px + m_UpperLegRightSprite.getWidth()/2, py + 212.5f);
   	Body upperLegR = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_UpperLegRightSprite, BodyType.DynamicBody, fixtureDef);
   	m_UpperLegRightSprite.setUserData(upperLegR);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector( m_UpperLegRightSprite, upperLegR));

   	// LowerLeg

   	// L
   	m_LowerLegLeftSprite.setPosition(px - m_UpperLegLeftSprite.getWidth()/2, py + 310);
   	Body lowerLegL = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_LowerLegLeftSprite, BodyType.DynamicBody, fixtureDef);
   	m_LowerLegLeftSprite.setUserData(lowerLegL);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_LowerLegLeftSprite, lowerLegL));

   	// R
   	m_LowerLegRightSprite.setPosition(px + m_UpperLegRightSprite.getWidth()/2, py + 310);
   	Body lowerLegR = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_LowerLegRightSprite, BodyType.DynamicBody, fixtureDef);
   	m_LowerLegRightSprite.setUserData(lowerLegR);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_LowerLegRightSprite, lowerLegR));

   	// JOINTS
   	jd.enableLimit = true;

   	// Head to shoulders
   	jd.lowerAngle = (float) (-40 / (180 / Math.PI));
   	jd.upperAngle = (float) (40 / (180 / Math.PI));
   	jd.initialize(torso1, head, getTopPoint(m_Torso1Sprite, torso1));
   	m_PhysicsWorld.createJoint(jd);

   	// Upper arm to shoulders
   	// L
   	jd.lowerAngle = (float) (-85 / (180 / Math.PI));
   	jd.upperAngle = (float) (130 / (180 / Math.PI));
   	jd.initialize(torso1, upperArmL, getRightPoint(m_UpperArmLeftSprite, upperArmL));
   	m_PhysicsWorld.createJoint(jd);

   	// R
   	jd.lowerAngle = (float) (-130 / (180 / Math.PI));
   	jd.upperAngle = (float) (85 / (180 / Math.PI));
   	jd.initialize(torso1, upperArmR, getLeftPoint(m_UpperArmRightSprite, upperArmR));
   	m_PhysicsWorld.createJoint(jd);

   	// Lower arm to upper arm
   	// L
   	jd.lowerAngle = (float) (-130 / (180 / Math.PI));
   	jd.upperAngle = (float) (10 / (180 / Math.PI));
   	jd.initialize(upperArmL, lowerArmL, getLeftPoint(m_UpperArmLeftSprite, upperArmL));
   	m_PhysicsWorld.createJoint(jd);
   	// R
   	jd.lowerAngle = (float) (-10 / (180 / Math.PI));
   	jd.upperAngle = (float) (130 / (180 / Math.PI));
   	jd.initialize(upperArmR, lowerArmR, getRightPoint(m_UpperArmRightSprite, upperArmR));
   	m_PhysicsWorld.createJoint(jd);

   	// Shoulders/stomach
   	jd.lowerAngle = (float) (-15 / (180 / Math.PI));
   	jd.upperAngle = (float) (15 / (180 / Math.PI));
   	jd.initialize(torso1, torso2, getBottomPoint(m_Torso1Sprite, torso1));
   	m_PhysicsWorld.createJoint(jd);
   	// Stomach/hips
   	jd.initialize(torso2, torso3, getBottomPoint(m_Torso2Sprite, torso2));
   	m_PhysicsWorld.createJoint(jd);

   	// Torso to upper leg
   	// L
   	jd.lowerAngle = (float) (-25 / (180 / Math.PI));
   	jd.upperAngle = (float) (45 / (180 / Math.PI));
   	jd.initialize(torso3, upperLegL, getBottomPoint(m_Torso3Sprite, torso3));
   	m_PhysicsWorld.createJoint(jd);
   	// R
   	jd.lowerAngle = (float) (-45 / (180 / Math.PI));
   	jd.upperAngle = (float) (25 / (180 / Math.PI));
   	jd.initialize(torso3, upperLegR, getBottomPoint(m_Torso3Sprite, torso3));
   	m_PhysicsWorld.createJoint(jd);

   	// Upper leg to lower leg
   	// L
   	jd.lowerAngle = (float) (-25 / (180 / Math.PI));
   	jd.upperAngle = (float) (75 / (180 / Math.PI));
   	jd.initialize(upperLegL, lowerLegL, getBottomPoint(m_UpperLegLeftSprite, upperLegL));
   	m_PhysicsWorld.createJoint(jd);
   	// R
   	jd.lowerAngle = (float) (-75 / (180 / Math.PI));
   	jd.upperAngle = (float) (25 / (180 / Math.PI));
   	jd.initialize(upperLegR, lowerLegR, getBottomPoint(m_UpperLegLeftSprite, upperLegR));
   	m_PhysicsWorld.createJoint(jd);
   }

}
