package utility;

public class Vector2Int {
    private int x;
    private int y;

    public Vector2Int() {
        x = 0;
        y = 0;
    }

    public Vector2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2Int up() {
        return new Vector2Int(0, 1);
    }

    public static Vector2Int down() {
        return new Vector2Int(0, -1);
    }

    public static Vector2Int left() {
        return new Vector2Int(-1, 0);
    }

    public static Vector2Int right() {
        return new Vector2Int(1, 0);
    }
    public static int distanceFromZero(Vector2Int point){
        return Math.abs(point.getX()) + Math.abs(point.getY());
    }

    public Vector2Int add(Vector2Int other) {
        return new Vector2Int(this.x + other.x, this.y + other.y);
    }

    public boolean equalValue(Vector2Int other) {
        return  other.x == this.x && other.y == this.y;
    }

    public Vector2Int reversed(){
        return new Vector2Int(-x, -y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x=" + x +
                ", y=" + y ;
    }
}
