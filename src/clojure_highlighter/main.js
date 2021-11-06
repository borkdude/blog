import { LezerLanguage } from "@codemirror/language";
import { parser } from "lezer-clojure";
import { tags, styleTags, highlightTree, defaultHighlightStyle} from "@codemirror/highlight";

function runmode(textContent, language, callback) {
  const tree = language.parser.parse(textContent);
  let pos = 0;
  highlightTree(tree, defaultHighlightStyle.match, (from, to, classes) => {
    from > pos && callback(textContent.slice(pos, from), null, pos, from);
    callback(textContent.slice(from, to), classes, from, to);
    pos = to;
  });
  pos != tree.length && callback(textContent.slice(pos, tree.length), null, pos, tree.length);
}

// let theme = EditorView.theme({
//   '.cm-content': { 'white-space': 'pre-wrap', padding: '10px 0' },
//   '&.cm-focused': { outline: 'none' },
//   '.cm-line': {
//     padding: '0 9px',
//     'line-height': '1.6',
//     'font-size': '16px',
//     'font-family': 'var(--code-font)'
//   },
//   '.cm-matchingBracket': { 'border-bottom': '1px solid var(--teal-color)', color: 'inherit' },
//   '.cm-gutters': { background: 'transparent', border: 'none' },
//   '.cm-gutterElement': { 'margin-left': '5px' },
//   '.cm-cursor': { visibility: 'hidden' },
//   '&.cm-focused .cm-cursor': { visibility: 'visible' }
// });

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
// let extensions = [EditorView.editable.of(false), theme, defaultHighlightStyle, [syntax]];

document.querySelectorAll("code.clojure").forEach( elt => {
  runmode(elt.innerText.trim(), syntax, (content, classes, from, to) => {
    let span = document.createElement('span');
    if (classes) {
      span.classList = classes;
    }
    span.innerText = content;
    elt.appendChild(span);
    console.log(content, classes, from, to);
  });

  // new EditorView({state: EditorState.create({doc: elt.innerText.trim(),
  //                                            extensions: [extensions]}),
  //                 parent: elt});
  elt.firstChild.remove();
});
