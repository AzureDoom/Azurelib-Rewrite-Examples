package mod.azure.azexamples.registry;

/**
 * Numeric ids the examples still pick themselves.
 * <p>
 * Items don't need one: OSL's Items API assigns their numeric ids automatically and keeps them in sync between server,
 * client and saves. Blocks do, because OSL can't auto-assign block ids on Beta (the save and packet formats only hold
 * 0-255), and so do entity network ids. These must never change once worlds exist.
 */
public final class ExamplesIds {

    /** Block ids are 0-255; vanilla Beta uses up to 96. */
    public static final int STARGATE_BLOCK = 200;

    /**
     * Entity network ids take consecutive ids from here, in {@link EntityRegistry} order. Vanilla Beta uses up to 95,
     * and mobs must stay at or below 127 to spawn on multiplayer clients.
     */
    public static final int FIRST_ENTITY = 100;

    private ExamplesIds() {}
}
