package jbl.assertion.generator;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import jbl.assertion.generator.junit.JUnitContext;
import org.jetbrains.annotations.NotNull;

import static jbl.assertion.generator.junit.JUnitIdentifiers.JUNIT4_TEST_FQN;
import static jbl.assertion.generator.junit.JUnitIdentifiers.JUNIT5_TEST_FQN;

public abstract class BaseAssertionsGenerator extends PsiElementBaseIntentionAction implements IntentionAction {

    @Override
    public @IntentionFamilyName @NotNull String getFamilyName() {
        return "Assertions generation";
    }

    @Override
    public abstract @IntentionName @NotNull String getText();

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element)
        throws IncorrectOperationException {
        new GenerateAssertionsIntentionActionDelegate(project, editor, element, getJUnitContext()).invoke();
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        final PsiMethod method = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
        return element instanceof PsiIdentifier && method != null &&
            (method.hasAnnotation(JUNIT5_TEST_FQN) || method.hasAnnotation(JUNIT4_TEST_FQN));
    }

    public abstract JUnitContext getJUnitContext();

}
