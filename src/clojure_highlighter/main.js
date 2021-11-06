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

let literalStyle = `
.ͼ1.cm-focused {outline: 1px dotted #212121;}
.ͼ1 {position: relative !important; box-sizing: border-box; display: flex !important; flex-direction: column;}
.ͼ1 .cm-scroller {display: flex !important; align-items: flex-start !important; font-family: monospace; line-height: 1.4; height: 100%; overflow-x: auto; position: relative; z-index: 0;}
.ͼ1 .cm-content {margin: 0; flex-grow: 2; min-height: 100%; display: block; white-space: pre; word-wrap: normal; box-sizing: border-box; padding: 4px 0; outline: none;}
.ͼ1 .cm-lineWrapping {white-space: pre-wrap; word-break: break-word; overflow-wrap: anywhere;}
.ͼ2 .cm-content {caret-color: black;}
.ͼ3 .cm-content {caret-color: white;}
.ͼ1 .cm-line {display: block; padding: 0 2px 0 4px;}
.ͼ1 .cm-selectionLayer {z-index: -1; contain: size style;}
.ͼ1 .cm-selectionBackground {position: absolute;}
.ͼ2 .cm-selectionBackground {background: #d9d9d9;}
.ͼ3 .cm-selectionBackground {background: #222;}
.ͼ2.cm-focused .cm-selectionBackground {background: #d7d4f0;}
.ͼ3.cm-focused .cm-selectionBackground {background: #233;}
.ͼ1 .cm-cursorLayer {z-index: 100; contain: size style; pointer-events: none;}
.ͼ1.cm-focused .cm-cursorLayer {animation: steps(1) cm-blink 1.2s infinite;}
@keyframes cm-blink {50% {visibility: hidden;}}
@keyframes cm-blink2 {50% {visibility: hidden;}}
.ͼ1 .cm-cursor {position: absolute; border-left: 1.2px solid black; margin-left: -0.6px; pointer-events: none; display: none;}
.ͼ3 .cm-cursor {border-left-color: #444;}
.ͼ1.cm-focused .cm-cursor {display: block;}
.ͼ2 .cm-activeLine {background-color: #f3f9ff;}
.ͼ3 .cm-activeLine {background-color: #223039;}
.ͼ2 .cm-specialChar {color: red;}
.ͼ3 .cm-specialChar {color: #f78;}
.ͼ1 .cm-tab {display: inline-block; overflow: hidden; vertical-align: bottom;}
.ͼ1 .cm-placeholder {color: #888; display: inline-block;}
.ͼ1 .cm-button {vertical-align: middle; color: inherit; font-size: 70%; padding: .2em 1em; border-radius: 3px;}
.ͼ2 .cm-button:active {background-image: linear-gradient(#b4b4b4, #d0d3d6);}
.ͼ2 .cm-button {background-image: linear-gradient(#eff1f5, #d9d9df); border: 1px solid #888;}
.ͼ3 .cm-button:active {background-image: linear-gradient(#111, #333);}
.ͼ3 .cm-button {background-image: linear-gradient(#393939, #111); border: 1px solid #888;}
.ͼ1 .cm-textfield {vertical-align: middle; color: inherit; font-size: 70%; border: 1px solid silver; padding: .2em .5em;}
.ͼ2 .cm-textfield {background-color: white;}
.ͼ3 .cm-textfield {border: 1px solid #555; background-color: inherit;}
.ͼ4 {text-decoration: underline;}
.ͼ5 {text-decoration: underline; font-weight: bold;}
.ͼ6 {font-style: italic;}
.ͼ7 {font-weight: bold;}
.ͼ8 {text-decoration: line-through;}
.ͼ9 {color: #708;}
.ͼa {color: #219;}
.ͼb {color: #164;}
.ͼc {color: #a11;}
.ͼd {color: #e40;}
.ͼe {color: #00f;}
.ͼf {color: #30a;}
.ͼg {color: #085;}
.ͼh {color: #167;}
.ͼi {color: #256;}
.ͼj {color: #00c;}
.ͼk {color: #940;}
.ͼl {color: #7a757a;}
.ͼm {color: #f00;}
.ͼn .cm-content {white-space: pre-wrap; padding: 10px 0;}
.ͼn.cm-focused {outline: none;}
.ͼn .cm-line {padding: 0 9px; line-height: 1.6; font-size: 16px; font-family: var(--code-font);}
.ͼn .cm-matchingBracket {border-bottom: 1px solid var(--teal-color); color: inherit;}
.ͼn .cm-gutters {background: transparent; border: none;}
.ͼn .cm-gutterElement {margin-left: 5px;}
.ͼn .cm-cursor {visibility: hidden;}
.ͼn.cm-focused .cm-cursor {visibility: visible;}
`;

let styleElt = document.createElement('style');
styleElt.innerText = literalStyle;
document.head.appendChild(styleElt);

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
