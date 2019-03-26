package common;

public enum ClientType {
    READER("READER"),
    WRITER("WRITER");

    private String value;

    ClientType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
