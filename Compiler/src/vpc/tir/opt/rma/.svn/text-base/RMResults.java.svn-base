/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Oct 8, 2007
 */
package vpc.tir.opt.rma;

import vpc.core.*;
import vpc.core.decl.Field;
import vpc.core.decl.Method;
import vpc.core.types.Type;

import java.util.Collection;
import java.util.Set;

/**
 * The <code>RMResults</code> definition.
 *
 * @author Ben L. Titzer
 */
public interface RMResults {
    public Program getProgram();

    public Heap getHeap();

    public Set<Method> getLiveMethods();

    public Set<Heap.Record> getLiveRecords();

    public boolean isWriteOnly(Field sf);

    public Value getConstantMethodValue(Method method);

    public boolean isConstantMethod(Method method);

    public Value getConstantFieldValue(Field field);

    public boolean isConstantField(Field field);

    public Collection<Field> getFields(Type t);
}
