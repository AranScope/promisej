public class PromiseFileReaderTest {

    public PromiseFileReaderTest() {
        readASyncTest();
    }

    private void readASyncTest(){
        PromiseFileReader pfr = new PromiseFileReader();

        // Read and print the contents of a text file - async / non-blocking
        pfr.readLines("examples/test.txt").then(
                lines -> {
                    for(String line: lines) System.out.println(line);
                },
                error -> System.out.println("Cant read the file :/")
        );

        System.out.println("I will print first"); //showing that readLines is non blocking

        // A more simple example, only dealing with the success scenario
        pfr.readLines("examples/test.txt").then(lines -> System.out.println(lines.length + " lines"));
    }

    public static void main(String[] args) {
        new PromiseFileReaderTest();
    }
}
