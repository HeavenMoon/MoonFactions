package fr.heavenmoon.factions.koth;

public enum KothState {

    WAITING(), STARTING();

    private static KothState current;

    KothState() {
    }

    public static boolean isState(KothState state) {
        return current == state;
    }

    public static KothState getCurrent() {
        return current;
    }

    public static void setCurrent(KothState state) {
        current = state;
    }
}
