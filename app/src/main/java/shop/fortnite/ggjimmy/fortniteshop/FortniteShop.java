package shop.fortnite.ggjimmy.fortniteshop;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class FortniteShop extends AppCompatActivity {

    public ArrayList<String> urls;
    public ArrayList<String> itemNames;
    public Document document;
    public String URL = "https://fnbr.co/shop";
    public ListView listOfItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortnite_shop);

        urls = new ArrayList<>();
        itemNames = new ArrayList<>();
        listOfItems = (ListView) findViewById(R.id.items);
        JsoupAsyncTask asyncTask = new JsoupAsyncTask();
        asyncTask.execute();

    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, ArrayList<String>>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            try{
                document = Jsoup.connect(URL).get();
                Elements elements = document.getAllElements();

                for(Element el : elements){
                    if(el.attr("class").equals("col-md-3")){
                        Elements item = el.getAllElements();
                        for(Element index : item){
                            String[] s = index.attr("src").split("/");

                            if(s[s.length-1].equals("icon.png")){
                                urls.add(index.attr("src"));
                            }else if(index.attr("class").contains("card splash-card")){
                                itemNames.add(index.text());
                            }
                            if(index.attr("class").equals("card splash-card rarity-legendary")){

                            }
                        }
                    }
                }

            } catch(IOException e){
                e.printStackTrace();
            }
            return urls;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result){
            super.onPostExecute(result);
            listOfItems.setAdapter(new ItemList(FortniteShop.this,result,itemNames));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
