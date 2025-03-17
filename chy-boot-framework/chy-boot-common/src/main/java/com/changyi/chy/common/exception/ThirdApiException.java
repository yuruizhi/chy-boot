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
package com.changyi.chy.common.exception;

/**
 * 第三方API异常
 */
public class ThirdApiException extends RuntimeException {

	private static final long serialVersionUID = -8249233823066987044L;

	public ThirdApiException() {
	}

	public ThirdApiException(String message) {
		super(message);
	}

	public ThirdApiException(Throwable cause) {
		super(cause);
	}

	public ThirdApiException(String message, Throwable cause) {
		super(message, cause);
	}
} 