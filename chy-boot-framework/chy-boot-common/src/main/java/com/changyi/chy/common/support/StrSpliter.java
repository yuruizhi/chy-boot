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
package com.changyi.chy.common.support;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串分割器
 */
public class StrSpliter {
	
	//---------------------------------------------------------------------------------------------- Split by char
	/**
	 * 切分字符串
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @return 切分后的集合
	 */
	public static List<String> split(CharSequence str, char separator) {
		return split(str, separator, 0);
	}
	
	/**
	 * 切分字符串为字符串数组
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数
	 * @return 切分后的集合
	 */
	public static List<String> split(CharSequence str, char separator, int limit) {
		if(null == str) {
			return new ArrayList<>(0);
		}
		return split(str.toString(), separator, limit);
	}
	
	/**
	 * 切分字符串
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @return 切分后的集合
	 */
	public static List<String> split(String str, char separator) {
		return split(str, separator, 0);
	}
	
	/**
	 * 切分字符串
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数，-1不限制
	 * @return 切分后的集合
	 */
	public static List<String> split(String str, char separator, int limit) {
		return split(str, separator, limit, false, false);
	}
	
	/**
	 * 切分字符串，去除切分后每个元素两边的空白符
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @return 切分后的集合
	 * @since 3.1.2
	 */
	public static List<String> splitTrim(String str, char separator) {
		return splitTrim(str, separator, -1);
	}
	
	/**
	 * 切分字符串，去除切分后每个元素两边的空白符
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数，-1不限制
	 * @return 切分后的集合
	 * @since 3.1.2
	 */
	public static List<String> splitTrim(String str, char separator, int limit) {
		return split(str, separator, limit, true, true);
	}
	
	/**
	 * 切分字符串，去除切分后每个元素两边的空白符
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数，-1不限制
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 3.2.0
	 */
	public static List<String> split(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
		if(str == null) {
			return new ArrayList<>(0);
		}
		final List<String> list = new ArrayList<>();
		int len = str.length();
		int start = 0;
		for (int i = 0; i < len; i++) {
			if(separator == str.charAt(i)) {
				addToList(list, str.substring(start, i), isTrim, ignoreEmpty);
				start = i+1;
				
				//检查是否超出范围（最大允许limit-1个，剩下一个留给末尾字符串）
				if(limit > 0 && list.size() > limit-2) {
					break;
				}
			}
		}
		return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
	}
	
	//---------------------------------------------------------------------------------------------- Split by String
	
	/**
	 * 切分字符串，分隔符为逗号
	 * 
	 * @param str 被切分的字符串
	 * @return 切分后的集合
	 */
	public static List<String> split(String str) {
		return split(str, ',');
	}
	
	/**
	 * 切分字符串
	 * 
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符串
	 * @return 切分后的集合
	 */
	public static List<String> split(String str, String separator) {
		return split(str, separator, -1, false, false);
	}
	
	/**
	 * 切分字符串
	 * 
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符串
	 * @param limit 限制分片数
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 3.0.8
	 */
	public static List<String> split(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
		if(str == null) {
			return new ArrayList<>(0);
		}
		final List<String> list = new ArrayList<>();
		
		if(limit == 1) {
			list.add(str);
			return list;
		}
		
		if(org.springframework.util.StringUtils.isEmpty(separator)) {
			list.add(str);
			return list;
		}
		
		int len = str.length();
		int separatorLen = separator.length();
		int start = 0;
		int i = 0;
		
		while(i < len) {
			i = str.indexOf(separator, start);
			if(i > -1) {
				addToList(list, str.substring(start, i), isTrim, ignoreEmpty);
				start = i + separatorLen;
				
				//检查是否超出范围（最大允许limit-1个，剩下一个留给末尾字符串）
				if(limit > 0 && list.size() > limit-2) {
					break;
				}
			}else {
				break;
			}
		}
		return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
	}
	
	//---------------------------------------------------------------------------------------------- Split by regex
	/**
	 * 通过正则切分字符串
	 * 
	 * @param str 字符串
	 * @param separatorPattern 分隔符正则{@link Pattern}
	 * @return 切分后的集合
	 * @since 3.0.8
	 */
	public static List<String> split(String str, Pattern separatorPattern) {
		return split(str, separatorPattern, 0, false, false);
	}
	
	/**
	 * 通过正则切分字符串
	 * 
	 * @param str 字符串
	 * @param separatorPattern 分隔符正则{@link Pattern}
	 * @param limit 限制分片数
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 3.0.8
	 */
	public static List<String> split(String str, Pattern separatorPattern, int limit, boolean isTrim, boolean ignoreEmpty) {
		if(str == null) {
			return new ArrayList<>(0);
		}
		final Matcher matcher = separatorPattern.matcher(str);
		final List<String> list = new ArrayList<>();
		
		int len = str.length();
		int start = 0;
		int i;
		
		while(matcher.find()) {
			i = matcher.start();
			addToList(list, str.substring(start, i), isTrim, ignoreEmpty);
			start = i + matcher.group().length();
			
			//检查是否超出范围（最大允许limit-1个，剩下一个留给末尾字符串）
			if(limit > 0 && list.size() > limit-2) {
				break;
			}
		}
		return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
	}
	
	//---------------------------------------------------------------------------------------------- Private method start
	/**
	 * 将字符串加入List中
	 * @param list 列表
	 * @param part 被加入的部分
	 * @param isTrim 是否去除两端空白符
	 * @param ignoreEmpty 是否略过空字符串（空字符串不做为一个元素）
	 * @return 列表
	 */
	private static List<String> addToList(List<String> list, String part, boolean isTrim, boolean ignoreEmpty) {
		if(isTrim) {
			part = part.trim();
		}
		if(false == ignoreEmpty || false == part.isEmpty()) {
			list.add(part);
		}
		return list;
	}
	
	/**
	 * 切分字符串为字符串数组
	 *
	 * @param str               被切分的字符串
	 * @param separator         分隔符字符串
	 * @param limit             限制分片数
	 * @param isTrim            是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty       是否忽略空串
	 * @return 切分后的集合
	 * @since 3.0.8
	 */
	public static String[] splitToArray(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
		List<String> list = split(str, separator, limit, isTrim, ignoreEmpty);
		return list.toArray(new String[0]);
	}
	
	/**
	 * 根据给定长度，将字符串切分为数组
	 *
	 * @param str 字符串
	 * @param len 每一个小节的长度
	 * @return 切分后的数组
	 */
	public static String[] splitByLength(String str, int len) {
		if(null == str) {
			return new String[0];
		}
		
		int partCount = str.length() / len;
		int lastPartCount = str.length() % len;
		int fixedCount = 0;
		if(lastPartCount != 0) {
			fixedCount = 1;
		}
		
		final String[] strs = new String[partCount + fixedCount];
		for(int i = 0; i < partCount; i++) {
			strs[i] = str.substring(i * len, (i + 1) * len);
		}
		
		if(lastPartCount != 0) {
			strs[partCount] = str.substring(partCount * len);
		}
		
		return strs;
	}
} 