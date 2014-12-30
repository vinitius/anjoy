package br.org.mantra.anjoy.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;
import br.org.mantra.anjoy.session.ControlledSession;
import br.org.mantra.anjoy.util.ConnectionUtils.HTTPStatus;

public class RestClient {

	private String mToken;
	private String mUserAgent;
	private String mHost;
	private String mBaseUrl;
	private List<NameValuePair> mPostParams;
	private String mPostBody;

	public RestClient(){
		this.mHost = ControlledSession.getInstance().
				get(ControlledSession.REST_CLIENT_HOST).toString();

		this.mBaseUrl = ControlledSession.getInstance().
				get(ControlledSession.REST_CLIENT_BASE_URL).toString();

	}

	public RestClient(String token,String host, String userAgent) {
		this.mToken = token;
		this.mHost = host;
		this.mUserAgent = userAgent;


	}

	public enum RequestMethod{
		GET,
		POST
	}



	public String execute(String url, RequestMethod requestMethod ){

		url = this.mBaseUrl + url;

		if (requestMethod == RequestMethod.GET)
			return executeGet(url);
		else if (requestMethod == RequestMethod.POST)
			return executePost(url);

		return null;

	}



	private String executeGet(String url){

		HttpClient client;
		HttpGet get = new HttpGet(url);

		// add header
		get.setHeader("Host", mHost);
		get.setHeader("User-Agent", mUserAgent);
		get.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.setHeader("Accept-Language", "en-US,en;q=0.5");
		//get.setHeader("Accept-Encoding", "gzip, deflate");
		get.setHeader("Connection", "keep-alive");
		get.setHeader("Content-Type", "application/x-www-form-urlencoded");
		get.setHeader("token", mToken);

		try {

			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 10000); //timeout 10s
			HttpConnectionParams.setSoTimeout(httpParameters, 10000); //timeout 10s

			client = new DefaultHttpClient(httpParameters);	


			//client = new DefaultHttpClient();
			HttpResponse response = client.execute(get);

			int responseCode = response.getStatusLine().getStatusCode();

			Log.i("###", "Response Code : " + responseCode);


			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";

			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			Log.i("###", "Result : " + result);

			if (responseCode == Integer.valueOf(HTTPStatus.SUCCESS))
				return result.toString();
			else
				return String.valueOf(responseCode);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}



	private String executePost(String url){

		HttpClient client;		
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("Host", mHost);
		post.setHeader("User-Agent", mUserAgent);
		//post.setHeader("Accept",
				//"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("Accept-Language", "en-US,en;q=0.5");
		//post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		post.setHeader("token", mToken);

		if (mPostBody == null){
			try{
				post.setEntity(new UrlEncodedFormEntity(mPostParams));
			}catch (UnsupportedEncodingException e){

			}
		}else{
			try{
				post.setEntity(new StringEntity(mPostBody));
			}catch (UnsupportedEncodingException e){

			}

		}

		try {

			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 10000); //timeout 10s
			HttpConnectionParams.setSoTimeout(httpParameters, 10000); //timeout 10s

			client = new DefaultHttpClient(httpParameters);	
			//client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);

			int responseCode = response.getStatusLine().getStatusCode();



			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";

			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			if (responseCode == HTTPStatus.SUCCESS)
				return result.toString();
			else
				return String.valueOf(responseCode);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}


	public void addPostParams(BasicNameValuePair params){

		if (mPostParams == null)
			mPostParams = new ArrayList<NameValuePair>();

		mPostParams.add(params);


	}

	public void addPostParams(String params){

		if (mPostBody == null)
			mPostBody = params;		



	}







}




