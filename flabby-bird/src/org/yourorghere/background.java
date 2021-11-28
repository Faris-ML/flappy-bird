package org.yourorghere;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;



public class background {
    float  x=3;
    float y=0;
    float delta=0;

    public background() {
        
    }

    public void init(GL gl){
        Texture tex;
        //activate texture mapping for 2D
        gl.glEnable(GL.GL_TEXTURE_2D);
        try {
            //load texture
            tex = TextureIO.newTexture(new File("bg.jpeg"), true);
            tex.bind();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
    }
    public void draw(GL gl) {
        if(this.delta<=-2){
        this.delta=0;
        }
        this.delta=this.delta-0.001f;
    
        init(gl);
        gl.glTranslatef(delta, 0f, 0f);
        
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2d(1, 1);
        gl.glVertex2d(1,-1f*FlabbyBird.h);
        gl.glTexCoord2d(0, 1);
        gl.glVertex2d(-1, -1f*FlabbyBird.h);
        gl.glTexCoord2d(0, 0);
        gl.glVertex2d(-1, 1f*FlabbyBird.h);
        gl.glTexCoord2d(1, 0);
        gl.glVertex2d(1, 1f*FlabbyBird.h);
        gl.glEnd();
        gl.glFlush();
        
        
        
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2d(1, 1);
        gl.glVertex2d(3,-1f*FlabbyBird.h);
        gl.glTexCoord2d(0, 1);
        gl.glVertex2d(1, -1f*FlabbyBird.h);
        gl.glTexCoord2d(0, 0);
        gl.glVertex2d(1, 1f*FlabbyBird.h);
        gl.glTexCoord2d(1, 0);
        gl.glVertex2d(3, 1f*FlabbyBird.h);
        gl.glEnd();
        gl.glFlush();

    }
}