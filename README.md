### Avicenna Dependency Injection Framework

####Synopsis

**Avicenna** is a simple lightweight dependency injection framework for Java and Android, inspired by Dagger and CDI. It is mainly designed to reduce overheads of dependency injection in simple Java environments like Android. Using a full featured DI framework like Spring IoC, makes developers engage with many difficulties to configure these frameworks and use them. Such lightweight DI frameworks are best for fornt-end development where you are about to design a nice little logic for your front-end application.

#### Adding to your project

It is really easy for projects built by Maven. Just add this dependency to your pom.xml file.

```xml
  <dependency>
    <groupId>org.labcrypto</groupId>
    <artifactId>avicenna</artifactId>
    <version>1.4</version>
  </dependency>
```

For other build systems, you have to use their proper dependency declaration syntax. If you're not using any build system, you can download it as a **jar** file and add it to your project.

Notice that 1.4 is the current latest version and you should always use the latest version.

#### Simple Start

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
