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

public class pipe {

    float x;
    float y;
    float gap;

    public pipe(float x, float y, float gap) {
        this.x = x;
        this.y = y;
        this.gap = gap;
    }

    public static void init(GL gl) {
        Texture tex;
        //activate texture mapping for 2D 
        gl.glEnable(GL.GL_TEXTURE_2D);
        try {
            //load texture 
            tex = TextureIO.newTexture(new File("pipe.png"), true);
            tex.bind();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public void draw(GL gl) {
        if (this.x <= -10) {
            this.x = 10;
        }
        this.x = this.x - 0.03f;
        
        init(gl);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2d(1, 0);
        gl.glVertex2d(x, y);
        gl.glTexCoord2d(0, 0);
        gl.glVertex2d(x+1, y);
        gl.glTexCoord2d(0, 1);
        gl.glVertex2d(x+1, -10);
        gl.glTexCoord2d(1, 1);
        gl.glVertex2d(x, -10);
        gl.glEnd();
        gl.glFlush();
      
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2d(0, 0);
        gl.glVertex2d(x, y+gap);
        gl.glTexCoord2d(1, 0);
        gl.glVertex2d(x+1, y+gap);
        gl.glTexCoord2d(1, 1);
        gl.glVertex2d(x+1, 10);
        gl.glTexCoord2d(0, 1);
        gl.glVertex2d(x, 10);
        gl.glEnd();
        gl.glFlush();
        
        
    }

}
