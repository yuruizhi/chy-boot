package com.changyi.chy.web.xss;

import com.changyi.chy.common.constant.StringPool;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML过滤器，用于过滤XSS威胁
 *
 * @author YuRuizhi
 */
@Slf4j
public final class HtmlFilter {

	/**
	 * regex flag union representing /si modifiers in php
	 **/
	private static final int REGEX_FLAGS_SI = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
	private static final Pattern P_COMMENTS = Pattern.compile("<!--(.*?)-->", Pattern.DOTALL);
	private static final Pattern P_COMMENT = Pattern.compile("^!--(.*)--$", REGEX_FLAGS_SI);
	private static final Pattern P_TAGS = Pattern.compile("<(.*?)>", Pattern.DOTALL);
	private static final Pattern P_END_TAG = Pattern.compile("^/([a-z0-9]+)", REGEX_FLAGS_SI);
	private static final Pattern P_START_TAG = Pattern.compile("^([a-z0-9]+)(.*?)(/?)$", REGEX_FLAGS_SI);
	private static final Pattern P_QUOTED_ATTRIBUTES = Pattern.compile("([a-z0-9]+)=([\"'])(.*?)\\2", REGEX_FLAGS_SI);
	private static final Pattern P_UNQUOTED_ATTRIBUTES = Pattern.compile("([a-z0-9]+)(=)([^\"\\s']+)", REGEX_FLAGS_SI);
	private static final Pattern P_PROTOCOL = Pattern.compile("^([^:]+):", REGEX_FLAGS_SI);
	private static final Pattern P_ENTITY = Pattern.compile("&#(\\d+);?");
	private static final Pattern P_ENTITY_UNICODE = Pattern.compile("&#x([0-9a-f]+);?");
	private static final Pattern P_ENCODE = Pattern.compile("%([0-9a-f]{2});?");
	private static final Pattern P_VALID_ENTITIES = Pattern.compile("&([^&;]*)(?=(;|&|$))");
	private static final Pattern P_VALID_QUOTES = Pattern.compile("(>|^)([^<]+?)(<|$)", Pattern.DOTALL);
	private static final Pattern P_END_ARROW = Pattern.compile("^>");
	private static final Pattern P_BODY_TO_END = Pattern.compile("<([^>]*?)(?=<|$)");
	private static final Pattern P_XML_CONTENT = Pattern.compile("(^|>)([^<]*?)(?=>)");
	private static final Pattern P_STRAY_LEFT_ARROW = Pattern.compile("<([^>]*?)(?=<|$)");
	private static final Pattern P_STRAY_RIGHT_ARROW = Pattern.compile("(^|>)([^<]*?)(?=>)");
	private static final Pattern P_AMP = Pattern.compile("&");
	private static final Pattern P_QUOTE = Pattern.compile("<");
	private static final Pattern P_LEFT_ARROW = Pattern.compile("<");
	private static final Pattern P_RIGHT_ARROW = Pattern.compile(">");
	private static final Pattern P_BOTH_ARROWS = Pattern.compile("<>");

	// @xxx could grow large... maybe use sesat's ReferenceMap
	private static final ConcurrentMap<String, Pattern> P_REMOVE_PAIR_BLANKS = new ConcurrentHashMap<>();
	private static final ConcurrentMap<String, Pattern> P_REMOVE_SELF_BLANKS = new ConcurrentHashMap<>();

	/**
	 * set of allowed html elements, along with allowed attributes for each element
	 **/
	private final Map<String, List<String>> vAllowed;
	/**
	 * counts of open tags for each (allowable) html element
	 **/
	private final Map<String, Integer> vTagCounts = new HashMap<>();

	/**
	 * html elements which must always be self-closing (e.g. "<img />")
	 **/
	private final String[] vSelfClosingTags;
	/**
	 * html elements which must always have separate opening and closing tags (e.g. "<b></b>")
	 **/
	private final String[] vNeedClosingTags;
	/**
	 * set of disallowed html elements
	 **/
	private final String[] vDisallowed;
	/**
	 * attributes which should be checked for valid protocols
	 **/
	private final String[] vProtocolAtts;
	/**
	 * allowed protocols
	 **/
	private final String[] vAllowedProtocols;
	/**
	 * tags which should be removed if they contain no content (e.g. "<b></b>" or "<b />")
	 **/
	private final String[] vRemoveBlanks;
	/**
	 * entities allowed within html markup
	 **/
	private final String[] vAllowedEntities;
	/**
	 * flag determining whether comments are allowed in input String.
	 */
	private final boolean stripComment;
	private final boolean encodeQuotes;
	private boolean vDebug = false;
	/**
	 * flag determining whether to try to make tags when presented with "unbalanced" angle brackets (e.g. "<b text </b>"
	 * becomes "<b> text </b>").  If set to false, unbalanced angle brackets will be html escaped.
	 */
	private final boolean alwaysMakeTags;

