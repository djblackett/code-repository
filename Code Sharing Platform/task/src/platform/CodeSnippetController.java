package platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.Gson;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CodeSnippetController {

    Map<String, CodeSnippet> snippetTimers = new HashMap<>();
    private final CodeSnippetService codeSnippetService;

    @Autowired
    public CodeSnippetController(CodeSnippetService codeSnippetService) {
        this.codeSnippetService = codeSnippetService;
    }


    @GetMapping("/code/{id}")
    public String getCodeHTML(@PathVariable String id, HttpServletResponse response) throws IOException, TemplateException {
        response.addHeader("Content-Type", "text/html");
        Optional<CodeSnippet> codeSnippet = codeSnippetService.snippetRepository.getCodeSnippetByUuid(id);
        if (codeSnippet.isPresent()) {

            CodeSnippet snippet = codeSnippet.get();

            if (snippet.isTimePresent()) {
                long duration = ChronoUnit.SECONDS.between(snippet.getDate(), LocalDateTime.now());
                long timeLeft = snippet.getTime() - duration;

                if (timeLeft > 0) {
                    snippet.setTime(timeLeft);
                    codeSnippetService.updateTime(timeLeft, snippet.getUuid());
                } else {
                    codeSnippetService.deleteSnippet(snippet.getUuid());
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }

            }
            // check and decrement views if exists
            if (snippet.isViewPresent()) {

                long decrementedViews = snippet.getViews() - 1;
                if (snippet.getViews() == 0) {
                    codeSnippetService.deleteSnippet(snippet.getUuid());
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);

                } else {

                    snippet.setViews(decrementedViews);
                    codeSnippetService.updateViewsColumn(decrementedViews, snippet.getUuid());
                }
            }


            Template temp = CodeSharingPlatform.cfg.getTemplate("template-get.ftl");
            Writer out = new StringWriter();
            SnippetDataModel dataModel = new SnippetDataModel(snippet);
            temp.process(dataModel, out);
            return out.toString();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        }



    @GetMapping("/api/code/{id}")
    public CodeSnippet getCodeJSON(@PathVariable String id) {

        Optional<CodeSnippet> codeSnippet = codeSnippetService.getCodeSnippetByUuid(id);

        if (codeSnippet.isPresent()) {
            CodeSnippet snippet = codeSnippet.get();


            // logic for when timer exists
            // check and set timer if required

            if (snippet.isTimePresent()) {

//                    long elapsedTime = System.currentTimeMillis() - snippet.getOriginalTimerStart();
//                    long timeLeft = snippet.getOriginalTime() - (elapsedTime / 1000);
                // Duration duration = Duration.between(snippet.getDate(), LocalDateTime.now());
                //long timeLeft = snippet.getOriginalTime() - duration.get(ChronoUnit.SECONDS);
                //long timeLeftSeconds = (long) Math.floor(timeLeft / 1000.0);

                long duration = ChronoUnit.SECONDS.between(snippet.getDate(), LocalDateTime.now());
                long timeLeft = snippet.getTime() - duration;

                if (timeLeft > 0) {
                    snippet.setTime(timeLeft);
                    codeSnippetService.updateTime(timeLeft, snippet.getUuid());
                } else {
                    codeSnippetService.deleteSnippet(snippet.getUuid());
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
            }



                // check and decrement views if exists
                if (snippet.isViewPresent()) {

                    long decrementedViews = snippet.getViews() - 1;
                    if (snippet.getViews() > 0) {
                        snippet.setViews(decrementedViews);
                        codeSnippetService.updateViewsColumn(decrementedViews, snippet.getUuid());


                    } else {
                        codeSnippetService.deleteSnippet(snippet.getUuid());
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

                    }
                }


            System.out.println(snippet);
            System.out.println("Snippet accessed at " + LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS) + " Snippet.time: " + snippet.getTime() + "\nViews left: " + snippet.getViews());
            return snippet;
        } else {
            //notFound.put("code", "");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private JSONObject convertSnippetToJsonObject(CodeSnippet snippet) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", snippet.getCode());
        jsonObject.put("date", snippet.getDate());
        jsonObject.put("time", snippet.getTime());
        jsonObject.put("views", snippet.getViews());
        return jsonObject;
    }

    //todo

    @GetMapping("/api/code/latest")
    public JSONArray getLatestCode() {
        List<CodeSnippet> codeSnippets = codeSnippetService.getLatestCodeSnippets();
        Collections.reverse(codeSnippets);
        JSONArray jsonArray = new JSONArray();
        for (int i = codeSnippets.size() - 1; i > codeSnippets.size() - 11; i--) {
            if (i < 0) {
                break;
            }
            jsonArray.add(convertSnippetToJsonObject(codeSnippets.get(i)));
        }
        return jsonArray;
    }

    @GetMapping("/code/new")
    public String getSubmissionForm() throws FileNotFoundException {
       System.out.println("web form sent");
//        return "<html>" +
//                "<head>" +
//                "    <title>Create</title>" +
//                "   <script>function send() {" +
//                "    let object = {" +
//                "        \"code\": document.getElementById(\"code_snippet\").value," +
//                "        \"time\": document.getElementById(\"time_restriction\").value," +
//                "        \"views\": document.getElementById(\"views_restriction\").value" +
//                "    };" +
//                "    " +
//                "    let json = JSON.stringify(object);" +
//                "    " +
//                "    let xhr = new XMLHttpRequest();" +
//                "    xhr.open(\"POST\", '/api/code/new', false);" +
//                "    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');" +
//                "    xhr.send(json);" +
//                "    " +
//                "    if (xhr.status == 200) {" +
//                "      alert(\"Success!\");" +
//                "    }" +
//                "}</script>" +
//                "</head>" +
//                "<body>" +
//                "<input id=\"time_restriction\" type=\"text\"/ value=\"0\">" +
//                "<br>" +
//                "<input id=\"views_restriction\" type=\"text\" value=\"0\"/>" +
//                "<br>" +
//                "<textarea id=\"code_snippet\"> Enter Code Snippet </textarea>" +
//                "<br>" +
//                "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>" +
//                "</body>" +
//                "</html>";
        File file = new File("Code Sharing Platform/task/src/resources/static/new-code-snippet.html");
        FileReader in = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(in);
        String html = bufferedReader.lines().collect(Collectors.joining());

        return html;
    }

//    @GetMapping("/code/new")
//    public ModelAndView getSubmissionForm() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("new-code-snippet.html");
//        return modelAndView;
//    }

    @GetMapping("code/latest")
    public String getLatestCodeHtml() throws IOException, TemplateException {
        Map<String, CodeSnippet> root = new HashMap<>();
        Template temp = CodeSharingPlatform.cfg.getTemplate("template.ftl");
        Writer out = new StringWriter();
        List<CodeSnippet> latestSnippets = codeSnippetService.getLatestCodeSnippets();

        DataModel dataModel = new DataModel(latestSnippets);
        temp.process(dataModel, out);
        return out.toString();
    }


    @PostMapping("/api/code/new")
    public ResponseEntity<String> postCode(@RequestBody String body) throws ParseException, InterruptedException {
        System.out.println(body);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
//
        Object obj = new JSONParser().parse(body);
        JSONObject jsonObject = (JSONObject) obj;
        String code = (String) jsonObject.get("code");
        long time = Long.parseLong(String.valueOf(jsonObject.get("time")));
        long views = Long.parseLong(String.valueOf(jsonObject.get("views")));
        CodeSnippet snippet = new CodeSnippet(code, date, time, views);

        if (time > 0) {
            snippet.setTimePresent(true);
        }

        if (views > 0) {
            snippet.setViewPresent(true);
            //snippet.setViews(views - 1);
        }


        if (snippet.getTime() > 0) {
            snippet.setOriginalTimerStart(System.currentTimeMillis());
            snippet.setOriginalTime(snippet.getTime());

            // Set timer and timertask to delete the snippet after the given time
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    codeSnippetService.deleteSnippet(snippet.getUuid());
                    snippetTimers.remove(snippet.getUuid());
                }
            }, snippet.getTime() * 1000);
            snippetTimers.put(snippet.getUuid(), snippet);
        }

        codeSnippetService.newSnippet(snippet);



        Map<String, String> snippetId = new HashMap<>();
        snippetId.put("id", String.valueOf(snippet.getUuid()));
        return new ResponseEntity<>(snippetId.toString(), HttpStatus.OK);
    }


    @GetMapping("/error")
    String getErrorMessage() {
        return "<h1>No code snippet(s) found</h1>";
    }
}


/*
Does shutting down the program
 */