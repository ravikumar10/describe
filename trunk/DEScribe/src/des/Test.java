/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package des;
import java.util.List;
import com.google.gson.Gson;

public class Test {
	private int data1;
        private String type; //fullscreen, copyText...
	//private String data2;

	public static void main(String[] args) {
	  //json data
	  String json = "{'data1':100,'data2':'hello'}";
	  Gson gson = new Gson();

	  //convert JSON into java object
	  Test obj = gson.fromJson(json, Test.class);
	  System.out.println(obj);
	}

	@Override
	public String toString() {
		return "Test [data1=" + data1 +/* ", data2=" + data2 +*/ "]";
	}

    }
