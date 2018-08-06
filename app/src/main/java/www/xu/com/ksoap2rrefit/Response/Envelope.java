package www.xu.com.ksoap2rrefit.Response;

import android.provider.DocumentsContract;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


@Root(name = "soap:Envelope")
@NamespaceList({
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "soap")
})
public class Envelope {
    private static Envelope envelope;
    public static Envelope getEnvelope(){
        if(null==envelope){
            envelope=new Envelope();
        }
        return envelope;
    }

    public ResponseBody getBody() {
        return body;
    }

    public void setBody(ResponseBody body) {
        this.body = body;
    }

    @Element(name = "soap:Body")
    public ResponseBody body;

    @Root(name = "soap:Body")
    public static class ResponseBody<Data> {
        public ResponseBody(Data response) {
            this.response = response;
        }

        public Data response;
    }
}
