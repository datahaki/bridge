![alpine_877](https://user-images.githubusercontent.com/4012178/116814864-1b1a1580-ab5b-11eb-97e6-1441af4ececa.png)

# ch.ethz.idsc.java

Library for Java, version `0.1.8`

## Integration

Specify `repository` and `dependency` of the tensor library in the `pom.xml` file of your maven project:

```xml
<repositories>
  <repository>
    <id>tensor-mvn-repo</id>
    <url>https://raw.github.com/datahaki/java/mvn-repo/</url>
    <snapshots>
      <enabled>true</enabled>
      <updatePolicy>always</updatePolicy>
    </snapshots>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>ch.alpine</groupId>
    <artifactId>java</artifactId>
    <version>0.1.8</version>
  </dependency>
</dependencies>
```

The source code is attached to every release.
