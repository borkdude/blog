import { tags, styleTags, defaultHighlightStyle } from "@codemirror/highlight";
import { LezerLanguage } from "@codemirror/language";
import { EditorState } from "@codemirror/state";
import { EditorView } from "@codemirror/view";
import { parser } from "lezer-clojure";

let theme = EditorView.theme({
  '.cm-content': { 'white-space': 'pre-wrap', padding: '10px 0' },
  '&.cm-focused': { outline: 'none' },
  '.cm-line': {
    padding: '0 9px',
    'line-height': '1.6',
    'font-size': '16px',
    'font-family': 'var(--code-font)'
  },
  '.cm-matchingBracket': { 'border-bottom': '1px solid var(--teal-color)', color: 'inherit' },
  '.cm-gutters': { background: 'transparent', border: 'none' },
  '.cm-gutterElement': { 'margin-left': '5px' },
  '.cm-cursor': { visibility: 'hidden' },
  '&.cm-focused .cm-cursor': { visibility: 'visible' }
});

let style = {
  DefLike: tags.keyword,
  "Operator/Symbol": tags.keyword,
  "VarName/Symbol": tags.definition(tags.variableName),
  Boolean: tags.atom,
  "DocString/...": tags.emphasis,
  "Discard!": tags.comment,
  Number: tags.number,
  StringContent: tags.string,
  Keyword: tags.atom,
  Nil: tags.null,
  LineComment: tags.lineComment,
  RegExp: tags.regexp,
  "\"\\\"\"": tags.string,
};

let cljParser = parser.configure({props: [styleTags(style)]});

let syntax = LezerLanguage.define({parser: cljParser}, {languageData: {commentTokens: {line: ";;"}}});
let extensions = [theme, defaultHighlightStyle, [syntax]];

document.querySelectorAll("code.clojure").forEach( elt => {
  new EditorView({state: EditorState.create({doc: elt.innerText, extensions: [extensions]}),
                  parent: elt});
  elt.firstChild.remove();
});
