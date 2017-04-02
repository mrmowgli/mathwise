# Mathwise

A Clojure application to visualize simple math functions.

## Building
To build this you will need [Leiningen](https://github.com/technomancy/leiningen) and Java SDK installed.

To Build, go to the top folder of this repository and then run:

```
lein uberjar
cp ./target/mathwise*standalone.jar ./mathwise.jar
```

## Usage

```
java -jar mathwise.jar -h 400 -w 400 xor
```

## Roadmap 

### v1.1
- add more visualizations
- add framebuffer to avoid screen redraw issues
- Add multi-threading and lazy evaluation.

### v1.2
- Add close handlers, so the application stops processing the function
- Add function chaining

## License

Copyright © 2017 Andre Lewis

Distributed under the Eclipse Public License version 1.0.
Some portions of this code are licensed under their respective owners, in particular Clojure libraries and components.