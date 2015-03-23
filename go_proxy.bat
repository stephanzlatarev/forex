echo packaging application ...

call mvn -DskipTests -DskipITs clean package

echo
echo starting application ...

java -Dhttp.proxyHost=proxy -Dhttp.proxyPort=8080 -Dhttps.proxyHost=proxy -Dhttps.proxyPort=8080 -jar target/forex-1.0.0.jar
