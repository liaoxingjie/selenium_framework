package com.springboot.demo.web;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.tools.ExecDumpClient;
import org.jacoco.core.tools.ExecFileLoader;
import org.jacoco.report.*;
import org.jacoco.report.html.HTMLFormatter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/jacoco"})
public class JacocoDumpAndReport {
    //dump
    private String address = "10.250.90.99";
    private int port = 8910;
    private boolean dump = true;
    private File destFile = new File("C:/Users/Administrator/Desktop/jacoco/jacoco.exec");
    private boolean append = true;
    //report
    private File sourceDirectory = new File("C:/Users/Administrator/IdeaProjects/demo/src/main/java/com/springboot/demo");
    private File classesDirectory = new File("C:/Users/Administrator/IdeaProjects/demo/target/classes/com/springboot/demo");
    private File reportDirectory = new File("C:/Users/Administrator/Desktop/jacoco");
    private String title = "demo";

    @RequestMapping(value={"/dump"}, method = RequestMethod.GET )
    public String executeDump() {
        ExecDumpClient cli = new ExecDumpClient();
        cli.setDump(this.dump);
        try {
            ExecFileLoader loader = cli.dump(this.address, this.port);
            loader.save(this.destFile, this.append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "dump done!!!";
    }

    @RequestMapping(value={"/report"}, method=RequestMethod.GET)
    public String executeReport() throws Exception {
        loadexec();
        IBundleCoverage bundleCoverage = analyzeStructure();
        createReport(bundleCoverage);
        return "report done!!!";
    }

    public ExecFileLoader loadexec() throws IOException {
        ExecFileLoader execFileLoader = new ExecFileLoader();
        execFileLoader.load(this.destFile);
        return execFileLoader;
    }

    private IBundleCoverage analyzeStructure() throws IOException {
        CoverageBuilder coverageBuilder = new CoverageBuilder();
        Analyzer analyzer = new Analyzer(loadexec().getExecutionDataStore(), coverageBuilder);
        analyzer.analyzeAll(this.classesDirectory);
        return coverageBuilder.getBundle(this.title);
    }

    public void createReport(IBundleCoverage bundleCoverage) throws IOException {
        HTMLFormatter htmlFormatter = new HTMLFormatter();
        IReportVisitor visitor = htmlFormatter.createVisitor(new FileMultiReportOutput(this.reportDirectory));
        visitor.visitInfo(loadexec().getSessionInfoStore().getInfos(), loadexec().getExecutionDataStore().getContents());
        visitor.visitBundle(bundleCoverage, new DirectorySourceFileLocator(this.sourceDirectory, "utf-8", 4));
        visitor.visitEnd();
    }

    @RequestMapping(value={"/r2"}, method=RequestMethod.GET)
    private void report2() throws IOException {
        loadexec();
        CoverageBuilder coverageBuilder = new CoverageBuilder();
        Analyzer analyzer;
        if (this.classesDirectory.isDirectory()) {
            analyzer = new Analyzer(loadexec().getExecutionDataStore(), coverageBuilder);
            analyzer.analyzeAll(this.classesDirectory);
        }
        IBundleCoverage bundle = coverageBuilder.getBundle(this.title);
        HTMLFormatter htmlFormatter = new HTMLFormatter();
        IReportVisitor visitor = htmlFormatter.createVisitor(new FileMultiReportOutput(this.reportDirectory));
        visitor.visitInfo(loadexec().getSessionInfoStore().getInfos(), loadexec().getExecutionDataStore().getContents());
        SourceFileCollection locator = new SourceFileCollection(this.sourceDirectory);
        Reader reader = locator.getSourceFile("","");
        visitor.visitBundle(bundle, locator);
        visitor.visitEnd();
    }


    @RequestMapping(value={"/dr"}, method=RequestMethod.GET)
    public String executeDumpAndReport() throws Exception{
        executeDump();
        executeReport();
        return "Dump And Report Done !!!!!!";
    }


    //增加内部类用于处理报告生成期间找不到源码文件的问题--依然没有解决
    private class SourceFileCollection implements ISourceFileLocator {
        private final List<File> sourceRoots;
        private final String encoding;
        private final File sourceDirctory;
        public SourceFileCollection(File sourceDirectory) {
            this.sourceDirctory = new File("C:/Users/Administrator/IdeaProjects/demo/src/main/java/com/springboot/demo");
            this.sourceRoots = JacocoDumpAndReport.getCompileSourceRoots(this.sourceDirctory);
            this.encoding = "utf-8";
        }
        public Reader getSourceFile(String packageName, String fileName) throws IOException {
            String r;
            if (packageName.length() > 0) {
                r = packageName + '/' + fileName;
            } else {
                r = fileName;
            }
            for (File sourceRoot : this.sourceRoots)
            {
                File file = new File(sourceRoot, r);
                if ((file.exists()) && (file.isFile())) {
                    return new InputStreamReader(new FileInputStream(file), this.encoding);
                }
            }
            return null;
        }

        public int getTabWidth() {
            return 4;
        }
    }
    private static List<File> getCompileSourceRoots(File sourceDirectory) {
        List<File> result = new ArrayList();

        for (Object path :sourceDirectory.list()) {
            result.add(resolvePath(sourceDirectory, (String)path));
        }
        return result;
    }

    private static File resolvePath(File sourceDirectory, String name) {
        File file = new File(name);
        if (!file.isAbsolute()) {
            file = new File(sourceDirectory.getAbsolutePath(), name);
        }
        return file;
    }

}