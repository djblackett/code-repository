package platform;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class CodeSharingPlatform {

    public static Configuration cfg;


    public static void main(String[] args) throws IOException {

        // FreeMarker tools setup
        cfg = new Configuration(Configuration.VERSION_2_3_29);
        File tempDir = new File("\\dev\\Java\\Code Sharing Platform\\Code Sharing Platform\\task\\src\\resources\\templates");


        cfg.setDirectoryForTemplateLoading(tempDir);
        cfg.setDefaultEncoding("UTF-8");
//
//      During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//
//      Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        cfg.setLogTemplateExceptions(false);
//
//      Wrap unchecked exceptions thrown during template processing into TemplateException-s:
        cfg.setWrapUncheckedExceptions(true);
//
//      Do not fall back to higher scopes when reading a null loop variable:
        cfg.setFallbackOnNullLoopVariable(false);



        SpringApplication.run(CodeSharingPlatform.class, args);
    }
}


