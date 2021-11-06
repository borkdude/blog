import { EditorState } from "@codemirror/state";
import { tags, styleTags, defaultHighlightStyle } from '@codemirror/highlight';
import { parser } from "lezer-clojure";

import { /* StyleSpec,*/ StyleModule } from "style-mod";
import { LezerLanguage } from "@codemirror/language";

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
let extensions = [/*theme,*/ defaultHighlightStyle, [syntax]];

class StyleRule {
    constructor(type, flags, specificity, cls) {
        this.type = type;
        this.flags = flags;
        this.specificity = specificity;
        this.cls = cls;
    }
}
// TODO: this class is copied from @codemirror/next/highlight,
// maybe it can be exported?
class Styling {
    constructor(tags, spec) {
        this.tags = tags;
        this.cache = Object.create(null);
        let modSpec = Object.create(null);
        let rules = [];
        for (let prop in spec) {
            let cls = StyleModule.newName();
            modSpec["." + cls] = spec[prop];
            for (let part of prop.split(/\s*,\s*/)) {
              console.log('part', part);
              let tag = tags.get(part);
                rules.push(new StyleRule(tag >> tags.typeShift, tag & tags.flagMask, tags.specificity(tag), cls));
            }
        }
        this.rules = rules.sort((a, b) => b.specificity - a.specificity);
        this.module = new StyleModule(modSpec);
    }
    match(tag) {
        let known = this.cache[tag];
        if (known != null)
            return known;
        let result = "";
        let type = tag >> this.tags.typeShift, flags = tag & this.tags.flagMask;
        for (;;) {
            for (let rule of this.rules) {
                if (rule.type == type && (rule.flags & flags) == rule.flags) {
                    if (result)
                        result += " ";
                    result += rule.cls;
                    flags &= ~rule.flags;
                    if (type)
                        break;
                }
            }
            if (type)
                type = this.tags.parents[type];
            else
                break;
        }
        return this.cache[tag] = result;
    }
}
// Note: mostly copied from Highlighter#buildDeco
export const highlight = (contents, _lang) => {
    const state = EditorState.create({
        doc: contents,
        extensions: extensions,
    });
    const tree = state.tree;
    const nodeStack = [""];
    const classStack = [""];
    const inheritStack = [""];
    const prop = tags.prop;
    // const styling = new Styling(tags, defaultSpec);
    let result = '';
    let start, curClass, depth;
    function flush(pos, style) {
        if (pos > start) {
            if (style) {
                result += `<span class="${style}">`;
            }
            result += contents.substring(start, pos).replace('<', '&gt;');
            if (style) {
                result += `</span>`;
            }
        }
        start = pos;
    }
    const from = 0, to = contents.length - 1;
    curClass = "";
    depth = 0;
    start = from;
    tree.iterate({
        from, to,
        enter: (type, start) => {
            depth++;
            let inheritedClass = inheritStack[depth - 1];
            let cls = inheritedClass;
            let rule = type.prop(prop);
            let opaque = false;
            while (rule) {
                if (!rule.context.length || matchContext(rule.context, nodeStack, depth)) {
                    //let style = styling.match(rule.tag);
                  if (false) {  //(style) {
                        if (cls)
                            cls += " ";
                        cls += style;
                        if (rule.mode == 1 /* Inherit */)
                            inheritedClass = cls;
                        else if (rule.mode == 0 /* Opaque */)
                            opaque = true;
                    }
                    break;
                }
                rule = rule.next;
            }
            if (cls != curClass) {
                flush(start, curClass);
                curClass = cls;
            }
            if (opaque) {
                depth--;
                return false;
            }
            classStack[depth] = cls;
            inheritStack[depth] = inheritedClass;
            nodeStack[depth] = type.name;
            return undefined;
        },
        leave: (_t, _s, end) => {
            depth--;
            let backTo = classStack[depth];
            if (backTo != curClass) {
                flush(Math.min(to, end), curClass);
                curClass = backTo;
            }
        }
    });
    flush(contents.length - 1, '');
    return {
        html: result,
        // TODO: we need to render this module to CSS text
        //css: styling.module,
    };
};
function matchContext(context, stack, depth) {
    if (context.length > depth - 1)
        return false;
    for (let d = depth - 1, i = context.length - 1; i >= 0; i--, d--) {
        let check = context[i];
        if (check && check != stack[d])
            return false;
    }
    return true;
}
// A default highlighter (works well with light themes).
export const defaultSpec = {
    deleted: { textDecoration: "line-through" },
    inserted: { textDecoration: "underline" },
    link: { textDecoration: "underline" },
    strong: { fontWeight: "bold" },
    emphasis: { fontStyle: "italic" },
    keyword: { color: "#708" },
    "atom, bool": { color: "#219" },
    number: { color: "#164" },
    string: { color: "#a11" },
    "regexp, escape, string#2": { color: "#e40" },
    "variableName definition": { color: "#00f" },
    typeName: { color: "#085" },
    className: { color: "#167" },
    "name#2": { color: "#256" },
    "propertyName definition": { color: "#00c" },
    comment: { color: "#940" },
    meta: { color: "#555" },
    invalid: { color: "#f00" },
};
