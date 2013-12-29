package org.flowutils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.flowutils.Check.strictIdentifier;

/**
 * A unique string type symbol, where each unique string is guaranteed to exist only once.
 * This way they can be identity compared instead of string compared.
 * Similar to the Symbol class in scala.
 *
 * Each Symbol is required to start with a letter or underscore, and contain letters, underscores, or numbers.
 *
 * Thread safe, the Symbols are unique across threads.
 */
public final class Symbol {

    private static final ConcurrentHashMap<String, Symbol> symbols = new ConcurrentHashMap<String, Symbol>();
    private static final AtomicInteger nextFreeId = new AtomicInteger(1);

    private final String  name;
    private transient int uniqueId;

    /**
     * @param name string to put in the symbol.
     *             Must not be null or an empty string.
     *             Must start with a-z, _, or A-Z, and contain only a-z, A-Z, _, or 0-9.
     * @return the symbol for the specified name.
     */
    public static Symbol get(String name) {
        strictIdentifier(name, "name");

        // Get symbol if found
        Symbol symbol = symbols.get(name);
        if (symbol == null) {
            // No existing symbol with the same name found

            // Create new symbol with a new unique id
            final Symbol newSymbol = new Symbol(name);
            newSymbol.uniqueId = nextFreeId.getAndIncrement();

            // Try to add symbol, but if someone else already added one in between,
            // use that instead.
            symbol = symbols.putIfAbsent(name, newSymbol);
            if (symbol == null) {
                symbol = newSymbol;
            }
        }

        return symbol;
    }

    /**
     * @return the string wrapped by the symbol.
     *         Will not be null or an empty string.
     *         will start with a-z, _, or A-Z, and contain only a-z, A-Z, _, or 0-9.

     */
    public String getName() {
        return name;
    }

    /**
     * Creates a new instance of a symbol.
     *
     * @param name name of the symbol.
     *             Must not be null or an empty string.
     *             Must start with a-z, _, or A-Z, and contain only a-z, A-Z, _, or 0-9.
     */
    private Symbol(String name) {
        strictIdentifier(name, "name");

        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) return true;
        else if (obj == null || !(obj instanceof Symbol)) return false;
        else {
            return getUniqueId() == ((Symbol)obj).getUniqueId();
        }
    }

    @Override
    public int hashCode() {
        return getUniqueId();
    }

    private int getUniqueId() {
        // Get unique id if we do not have one
        // We do not have one if this class was deserialized.
        if (uniqueId == 0) {
            // Double check that the name is valid (in case serialized data tried to inject an invalid name)
            strictIdentifier(name, "name");

            // Get existing symbol with same name if found
            Symbol symbol = symbols.get(name);
            if (symbol == null) {
                // No existing symbol found

                // Get next free unique id
                uniqueId = nextFreeId.getAndIncrement();

                // Try to add ourselves, or get already added symbol, if one was added from another thread.
                symbol = symbols.putIfAbsent(name, this);
            }

            if (symbol != null) {
                // Symbol existed, use unique id of existing symbol with same name
                uniqueId = symbol.uniqueId;
            }
        }

        return uniqueId;
    }

    @Override public String toString() {
        return name;
    }
}
