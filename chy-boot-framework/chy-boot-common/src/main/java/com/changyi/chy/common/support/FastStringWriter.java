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

import java.io.IOException;
import java.io.Writer;

/**
 * FastStringWriter，不需要同步，且初始大小设置为默认值
 */
public class FastStringWriter extends Writer {
	private StringBuilder builder;

	public FastStringWriter() {
		builder = new StringBuilder(64);
	}

	public FastStringWriter(final int capacity) {
		builder = new StringBuilder(capacity);
	}

	public FastStringWriter(StringBuilder builder) {
		this.builder = builder;
	}

	@Override
	public void write(final int c) {
		builder.append((char) c);
	}

	@Override
	public void write(final char[] cArr, final int off, final int len) {
		if ((off < 0) || (off > cArr.length) || (len < 0) ||
			((off + len) > cArr.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return;
		}
		builder.append(cArr, off, len);
	}

	@Override
	public void write(final String str) {
		builder.append(str);
	}

	@Override
	public void write(final String str, final int off, final int len) {
		builder.append(str.substring(off, off + len));
	}

	@Override
	public FastStringWriter append(final CharSequence csq) {
		if (csq == null) {
			write("null");
		} else {
			write(csq.toString());
		}
		return this;
	}

	@Override
	public FastStringWriter append(final CharSequence csq, final int start, final int end) {
		if (csq == null) {
			write("null".substring(start, end));
		} else {
			write(csq.subSequence(start, end).toString());
		}
		return this;
	}

	@Override
	public FastStringWriter append(final char c) {
		write(c);
		return this;
	}

	@Override
	public String toString() {
		return builder.toString();
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() throws IOException {
	}
} 