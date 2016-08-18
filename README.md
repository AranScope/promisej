# promisej
A lightweight Java interpretation of JS promises.

## Examples
Example usages can be found in the examples directory. Here is a brief look at the syntax:

```Java
fr.readLines("hello.txt").then(
  lines -> print("there are " + lines.length + " lines"),
  error -> print("an error occured")
);
```
```Java
physics.raycast(point, angle).then(
    hit -> print(hit.points),
    nohit -> print("no collision")
);
```

## Usage

### Sync (blocking)
Change the return type of your function to 'Promise<Object>'

Return a promise constructed with the (data, wasSuccessful) constructor.

When you call the function e.g. fr.readLines(), call .then(lambda success) or .then(lambda success, lambda fail)

### ASync (non-blocking)
Change the return type of your function to 'Promise<Object>'

At the start of the function, declare a default constructed promise i.e. Promise<Object> prom = new Promise<>();

Perform all of your 'intensive' operations inside a new thread

When an operation is successful call 'promisename'.resolve(data, wasSuccessful)

The last line of the function should return the default constructed promise (i.e. non-blocking)

When you call the function e.g. fr.readLines(), call .then(lambda success) or .then(lambda success, lambda fail)
