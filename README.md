# Camel, Snake, Pascal, Kebab case kata

## Purpose

This is a study (i.e., an etude), with a narrow target

  * how to dispatch to different functions coming from a string input
  * how to apply property-based testing (such as quickcheck)


## Dispatching

In `dispatching/*` I have written different ways of dispatching the output format:

  * by-map: link a string to a function into a map
  * by-function-name: grab the function by its name, finding it in the namespace
  * by-macro: expanding on the previous, using a macro. The macro is not needed, just a scenario where to use it 
  * by-function-name-no-macro: expanding on the previous, using an ear-muff binding (`*ns*`) to refer to the current namespace

## Misc notes

source: trikitrok's https://gist.github.com/trikitrok/a97d330bacb1f56fe5ee027c12ff273a

Warning: this kata is not finished, it does not include processing the data structures part

