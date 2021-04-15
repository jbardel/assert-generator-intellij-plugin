package jbl.assertion.generator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDeclarationStatement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiImportStaticStatement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiPackageStatement;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;
import jbl.assertion.generator.getters.DefaultValueGenerator;
import jbl.assertion.generator.getters.Getter;
import jbl.assertion.generator.junit.JUnitContext;
import org.jetbrains.annotations.NotNull;

import static jbl.assertion.generator.constants.CommonConstants.ASSERT_EQUALS_CODE;
import static jbl.assertion.generator.constants.CommonConstants.GET;
import static jbl.assertion.generator.constants.CommonConstants.IS;

public class GenerateAssertionsIntentionActionDelegate {

    private final Project project;
    private final Editor editor;
    private final PsiElement element;
    private final JUnitContext jUnitContext;

    private final PsiElementFactory psiFactory;
    private final CodeStyleManager codeStyleManager;

    public GenerateAssertionsIntentionActionDelegate(@NotNull final Project project, final Editor editor,
                                                     @NotNull final PsiElement element,
                                                     final JUnitContext jUnitContext) {
        this.project = project;
        this.editor = editor;
        this.element = element;
        this.jUnitContext = jUnitContext;

        //Factory for creating psiElements
        this.psiFactory = JavaPsiFacade.getElementFactory(project);

        //Manager for format code
        this.codeStyleManager = CodeStyleManager.getInstance(project);

    }

    public void invoke() {

        //Get the type variable of the identifier
        final PsiLocalVariable variableToAssert = PsiTreeUtil.getParentOfType(element, PsiLocalVariable.class);
        if (variableToAssert == null) {
            return;
        }

        //Get type of the variable
        final PsiClass psiClass = PsiTypesUtil.getPsiClass(variableToAssert.getType());
        if (psiClass == null) {
            return;
        }

        final List<Getter> getters = collectGettersOfClass(psiClass);

        final PsiDeclarationStatement declaration = PsiTreeUtil.getParentOfType(
            element,
            PsiDeclarationStatement.class
        );

        for (final Getter getter : getters) {
            final PsiStatement expression = generateAssertionFromGetter(
                codeStyleManager,
                getter,
                variableToAssert
            );
            declaration.addAfter(expression, declaration);
        }

        addNecessaryImport(project, PsiTreeUtil.getParentOfType(element, PsiJavaFile.class));

    }

    private List<Getter> collectGettersOfClass(final PsiClass psiClass) {
        return Arrays.stream(psiClass.getMethods())
            .filter(m -> m.getName().startsWith(GET) || m.getName().startsWith(IS))
            .map(m -> new Getter(m.getName(), m.getReturnType().getPresentableText()))
            .collect(Collectors.toList());
    }

    private PsiStatement generateAssertionFromGetter(final CodeStyleManager styleManager, final Getter method,
                                                     final PsiLocalVariable localVariable) {
        final PsiStatement assertStatement = psiFactory.createStatementFromText(ASSERT_EQUALS_CODE, null);

        final PsiElement getterMethodCall = createGetterMethodCall(styleManager, method, localVariable);

        final PsiMethodCallExpression methodCall = PsiTreeUtil.getChildOfType(
            assertStatement,
            PsiMethodCallExpression.class
        );
        methodCall.getArgumentList()
            .getExpressions()[0].replace(psiFactory.createExpressionFromText(DefaultValueGenerator.getDefaultValueForClass(
            method.getReturnType()), null));
        methodCall.getArgumentList().getExpressions()[1].replace(getterMethodCall);

        return assertStatement;
    }

    private PsiElement createGetterMethodCall(final CodeStyleManager styleManager,
                                              final Getter method, final PsiLocalVariable localVariable) {
        final PsiExpression getterStatement = psiFactory.createExpressionFromText(
            localVariable.getName() + "." + method.getMethodName() + "()",
            null
        );
        return styleManager.reformat(getterStatement);
    }

    private void addNecessaryImport(final Project project,
                                    final PsiJavaFile javaFile) {

        final JavaPsiFacade psiFacade = JavaPsiFacade.getInstance(project);

        //import static org.junit.jupiter.api.Assertions.assertEquals
        final PsiClass assertionClass = psiFacade.findClass(
            jUnitContext.getAssertionsFqn(),
            GlobalSearchScope.allScope(project)
        );

        if (assertionClass == null) {
            return;
        }

        final PsiImportStaticStatement assertEqualsImport = psiFactory.createImportStaticStatement(
            assertionClass,
            "assertEquals"
        );

        javaFile.addAfter(assertEqualsImport, PsiTreeUtil.getChildOfType(javaFile, PsiPackageStatement.class));
    }


}
