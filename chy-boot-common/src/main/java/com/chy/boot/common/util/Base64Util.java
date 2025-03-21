/**
 * Copyright (c) 2018-2028, DreamLu 卢春梦 (qq596392912@gmail.com).
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
package com.chy.boot.common.util;

import java.util.Base64;

/**
 * Base64工具
 *
 */
public class Base64Util {

	/**
	 * 编码
	 *
	 * @param value 字符串
	 * @return {String}
	 */
	public static String encode(String value) {
		return Base64Util.encode(value, Charsets.UTF_8);
	}

	/**
	 * 编码
	 *
	 * @param value   字符串
	 * @param charset 字符集
	 * @return {String}
	 */
	public static String encode(String value, java.nio.charset.Charset charset) {
		byte[] val = value.getBytes(charset);
		return new String(Base64Util.encode(val), charset);
	}

	/**
	 * 编码URL安全
	 *
	 * @param value 字符串
	 * @return {String}
	 */
	public static String encodeUrlSafe(String value) {
		return Base64Util.encodeUrlSafe(value, Charsets.UTF_8);
	}

	/**
	 * 编码URL安全
	 *
	 * @param value   字符串
	 * @param charset 字符集
	 * @return {String}
	 */
	public static String encodeUrlSafe(String value, java.nio.charset.Charset charset) {
		byte[] val = value.getBytes(charset);
		return new String(Base64Util.encodeUrlSafe(val), charset);
	}

	/**
	 * 解码
	 *
	 * @param value 字符串
	 * @return {String}
	 */
	public static String decode(String value) {
		return Base64Util.decode(value, Charsets.UTF_8);
	}

	/**
	 * 解码
	 *
	 * @param value   字符串
	 * @param charset 字符集
	 * @return {String}
	 */
	public static String decode(String value, java.nio.charset.Charset charset) {
		byte[] val = value.getBytes(charset);
		byte[] decodedValue = Base64Util.decode(val);
		return new String(decodedValue, charset);
	}

	/**
	 * 解码URL安全
	 *
	 * @param value 字符串
	 * @return {String}
	 */
	public static String decodeUrlSafe(String value) {
		return Base64Util.decodeUrlSafe(value, Charsets.UTF_8);
	}

	/**
	 * 解码URL安全
	 *
	 * @param value   字符串
	 * @param charset 字符集
	 * @return {String}
	 */
	public static String decodeUrlSafe(String value, java.nio.charset.Charset charset) {
		byte[] val = value.getBytes(charset);
		byte[] decodedValue = Base64Util.decodeUrlSafe(val);
		return new String(decodedValue, charset);
	}

	/**
	 * 编码
	 *
	 * @param src 字节数组
	 * @return {byte[]}
	 */
	public static byte[] encode(byte[] src) {
		return Base64.getEncoder().encode(src);
	}

	/**
	 * 编码URL安全
	 *
	 * @param src 字节数组
	 * @return {byte[]}
	 */
	public static byte[] encodeUrlSafe(byte[] src) {
		return Base64.getUrlEncoder().encode(src);
	}

	/**
	 * 解码
	 *
	 * @param src 字节数组
	 * @return {byte[]}
	 */
	public static byte[] decode(byte[] src) {
		return Base64.getDecoder().decode(src);
	}

	/**
	 * 解码URL安全
	 *
	 * @param src 字节数组
	 * @return {byte[]}
	 */
	public static byte[] decodeUrlSafe(byte[] src) {
		return Base64.getUrlDecoder().decode(src);
	}
}
