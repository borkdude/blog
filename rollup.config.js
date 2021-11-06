import {nodeResolve} from "@rollup/plugin-node-resolve";
import { terser } from 'rollup-plugin-terser';

export default {
  input: "src/clojure_highlighter/main.js",
  output: {
    file: "public/clojure_highlighter.js",
    format: "iife"
  },
  plugins: [nodeResolve(), terser()]
};
