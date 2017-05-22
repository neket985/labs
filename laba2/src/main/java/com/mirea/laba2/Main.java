package com.mirea.laba2;

import org.asynchttpclient.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nikitos on 20.05.17.
 */
public class Main {
    private static String reg = "<a[ ]{0,1}href=[\"]{0,1}https?:\\/\\/[^\"]{0,}[\"]{0,1}>";
    private static String reg2 = "<a[ ]{0,1}href=[\"]{0,1}";

    public static void main(String[] args) {

        try {
            AsyncHttpClient client = new DefaultAsyncHttpClient();
            Response resp = client.prepareGet("http://stackoverflow.com/questions/886722/how-to-use-wait-and-notify-in-java").execute().get();
            client.close();
            List<String> list = check(resp.getResponseBody());
            final List<String> listOfIncludedSites = new ArrayList<String>();
            List<ListenableFuture> futures = new ArrayList<ListenableFuture>();
            for (String i : list.subList(0, list.size() < 100 ? list.size() : 100)) {
                final AsyncHttpClient dopClient = new DefaultAsyncHttpClient();
                System.out.println("Request sended to " + i + ".");
                futures.add(dopClient.prepareGet(i).execute(new AsyncCompletionHandler() {
                    public Object onCompleted(Response response) throws Exception {
                        String body = response.getResponseBody();
                        listOfIncludedSites.addAll(check(body));
                        System.out.println("Response from " + response.getUri() + " was got and processed.");
                        dopClient.close();
                        return 0;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        System.out.println("Url is incorrect");
                        try {
                            dopClient.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
            }
            for (ListenableFuture future : futures) {
                while (!future.isDone()) {
                    Thread.sleep(1000);
                }
            }
            list.addAll(listOfIncludedSites);
            System.out.println("\nСписок полученных ссылок:");
            for (String i : list) {
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> check(String str) {
        List list = new ArrayList();
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        while (m.find()) {
            String url = str.substring(m.start(), m.end()).replaceAll(reg2, "").replaceAll("[\"]{0,1}>", "");
            list.add(url);
        }
        return list;
    }
}
