package platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class CodeSharingPlatform {

    public static Configuration cfg;


    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        // FreeMarker tools setup
        cfg = new Configuration(Configuration.VERSION_2_3_29);
        File tempDir = new File("\\dev\\Java\\Code Sharing Platform\\Code Sharing Platform\\task\\src\\resources\\templates");
        //cfg.setClassForTemplateLoading(CodeSharingPlatform.class, "/");

        //FileTemplateLoader templateLoader = new FileTemplateLoader(new File("resources"));
        //cfg.setTemplateLoader(templateLoader);

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


        //Thread.sleep(2000);
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

//    @Bean
//    public CommandLineRunner runApplication(SnippetRepository snippetRepository) {
//        return (args -> {
//            // call methods you want to use
//        });
//    }
}


/*
I sort of butchered this. Needs to be redone now that I understand how to actually use JSON in Java.
This will simplify quite a few things. I should use a map or list to hold secret objects,
that way multiple can be done at the same time. Only the controller and snippet classes need to be changed
 */