package com.springboot.demo.web;

import java.io.File;
import java.io.IOException;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.tools.ExecDumpClient;
import org.jacoco.core.tools.ExecFileLoader;
import org.jacoco.report.DirectorySourceFileLocator;
import org.jacoco.report.FileMultiReportOutput;
import org.jacoco.report.IReportVisitor;
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
    private ExecFileLoader execFileLoader = new ExecFileLoader();

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

    public void loadexec() throws IOException {
        this.execFileLoader.load(this.destFile);
    }

    private IBundleCoverage analyzeStructure() throws IOException {
        CoverageBuilder coverageBuilder = new CoverageBuilder();
        Analyzer analyzer = new Analyzer(this.execFileLoader.getExecutionDataStore(), coverageBuilder);
        analyzer.analyzeAll(this.classesDirectory);
        return coverageBuilder.getBundle(this.title);
    }

    public void createReport(IBundleCoverage bundleCoverage) throws IOException {
        HTMLFormatter htmlFormatter = new HTMLFormatter();
        IReportVisitor visitor = htmlFormatter.createVisitor(new FileMultiReportOutput(this.reportDirectory));
        visitor.visitInfo(this.execFileLoader.getSessionInfoStore().getInfos(), this.execFileLoader
                .getExecutionDataStore().getContents());
        visitor.visitBundle(bundleCoverage, new DirectorySourceFileLocator(this.sourceDirectory, "utf-8", 4));
        visitor.visitEnd();
    }

    @RequestMapping(value={"/dr"}, method=RequestMethod.GET)
    public String executeDumpAndReport() throws Exception{
        executeDump();
        executeReport();
        return "Dump And Report Done !!!!!!";
    }

}