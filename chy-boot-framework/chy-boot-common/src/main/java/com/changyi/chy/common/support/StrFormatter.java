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


/**
 * 字符串格式化工具
 */
public class StrFormatter {

	/**
	 * 格式化字符串<br>
	 * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
	 * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
	 * 例：<br>
	 * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
	 * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
	 * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
	 *
	 * @param strPattern 字符串模板
	 * @param argArray   参数列表
	 * @return 结果
	 */
	public static String format(final String strPattern, final Object... argArray) {
		if (strPattern == null || argArray == null) {
			return strPattern;
		}
		final int strPatternLength = strPattern.length();

		// 初始化定义好的长度以获得更好的性能
		StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

		int handledPosition = 0;
		int delimiterIndex;// 占位符所在位置
		for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
			delimiterIndex = strPattern.indexOf("{}", handledPosition);
			if (delimiterIndex == -1) {
				if (handledPosition == 0) {
					return strPattern;
				} else { // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
					sbuf.append(strPattern, handledPosition, strPatternLength);
					return sbuf.toString();
				}
			} else {
				if (delimiterIndex > 0 && strPattern.charAt(delimiterIndex - 1) == '\\') { // 转义符
					if (delimiterIndex > 1 && strPattern.charAt(delimiterIndex - 2) == '\\') { // 双转义符
						// 转义符之前还有一个转义符，占位符依旧有效
						sbuf.append(strPattern, handledPosition, delimiterIndex - 2);
						sbuf.append(argArray[argIndex]);
						handledPosition = delimiterIndex + 2;
					} else {
						// 占位符被转义
						argIndex--;
						sbuf.append(strPattern, handledPosition, delimiterIndex - 1);
						sbuf.append('{');
						handledPosition = delimiterIndex + 1;
					}
				} else { // 正常占位符
					sbuf.append(strPattern, handledPosition, delimiterIndex);
					sbuf.append(argArray[argIndex]);
					handledPosition = delimiterIndex + 2;
				}
			}
		}
		// 加入最后一个占位符后所有的字符
		sbuf.append(strPattern, handledPosition, strPattern.length());

		return sbuf.toString();
	}
} 