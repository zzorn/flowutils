package org.flowutils.editable.module;

import org.flowutils.Symbol;
import org.flowutils.editable.MemberRef;

/**
 * Imports the specified module or member, making it visible as a member of the current module for references.
 */
public interface ModuleImport {

    /**
     * @return path to member to import.
     */
    MemberRef getImportPath();

    /**
     * @return if true, all immediate members of the module member specified by the path are imported, instead of the member at the imported path.
     */
    boolean isAllMembersImported();

    /**
     * @return the alias to use for the imported member, or null if no renaming should be used, or if all members are imported.
     */
    Symbol getAlias();

}
