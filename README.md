# Camel, Snake, Pascal, Kebab case kata

source: trikitrok's https://gist.github.com/trikitrok/a97d330bacb1f56fe5ee027c12ff273a

Warning: this kata is not finished.

## Dispatching

In `dispatching/*` I have written different ways of dispatching the output format:

  * by-map: link a string to a function into a map
  * by-function-name: grab the function by its name, finding it in the namespace
  * by-macro: expanding on the previous, using a macro
  * by-function-name-no-macro: expanding on the previous, using an ear-muff binding (`*ns*`) to refer to the current namespace

