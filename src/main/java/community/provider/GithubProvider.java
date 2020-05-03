package community.provider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import community.dto.AccessTokenDTO;
import community.dto.GithubUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class GithubProvider {
	
	
	public String getAccessToken(AccessTokenDTO accessTokenDTO) {
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient();
		client.newBuilder().connectTimeout(500000, TimeUnit.MILLISECONDS).readTimeout(500000, TimeUnit.MILLISECONDS).build();
		RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
		Request request = new Request.Builder()
				.url("https://github.com/login/oauth/access_token")
				.post(body)
				.build();
	     
		try (Response response = client.newCall(request).execute()) {
			String msg = response.body().string();
 			String token=msg.split("&")[0].split("=")[1];
 			return token;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return null;
	}

	public GithubUser getUser(String accessToken) { 
		OkHttpClient client = new OkHttpClient(); 
		client.newBuilder().connectTimeout(500000, TimeUnit.MILLISECONDS).readTimeout(500000, TimeUnit.MILLISECONDS).build();
		Request request = new Request.Builder()
				.url("https://api.github.com/user?access_token="+accessToken)
				.build();
	  
		try {
			Response response = client.newCall(request).execute();
			String msg = response.body().string();
			GithubUser githubUser = JSON.parseObject(msg,GithubUser.class);
 			return githubUser;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
