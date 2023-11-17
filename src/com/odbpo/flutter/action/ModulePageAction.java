package com.odbpo.flutter.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ThrowableRunnable;
import com.odbpo.flutter.bean.ModulePageBean;
import com.odbpo.flutter.util.FileUtil;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * createDate: 2023/9/16 on 13:09
 * desc:
 *
 * @author azhon
 */

public class ModulePageAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        String inputName = Messages.showInputDialog("Name", "New Module Page File", new ImageIcon());
        if (inputName == null || inputName.isEmpty()) return;
        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        ModulePageBean blocBean = new ModulePageBean(inputName);
        String moduleCode = generateModuleCode(blocBean);
        String routeCode = generateRouteCode(blocBean);
        String pageCode = generatePageCode(blocBean);
        String apiCode = generateApiCode(blocBean);

        try {
            WriteAction.run((ThrowableRunnable<Throwable>) () -> {
                FileUtil.writeFile(project, file, blocBean.getModuleFileName(), moduleCode);
                FileUtil.writeFile(project, file, blocBean.getRouteFileName(), routeCode);
                FileUtil.writeFile(project, file, blocBean.getPageFileName(), pageCode);
                FileUtil.writeFile(project, file, blocBean.getApiFileName(), apiCode);
            });
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }

        if (file != null) {
            file.refresh(true, true);
        }
    }

    private String generateModuleCode(ModulePageBean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append("import 'package:flutter_thrio/flutter_thrio.dart';").append("\n\n")
                //import page
                .append("import '").append(bean.getPageFileName()).append("';").append("\n\n")
                //class name
                .append("class ").append(bean.getModuleClsName()).append(" with ThrioModule, ModuleParamScheme, ModulePageBuilder {").append("\n")
                //override
                .append("  @override").append("\n")
                //key
                .append("  String get key => '").append(bean.getInputName()).append("';").append("\n\n")
                //override
                .append("  @override").append("\n")
                .append("  void onPageBuilderSetting(final ModuleContext moduleContext) =>").append("\n")
                .append("    pageBuilder = (final settings) => ").append(bean.getPageClsName()).append("(").append("\n")
                .append("        moduleContext: moduleContext, settings: settings);").append("\n\n")
                .append("}").append("\n");
        return sb.toString();
    }

    private String generateRouteCode(ModulePageBean bean) {
        StringBuilder sb = new StringBuilder();
        //import
        sb.append("import 'package:flutter_thrio/flutter_thrio.dart';").append("\n")
                //import page
                .append("import '").append(bean.getPageFileName()).append("';").append("\n\n")
                //class name
                .append("class ").append(bean.getRouteClsName()).append(" extends NavigatorRouteLeaf {").append("\n")
                .append("  factory ").append(bean.getRouteClsName()).append("(final NavigatorRouteNode parent) =>").append("\n")
                .append("      _instance ??= ").append(bean.getRouteClsName()).append("._(parent);").append("\n\n")
                .append("  ").append(bean.getRouteClsName()).append("._(super.parent);").append("\n\n")
                .append("  static ").append(bean.getRouteClsName()).append("? _instance;").append("\n\n")
                //override
                .append("  @override").append("\n")
                .append("  String get name => '").append(bean.getInputName()).append("';").append("\n\n")
                .append("}").append("\n");
        return sb.toString();
    }

    private String generatePageCode(ModulePageBean bean) {
        StringBuilder sb = new StringBuilder();
        //import
        sb.append("import 'package:flutter_thrio/flutter_thrio.dart';").append("\n")
                .append("import 'package:flutter/cupertino.dart';").append("\n")
                .append("import 'package:flutter/material.dart';").append("\n")
                .append("import 'package:请自行在此输入项目名称/components/common_page.dart';").append("\n\n")
                //class name
                .append("class ").append(bean.getPageClsName()).append(" extends NavigatorStatefulPage {").append("\n")
                .append("  const ").append(bean.getPageClsName()).append("(").append("\n")
                .append("      {super.key, required super.moduleContext, required super.settings});").append("\n\n")
                .append("  @override").append("\n  ")
                .append(bean.getPageClsName()).append("State createState() => ").append(bean.getPageClsName()).append("State();").append("\n")
                .append("}").append("\n\n")

                .append("class ").append(bean.getPageClsName()).append("State extends CommonPage<").append(bean.getPageClsName()).append(">\n")
                .append("with NavigatorPageLifecycleMixin {\n")
                .append("  @override").append("\n")
                .append("  Widget baseBuild(BuildContext context) => const SizedBox(child: CommonText('把这里的CommonText替换为页面代码'));").append("\n\n")

                .append("  @override").append("\n")
                .append("  void initState() {").append("\n").append("    super.initState();\n").append("  }\n\n")

                .append("  @override").append("\n").append("  void didAppear(final RouteSettings settings) {").append("\n")
                .append("    super.didAppear(settings);\n").append("    print('$this,Appear');\n").append("  }\n\n")

                .append("  @override").append("\n").append("  String? get pageName => '请输入页面名称';\n\n")

                .append("  @override").append("\n").append("  void didDisappear(final RouteSettings settings) {").append("\n")
                .append("    super.didDisappear(settings);\n").append("    print('$this,Disappear');\n").append("  }\n\n")

                .append("}").append("\n");
        return sb.toString();
    }

    private String generateApiCode(ModulePageBean bean) {
        //import
        StringBuilder sb = new StringBuilder();
        sb.append("import 'package:todo_flutter/todo_flutter.dart';").append("\n\n")
                .append("import 'package:请自行在此输入项目名称/service/network/api_provider.dart';")
                //class name
                .append("class ").append(bean.getApiClsName()).append("{").append("\n")
                .append("//在此填写api url 示例：\n").append("  static const String dataDic = '/e-system/dict/data/type/';").append("\n")
                .append("}").append("\n\n")

                .append("class ExampleRequest extends ApiRequest<Map<String, dynamic>> {\n")
                .append("  ExampleRequest(Map<String, dynamic>? params) : super(params);\n\n")
                .append("  @override\n")
                .append("  RequestMethod get method => RequestMethod.get;\n\n")
                .append("  @override\n")
                .append("  String get url => ChangePhoneApi.dataDic;\n")
                .append("}").append("\n");
        return sb.toString();
    }
}
