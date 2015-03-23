## Avicenna Dependency Injection Framework

###Synopsis

**Avicenna** is a simple lightweight dependency injection framework for Java and Android, inspired by Dagger and CDI. It is mainly designed to reduce overheads of dependency injection in simple Java environments like Android. Using a full featured DI framework like Spring IoC, makes developers engage with many difficulties to configure these frameworks and use them. Such lightweight DI frameworks are best for fornt-end development where you are about to design a nice little logic for your front-end application.

### Adding to your project

It is really easy for projects using Maven build system. Just add following dependency tag to your *pom.xml* file.

```xml
  <dependency>
    <groupId>org.labcrypto</groupId>
    <artifactId>avicenna</artifactId>
    <version>1.4</version>
  </dependency>
```
If you are going to use **Avicenna** in an Android project using Gradle build system, it is easier to add following line to *build.gradle* file. This will trigger Gradle to download **Avicenna** and add it to your project.

```groovy
  compile 'org.labcrypto:avicenna:1.4';
```

For other build systems, you have to use their proper dependency declaration syntax. If you're not using any build system, you can download it as a **jar** file and add it to your project.

Notice that 1.4 is the current latest version and you should always use the latest version.

### Quick Start

#### Hello World

Here I start with a very simple usage; Hello World example. First of all, we need a dependency factory. It is a class annotated by **DependencyFactory** annotation. It has a method which returns a **String** reference and we will inject this reference wherever a **String** is needed. Notice, **names don't matter**.

```Java
@DependencyFactory
class HelloWorldDependencyFactory {
  @Dependency
  public String getSomeHelloWorldMessage() {
    return "Hello world ...";
  }
}
```

Then, we need a class for getting injected. This class has a field of type **String** which it will be injected.

```Java
class InjectionTarget {
  @InjectHere
  private String messageField;
  
  public InjectionTarget() {
    Avicenna.inject(this);
  }
  
  public void sayHelloToWorld() {
    System.out.println(messageField);
  }
}
```

And finally, the **Main** class with **main** method.

```Java
public class Main {
  public static void main(String[] args) {
    Avicenna.addDependencyFactory(new HelloWorldDependencyFactory());
    InjectionTarget object = new InjectionTarget();
    object.sayHelloToWorld();
  }
}
```

#### Generics

Generis are supported by **Avicenna**. Here is an example.

```Java
@DependencyFactory
class ListsDependencyFactory {
  @Dependency
  public List<String> getListOfStrings() {
    // Make a list of strings and return.
  }
  
  @Dependency
  public List<Double> getListOfDoubles() {
    // Make a list of doubles and return.
  }
  
  @Dependency
  public Set<String> getSetOfStrings() {
    // Make a set of strings and return.
  }
}
```
Then we can easily request injection like this.

```Java
class InjectionTarget {

  @InjectHere
  private List<String> listOfStrings;
  
  @InjectHere
  private List<Double> listOfDoubles;
  
  @InjectHere
  private Set<String> listOfStrings;
  
  static {
    // This line should be moved to a better place!
    Avicenna.addDependencyFactory(new ListsDependencyFactory());
  }
  
  public InjectionTarget() {
    Avicenna.inject(this);
  }
}
```
#### Qualifiers

**Avicenna** supports qualifiers. Qualifiers are annotations added to **Dependency** sources to make them unique. All qualifier annotations must have **Qualifier** annotation themselves.

```Java
@Qualifier
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HelloWorld {
}

@Qualifier
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ByeWorld {
}

@DependencyFactory
class QualifersDepFac {

  @Dependency
  private String message = "This is a message ...";
  
  @Dependency
  @HelloWorld
  private String helloWorldMessage = "Hello World ...";
  
  @Dependency
  @ByeWorld
  public String getByeWorldMessage() {
    return "Bye World ...";
  }
  
  @Dependecny
  @HelloWorld
  @ByeWorld
  public String getAnotherMessage() {
    return "Another message ...";
  }
}
```

As you can see above, we can have multiple qualifiers on a single dependency source. Above dependenvy factory has four distinct dependency sources.

```Java
class InjectionTarget {
  
  @InjectHere
  private String message;
  
  @InjectHere
  @HelloWorld
  private String helloWorldMessage;
  
  @InjectHere
  @ByeWorld
  private String byeWorldMessage;
  
  @InjectHere
  @ByeWorld
  @HelloWorld
  private String anotherMessage;
  
  static {
    Avicenna.addDependencyFactory(new QualifiersDepFac());
  }
  
  public InjectionTarget() {
    Avicenna.inject(this);
  }
}
```
More features will be added soon.

### Contributors
1. [Kamran Amini](https://github.com/kamcpp)

### License

**Avicenna** is free, open source and redistributable under [Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
