package com.odbpo.flutter.util;

import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import com.intellij.util.ThrowableRunnable;
import com.odbpo.flutter.common.Constants;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * createDate: 2022/11/16 on 16:22
 * desc:
 *
 * @author azhon
 */


public class FileUtil {

    /**
     * 获取目录下的所有文件
     */
    public static Collection<VirtualFile> getDirFile(Project project, String dir) {
        VirtualFile libFile = ProjectUtil.guessProjectDir(project).findChild(dir);
        if (libFile == null || !libFile.exists()) {
            NotificationUtil.showNotify("Error: [" + dir + "] directory not found!");
            return null;
        }
        Collection<VirtualFile> files = new ArrayList<>();
        recursiveGetAllFiles(files, libFile);
        return files;
    }

    /**
     * 递归获取文件
     */
    private static void recursiveGetAllFiles(Collection<VirtualFile> list, VirtualFile file) {
        VirtualFile[] children = file.getChildren();
        for (VirtualFile child : children) {
            if (child.isDirectory()) {
                recursiveGetAllFiles(list, child);
            } else {
                if (!child.getName().startsWith(".")) {
                    list.add(child);
                }
            }
        }
    }

    /**
     * 创建 lib/generated/ 文件夹
     */
    public static VirtualFile createDir(Project project, String dir) {
        VirtualFile libFile = ProjectUtil.guessProjectDir(project).findChild(Constants.LIB_DIR);
        if (libFile == null) return null;
        try {
            WriteAction.run((ThrowableRunnable<Throwable>) () -> {
                VirtualFile generated = libFile.findChild(Constants.GENERATED_DIR);
                if (generated == null) {
                    generated = libFile.createChildDirectory(project, Constants.GENERATED_DIR);
                }
                VirtualFile bridge = generated.findChild(dir);
                if (bridge == null) {
                    generated.createChildDirectory(project, dir);
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return libFile.findFileByRelativePath(Constants.GENERATED_DIR + File.separator + dir);
    }

    /**
     * 获取模块名称
     */
    public static String getModuleName(Project project) {
        return parserYaml(project).get("name").toString();
    }

    /**
     * 如果yaml没有配置，则使用moduleName
     */
    public static String getGeneratePrefix(Project project, String moduleName) {
        Object prefix = FileUtil.getYamlSetting(project, "prefix");
        return (prefix == null || prefix.toString().isEmpty()) ? moduleName : prefix.toString();
    }

    /**
     * 如果yaml没有配置，默认为true
     */
    public static Boolean isModule(Project project) {
        Object isModule = FileUtil.getYamlSetting(project, "isModule");
        return isModule == null || (Boolean) isModule;
    }

    /**
     * 获取配置的前缀
     */
    private static Object getYamlSetting(Project project, String key) {
        Map<String, Object> map = parserYaml(project);
        if (map == null) return null;
        Object xieYin = map.get("xie_yin");
        if (xieYin == null) return null;
        return ((Map<String, Object>) xieYin).get(key);
    }

    /**
     * 解析yaml文件
     */
    public static Map<String, Object> parserYaml(Project project) {
        try {
            Yaml yaml = new Yaml();
            FileInputStream inputStream = new FileInputStream(project.getBasePath() + File.separator + Constants.PUBSPEC_FILE);
            return yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建辅助类
     */
    public static void writeFile(Project project, VirtualFile dirFile, String fileName, String code) {
        PsiDirectory psiDirectory = PsiManager.getInstance(project).findDirectory(dirFile);
        if (psiDirectory == null) return;
        PsiFileFactory factory = PsiFileFactory.getInstance(project);
        PsiFile psiFile = psiDirectory.findFile(fileName);
        if (psiFile != null) {
            try {
                OutputStream out = psiFile.getVirtualFile().getOutputStream(dirFile);
                out.write(code.getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            psiDirectory.add(factory.createFileFromText(fileName, PlainTextFileType.INSTANCE, code));
        }
    }
}
