package www.xu.com.ksoap2rrefit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.xu.com.ksoap2rrefit.Response.Envelope;
import www.xu.com.ksoap2rrefit.bean.PositionRole;

public class MainActivity extends AppCompatActivity {
    String nameSpace = "http://post.extinterface.web.wisesign.cn/";
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

                        PositionRole role = new PositionRole();
                        role.setNameSpace(nameSpace);
                        Envelope.getEnvelope().setBody(new Envelope.ResponseBody<>(role));
                        main(result);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });


            }
        });
    }

    /**
     * @param args
     */
    public static void main(String args) {

        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(args);
            Node node=document.selectSingleNode("");

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

}
