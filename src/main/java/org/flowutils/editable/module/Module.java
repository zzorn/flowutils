package org.flowutils.editable.module;

import org.flowutils.Symbol;
import org.flowutils.editable.EditableProvider;
import org.flowutils.editable.object.Member;
import org.flowutils.editable.Type;
import org.flowutils.editable.Variable;
import org.flowutils.editable.function.Function;

import java.util.List;

/**
 * Contains various types, values and functions.
 */
public interface Module extends Member {

    // IDEA: Should we allow modules to import other modules, allowing memberRefs to traverse there as well?
    // IDEA: Should parent refs be forbidden, instead replaced by explicit imports?

    /**
     * @return the module that this module is contained in.
     */
    Module getParent();

    /**
     * @return the module member with the specified id.
     */
    Member getMember(Symbol memberId);

    /**
     * @return the modules contained in this module.
     */
    List<Module> getSubModules();

    /**
     * @return the imported members.
     */
    List<ModuleImport> getImports();

    /**
     * @return the types declared by this module.
     */
    List<Type> getTypes();

    /**
     * @return the (constant or editable) named variables defined in this module.
     */
    List<Variable> getVariables();

    /**
     * @return the functions defined in this module.
     */
    List<Function> getFunctions();

    /**
     * @return the EditableProvider that is providing this module.
     */
    EditableProvider getEditableProvider();


    // TODO: add listener support

    // TODO: Save and load / (xml)serialization support for modules.

}
