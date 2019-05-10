package sample.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.io.internal.ByteArrayOutputStream;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import sample.model.DataContainer;

public class WordWorker {
    /**
     * Takes file path as input and returns the stream opened on it
     */
    private InputStream loadDocumentAsStream(String filePath) throws IOException {
        URL url = new File(filePath).toURI().toURL();
        InputStream documentTemplateAsStream = null;
        documentTemplateAsStream = url.openStream();
        return documentTemplateAsStream;
    }

    /**
     * Loads the docx report
     */
    private IXDocReport loadDocumentAsIDocxReport(InputStream documentTemplateAsStream,
                                                  TemplateEngineKind freemarkerOrVelocityTemplateKind)
                                                                throws IOException, XDocReportException {
        return XDocReportRegistry.getRegistry().loadReport(documentTemplateAsStream, freemarkerOrVelocityTemplateKind);
    }

    /**
     * Takes the IXDocReport instance, creates IContext instance out of it and puts variables in the context
     */
    private IContext replaceVariablesInTemplateOtherThanImages(IXDocReport report,
                                                               Map<String, Object> variablesToBeReplaced) throws XDocReportException {
        IContext context = report.createContext();
        for (Map.Entry<String, Object> variable : variablesToBeReplaced.entrySet()) {
            context.put(variable.getKey(), variable.getValue());
        }
        return context;
    }

    /**
     * Generates byte array as output from merged template
     */
    private byte[] generateMergedOutput(IXDocReport report, IContext context) throws XDocReportException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        report.process(context, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * Takes inputs and returns merged output as byte[]
     */
    private byte[] mergeAndGenerateOutput(String templatePath, TemplateEngineKind templateEngineKind,
                                         Map<String, Object> nonImageVariableMap) throws IOException, XDocReportException {
        InputStream inputStream = loadDocumentAsStream(templatePath);
        IXDocReport xdocReport = loadDocumentAsIDocxReport(inputStream, templateEngineKind);
        IContext context = replaceVariablesInTemplateOtherThanImages(xdocReport, nonImageVariableMap);
        byte[] mergedOutput = generateMergedOutput(xdocReport, context);
        return mergedOutput;
    }

    public static void parseWord(DataContainer dataContainer, File templateFile) throws IOException, XDocReportException {
        String templatePath = "D:\\study\\CourtDesicionMaker\\c.docx";
        for (int i = 1; i < dataContainer.getPersons().size(); i ++){
            Map<String, Object> nonImageVariableMap = new HashMap<String, Object>();
            for (int j = 0; j < dataContainer.getPersons().get(0).size(); j++) {
                nonImageVariableMap.put(dataContainer.getPersons().get(0).get(j),
                        dataContainer.getPersons().get(i).get(j));
            }
            WordWorker docxDocumentMergerAndConverter = new WordWorker();
            byte[] mergedOutput = docxDocumentMergerAndConverter.mergeAndGenerateOutput(templatePath, TemplateEngineKind.Freemarker, nonImageVariableMap);
            FileOutputStream os = new FileOutputStream("D:\\study\\CourtDesicionMaker\\"+System.nanoTime()+".docx");
            os.write(mergedOutput);
            os.flush();
            os.close();
        }
    }

//    public static void main(String[] args) throws Exception{
//        String templatePath = "D:\\study\\CourtDesicionMaker\\a.docx";
//
//        Map<String, Object> nonImageVariableMap = new HashMap<String, Object>();
//        nonImageVariableMap.put("name", "Rajani Jha");
//
//        WordWorker docxDocumentMergerAndConverter = new WordWorker();
//        byte[] mergedOutput = docxDocumentMergerAndConverter.mergeAndGenerateOutput(templatePath, TemplateEngineKind.Freemarker, nonImageVariableMap);
//        FileOutputStream os = new FileOutputStream("D:\\study\\CourtDesicionMaker\\"+System.nanoTime()+".docx");
//        os.write(mergedOutput);
//        os.flush();
//        os.close();
//    }
}
