# RecordFileManager
Allow to read and write positional file by shema defined 

## Dependecy

```xml
<project>
    ...
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    ...
    <dependencies>
        ...
        <dependency>
            <groupId>com.github.CriptoCosmo</groupId>
            <artifactId>RecordFileManager</artifactId>
            <version>master-SNAPSHOT</version>
        </dependency>
        ...
    </dependencies>
    ...
</project>
```

for details : https://jitpack.io/#CriptoCosmo/RecordFileManager/master-SNAPSHOT

## Schema 

Example schema definition `input-schema.properties`

```properties
# KEY = <length-of-field>
id=2
codFiscale=16
sequence=16
number=10
FILLER_1=4
date=10
FILLER_2=4
Note=22	
```

or if you want use custom model class with annotation

```java
public class ModelMap {

   @Definition(name="id", size=2)
   private String id ;

   @Definition(name="codiceFiscale", size=16)
   private String codiceFiscale ;

   @Definition(name="sequence", size=12)
   private String sequence ;

   @Definition(name="number", size=10)
   private String number ;

   @Definition(size=4)
   private Filler filler ;

   @Definition(name="date", size=10)
   private String date ;

   @Definition(size=4)
   private Filler filler2 ;

   @Definition(name="note", size=6)
   private Filler note ;

}
```

## Read

reading  `input.txt` file

```txt
01ABCDFGHILMNOPQR99999999999991234567890    18/04/2019    NOTE 1
```

```java
public static void main(String...args) throws IOException {

   String inputFile = "input.txt";

   RecordFileReader recorFileReader = new RecordFileReader(inputFile, ModelMap.class);

   for(Map element: recorFileReader.getElements()) {
		System.out.print(element);
   }

}
```
Result Map

```json
{
	date=4/2019, 
	sequence=9999999999991234, 
	number=567890, 
	codFiscale=ABCDFGHILMNOPQR9, 
	id=01
}
```

## Write

```java
public static void main(String...args) throws IOException {
  
    String outputFile = "out.txt";

    Map<String,String> elements = new HashMap<>();

    elements.put("id","01");
    elements.put("codiceFiscale","ABCDFGHILMNOPQR9");
    elements.put("sequence","9999999999991234");
    elements.put("number","567890");
    elements.put("date","2019");
    elements.put("note","asd");

    RecorFileWriter recorFileWriter = new RecorFileWriter(outputFile, ModelMap.class);
    recorFileWriter.write(Arrays.asList(elements));

}
```



# Note

By default  `RecorFileReader` loocking for `input-schema.properties` file in resource folder but you can pass as parameter 

- File name of your schema 

- Directly the `Properties` object

- `.class` of your custom entity

  

Like `RecorFileReader` also `RecorFileWriter`  loocking for `output-schema.properties` or `input-schema.properties` else you can pass as parameter 

- File name of your schema 
- Directly the `Properties` object
- `.class` of your custom entity

