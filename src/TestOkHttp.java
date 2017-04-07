import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;
import tools.OKHttpUtil;

public class TestOkHttp {
	
	public static void main(String[] args) {
//		OKHttpUtil.getRequest("http://img7.doubanio.com//view//movie_poster_cover//spst//public//p2453176400.webp", null, new Callback() {
//
//			@Override
//			public void onFailure(Call arg0, IOException arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onResponse(Call arg0, Response response) throws IOException {
//				// TODO Auto-generated method stub
//				
//				String path = "/home/chk";
//				InputStream is = null;
//                byte[] buf = new byte[2048];
//                int len = 0;
//                FileOutputStream fos = null;
//                try {
//                	String content_Type = response.header("Content-Type");
//                	String type = content_Type.substring(content_Type.lastIndexOf('/')+1);
//                	System.out.println(type);
//                    is = response.body().byteStream();
//                    long total = response.body().contentLength();
//                   
//                    File file = new File(path, "test.webp");
//                    fos = new FileOutputStream(file);
//                    long sum = 0;
//                    while ((len = is.read(buf)) != -1) {
//                        fos.write(buf, 0, len);
//                        sum += len;
//                    }
//                    fos.flush();
//                    System.out.println("文件下载成功！");
//                } catch (Exception e) {
//                	System.out.println("文件下载失败！");
//                } finally {
//                    try {
//                        if (is != null)
//                            is.close();
//                    } catch (IOException e) {
//                    }
//                    try {
//                        if (fos != null)
//                            fos.close();
//                    } catch (IOException e) {
//                    }
//                }
//			}
//			
//		});
		parseComingSoon();
	}
	
	public static void parseComingSoon() {
		JsonParser parser = new JsonParser();
		JsonObject object = null;
		try {
			object = (JsonObject) parser.parse(new FileReader("/home/chk/coming_soon.json"));
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonArray array = object.get("subjects").getAsJsonArray();
		for(int i=0; i<array.size(); i++) {
			
			JsonObject subjects = array.get(i).getAsJsonObject();
			JsonArray genres = subjects.get("genres").getAsJsonArray();
			JsonObject images = subjects.get("images").getAsJsonObject();
			JsonObject rating = subjects.get("rating").getAsJsonObject();
			String id = subjects.get("id").getAsString();
			String title = subjects.get("title").getAsString();
			String collect_count = subjects.get("collect_count").getAsString();
			String original_title = subjects.get("original_title").getAsString();
			String year = subjects.get("year").getAsString();
						
			System.out.println(title);
			System.out.println(rating.get("average").getAsString());
			System.out.println(year);
			System.out.println(collect_count+"");
			for(int j=0; j<genres.size(); j++) {
				System.out.println(genres.get(j).getAsString());
			}
			System.out.println(original_title);
			System.out.println(id);
			System.out.println(images.get("large").getAsString()+"\n");	
		}
	}
	
	public static void parseDetail() {
		JsonParser parser = new JsonParser();
		JsonObject object = null;
		try {
			object = (JsonObject) parser.parse(new FileReader("/home/chk/detail.json"));
			JsonArray casts = object.get("casts").getAsJsonArray();
			JsonArray directors = object.get("directors").getAsJsonArray();
			JsonObject director = directors.get(0).getAsJsonObject();
			String mobile_url = object.get("mobile_url").getAsString();
			String countries = object.get("countries").getAsString();
			String summary = object.get("summary").getAsString();
			String name = director.get("name").getAsString();
			System.out.println(mobile_url);
			System.out.println(countries);
			System.out.println(summary);
			System.out.println(name);
			for (int i=0; i<casts.size(); i++) {
				JsonObject cast = casts.get(i).getAsJsonObject();
				String castName = cast.get("name").getAsString();
				System.out.print(castName);
			}
			System.out.println("\n");
			
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
