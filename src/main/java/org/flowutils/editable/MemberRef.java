package org.flowutils.editable;

import org.flowutils.Symbol;
import org.flowutils.editable.module.Module;
import org.flowutils.editable.object.Member;

import java.util.Arrays;
import java.util.Collection;

import static org.flowutils.Check.notNull;

/**
 * Reference to a specific module member.
 * May be a partial path or a full path from the root.
 */
public final class MemberRef {

    private final Symbol[] path;

    private final boolean startFromRoot;

    public MemberRef(boolean startFromRoot, Symbol ... path) {
        notNull(path, "path");

        this.startFromRoot = startFromRoot;
        this.path = path;
    }

    public MemberRef(boolean startFromRoot, Collection<Symbol> path) {
        this(startFromRoot, path.toArray(new Symbol[path.size()]));
    }

    public MemberRef(MemberRef memberRef, Symbol additionalPathElement) {
        notNull(memberRef, "memberRef");
        notNull(additionalPathElement, "additionalPathElement");

        path = new Symbol[memberRef.path.length + 1];
        startFromRoot = memberRef.isStartFromRoot();

        // Copy prefix path
        System.arraycopy(memberRef.path, 0, path, 0, memberRef.path.length);

        // Add last element
        path[path.length - 1] = additionalPathElement;
    }

    /**
     * @param module module to start from.
     * @return the module member that this reference references, using the specified start module.
     */
    public Member getMember(Module module) {
        notNull(module, "module");

        // Start from root if specified
        if (startFromRoot) {
            while (module.getParent() != null) {
                module = module.getParent();
            }
        }

        // Walk through modules
        for (int i = 0; i < path.length - 1; i++) {
            module = (Module) module.getMember(path[i]);
        }

        // Get the member
        return module.getMember(path[path.length - 1]);
    }

    public int getPathLength() {
        return path.length;
    }

    public Symbol getPathElement(int i) {
        return path[i];
    }

    public boolean isStartFromRoot() {
        return startFromRoot;
    }

    @Override public String toString() {
        StringBuilder s = new StringBuilder();
        if (startFromRoot) s.append("/");
        for (int i = 0; i < path.length; i++) {
            if (i > 0) s.append(".");
            s.append(path[i]);
        }
        return s.toString();
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberRef memberRef = (MemberRef) o;

        if (startFromRoot != memberRef.startFromRoot) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(path, memberRef.path);

    }

    @Override public int hashCode() {
        int result = Arrays.hashCode(path);
        result = 31 * result + (startFromRoot ? 1 : 0);
        return result;
    }

}
