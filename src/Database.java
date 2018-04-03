import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

class Database {

    private Model model;
    private Collection<Log> logs;

    private static int i = 0;
    final private Supplier<Integer> idSequence = () -> i++;

    Database() {
    }

    Database add(String x, int y) {
        this.model = new Model(x, y);
        this.logs = new ArrayList<>();
        return this;
    }

    Database update(String x, int y, int week, int year) {
        if (!this.model.x.equals(x)) {
            this.logs.add(new Log(idSequence.get(), week, year, "x", this.model.x));
            this.model.x = x;
        }

        if (this.model.y != y) {
            this.logs.add(new Log(idSequence.get(), week, year, "y", Integer.toString(this.model.y)));
            this.model.y = y;
        }

        return this;
    }

    void print() {
        System.out.println(model);
        logs.forEach(System.out::println);
    }

    View snapshot(int week, int year) {
        var view = new View();

        Function<String, Predicate<Log>> byField = field -> log -> log.field.equals(field);
        Predicate<Log> byFieldX = byField.apply("x");
        Predicate<Log> byFieldY = byField.apply("y");

        Predicate<Log> byAfterYearAndWeek = log -> log.year >= year && log.week > week;
        Predicate<Log> bySameYearAndWeek = log -> log.year == year && log.week == week;

        view.actualX = logs
                .stream()
                .filter(byFieldX)
                .filter(byAfterYearAndWeek)
                .min(comparingInt(Log::byId))
                .map(log -> log.value)
                .orElse(model.x);

        view.actualY = logs
                .stream()
                .filter(byFieldY)
                .filter(byAfterYearAndWeek)
                .min(comparingInt(Log::byId))
                .map(log -> log.value)
                .orElse(Integer.toString(model.y));

        view.lastX = logs
                .stream()
                .filter(byFieldX)
                .filter(bySameYearAndWeek)
                .min(comparing(Log::byId))
                .map(log -> log.value)
                .orElse(null);

        view.lastY = logs
                .stream()
                .filter(byFieldY)
                .filter(bySameYearAndWeek)
                .min(comparing(Log::byId))
                .map(log -> log.value)
                .orElse(null);

        return view;
    }

}
