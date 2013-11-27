package grails.plugins.quartz

enum TriggerState {
    BLOCKED(4), COMPLETE(2), ERROR(3), NONE(-1), NORMAL(0), PAUSED(1)

    TriggerState(int value) { this.value = value }

    private final int value

    int value() { value }
}
