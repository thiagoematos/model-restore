
public class Main {

    public static void main(String[] args) {
        var database = new Database();
        database
                .add("a", 1)

                .update("a", 2, 2, 2018)
                .update("b", 3, 4, 2018)
                .update("c", 4, 4, 2018)
                .update("d", 5, 4, 2018)
                .update("e", 6, 8, 2018)
                .update("f", 7, 52, 2018)

                .print();

        System.out.println();

        ViewAssert
                .on(database)
                .assertThat(1, 2018, "a", "1", null, null)
                .assertThat(2, 2018, "a", "2", null, "1")
                .assertThat(3, 2018, "a", "2", null, null)
                .assertThat(4, 2018, "d", "5", "a", "2")
                .assertThat(5, 2018, "d", "5", null, null)
                .assertThat(6, 2018, "d", "5", null, null)
                .assertThat(7, 2018, "d", "5", null, null)
                .assertThat(8, 2018, "e", "6", "d", "5")
                .assertThat(9, 2018, "e", "6", null, null)
                .assertThat(52, 2018, "f", "7", "e", "6")
                .assertThat(1, 2019, "f", "7", null, null)
        ;
    }

}
