package com.routon.jme_droid;

import com.jme3.animation.Animation;
import com.jme3.animation.AnimationFactory;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

public class AnimationCollects{
   
    static public Animation MoveIn() {
      
        AnimationFactory af = new AnimationFactory(1, "show", 10);
        af.addKeyFrameTranslation(0, new Vector3f(-15, 0, 0));
        af.addKeyFrameTranslation(3, new Vector3f(-3, 0, 0));
        af.addKeyFrameTranslation(10, new Vector3f(0, 0, 0));
        af.addKeyFrameRotationAngles(0, 0, 0, (-180) * FastMath.DEG_TO_RAD);
        af.addKeyFrameRotationAngles(10, 0, 0, 0);
        return af.buildAnimation();
    }
    
    static public Animation MoveOut() {
        AnimationFactory af = new AnimationFactory(1, "hide", 10);
        af.addKeyFrameTranslation(0, new Vector3f(0, 0, 0));
        af.addKeyFrameTranslation(3, new Vector3f(-12, 0, 0));
        af.addKeyFrameTranslation(10, new Vector3f(-15, 0, 0));
        af.addKeyFrameRotationAngles(0, 0, 0, 0);
        af.addKeyFrameRotationAngles(10, 0, 0, (-180) * FastMath.DEG_TO_RAD);
        return af.buildAnimation();        
    }
}