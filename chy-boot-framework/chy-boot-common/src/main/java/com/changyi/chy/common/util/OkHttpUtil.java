/**
 * Copyright (c) 2018-2028, YuRuizhi (282373647@qq.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.changyi.chy.common.util;

import com.changyi.chy.common.exception.ThirdApiException;
import com.changyi.chy.common.jackson.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.Iterator;
import java.util.Map;

/**
 * Http请求工具类
 *
 * @author Chill
 */
@Slf4j
public class OkHttpUtil {

	public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static MediaType XML = MediaType.parse("application/xml; charset=utf-8");


	/**
	 * GET
	 *
	 * @param url     请求的url
	 * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
	 * @return String
	 */
	public static String get(String url, Map<String, String> queries) {
		return get(url, null, queries);
	}

	/**
	 * GET
	 *
	 * @param url     请求的url
	 * @param header  请求头
	 * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
	 * @return String
	 */
	public static String get(String url, Map<String, String> header, Map<String, String> queries) {
		StringBuffer sb = new StringBuffer(url);
		if (queries != null && queries.keySet().size() > 0) {
			sb.append("?clientId=cy");
			queries.forEach((k, v) -> sb.append("&").append(k).append("=").append(v));
		}

		Request.Builder builder = new Request.Builder();

		if (header != null && header.keySet().size() > 0) {
			header.forEach(builder::addHeader);
		}

		Request request = builder.url(sb.toString()).build();
		return getBody(request);
	}

	/**
	 * POST
	 *
	 * @param url    请求的url
	 * @param params post form 提交的参数
	 * @return String
	 */
	public static String post(String url, Map<String, String> params) {
		return post(url, null, params);
	}

	/**
	 * POST
	 *
	 * @param url    请求的url
	 * @param header 请求头
	 * @param params post form 提交的参数
	 * @return String
	 */
	public static String post(String url, Map<String, String> header, Map<String, String> params) {
		FormBody.Builder formBuilder = new FormBody.Builder().add("clientId", "cy");
		//添加参数
		if (params != null && params.keySet().size() > 0) {
			params.forEach(formBuilder::add);
		}

		Request.Builder builder = new Request.Builder();

		if (header != null && header.keySet().size() > 0) {
			header.forEach(builder::addHeader);
		}

		Request request = builder.url(url).post(formBuilder.build()).build();
		return getBody(request);
	}


	/**
	 * get 请求
	 * @param url url
	 * @param header 请求头参数
	 * @param query 参数
	 * @return
	 */
	public static String doGet(String url, Map<String, String> header, Map<String, Object> query) {

		log.info("posturl:{},header{}, post请求参数:{}",url, header, JsonUtil.object2Json(query));
		// 创建一个请求 Builder
		Request.Builder builder = new Request.Builder();
		// 创建一个 request
		Request request = builder.url(url).build();

		// 创建一个 HttpUrl.Builder
		HttpUrl.Builder urlBuilder = request.url().newBuilder();
		// 创建一个 Headers.Builder
		Headers.Builder headerBuilder = request.headers().newBuilder();

		// 装载请求头参数
		Iterator<Map.Entry<String, String>> headerIterator = header.entrySet().iterator();
		headerIterator.forEachRemaining(e -> {
			headerBuilder.add(e.getKey(), e.getValue());
		});

		// 装载请求的参数
		if(query != null){
			Iterator<Map.Entry<String, Object>> queryIterator = query.entrySet().iterator();
			queryIterator.forEachRemaining(e -> {
				urlBuilder.addQueryParameter(e.getKey(), (String) e.getValue());
			});
		}


		// 设置自定义的 builder
		// 因为 get 请求的参数，是在 URL 后面追加  http://xxxx:8080/user?name=xxxx?sex=1
		builder.url(urlBuilder.build()).headers(headerBuilder.build());

		return getBody(builder.build());
	}

	/**
	 * post 请求， 请求参数
	 * @param url
	 * @param header
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, String> header, Map<String, Object> parameter) {

		// 创建一个请求 Builder
		Request.Builder builder = new Request.Builder();
		// 创建一个 request
		Request request = builder.url(url).build();

		// 创建一个 Headers.Builder
		Headers.Builder headerBuilder = request.headers().newBuilder();

		// 装载请求头参数
		Iterator<Map.Entry<String, String>> headerIterator = header.entrySet().iterator();
		headerIterator.forEachRemaining(e -> {
			headerBuilder.add(e.getKey(), e.getValue());
		});

		MultipartBody.Builder requestBuilder = new MultipartBody.Builder();

		// 状态请求参数
		Iterator<Map.Entry<String, Object>> queryIterator = parameter.entrySet().iterator();
		queryIterator.forEachRemaining(e -> {
			requestBuilder.addFormDataPart(e.getKey(), (String) e.getValue());
		});

		// 设置自定义的 builder
		builder.headers(headerBuilder.build()).post(requestBuilder.build());

		// 然后再 build 一下
		/*try (Response execute = client.newCall(builder.build()).execute()) {
			return execute.body().string();
		}*/
		return getBody(builder.build());
	}

	/**
	 * POST请求发送JSON数据
	 *
	 * @param url     请求的url
	 * @param json    请求的json串
	 * @return String
	 */
	public static String postJson(String url, String json) {
		return postJson(url, null, json);
	}

	/**
	 * POST请求发送JSON数据
	 * @param url     请求的url
	 * @param header  请求头
	 * @param json    请求的json串
	 * @return String
	 */
	public static String postJson(String url, Map<String, String> header, String json) {
		log.info("post url:{},header:{}, post请求参数:{}",url, header, JsonUtil.jsonStr2EscapeStr(json));
		return postContent(url, header, json, JSON);
	}

	/**
	 * POST请求发送xml数据
	 *
	 * @param url     请求的url
	 * @param xml     请求的xml串
	 * @return String
	 */
	public static String postXml(String url, String xml) {
		return postXml(url, null, xml);
	}

	/**
	 * POST请求发送xml数据
	 *
	 * @param url     请求的url
	 * @param header  请求头
	 * @param xml     请求的xml串
	 * @return String
	 */
	public static String postXml(String url, Map<String, String> header, String xml) {
		return postContent(url, header, xml, XML);
	}

	/**
	 * 发送POST请求
	 *
	 * @param url     请求的url
	 * @param header  请求头
	 * @param content 请求内容
	 * @param mediaType 请求类型
	 * @return String
	 */
	public static String postContent(String url, Map<String, String> header, String content, MediaType mediaType) {
		RequestBody requestBody = RequestBody.create(mediaType, content);
		Request.Builder builder = new Request.Builder();

		if (header != null && header.keySet().size() > 0) {
			header.forEach(builder::addHeader);
		}
		Request request = builder.url(url).post(requestBody).build();
		return getBody(request);
	}

	/**
	 * 获取body
	 *
	 * @param request request
	 * @return String
	 */
	private static String getBody(Request request) {
		String responseBody = "";
		Response response = null;
		try {
			OkHttpClient okHttpClient = new OkHttpClient();
			response = okHttpClient.newCall(request).execute();

			log.info("http 请求外部返回结果 httpStatusCode:{}", response.code());
			if (response.isSuccessful()) {
				return response.body().string();
			} else if (response.code() == 503 || response.code() == 504) {
				throw new ThirdApiException(response.message());
			} else {
				throw new ThirdApiException(response.body().string());
			}
		}catch (ThirdApiException e){
			throw e;
		}catch (Exception e) {
			log.error("okhttp3 post error >> ex = {}", e.getMessage());
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return responseBody;
	}

}
