# JSON to ORM converter

This tool will allow you to quickly prototype your ORM for Django and Hibernate from a JSON Object you will provide. It can also generate SQL create statements for Postgresql from the same JSON Object.

For more information about the tool you can head to [this article on medium](https://) that provides further information about the tool, the why it was build and the approach.
You can also visit [Json to ORM converter tool online](https://www.p2sdev.com/projects/json-to-orm-converter)

## How to use it
You can use the command line tool
```
git clone https://github.com/PSteph/jsonToOrmMapper.git && cd jsonToOrmMapper
java -jar target/jsonToOrmMapper-0.0.1-SNAPSHOT.jar --filename /pathToFile.json --option [hibernate|django|postgres]
```
You can also include the jar in your project and call the converter this way for your desired ORM
```
new JSONToConverter(stringJsonContent).getORMModel(ORMModel.DJANGO_MODEL);
// or
new JSONToConverter(stringJsonContent).getORMModel(ORMModel.HIBERNATE);
```
or if you want to generate SQL
```
new JSONToConverter(stringJsonContent).getSQL(DATABASE.POSTGRES, SQLOperation.CREATE);
```

## Example
Converting 
![json to be converted](https://github.com/PSteph/jsonToOrmMapper/tree/master/images/personJson.png)
JSON converted into Django Model
![corresponding Django Model](https://github.com/PSteph/jsonToOrmMapper/tree/master/images/person-djangoModel.png)
