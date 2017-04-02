# Introduction to Mathwise

## What is Mathwise?
Initially this is a simple math function visualization tool, that takes functions that take two numbers, perform some operation on them, and return another number. That returned value is mapped to color and displayed, creating patterns to help visualize terms.

This allows an interesting set of operations to be visualized.  

Initially these visualizations include:

* sine      - A modified Sin(x) * Cos(y)
* xor       - Bitwise xor (Default) 
* and       - Bitwise and
* or        - Bitwise or
* plus      - Addition
* minus     - Subtraction 
* multiply  - Multiplication 
* right     - Bitwise shift right
* left      - Bitwise shift left
* and-not   - Bitwise and-not

The visualizations shown are based on simple functions available within the Clojure language and Java.

### Caveats
**Color** 

To visualize the colors, the returned value is mapped to a color.  The returned value will often overflow each of the color channels.
In order to fix this we run a remainder function of the return value and 255.  This means we lose scale information.  The mapped colors are interesting, but not a true representation of the values.

**Operators** 

Not all operations are represented, in particular the `/` operator is missing, as it causes a divide by zero error.

**Precision** 

The precision of the numbers is radically reduced for larger numbers, as the values are currently mapped to Java types and thus overflow.
Some operators, like Shift-Left, or Shift Right will automatically lose precision as we are throwing away bits.

**Cancelling** 

Currently efforts to cancel by closing the window only remove the window, but the algorithm tries to finish calculating all the values.  For smaller operations this isn't an issue, but for larger ones it, will wait for some time before completing.


