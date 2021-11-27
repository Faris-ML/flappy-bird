package org.yourorghere;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;



public class background {
    final float  x=0;
    final float y=0;

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
    
        init(gl);
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

    }
}