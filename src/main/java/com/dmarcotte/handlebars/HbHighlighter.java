package com.dmarcotte.handlebars;

import com.dmarcotte.handlebars.parsing.HbTokenTypes;
import com.dmarcotte.handlebars.parsing._HbLexer;
import consulo.codeEditor.DefaultLanguageHighlighterColors;
import consulo.colorScheme.TextAttributes;
import consulo.colorScheme.TextAttributesKey;
import consulo.language.ast.IElementType;
import consulo.language.editor.annotation.HighlightSeverity;
import consulo.language.editor.highlight.SyntaxHighlighterBase;
import consulo.language.lexer.Lexer;
import consulo.util.lang.Pair;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author max
 */
public class HbHighlighter extends SyntaxHighlighterBase {
  private static final Map<IElementType, TextAttributesKey> keys1;
  private static final Map<IElementType, TextAttributesKey> keys2;

  @Nonnull
  public Lexer getHighlightingLexer() {
    return new _HbLexer();
  }

  private static final TextAttributesKey MUSTACHES = TextAttributesKey.createTextAttributesKey(
    "HANDLEBARS.MUSTACHES",
    new TextAttributes(null, null, null, null, 1)
  );

  private static final TextAttributesKey IDENTIFIERS = TextAttributesKey.createTextAttributesKey(
    "HANDLEBARS.IDENTIFIERS",
    DefaultLanguageHighlighterColors.KEYWORD
  );

  private static final TextAttributesKey COMMENTS = TextAttributesKey.createTextAttributesKey(
    "HANDLEBARS.COMMENTS",
    DefaultLanguageHighlighterColors.DOC_COMMENT
  );

  private static final TextAttributesKey OPERATORS = TextAttributesKey.createTextAttributesKey(
    "HANDLEBARS.OPERATORS",
    DefaultLanguageHighlighterColors.OPERATION_SIGN
  );

  private static final TextAttributesKey VALUES = TextAttributesKey.createTextAttributesKey(
    "HANDLEBARS.VALUES",
    DefaultLanguageHighlighterColors.NUMBER
  );

  private static final TextAttributesKey STRINGS = TextAttributesKey.createTextAttributesKey(
    "HANDLEBARS.STRINGS",
    DefaultLanguageHighlighterColors.STRING
  );

  private static final TextAttributesKey DATA_PREFIX = TextAttributesKey.createTextAttributesKey(
    "HANDLEBARS.DATA_PREFIX",
    DefaultLanguageHighlighterColors.NUMBER
  );

  private static final TextAttributesKey DATA = TextAttributesKey.createTextAttributesKey(
    "HANDLEBARS.DATA",
    DefaultLanguageHighlighterColors.KEYWORD
  );

  @SuppressWarnings("UseJBColor")
  private static final TextAttributesKey ESCAPE = TextAttributesKey.createTextAttributesKey(
    "HANDLEBARS.ESCAPE", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE
  );

  static {
    keys1 = new HashMap<IElementType, TextAttributesKey>();
    keys2 = new HashMap<IElementType, TextAttributesKey>();

    keys1.put(HbTokenTypes.OPEN, MUSTACHES);
    keys1.put(HbTokenTypes.OPEN_BLOCK, MUSTACHES);
    keys1.put(HbTokenTypes.OPEN_PARTIAL, MUSTACHES);
    keys1.put(HbTokenTypes.OPEN_ENDBLOCK, MUSTACHES);
    keys1.put(HbTokenTypes.OPEN_INVERSE, MUSTACHES);
    keys1.put(HbTokenTypes.OPEN_UNESCAPED, MUSTACHES);
    keys1.put(HbTokenTypes.CLOSE, MUSTACHES);
    keys1.put(HbTokenTypes.ID, IDENTIFIERS);
    keys1.put(HbTokenTypes.PARTIAL_NAME, IDENTIFIERS);
    keys1.put(HbTokenTypes.COMMENT, COMMENTS);
    keys1.put(HbTokenTypes.UNCLOSED_COMMENT, COMMENTS);
    keys1.put(HbTokenTypes.EQUALS, OPERATORS);
    keys1.put(HbTokenTypes.SEP, OPERATORS);
    keys1.put(HbTokenTypes.INTEGER, VALUES);
    keys1.put(HbTokenTypes.ELSE, IDENTIFIERS);
    keys1.put(HbTokenTypes.BOOLEAN, VALUES);
    keys1.put(HbTokenTypes.STRING, STRINGS);
    keys1.put(HbTokenTypes.DATA_PREFIX, DATA_PREFIX);
    keys1.put(HbTokenTypes.DATA, DATA);
    keys1.put(HbTokenTypes.ESCAPE_CHAR, ESCAPE);
  }

  @Nonnull
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    return pack(keys1.get(tokenType), keys2.get(tokenType));
  }

  public static final Map<TextAttributesKey, Pair<String, HighlightSeverity>> DISPLAY_NAMES
    = new LinkedHashMap<TextAttributesKey, Pair<String, HighlightSeverity>>();

  static {
    DISPLAY_NAMES.put(MUSTACHES, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.mustaches.key"), null));
    DISPLAY_NAMES
      .put(IDENTIFIERS, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.identifiers.key"), null));
    DISPLAY_NAMES.put(COMMENTS, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.comments.key"), null));
    DISPLAY_NAMES.put(OPERATORS, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.operators.key"), null));
    DISPLAY_NAMES.put(VALUES, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.values.key"), null));
    DISPLAY_NAMES.put(STRINGS, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.strings.key"), null));
    DISPLAY_NAMES
      .put(DATA_PREFIX, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.data.prefix.key"), null));
    DISPLAY_NAMES.put(DATA, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.data.key"), null));
    DISPLAY_NAMES.put(ESCAPE, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.escape.key"), null));
  }
}
