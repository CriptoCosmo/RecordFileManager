# RecordFileManager
Allow to read and write positional file by shema defined 

## Examples

### Read

reading  `input.txt` file

```
public static void main(String...args){

   String inputFile = "C:\\Users\\User1\\Desktop\\input.txt";
   String outputFile = "C:\\Users\\User1\\Desktop\\out.txt";

   try {
      RecorFileReader recorFileReader = new RecorFileReader(inputFile);
      RecorFileWriter recorFileWriter = new RecorFileWriter(outputFile);

      recorFileWriter.write(recorFileReader.getElements());

   } catch (IOException e) {
      e.printStackTrace();
   }

}
```

```txt
01ABCDFGHILMNOPQR99999999999991234567890    18/04/2019    NOTE 1
```

```java
public static void main(String...args){

   String inputFile = "input.txt";
   
   try {
      RecorFileReader recorFileReader = new RecorFileReader(inputFile);

      for (Map element: recorFileReader.getElements()) {
         System.out.print(element);
      }

   } catch (IOException e) {
      e.printStackTrace();
   }

}
```

Result

```
{date=4/2019, sequence=9999999999991234, number=567890, codFiscale=ABCDFGHILMNOPQR9, id=01}
```



## Write

write file 

```java
public static void main(String...args){

   String inputFile = "input.txt";
   String outputFile = "out.txt";

   try {
      RecorFileReader recorFileReader = new RecorFileReader(inputFile);
      RecorFileWriter recorFileWriter = new RecorFileWriter(outputFile);

      recorFileWriter.write(recorFileReader.getElements());

   } catch (IOException e) {
      e.printStackTrace();
   }

}
```



