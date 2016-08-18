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

So if we want to make a function that uses promises and callbacks

```Java
public Promise<Image> loadImage(String url) {
    Promise<Image> image = new Promise<>();
    
    new Thread(() -> {
      Image loadedImage;
      
      try {
        // you can probably work this out
      } catch (IOException ex) {
        image.resolve(loadedImage, false); // false as we couldn't load the image
      }
      
      image.resolve(loadedImage, true); // true as we've loaded the image
    }).start();
    
    return image;
}
```
And the usage
```Java
Utils.loadImage("cats.png").then(
  image -> drawImage(image),
  error -> print("No cats :(")
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
