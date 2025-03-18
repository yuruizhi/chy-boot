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
package com.chy.boot.framework.common.util;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

/**
 * 对象工具类
 *
 * @author YuRuiZhi
 */
public class ObjectUtil extends ObjectUtils {

	/**
	 * 判断元素是否为null
	 * @param obj 对象
	 * @return boolean
	 */
	public static boolean isNull(@Nullable Object obj) {
		return obj == null;
	}

	/**
	 * 判断元素是否为null或空对象
	 * @param obj 对象
	 * @return boolean
	 */
	public static boolean isEmpty(@Nullable Object obj) {
		return ObjectUtils.isEmpty(obj);
	}

	/**
	 * 判断元素不为null和空对象
	 * @param obj 对象
	 * @return boolean
	 */
	public static boolean isNotEmpty(@Nullable Object obj) {
		return !ObjectUtils.isEmpty(obj);
	}

	/**
	 * 判断元素不为null
	 * @param obj 对象
	 * @return boolean
	 */
	public static boolean isNotNull(@Nullable Object obj) {
		return !isNull(obj);
	}

	/**
	 * 如果值为null返回默认值
	 * @param object       对象
	 * @param defaultValue 默认值
	 * @return obj
	 */
	public static <T> T defaultIfNull(@Nullable T object, final T defaultValue) {
		return object != null ? object : defaultValue;
	}

	/**
	 * 比较两个对象是否相等，两个对象都为null也返回true
	 * @param a 对象a
	 * @param b 对象b
	 * @return 是否相等
	 */
	public static boolean nullSafeEquals(@Nullable Object a, @Nullable Object b) {
		if (a == b) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		if (a.equals(b)) {
			return true;
		}
		if (a.getClass().isArray() && b.getClass().isArray()) {
			return arrayEquals(a, b);
		}
		return false;
	}

	/**
	 * 数组比较
	 * @param a 对象a
	 * @param b 对象b
	 * @return 是否相等
	 */
	private static boolean arrayEquals(Object a, Object b) {
		if (a instanceof Object[] && b instanceof Object[]) {
			return ObjectUtils.nullSafeEquals((Object[]) a, (Object[]) b);
		}
		if (a instanceof boolean[] && b instanceof boolean[]) {
			return ObjectUtils.nullSafeEquals((boolean[]) a, (boolean[]) b);
		}
		if (a instanceof byte[] && b instanceof byte[]) {
			return ObjectUtils.nullSafeEquals((byte[]) a, (byte[]) b);
		}
		if (a instanceof char[] && b instanceof char[]) {
			return ObjectUtils.nullSafeEquals((char[]) a, (char[]) b);
		}
		if (a instanceof double[] && b instanceof double[]) {
			return ObjectUtils.nullSafeEquals((double[]) a, (double[]) b);
		}
		if (a instanceof float[] && b instanceof float[]) {
			return ObjectUtils.nullSafeEquals((float[]) a, (float[]) b);
		}
		if (a instanceof int[] && b instanceof int[]) {
			return ObjectUtils.nullSafeEquals((int[]) a, (int[]) b);
		}
		if (a instanceof long[] && b instanceof long[]) {
			return ObjectUtils.nullSafeEquals((long[]) a, (long[]) b);
		}
		if (a instanceof short[] && b instanceof short[]) {
			return ObjectUtils.nullSafeEquals((short[]) a, (short[]) b);
		}
		return false;
	}
}
