class Log {

    private int id;
    int week;
    int year;
    String field;
    private String value;

    Log(int id, int week, int year, String field, String value) {
        this.id = id;
        this.week = week;
        this.year = year;
        this.field = field;
        this.value = value;
    }

    int id() {
        return this.id;
    }

    String value() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", week=" + week +
                ", year=" + year +
                ", field='" + field + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
