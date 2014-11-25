/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Dec 26, 2006
 */
package vpc.tir.opt.rma;

import cck.text.Printer;
import cck.text.TermUtil;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.tir.opt.rma.RMAnalyzer.TypeInfo;

import java.util.Map;
import java.util.Set;

/**
 * The <code>RMPrinter</code> definition.
 *
 * @author Ben L. Titzer
 */
public class RMPrinter {
    protected Printer printer;

    public void printResults(RMAnalyzer rma) {
        TermUtil.printSeparator(78, "RMA Results");
        printer.startblock("methods");
        for (Method m : rma.liveMethods)
            printer.println(m.getFullName());
        printer.endblock();
        for (Map.Entry<Type, TypeInfo> e : rma.typeInfo.entrySet()) {
            printTypeInfo(e.getValue());
        }
    }

    private void printTypeInfo(TypeInfo info) {
        printer.startblock("type " + info.type);
        printList(printer, "Fields: ", info.fields);
        printList(printer, "Methods: ", info.methods);
        printList(printer, "Subtypes: ", info.subtypes);
        printList(printer, "Instances: ", info.instances);
        printer.endblock();
    }

    public static void printList(Printer printer, String name, Set<?> list) {
        printer.beginList(name);
        if (list != null) for (Object r : list) {
            printer.print(r.toString());
        }
        printer.endListln();
    }
}
