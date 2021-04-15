package jbl.assertion.generator;

import com.intellij.ide.hierarchy.call.CallHierarchyNodeDescriptor;
import com.intellij.ide.hierarchy.call.CallerMethodsTreeStructure;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMember;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.util.PsiTreeUtil;
import jbl.assertion.generator.junit.JUnitIdentifiers;

public class JUnitAnnotationFinder {

    private final CallerMethodsTreeStructure callerMethodsTreeStructure;

    public JUnitAnnotationFinder(final Project project, final PsiMethod psiMethod, final String scope) {
        this.callerMethodsTreeStructure = new CallerMethodsTreeStructure(project, (PsiMember) psiMethod, scope);
    }

    public String search() {

        final CallHierarchyNodeDescriptor baseDescriptor = (CallHierarchyNodeDescriptor) callerMethodsTreeStructure.getBaseDescriptor();
        return search(baseDescriptor);
    }

    private String search(final CallHierarchyNodeDescriptor nodeDescriptor) {

        PsiMethod method = null;
        if (nodeDescriptor.getTargetElement() instanceof PsiMethod) {
            method = (PsiMethod) nodeDescriptor.getTargetElement();
        }
        else if (nodeDescriptor.getTargetElement() instanceof PsiReferenceExpression) {
            final PsiElement referenceElement = nodeDescriptor.getTargetElement();
            method = PsiTreeUtil.getParentOfType(referenceElement, PsiMethod.class);
        }

        if (method == null) {
            return null;
        }

        final String annotation = getJUnitAnnotation(method);
        if (annotation != null) {
            return annotation;
        }

        final Object[] children = callerMethodsTreeStructure.getChildElements(nodeDescriptor);

        for (Object child : children) {
            final CallHierarchyNodeDescriptor childNodeDescriptor = (CallHierarchyNodeDescriptor) child;

            final String junitAnnotation = search(childNodeDescriptor);
            if (junitAnnotation != null) {
                return junitAnnotation;
            }
        }

        return null;
    }

    private String getJUnitAnnotation(final PsiMethod method) {

        if (method.hasAnnotation(JUnitIdentifiers.JUNIT4_TEST_FQN)) {
            return JUnitIdentifiers.JUNIT4_TEST_FQN;
        }
        else if (method.hasAnnotation(JUnitIdentifiers.JUNIT5_TEST_FQN)) {
            return JUnitIdentifiers.JUNIT5_TEST_FQN;
        }

        return null;
    }

}
