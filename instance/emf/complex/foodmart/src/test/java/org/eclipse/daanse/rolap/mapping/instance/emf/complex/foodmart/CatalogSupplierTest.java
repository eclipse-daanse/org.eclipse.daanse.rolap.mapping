package org.eclipse.daanse.rolap.mapping.instance.emf.complex.foodmart;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.impl.CatalogImpl;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.impl.SumMeasureImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Test;

class CatalogSupplierTest {

    @Test
    void test() {

        CatalogSupplier cs = new CatalogSupplier();
        CatalogImpl catalog = (CatalogImpl) cs.get();

        Set<EObject> allRefs = collectAllReachableRecursive(catalog);
        EcoreUtil.Copier copier = new EcoreUtil.Copier(true, false);

        Collection<EObject> allCopy = copier.copyAll(allRefs);
        copier.copyReferences();

        CatalogImpl catalogCopy = (CatalogImpl) copier.get(catalog);

        System.out.println(catalogCopy);

    }

    public static Set<EObject> collectAllReachableRecursive(EObject start) {
        LinkedHashSet<EObject> visited = new LinkedHashSet<>();
        dfs(start, visited);
        return visited;
    }

    @SuppressWarnings("unchecked")
    private static void dfs(EObject cur, Set<EObject> visited) {

        EObject container = cur.eContainer();
        if (container != null) {
            dfs(container, visited);
            return;
        }

        if (visited.contains(cur)) {
            return;
        } else {
            visited.add(cur);
        }

        for (EReference ref : cur.eClass().getEAllReferences()) {
            if (ref.isContainment()) {
                continue;
            }
            Object val;
            try {
                val = cur.eGet(ref, true);
            } catch (Exception ex) {
                continue;
            }
            if (val == null) {
                continue;
            }

            if (ref.isMany()) {
                for (Object o : (List<?>) val) {
                    if (o instanceof EObject) {
                        dfs((EObject) o, visited);

                    }
                }
            } else if (val instanceof EObject) {
                dfs((EObject) val, visited);
            }
        }

    }

}
