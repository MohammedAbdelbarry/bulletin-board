package common;

public enum ClientType {
    READER("reader"),
    WRITER("writer");

    private String value;

    ClientType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
