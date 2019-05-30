package com.springboot.demo.web;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.tools.ExecFileLoader;
import org.jacoco.report.*;
import org.jacoco.report.html.HTMLFormatter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReportGenerator {
    private String title = "demo";
    private File executionDataFile = new File("C:/Users/Administrator/Desktop/jacoco/jacoco.exec");
    private File reportDirectory = new File("C:/Users/Administrator/Desktop/jacoco123");
    private CoverageSourceData sourceData = new CoverageSourceData();
    private ExecFileLoader execFileLoader;
    String pathStr = "C:/Users/Administrator/IdeaProjects/demo/target/classes/com/springboot/demo";
    String pathStr2 = "C:/Users/Administrator/IdeaProjects/demo/src/main/java/com/springboot/demo";
    Set<File> fileSet = new HashSet<>();
    List<File> fileList = new ArrayList<>();

    public ReportGenerator() {
    }

    public void create() throws IOException {
        getFileSet(pathStr);
        getFileList(pathStr2);
        sourceData.setClassesDirectory(fileSet);
        sourceData.setSourceDirectories(fileList);
        loadExecutionData();
        final IBundleCoverage bundleCoverage = analyzeStructure();
        createReport(bundleCoverage);
    }
    private void createReport(final IBundleCoverage bundleCoverage) throws IOException {
        final HTMLFormatter htmlFormatter = new HTMLFormatter();
        final IReportVisitor visitor = htmlFormatter.createVisitor(new FileMultiReportOutput(reportDirectory));
        visitor.visitInfo(execFileLoader.getSessionInfoStore().getInfos(),
                execFileLoader.getExecutionDataStore().getContents());
        MultiSourceFileLocator locator = new MultiSourceFileLocator(4);
        for (File sourceDirectory : sourceData.getSourceDirectories()) {
            locator.add(new DirectorySourceFileLocator(sourceDirectory, "utf-8", 4));
        }
        visitor.visitBundle(bundleCoverage, locator);
        visitor.visitEnd();
    }
    private void loadExecutionData() throws IOException {
        execFileLoader = new ExecFileLoader();
        execFileLoader.load(executionDataFile);
    }
    private IBundleCoverage analyzeStructure() throws IOException {
        final CoverageBuilder coverageBuilder = new CoverageBuilder();
        final Analyzer analyzer = new Analyzer(
                execFileLoader.getExecutionDataStore(), coverageBuilder);

        for (File file : sourceData.getClassesDirectory()) {
            analyzer.analyzeAll(file);
        }
        return coverageBuilder.getBundle(title);
    }

    private void getFileSet(String path) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    System.out.println("目录：" + files[i].getPath());
                    getFileSet(files[i].getPath());
                } else {
                    System.out.println("文件：" + files[i].getPath());
                    fileSet.add(files[i]);
                }

            }
        } else {
            System.out.println("文件：" + file.getPath());
        }
    }

    private void getFileList(String path) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    System.out.println("目录：" + files[i].getPath());
                    getFileList(files[i].getPath());
                } else {
                    System.out.println("文件：" + files[i].getPath());
                    fileList.add(files[i]);
                }
            }
        } else {
            System.out.println("文件：" + file.getPath());
        }
    }

}
