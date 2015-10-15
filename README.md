# cli-tools

A Clojure library designed to learn Clojure while creating some useful command line tools 

## Usage

Install [leiningen](leiningen.org)

```
lein run path/to/file
lein compile
lein uberjar
```

If you just have Java you can run the jar from command line like this
```
java -jar ./target/cli-tools-0.1.0-SNAPSHOT-standalone.jar -i /tmp/test.txt -m split -o /tmp/
```

Options
```
-m Specify the mode. split or update
-i Input file path
-o Output file dir
-n Number of files to be split into
```

## License

Copyright © 2015 NIRANJANBPRITHVIRAJ

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
