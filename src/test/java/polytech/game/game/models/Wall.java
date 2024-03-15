package polytech.game.game.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.*;

public class Wall {
    private int x;
    private int y;

    private int width;
    private int height;

    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    @JsonIgnore
    public Rectangle getHitbox() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setY(int y) {this.y = y;}

    public int getX() {
        return x;
    }

    public int getY() {return y;}
}
