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
    public Sprite m_UpperTorsoSprite;
    public Sprite m_LowerTorsoSprite;
    public Sprite m_UpperArmLeftSprite;
    public Sprite m_UpperArmRightSprite;
    public Sprite m_LowerArmLeftSprite;
    public Sprite m_LowerArmRightSprite;
    public Sprite m_UpperLegLeftSprite;
    public Sprite m_UpperLegRightSprite;
    public Sprite m_LowerLegLeftSprite;
    public Sprite m_LowerLegRightSprite;
    public Sprite m_HandLeftSprite;
    public Sprite m_HandRightSprite;
    public Sprite m_FootLeftSprite;
    public Sprite m_FootRightSprite;
	
	public Climber(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld, Scene scene) {
		createRagdoll(vbo, physicsWorld, pX, pY);
		attachToScene(scene);
		scene.registerUpdateHandler(physicsWorld);	
	}
	
    private void attachToScene(Scene pScene) {
    	
    	pScene.registerTouchArea(m_HeadSprite);
    	pScene.registerTouchArea(m_UpperTorsoSprite);
    	pScene.registerTouchArea(m_LowerTorsoSprite);
    	pScene.registerTouchArea(m_UpperArmLeftSprite);
    	pScene.registerTouchArea(m_UpperArmRightSprite);         
    	pScene.registerTouchArea(m_LowerArmLeftSprite);
    	pScene.registerTouchArea(m_LowerArmRightSprite);         
    	pScene.registerTouchArea(m_UpperLegLeftSprite);
    	pScene.registerTouchArea(m_UpperLegRightSprite);         
    	pScene.registerTouchArea(m_LowerLegLeftSprite);
    	pScene.registerTouchArea(m_LowerLegRightSprite);
    	pScene.registerTouchArea(m_HandLeftSprite);
    	pScene.registerTouchArea(m_HandRightSprite);         
    	pScene.registerTouchArea(m_FootLeftSprite);
    	pScene.registerTouchArea(m_FootRightSprite);
    	
    	pScene.attachChild(m_HeadSprite);
    	pScene.attachChild(m_UpperTorsoSprite);
    	pScene.attachChild(m_LowerTorsoSprite);
    	pScene.attachChild(m_UpperArmLeftSprite);
    	pScene.attachChild(m_UpperArmRightSprite);
    	pScene.attachChild(m_LowerArmLeftSprite);
    	pScene.attachChild(m_LowerArmRightSprite);
    	pScene.attachChild(m_UpperLegLeftSprite);
    	pScene.attachChild(m_UpperLegRightSprite);
    	pScene.attachChild(m_LowerLegLeftSprite);
    	pScene.attachChild(m_LowerLegRightSprite);
    	pScene.attachChild(m_HandLeftSprite);
    	pScene.attachChild(m_HandRightSprite);
    	pScene.attachChild(m_FootLeftSprite);
    	pScene.attachChild(m_FootRightSprite);
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
   	m_UpperTorsoSprite = new Sprite(0, 0, rm.m_UpperTorso, vbom);
   	m_LowerTorsoSprite = new Sprite(0, 0, rm.m_LowerTorso, vbom);
   	m_UpperArmLeftSprite = new Sprite(0, 0, rm.m_UpperArmLeft, vbom);
   	m_UpperArmRightSprite = new Sprite(0, 0, rm.m_UpperArmRight, vbom);
   	m_LowerArmLeftSprite = new Sprite(0, 0, rm.m_LowerArmLeft, vbom);
   	m_LowerArmRightSprite = new Sprite(0, 0, rm.m_LowerArmRight, vbom);
   	m_UpperLegLeftSprite = new Sprite(0, 0, rm.m_UpperLegLeft, vbom);
   	m_UpperLegRightSprite = new Sprite(0, 0, rm.m_UpperLegRight, vbom);
   	m_LowerLegLeftSprite = new Sprite(0, 0, rm.m_LowerLegLeft, vbom);
   	m_LowerLegRightSprite = new Sprite(0, 0, rm.m_LowerLegRight, vbom);
   	m_HandLeftSprite = new Sprite(0, 0, rm.m_HandLeft, vbom);
   	m_HandRightSprite = new Sprite(0, 0, rm.m_HandRight, vbom);
   	m_FootLeftSprite = new Sprite(0, 0, rm.m_FootLeft, vbom);
   	m_FootRightSprite = new Sprite(0, 0, rm.m_FootRight, vbom);
   	
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
   	
   	// UpperTorso    	
   	m_UpperTorsoSprite.setPosition(px, py - (m_HeadSprite.getHeight()/2 + m_UpperTorsoSprite.getHeight()/2));
   	Body upperTorso = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_UpperTorsoSprite, BodyType.DynamicBody, fixtureDef);
   	m_UpperTorsoSprite.setUserData(upperTorso);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_UpperTorsoSprite, upperTorso));

   	// LowerTorso
   	m_LowerTorsoSprite.setPosition(px, py - (m_HeadSprite.getHeight()/2 + m_UpperTorsoSprite.getHeight() + m_LowerTorsoSprite.getHeight()/2));
   	Body lowerTorso = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_LowerTorsoSprite, BodyType.DynamicBody, fixtureDef);
   	m_LowerTorsoSprite.setUserData(lowerTorso);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_LowerTorsoSprite, lowerTorso));

   	// UpperArm

   	// L
   	m_UpperArmLeftSprite.setPosition(px - (m_UpperTorsoSprite.getWidth()/2 + m_UpperArmLeftSprite.getWidth()/2),
   									py - (m_HeadSprite.getHeight()/2 + m_UpperArmLeftSprite.getHeight()/2));
   	
   	Body upperArmL = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_UpperArmLeftSprite, BodyType.DynamicBody, fixtureDef);
   	m_UpperArmLeftSprite.setUserData(upperArmL);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_UpperArmLeftSprite, upperArmL));

   	// R
   	m_UpperArmRightSprite.setPosition(px + m_UpperTorsoSprite.getWidth()/2 + m_UpperArmRightSprite.getWidth()/2,
   									py - (m_HeadSprite.getHeight()/2 + m_UpperArmRightSprite.getHeight()/2));
   	Body upperArmR = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_UpperArmRightSprite, BodyType.DynamicBody, fixtureDef);
   	m_UpperArmRightSprite.setUserData(upperArmR);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_UpperArmRightSprite, upperArmR));

   	// LowerArm

   	// L
   	m_LowerArmLeftSprite.setPosition(px - (m_UpperTorsoSprite.getWidth()/2 + m_UpperArmLeftSprite.getWidth() + m_LowerArmLeftSprite.getWidth()/2), 
   									py - (m_HeadSprite.getHeight()/2 + m_UpperArmLeftSprite.getHeight()/2));
   	Body lowerArmL = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_LowerArmLeftSprite, BodyType.DynamicBody, fixtureDef);
   	m_LowerArmLeftSprite.setUserData(lowerArmL);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_LowerArmLeftSprite, lowerArmL));

   	// R
   	m_LowerArmRightSprite.setPosition(px + (m_UpperTorsoSprite.getWidth()/2 + m_UpperArmRightSprite.getWidth() + m_LowerArmRightSprite.getWidth()/2),
   									py - (m_HeadSprite.getHeight()/2 + m_UpperArmRightSprite.getHeight()/2));
   	Body lowerArmR = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_LowerArmRightSprite, BodyType.DynamicBody, fixtureDef);
   	m_LowerArmRightSprite.setUserData(lowerArmR);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_LowerArmRightSprite, lowerArmR));
   	
   	// Hands
   	
   	//L
   	m_HandLeftSprite.setPosition(px - (m_UpperTorsoSprite.getWidth()/2 + m_UpperArmLeftSprite.getWidth() + m_LowerArmLeftSprite.getWidth() + m_HandLeftSprite.getWidth()/2),
   								py - (m_HeadSprite.getHeight()/2 + m_UpperArmLeftSprite.getHeight()/2));
   	Body handL = PhysicsFactory.createCircleBody(m_PhysicsWorld, m_HandLeftSprite, BodyType.DynamicBody, fixtureDef);
   	m_HandLeftSprite.setUserData(handL);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_HandLeftSprite, handL));

   	//R
	m_HandRightSprite.setPosition(px + (m_UpperTorsoSprite.getWidth()/2 + m_UpperArmRightSprite.getWidth() + m_LowerArmRightSprite.getWidth() + m_HandRightSprite.getWidth()/2),
								py - (m_HeadSprite.getHeight()/2 + m_UpperArmRightSprite.getHeight()/2));
   	Body handR = PhysicsFactory.createCircleBody(m_PhysicsWorld, m_HandRightSprite, BodyType.DynamicBody, fixtureDef);
   	m_HandRightSprite.setUserData(handR);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_HandRightSprite, handR));
   	
   	// UpperLeg

   	// L
   	m_UpperLegLeftSprite.setPosition(px - m_UpperLegLeftSprite.getWidth()/2,
   								py - (m_HeadSprite.getHeight()/2 + m_UpperTorsoSprite.getHeight() + m_LowerTorsoSprite.getHeight() + m_UpperLegLeftSprite.getHeight()/2));
   	Body upperLegL = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_UpperLegLeftSprite, BodyType.DynamicBody, fixtureDef);
   	m_UpperLegLeftSprite.setUserData(upperLegL);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_UpperLegLeftSprite, upperLegL));

   	// R
   	m_UpperLegRightSprite.setPosition(px + m_UpperLegRightSprite.getWidth()/2,
   								py - (m_HeadSprite.getHeight()/2 + m_UpperTorsoSprite.getHeight() + m_LowerTorsoSprite.getHeight() + m_UpperLegRightSprite.getHeight()/2));
   	Body upperLegR = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_UpperLegRightSprite, BodyType.DynamicBody, fixtureDef);
   	m_UpperLegRightSprite.setUserData(upperLegR);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector( m_UpperLegRightSprite, upperLegR));

   	// LowerLeg

   	// L
   	m_LowerLegLeftSprite.setPosition(px - m_UpperLegLeftSprite.getWidth()/2,
   								py - (m_HeadSprite.getHeight()/2 + m_UpperTorsoSprite.getHeight() + m_LowerTorsoSprite.getHeight() + m_UpperLegLeftSprite.getHeight() + m_LowerLegLeftSprite.getHeight()/2));
   	Body lowerLegL = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_LowerLegLeftSprite, BodyType.DynamicBody, fixtureDef);
   	m_LowerLegLeftSprite.setUserData(lowerLegL);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_LowerLegLeftSprite, lowerLegL));

   	// R
   	m_LowerLegRightSprite.setPosition(px + m_UpperLegRightSprite.getWidth()/2, 
   								py - (m_HeadSprite.getHeight()/2 + m_UpperTorsoSprite.getHeight() + m_LowerTorsoSprite.getHeight() + m_UpperLegRightSprite.getHeight() + m_LowerLegRightSprite.getHeight()/2));
   	Body lowerLegR = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_LowerLegRightSprite, BodyType.DynamicBody, fixtureDef);
   	m_LowerLegRightSprite.setUserData(lowerLegR);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_LowerLegRightSprite, lowerLegR));

	// Feet

   	// L
   	m_FootLeftSprite.setPosition(px - m_UpperLegLeftSprite.getWidth()/2,
   								py - (m_HeadSprite.getHeight()/2 + m_UpperTorsoSprite.getHeight() + m_LowerTorsoSprite.getHeight() + m_UpperLegLeftSprite.getHeight() + m_LowerLegLeftSprite.getHeight() + m_FootLeftSprite.getHeight()/2));
   	Body footL = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_FootLeftSprite, BodyType.DynamicBody, fixtureDef);
   	m_FootLeftSprite.setUserData(footL);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_FootLeftSprite, footL));

   	// R
   	m_FootRightSprite.setPosition(px + m_UpperLegRightSprite.getWidth()/2, 
   								py - (m_HeadSprite.getHeight()/2 + m_UpperTorsoSprite.getHeight() + m_LowerTorsoSprite.getHeight() + m_UpperLegRightSprite.getHeight() + m_LowerLegRightSprite.getHeight() + m_FootRightSprite.getHeight()/2));
   	Body footR = PhysicsFactory.createBoxBody(m_PhysicsWorld, m_FootRightSprite, BodyType.DynamicBody, fixtureDef);
   	m_FootRightSprite.setUserData(footR);
   	m_PhysicsWorld.registerPhysicsConnector(new PhysicsConnector(m_FootRightSprite, footR));
   	
   	// JOINTS
   	jd.enableLimit = true;

   	// Head to shoulders
   	jd.lowerAngle = (float) (-40 / (180 / Math.PI));
   	jd.upperAngle = (float) (40 / (180 / Math.PI));
   	jd.initialize(upperTorso, head, getTopPoint(m_UpperTorsoSprite, upperTorso));
   	m_PhysicsWorld.createJoint(jd);

   	// Upper arm to shoulders
   	// L
   	jd.lowerAngle = (float) (-85 / (180 / Math.PI));
   	jd.upperAngle = (float) (130 / (180 / Math.PI));
   	jd.initialize(upperTorso, upperArmL, getRightPoint(m_UpperArmLeftSprite, upperArmL));
   	m_PhysicsWorld.createJoint(jd);

   	// R
   	jd.lowerAngle = (float) (-130 / (180 / Math.PI));
   	jd.upperAngle = (float) (85 / (180 / Math.PI));
   	jd.initialize(upperTorso, upperArmR, getLeftPoint(m_UpperArmRightSprite, upperArmR));
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

   	//Hands to lower arms
   	//L
   	jd.lowerAngle = (float) (-10 / (180 / Math.PI));
   	jd.upperAngle = (float) (10 / (180 / Math.PI));
   	jd.initialize(handL, lowerArmL, getLeftPoint(m_LowerArmLeftSprite, lowerArmL));
   	m_PhysicsWorld.createJoint(jd);
   	//R
   	jd.lowerAngle = (float) (-10 / (180 / Math.PI));
   	jd.upperAngle = (float) (10 / (180 / Math.PI));
   	jd.initialize(handR, lowerArmR, getRightPoint(m_LowerArmRightSprite, lowerArmR));
   	m_PhysicsWorld.createJoint(jd);
   	
   	// upperTorso/lowerTorso
   	jd.lowerAngle = (float) (-15 / (180 / Math.PI));
   	jd.upperAngle = (float) (15 / (180 / Math.PI));
   	jd.initialize(upperTorso, lowerTorso, getBottomPoint(m_UpperTorsoSprite, upperTorso));
   	m_PhysicsWorld.createJoint(jd);

   	// Torso to upper leg
   	// L
   	jd.lowerAngle = (float) (-25 / (180 / Math.PI));
   	jd.upperAngle = (float) (45 / (180 / Math.PI));
   	jd.initialize(lowerTorso, upperLegL, getBottomPoint(m_LowerTorsoSprite, lowerTorso));
   	m_PhysicsWorld.createJoint(jd);
   	// R
   	jd.lowerAngle = (float) (-45 / (180 / Math.PI));
   	jd.upperAngle = (float) (25 / (180 / Math.PI));
   	jd.initialize(lowerTorso, upperLegR, getBottomPoint(m_LowerTorsoSprite, lowerTorso));
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
   	
   	// Feet
   	// L
   	jd.lowerAngle = (float) (-25 / (180 / Math.PI));
   	jd.upperAngle = (float) (75 / (180 / Math.PI));
   	jd.initialize(footL, lowerLegL, getBottomPoint(m_LowerLegLeftSprite, lowerLegL));
   	m_PhysicsWorld.createJoint(jd);
   	// R
   	jd.lowerAngle = (float) (-75 / (180 / Math.PI));
   	jd.upperAngle = (float) (25 / (180 / Math.PI));
   	jd.initialize(footR, lowerLegR, getBottomPoint(m_LowerLegLeftSprite, lowerLegR));
   	m_PhysicsWorld.createJoint(jd);
   }

}
