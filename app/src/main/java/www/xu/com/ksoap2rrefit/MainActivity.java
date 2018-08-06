package www.xu.com.ksoap2rrefit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.xu.com.ksoap2rrefit.bean.PositionRole;

public class MainActivity extends AppCompatActivity {
    String nameSpace = "http://post.extinterface.web.wisesign.cn/";
    //TODO  动态添加 参数等 element
    String stringEnvelope = "<v:Envelope     xmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "       <v:Body>\n" +
            "          <n0:queryPostModel xmlns:n0=\"" + nameSpace + "\">\n" +
            "              \n" +
            " </n0:queryPostModel>\n" +
            "       </v:Body>\n" +
            "    </v:Envelope>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.bt_request);
        final TextView textView = findViewById(R.id.tv_result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<String> stringCall = ApiService.Creator.get().getJson(stringEnvelope);
                stringCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String result = response.body();
                        textView.setText(result);
                        deXmlToObject(result);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });


            }
        });
    }

    /**
     * @param xmlStr 将返回来的string形式的xml 解析成想要的对象数据形式
     */

    public void deXmlToObject(String xmlStr) {
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element rootElement = document.getRootElement();


            String returnResult = new String();
            for (Iterator<Element> it = rootElement.elementIterator(); it.hasNext(); ) {
                Element el = it.next();
                Log.e("treeWalk", el.getName() + "---------" + el.getStringValue());
                returnResult = el.getStringValue();
            }
            Gson gson = new Gson();
            List<PositionRole.Pos> data = gson.fromJson(returnResult.toString(), new TypeToken<List<PositionRole.Pos>>() {//TODO 返回对象的泛型 PositionRole.Pos
            }.getType());
            for (PositionRole.Pos pos : data) {
                Log.e(pos.getPostId(), pos.getPostName());
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

}
