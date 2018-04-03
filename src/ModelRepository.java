import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

class ModelRepository {

    private Model model;
    private Collection<Log> logs;

    private static int i = 0;
    final private Supplier<Integer> idSequence = () -> i++;

    ModelRepository() {
    }

    ModelRepository add(String x, int y) {
        this.model = new Model(x, y);
        this.logs = new ArrayList<>();
        return this;
    }

    ModelRepository update(String x, int y, int week, int year) {
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

    View restore(int givenWeek, int givenYear) {
        Function<String, Predicate<Log>> byField = field -> log -> log.field.equals(field);
        Predicate<Log> byFieldX = byField.apply("x");
        Predicate<Log> byFieldY = byField.apply("y");

        Predicate<Log> byAfterYearAndWeek = log -> log.year >= givenYear && log.week > givenWeek;
        Predicate<Log> bySameYearAndWeek = log -> log.year == givenYear && log.week == givenWeek;

        var view = new View();

        view.actualX = logs.stream()
                .filter(byFieldX.and(byAfterYearAndWeek))
                .min(comparingInt(Log::id))
                .map(Log::value)
                .orElse(model.x);

        view.actualY = logs.stream()
                .filter(byFieldY.and(byAfterYearAndWeek))
                .min(comparingInt(Log::id))
                .map(Log::value)
                .orElse(Integer.toString(model.y));

        view.lastX = logs.stream()
                .filter(byFieldX.and(bySameYearAndWeek))
                .min(comparing(Log::id))
                .map(Log::value)
                .orElse(null);

        view.lastY = logs.stream()
                .filter(byFieldY.and(bySameYearAndWeek))
                .min(comparing(Log::id))
                .map(Log::value)
                .orElse(null);

        return view;
    }

}
