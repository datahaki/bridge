![alpine_877](https://user-images.githubusercontent.com/4012178/116814864-1b1a1580-ab5b-11eb-97e6-1441af4ececa.png)

# ch.alpine.bridge

Library for Java

![](https://github.com/datahaki/bridge/actions/workflows/mvn_test.yml/badge.svg)

Features include 

* data plotting API inspired by Mathematica: `ListPlot`, `Spectrogram`, ...
* reflection-based serialization to properties-files, and gui generation
* data structures `BoundedPriorityQueue`, `DisjointSets`, ...
* class discovery

3rd party dependencies are

* `FlatLaf`

## Plots

![MatrixPlot1](https://user-images.githubusercontent.com/4012178/200159269-ed3b0969-3660-4bf3-ade6-0844d799e1e2.png)

![ReImPlot0](https://user-images.githubusercontent.com/4012178/200159270-7e441eac-5ace-4070-830d-dfa3918eb464.png)

![Spectrogram0](https://user-images.githubusercontent.com/4012178/200159271-29157014-c2f3-4aa9-817d-d8b7280e9ab5.png)

![TruncatedDistribution0](https://user-images.githubusercontent.com/4012178/200159273-dfe78967-dc64-4cc5-a677-f931dc7700b9.png)

![Cepstrogram0Re](https://user-images.githubusercontent.com/4012178/201620914-60a8e91a-8a69-470f-9a7a-8c153aeea83b.png)

## Dialogs

![LocalTime selection](https://user-images.githubusercontent.com/4012178/178134198-8b94131f-6163-408b-9b26-2bfb344d0da3.png)

![Font selection](https://user-images.githubusercontent.com/4012178/178134124-766d1067-8645-4060-8c06-33cb252e5c97.png)

## Reflection

For non-final fields of type `String`, `Boolean`, `Integer`, `File`, `Font`, `Color`, `LocalDate`, etc. of a class

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
<dependencies>
  <!-- other dependencies -->
  <dependency>
    <groupId>ch.alpine</groupId>
    <artifactId>bridge</artifactId>
    <version>0.3.6</version>
  </dependency>
</dependencies>

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
```

For Java 11, for `version` use `0.4.1-jdk-11`.

The source code is attached to every release.

The branch `master` always contains the latest features for Java 17, and does not correspond to the most recent deployed version generally.