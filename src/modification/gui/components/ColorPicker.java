/*
 * Decompiled with CFR 0.152.
 */
package modification.gui.components;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.Objects;
import modification.files.ColorFile;
import modification.gui.Component;
import modification.main.Modification;
import modification.modules.misc.GUI;

public class ColorPicker
extends Component {
    private Robot robot;
    private Color currentRGB;
    private Point rgbPoint;
    private Point colorPoint;
    private float xPosRGB;
    private float yPosRGB;
    private float xPos;
    private float yPos;
    private boolean rgbDragging;
    private boolean dragging;

    public ColorPicker() {
        super(null);
        try {
            this.robot = new Robot();
        }
        catch (AWTException aWTException) {
            Modification.LOG_UTIL.sendConsoleMessage("Error: Couldn't create robot");
        }
        this.currentRGB = Modification.color;
    }

    @Override
    public void draw(int n, int n2) {
        GUI gUI = (GUI)Objects.requireNonNull(Modification.MODULE_MANAGER.checkModuleForName("GUI"));
        if (((String)gUI.theme.value).equals("Abraxas")) {
            Modification.RENDER_UTIL.renderColorPicker(this.x, this.y, 100.0f, 100.0f, this.currentRGB.getRGB());
            if (this.rgbDragging && Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y + 130.0f, 100.0f, 15.0f)) {
                this.rgbPoint = MouseInfo.getPointerInfo().getLocation();
                this.xPosRGB = n;
                this.yPosRGB = n2;
                this.currentRGB = this.robot.getPixelColor(this.rgbPoint.x, this.rgbPoint.y);
            }
            if (this.dragging && Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y, 100.0f, 100.0f)) {
                this.colorPoint = MouseInfo.getPointerInfo().getLocation();
                this.xPos = n;
                this.yPos = n2;
                Modification.color = this.robot.getPixelColor(this.colorPoint.x, this.colorPoint.y);
                Modification.FILE_MANAGER.update(ColorFile.class);
            }
            if (this.dragging) {
                if (this.rgbPoint != null) {
                    Modification.RENDER_UTIL.drawCircle(this.xPosRGB, this.yPosRGB, 3.0f, 0x5000000, Color.BLACK.getRGB());
                }
                if (this.colorPoint != null) {
                    Modification.RENDER_UTIL.drawCircle(this.xPos, this.yPos, 3.0f, 0x5000000, Color.BLACK.getRGB());
                }
            }
            return;
        }
        Modification.RENDER_UTIL.renderColorPicker(this.x, this.y, 120.0f, 120.0f, this.currentRGB.getRGB());
        if (this.rgbDragging && Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y + 130.0f, 120.0f, 15.0f)) {
            this.rgbPoint = MouseInfo.getPointerInfo().getLocation();
            this.xPosRGB = n;
            this.yPosRGB = n2;
            this.currentRGB = this.robot.getPixelColor(this.rgbPoint.x, this.rgbPoint.y);
        }
        if (this.dragging && Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y, 120.0f, 120.0f)) {
            this.colorPoint = MouseInfo.getPointerInfo().getLocation();
            this.xPos = n;
            this.yPos = n2;
            Modification.color = this.robot.getPixelColor(this.colorPoint.x, this.colorPoint.y);
            Modification.FILE_MANAGER.update(ColorFile.class);
        }
        if (this.dragging) {
            if (this.rgbPoint != null) {
                Modification.RENDER_UTIL.drawCircle(this.xPosRGB, this.yPosRGB, 3.0f, 0x5000000, Color.BLACK.getRGB());
            }
            if (this.colorPoint != null) {
                Modification.RENDER_UTIL.drawCircle(this.xPos, this.yPos, 3.0f, 0x5000000, Color.BLACK.getRGB());
            }
        }
    }

    @Override
    public void click(int n, int n2, int n3) {
        if (n3 == 0) {
            if (Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y + 130.0f, 120.0f, 15.0f)) {
                this.rgbDragging = true;
            }
            if (Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y, 120.0f, 120.0f)) {
                this.dragging = true;
            }
        }
    }

    @Override
    public void release(int n) {
        if (n == 0) {
            this.dragging = false;
            this.rgbDragging = false;
        }
    }
}
