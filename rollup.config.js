import nodeResolve from '@rollup/plugin-node-resolve';
import commonjs from '@rollup/plugin-commonjs';
import typescript from '@rollup/plugin-typescript';

import path from 'path';

export default {
  input: path.resolve('src/index.ts'),
  output: {
    file: 'dist/plugin.js',
    format: 'cjs',
    name: 'DocumentDetectorPlugin', // TODO: change this
    globals: {
      '@capacitor/core': 'capacitorExports',
    },
    sourcemap: true,
  },
  plugins: [
    nodeResolve({
      mainFields: ['module', 'main', 'jsnext:main', 'browser'],
      extensions: ['.js', '.jsx', '.ts', '.tsx'],
      // allowlist of dependencies to bundle in
      // @see https://github.com/rollup/plugins/tree/master/packages/node-resolve#resolveonly
      // resolveOnly: ['lodash'],
    }),
    commonjs(),
    typescript(),
  ],
};
