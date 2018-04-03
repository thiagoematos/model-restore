import java.util.Comparator;

class ViewAssert {

    private Database database;

    private static final String errorMessageFormat = "w%dy%d %s -- Expected: %s Returned: %s\n";

    static ViewAssert on(Database database) {
        var viewAssert = new ViewAssert();
        viewAssert.database = database;
        return viewAssert;
    }

    ViewAssert assertThat(int week,
                          int year,
                          String actualX, String actualY,
                          String lastX, String lastY) {
        var snapshot = database.snapshot(week, year);
        if (notEquals(snapshot.actualX, actualX)) {
            printErrorMessage(week, year, "actualX", actualX, snapshot.actualX);
        }
        if (notEquals(snapshot.actualY, actualY)) {
            printErrorMessage(week, year, "actualY", actualY, snapshot.actualY);
        }
        if (notEquals(snapshot.lastX, lastX)) {
            printErrorMessage(week, year, "lastX", lastX, snapshot.lastX);
        }
        if (notEquals(snapshot.lastY, lastY)) {
            printErrorMessage(week, year, "lastY", lastY, snapshot.lastY);
        }

        return this;
    }

    private static boolean notEquals(final String a, final String b) {
        Comparator<String> tComparator = Comparator.nullsFirst(Comparator.naturalOrder());
        return tComparator.compare(a, b) != 0;
    }

    private static void printErrorMessage(final int week,
                                          final int year,
                                          final String fieldName,
                                          final String expected,
                                          final String returned) {
        System.out.printf(errorMessageFormat, week, year, fieldName, expected, returned);
    }

}
