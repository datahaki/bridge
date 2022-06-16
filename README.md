![alpine_877](https://user-images.githubusercontent.com/4012178/116814864-1b1a1580-ab5b-11eb-97e6-1441af4ececa.png)

# ch.alpine.bridge

<<<<<<< HEAD
Library for Java, version `0.3.7-jdk-11`
=======
Library for Java. Features include 

* wrapper for `jfreechart` library, API inspired by Mathematica
* reflection-based serialization to properties-files, and gui generation
* data structures `BoundedPriorityQueue`, `DisjointSets`, ...
* class discovery
>>>>>>> master

## Plots

![Spectrogram](https://user-images.githubusercontent.com/4012178/138568940-d96657ee-9e42-4065-aa93-64cbee7b3087.png)

## Reflection

For non-final fields of type `String`, `Boolean`, `File`, `Color`, etc. of a class

```java
public class MyConfig {
  public String text = "abc";
  public Boolean flag = false;
  public File file = new File("/home/user/text.txt");
  public Color color = Color.RED;
}
```

a dialog is auto-generated that allows the user to edit the values of the fields of an instance live.

![panelfieldseditor](https://user-images.githubusercontent.com/4012178/138581130-2be4d8ec-c15b-4ccb-83e5-fcd7bbda2a4f.png)

The code that generates the dialog is shown below

```java
MyConfig myConfig = new MyConfig();
PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(myConfig);
panelFieldsEditor.addUniversalListener(() -> System.out.println("my config changed"));
JFrame jFrame = new JFrame();
jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
jFrame.setContentPane(panelFieldsEditor.createJScrollPane());
jFrame.setBounds(100, 100, 320, 200);
jFrame.setVisible(true);
```

## Integration

From time to time, a version is deployed and made available for maven integration. Specify `repository` and `dependency` of the bridge library in the `pom.xml` file of your maven project:

```xml
<repositories>
  <!-- other repositories -->
  <repository>
    <id>bridge-mvn-repo</id>
    <url>https://raw.github.com/datahaki/bridge/mvn-repo/</url>
    <snapshots>
      <enabled>true</enabled>
      <updatePolicy>always</updatePolicy>
    </snapshots>
  </repository>
</repositories>

<dependencies>
  <!-- other dependencies -->
  <dependency>
    <groupId>ch.alpine</groupId>
<<<<<<< HEAD
    <artifactId>java</artifactId>
    <version>0.3.7-jdk-11</version>
=======
    <artifactId>bridge</artifactId>
    <version>0.3.6-jdk-11</version>
>>>>>>> master
  </dependency>
</dependencies>
```

For Java 17, for `version` use [...].

For Java 11, for `version` use `1.0.6-jdk-11`.

The source code is attached to every release.

The branch `master` always contains the latest features for Java 17, and does not correspond to the most recent deployed version generally.