package Tests;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Timer;

public class ThreadedPageLoadTest extends Application {
    private static final String YOUTUBE_URL = "https://youtube.com";
    private WebView webView;
    private WebEngine engine;
    static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        webView = new WebView();
        stage.setScene(new Scene(webView));
        multiThreadLoad();
        stage.show();

    }
    private void singleThreadLoad() {
        var startTime = System.currentTimeMillis();

        engine = webView.getEngine();
        engine.load(YOUTUBE_URL);
        var endTime = System.currentTimeMillis();
        System.out.println("took: " + (endTime-startTime) + "ms");
    }

    //speeds up significantly but does remove css and javascript
    private void multiThreadLoad() {

        engine = webView.getEngine();
        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                URLConnection conn = new URL(YOUTUBE_URL).openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

                StringBuilder html = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    html.append(line).append("\n");

                }
                reader.close();
                String processedHtml = heavyProcessing(html.toString());
                return processedHtml;
            }


        };
        task.setOnSucceeded( event ->  {
            var startTime = System.currentTimeMillis();
            engine.loadContent(task.getValue());
            var endTime = System.currentTimeMillis();
            System.out.println("took: " + (endTime-startTime) + "ms");
        });
        task.setOnFailed(event -> {
            System.out.println("failed to load page");
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }
    private String heavyProcessing(String html) {

        return html.replaceAll("(?s)<script.*?>.*?</script>", "")
                .replaceAll("(?s)<style.*?>.*?</style>", "");
    }
}
