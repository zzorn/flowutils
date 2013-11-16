package org.flowutils;

import java.util.concurrent.ConcurrentHashMap;

import static org.flowutils.Check.*;

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

    private final String name;
    private final int hash;

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
            // Symbol not found, try to add a new one, but if someone else already added one in between, use that instead.
            final Symbol newSymbol = new Symbol(name);
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

        // Cache hashcode, saves a function call and a comparison, so a minor optimization really.
        this.hash = name.hashCode();
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override public String toString() {
        return name;
    }
}
