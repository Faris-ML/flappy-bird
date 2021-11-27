package org.yourorghere;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;


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
        if (this.x <= -1) {//if the pipe reach the most left go back to right
            this.x = 1;
        }
        if(!FlabbyBird.bird.die){ //keep moving from left to right
        this.x = this.x - 0.003f;
        }
        //draw the bottom pipe
        init(gl);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2d(1, 0);
        gl.glVertex2d(x, y);
        gl.glTexCoord2d(0, 0);
        gl.glVertex2d(x+0.1, y);
        gl.glTexCoord2d(0, 1);
        gl.glVertex2d(x+0.1, -1f*FlabbyBird.h+0.45f);
        gl.glTexCoord2d(1, 1);
        gl.glVertex2d(x, -1f*FlabbyBird.h+0.45f);
        gl.glEnd();
        gl.glFlush();
      
        //draw the top pipe
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2d(0, 0);
        gl.glVertex2d(x, y+gap);
        gl.glTexCoord2d(1, 0);
        gl.glVertex2d(x+0.1, y+gap);
        gl.glTexCoord2d(1, 1);
        gl.glVertex2d(x+0.1, 1f*FlabbyBird.h-0.45f);
        gl.glTexCoord2d(0, 1);
        gl.glVertex2d(x, 1f*FlabbyBird.h-0.45f);
        gl.glEnd();
        gl.glFlush();
        
        
    }

}
