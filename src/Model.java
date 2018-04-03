public class Model {

    String x;
    int y;

    Model(String x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Model{" +
                "x='" + x + '\'' +
                ", y=" + y +
                '}';
    }
}