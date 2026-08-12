package modification.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import modification.main.Modification;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public final class ShaderUtil {
   public final int program = GL20.glCreateProgram();
   public float time;
   public boolean count;

   public ShaderUtil(String var1) {
      int var2 = GL20.glCreateShader(35632);
      int var3 = GL20.glCreateShader(35633);
      GL20.glShaderSource(var2, this.readShaderSource(var1.concat(".frag")));
      GL20.glShaderSource(var3, this.readShaderSource("default.vert"));
      GL20.glCompileShader(var3);
      GL20.glCompileShader(var2);
      GL20.glValidateProgram(this.program);
      GL20.glAttachShader(this.program, var3);
      GL20.glAttachShader(this.program, var2);
      GL20.glLinkProgram(this.program);
   }

   public final void renderShader(ScaledResolution var1) {
      if (this.count) {
         this.time = (float)((double)this.time + 0.015);
      }

      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glBegin(7);
      GL11.glTexCoord2d(0.0, 1.0);
      GL11.glVertex2d(0.0, 0.0);
      GL11.glTexCoord2d(0.0, 0.0);
      GL11.glVertex2d(0.0, (double)var1.getScaledHeight());
      GL11.glTexCoord2d(1.0, 0.0);
      GL11.glVertex2d((double)var1.getScaledWidth(), (double)var1.getScaledHeight());
      GL11.glTexCoord2d(1.0, 1.0);
      GL11.glVertex2d((double)var1.getScaledWidth(), 0.0);
      GL11.glEnd();
      GL11.glDisable(3042);
      GL20.glUseProgram(0);
   }

   public final int getUniform(String var1) {
      return GL20.glGetUniformLocation(this.program, var1);
   }

   private String readShaderSource(String var1) {
      StringBuilder var2 = new StringBuilder();

      try {
         BufferedReader var3 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("shaders/".concat(var1))));

         String var4;
         while ((var4 = var3.readLine()) != null) {
            var2.append(var4).append(System.lineSeparator());
         }

         var3.close();
      } catch (IOException var6) {
         Modification.LOG_UTIL.sendConsoleMessage("Error: Could't find file!");
      }

      return var2.toString();
   }
}
