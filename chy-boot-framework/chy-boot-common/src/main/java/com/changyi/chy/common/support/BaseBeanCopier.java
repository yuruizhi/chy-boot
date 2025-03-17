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

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 基于CGLib的Bean复制
 */
public abstract class BaseBeanCopier {
	private static final String CONVERTER_ARG_NAME = "converter";

	private static final ConcurrentMap<String, BaseBeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();

	interface BeanCopierKey {
		/**
		 * 生成key
		 * @param source 源类型
		 * @param target 目标类型
		 * @param useConverter 是否使用转换器
		 * @return key
		 */
		static String generate(Class<?> source, Class<?> target, boolean useConverter) {
			return source.getName() + '_' + target.getName() + '_' + useConverter;
		}
	}

	/**
	 * 创建一个新的实例
	 * @param source 源类型
	 * @param target 目标类型
	 * @param useConverter 是否使用转换器
	 * @return 新实例
	 */
	public static BaseBeanCopier create(Class<?> source, Class<?> target, boolean useConverter) {
		String key = BeanCopierKey.generate(source, target, useConverter);
		return BEAN_COPIER_MAP.computeIfAbsent(key, x -> new CglibBeanCopier(BeanCopier.create(source, target, useConverter)));
	}

	/**
	 * 复制属性
	 * @param source 源对象
	 * @param target 目标对象
	 * @param converter 转换器
	 */
	abstract public void copy(Object source, Object target, Converter converter);

	private static class CglibBeanCopier extends BaseBeanCopier {
		private final BeanCopier delegate;

		CglibBeanCopier(BeanCopier delegate) {
			this.delegate = delegate;
		}

		@Override
		public void copy(Object source, Object target, Converter converter) {
			delegate.copy(source, target, converter);
		}
	}
} 