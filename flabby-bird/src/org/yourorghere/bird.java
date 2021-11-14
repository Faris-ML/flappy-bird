package org.yourorghere;

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author TFgam
 */
public class bird {
    float x;
    float y;
    float size;

    public bird(float x,float y,float size) {
        this.x=x;
        this.y=y;
        this.size=size;        
    }

    public void init(GL gl) {
        Texture tex;
        //activate texture mapping for 2D 
        gl.glEnable(GL.GL_TEXTURE_2D);
        try {
            //load texture 
            tex = TextureIO.newTexture(new File("bird.png"), true);
            tex.bind();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.err.println("INIT GL IS: " + gl.getClass().getName());
    }

    public void draw(GL gl) {
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2d(1, 0);
        gl.glVertex2d(x+size, y+size);
        gl.glTexCoord2d(0, 0);
        gl.glVertex2d(x-size, y+size);
        gl.glTexCoord2d(0, 1);
        gl.glVertex2d(x-size, y-size);
        gl.glTexCoord2d(1, 1);
        gl.glVertex2d(x+size, y-size);
        gl.glEnd();
        gl.glFlush();
    }
}
