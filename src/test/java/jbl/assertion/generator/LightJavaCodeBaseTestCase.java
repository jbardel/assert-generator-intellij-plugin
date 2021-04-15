package jbl.assertion.generator;

import java.io.File;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;

public abstract class LightJavaCodeBaseTestCase extends LightJavaCodeInsightFixtureTestCase {

    private static final String JUNIT5_LIB_NAME = "junit-jupiter-api-5.7.1.jar";
    private static final String JUNIT4_LIB_NAME = "junit-4.13.jar";

    public static void loadJUnit4Library(@NotNull Disposable projectDisposable, @NotNull Module module) {
        loadLibrary(projectDisposable, module, JUNIT4_LIB_NAME, JUNIT4_LIB_NAME);
    }

    public static void loadJUnit5Library(@NotNull Disposable projectDisposable, @NotNull Module module) {
        loadLibrary(projectDisposable, module, JUNIT5_LIB_NAME, JUNIT5_LIB_NAME);
    }

    public static void loadLibrary(@NotNull Disposable projectDisposable, @NotNull Module module, String libraryName,
                                   String libraryJarName) {
        final String libPath = PathUtil.toSystemDependentName(new File("src/test/resources/libs").getAbsolutePath());
        VfsRootAccess.allowRootAccess(projectDisposable, libPath);
        PsiTestUtil.addLibrary(projectDisposable, module, libraryName, libPath, libraryJarName);
    }


}