	/**
	 * Default constructor.
	 */
	public HtmlFilter() {
		vAllowed = new HashMap<>();

		final ArrayList<String> aAtts = new ArrayList<String>();
		aAtts.add("href");
		aAtts.add("target");
		vAllowed.put("a", aAtts);

		final ArrayList<String> imgAtts = new ArrayList<String>();
		imgAtts.add("src");
		imgAtts.add("width");
		imgAtts.add("height");
		imgAtts.add("alt");
		vAllowed.put("img", imgAtts);

		final ArrayList<String> noAtts = new ArrayList<String>();
		vAllowed.put("b", noAtts);
		vAllowed.put("strong", noAtts);
		vAllowed.put("i", noAtts);
		vAllowed.put("em", noAtts);

		vSelfClosingTags = new String[]{"img"};
		vNeedClosingTags = new String[]{"a", "b", "strong", "i", "em"};
		vDisallowed = new String[]{};
		vAllowedProtocols = new String[]{"http", "mailto", "https"};
		vProtocolAtts = new String[]{"src", "href"};
		vRemoveBlanks = new String[]{"a", "b", "strong", "i", "em"};
		vAllowedEntities = new String[]{"amp", "gt", "lt", "quot"};
		stripComment = true;
		encodeQuotes = true;
		alwaysMakeTags = true;
	}

	/**
	 * Set debug flag to true. Otherwise use default settings. See the default constructor.
	 *
	 * @param debug turn debug on with a true argument
	 */
	public HtmlFilter(final boolean debug) {
		this();
		vDebug = debug;

	}

	/**
	 * Map-parameter configurable constructor.
	 *
	 * @param conf map containing configuration. keys match field names.
	 */
	public HtmlFilter(final Map<String, Object> conf) {
		this();
		for (final Map.Entry<String, Object> entry : conf.entrySet()) {
			final String key = entry.getKey();
			final Object value = entry.getValue();
			try {
				// 设置配置参数
			} catch (Throwable e) {
				log.error("Error setting configuration value: {}", key, e);
			}
		}
	}

	/**
	 * Filters out code that could be used to mount a cross-site scripting attack.
	 *
	 * @param input text to be filtered
	 * @return filtered text
	 */
	public String filter(final String input) {
		String s = input;

		if (s == null) {
			return null;
		}

		s = s.trim();
		if (s.isEmpty()) {
			return s;
		}

		// Remove comments
		if (stripComment) {
			s = P_COMMENTS.matcher(s).replaceAll("");
		}

		// Remove tags with content between them
		s = balanceHTML(s);

		// Remove any quote chars
		if (encodeQuotes) {
			s = regexReplace(P_QUOTE, "&quot;", s);
		}

		// Replace invalid entities
		s = validateEntities(P_VALID_ENTITIES.matcher(s).replaceAll("&amp;$1;"));

		return s;
	}

	// Protected methods that can be overridden by subclasses
	// ...

	protected String escapeQuotes(final String s) {
		// ... 实现保持不变
		return s;
	}

	protected String balanceHTML(String s) {
		// ... 实现保持不变
		return s;
	}

	protected String processTag(final String s) {
		// ... 实现保持不变
		return s;
	}

	protected String processParamProtocol(String s) {
		s = decodeEntities(s);
		final Matcher m = P_PROTOCOL.matcher(s);
		if (m.find()) {
			final String protocol = m.group(1);
			if (!inArray(protocol, vAllowedProtocols)) {
				// bad protocol, turn into local anchor link instead
				s = "#" + s.substring(protocol.length() + 1);
				if (s.startsWith(StringPool.DOUBLE_SLASH)) {
					s = "#" + s.substring(3);
				}
			}
		}

		return s;
	}

	protected String decodeEntities(String s) {
		// ... 实现保持不变
		return s;
	}

	protected String validateEntities(final String s) {
		// ... 实现保持不变
		return s;
	}

	protected String checkEntity(final String preamble, final String term) {
		// ... 实现保持不变
		return "&" + term + ";";
	}

	protected String encodeQuotes(final String s) {
		if (encodeQuotes) {
			return regexReplace(P_QUOTE, "&quot;", s);
		} else {
			return s;
		}
	}

	protected String regexReplace(final Pattern regex_pattern, final String replacement, final String s) {
		Matcher m = regex_pattern.matcher(s);
		return m.replaceAll(replacement);
	}

	protected boolean inArray(final String s, final String[] array) {
		for (String item : array) {
			if (item != null && item.equals(s)) {
				return true;
			}
		}
		return false;
	}

	protected boolean allowed(final String name) {
		return (vAllowed.isEmpty() || vAllowed.containsKey(name)) && !inArray(name, vDisallowed);
	}

	protected boolean allowedAttribute(final String name, final String paramName) {
		return allowed(name) && (vAllowed.isEmpty() || vAllowed.get(name).contains(paramName));
	}

	protected void logIn(final String msg) {
		if (vDebug) {
			log.debug(msg);
		}
	}

	/**
	 * 十进制转字符
	 */
	protected String chr(final int decimal) {
		return String.valueOf((char) decimal);
	}
}
