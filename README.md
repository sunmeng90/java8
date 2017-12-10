Timer & TimerTask
Timer is a facility for threads to schedule tasks for future execution in a background thread. Tasks may be scheduled for one-time execution, or for repeated execution at regular intervals. Corresponding to each Timer object is a single background thread that is used to execute all of the timer's tasks, sequentially. Timer tasks should complete quickly. If a timer task takes excessive time to complete, it "hogs" the timer's task execution thread. After the last live reference to a Timer object goes away and all outstanding tasks have completed execution, the timer's task execution thread terminates gracefully (and becomes subject to garbage collection).This class is thread-safe: multiple threads can share a single Timer object without the need for external synchronization.
TimerTask is a task that can be scheduled for one-time or repeated execution by a Timer. 

example:
```java
       //run a task once
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Run task 3 seconds after application startup");
            }
        }, 3 * 1000);

        //run a task at the specific time
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Run a task at the specific time");
            }
        }, new Date());

        //Run a task repeatedly at a fixed delay after a fixed duration
        //each execution is scheduled relative to the actual execution time of the previous execution. If an execution is delayed for any reason (such as garbage collection or other background activity), subsequent executions will be delayed as well
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("Run a task every 1 seconds after 5 seconds delay");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 5*1000, 1000);

        //Run a task repeatedly at a fixed delay after a fixed duration
        //each execution is scheduled relative to the scheduled execution time of the initial execution. If an execution is delayed for any reason (such as garbage collection or other background activity), two or more executions will occur in rapid succession to "catch up."
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("Run a task every 1 seconds after 10 seconds delay at fixed rate");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },10*1000, 1);

```

WebService
Jdk implementation





** Java 8 习惯用语 https://www.ibm.com/developerworks/cn/java/j-java8idioms1/index.html?ca=drs- **
###Singleton implementation & Double checking

#Concurrent
##BlockingQueue
BlockingQueue is a thread safe queue that supports operations that wait for queue to be non-empty when retrieving an element, and wait for space to become available when storing an element. Blockingqueue implementations are designed primarily for producer-consumer queues.

Example: Classical producer-consumer usage: Producer and Consumer that share a blocking queue for putting and retrieving elements for queue. 
```java

/**
 * Created by meng_ on 7/1/2017.
 */
public class BlockingQueueBasic {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(producer1).start();
        new Thread(producer2).start();
        new Thread(producer3).start();

        new Thread(consumer).start();
    }


}

class Producer implements Runnable {
    private BlockingQueue<String> queue;

    private static AtomicInteger count = new AtomicInteger(0);

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            try {
                String item = Thread.currentThread().getName() + " => " + (count.incrementAndGet());
                queue.put(item);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}


class Consumer implements Runnable {
    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            try {
                String item = queue.poll();
                System.out.println(queue);
                Thread.sleep(200);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



```

#JMX
##JMX   overview
The JMX technology provides a simple, standard way of managing resources such as applications, devices, and services. Because the JMX technology is dynamic, you can use it to monitor and manage resources as they are created, installed and implemented. You can also use the JMX technology to monitor and manage the Java Virtual Machine (Java VM).

##Architecture of JMX
The JMX technology can be divided into three levels, as follows:  

* Instrumentation 
* JMX agent 
* Remote management 

To manage resources using the JMX technology, 1)you must first instrument the resources such as applications, services, devices using Java objects known as MBeans. 2)Once a resource has been instrumented by MBeans, it can be managed through a JMX agent. The core component of a JMX agent is the MBean server, a managed object server in which MBeans are registered. To remotely access the JMX agent, you need protocol adaptors and connectors to handle the communication between the manager and JMX agent.

###JMX best practice
* Take a resouces(application, device, service etc.)
* Expose the functionality you want to manage through a managed bean(MBean)
* Register you MBeans with a MBeanServer that is part of a JMX agent
* Access the MBeans through connectors or portocols adaptors remotely to manage the resource from your management client.

####use case
Just imagine we have a cache service in our application, we want to manage the cahced entries lifecycle(say clear all cache). now what we need to do is:

Define an MBean with functionality to clear cache
```java
public interface CacheMBean {
    /**
     * clear all cache in memory
     */
    public void clearCache();

    /**
     * check the cache status
     * 
     * @return
     */
    public boolean isEmpty();

}

```

Implementation
```java

public class Cache implements CacheMBean {

    private boolean empty = false;

    @Override
    public synchronized void clearCache() {
        System.out.println("Starting clearing the cache");
        try {
            for (int i = 0; i < 10; i++) {
                System.out.print(".");
                Thread.sleep(500);
            }
            System.out.println("");
            this.empty = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally");
        }

        System.out.println("All cache cleared");

    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

}


```

Set up JMX agent, crate MBServer and register MBeans
```java
import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * Java agent with the MBeanServer in which MBeans are registed
 * @author meng_
 *
 */
public class CacheAgent {

    public static void main(String[] args) throws Exception {
        //Crate platform MBeanServer
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        //create name for MBeans
        ObjectName oname = new ObjectName(meng");
        Cache mbean = new Cache();
        //Register MBeans in MBeanServer
        mbs.registerMBean(mbean, oname);
        System.out.println("waiting");
        Thread.sleep(Long.MAX_VALUE);
    }
}

```
Access JMX agent through Jconsole/JVisualVM  

* Connect to the java process and open MBeans tab, drill down to the package and open CacheManagement MBean  
* Go to Operation tab and click on clearCache, check console log  
* When finsihed then check the isEmpty property in Attributes tab  

# java8
JDK8 new features

##lambda & functional interface
The biggest and most awaited change that Java 8 brings to us is *lambda*. Lambda allows us to pass functionality as argument to another method, or treat code as data. The syntax of lambda is:

    parameter -> expression body

There are three parts in lambda expression:

*  A comma-separated list of formal parameters enclosed in parentheses
*  The arrow token, ->
*  A body, which consists of a single expression or a statement block. 

Some valid example

```java
    (int x, int y) -> x + y

    () -> 42

    (String s) -> { System.out.println(s); }
```

Characteristics of Lambda

* Optional type declaration − No need to declare the type of a parameter. The compiler can inference the same from the value of the parameter.

* Optional parenthesis around parameter − No need to declare a single parameter in parenthesis. For multiple parameters, parentheses are required.

* Optional curly braces − No need to use curly braces in expression body if the body contains a single statement.

* Optional return keyword − The compiler automatically returns the value if the body has a single expression to return the value. Curly braces are required to indicate that expression returns a value.


What will be returned when  `this` is used in lambda:
`this` is not referencing to the instance of the lambda expression. It is referencing to the instance of the class you define the lambda expression in.


[Java Language Specification](http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.27.2)
 
[Lambda-QuickStart](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html)


###funciontal interface
How does lambda expressions fit into Javas type system? Each lambda corresponds to a given type, specified by an interface. A so called functional interface must contain exactly one abstract method declaration. Each lambda expression of that type will be matched to this abstract method. Since default methods are not abstract you're free to add default methods to your functional interface.
An interface with a single abstract method. The Java API has many one-method interfaces such as Runnable, Callable, Comparator, ActionListener and others. 

```java
@FunctionalInterface
public interface ITrade {
  public boolean check(Trade t);
}
//or
@FunctionalInterface
public interface IComponent {
  // Functional method - note we must have one of these functional methods only
  public void init();
  // default method - note the keyword default
  default String getComponentName(){
    return "DEFAULT NAME";
  }
  // default method - note the keyword default
  default Date getCreationDate(){
    return new Date();
  }
}
```

####How to use functional interface
```java
@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}
Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
Integer converted = converter.convert("123");
System.out.println(converted);    // 123
```

[Java 8 functional interfaces](https://www.oreilly.com/learning/java-8-functional-interfaces)


##Method Reference
Method references are shortcuts for calling existing methods via keyword ```::```.
example of method reference:
```java
    Class::static_method
    instance::method
    Class::new 
```

##Optional

`Optional` is a container: it can hold a value of some type T or just be null. It provides a lot of useful methods so the explicit null checks have no excuse anymore.

How to use Optional correctly?[Java 8 Optional Use Cases](https://dzone.com/articles/java-8-optional-use-cases)
Do:
* Optional should be used as method return result

Don't
* Optional should not be used as a collection wrapper even if as a method return result. In a case when there’s no element to return, an empty instance is superior to empty Optional and null as it conveys all necessary information.
```java
Optional<List<Item>> itemsOptional = getItems(); //discouraged usage
```

* Optional should not be used as method optional parameters. When a method can accept optional parameters, it’s preferable to adopt the well-proven approach and design such case using method overloading. 
```java
public SystemMessage(String title, String content, Optional<Attachment> attachment) {//discouraged use case
    // assigning field values
}

//Use method overloading
public SystemMessage(String title, String content) {
    this(title, content, null);
}

public SystemMessage(String title, String content, Attachment attachment) {
    // assigning field values
}
```
* Don’t use on POJO fields getters. Routinely using it as a return value for getters in POJO would definitely be over-use.

* Don’t use as class fields' container

* Optional is not a silver bullet


##Stream
[java8之lambda表达式（变量作用域）](https://my.oschina.net/fhd/blog/419892)
[](http://www.cnblogs.com/aoeiuv/p/5911692.html)

[Java 8 Features Tutorial – The ULTIMATE Guide](https://www.javacodegeeks.com/2014/05/java-8-features-tutorial.html)
http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html

http://www.java67.com/2014/09/top-10-java-8-tutorials-best-of-lot.html

http://blog.takipi.com/15-must-read-java-8-tutorials/

http://winterbe.com/posts/2014/03/16/java-8-tutorial/

https://my.oschina.net/benhaile/blog/175012



##Concurrent
A Future represents the result of an asynchronous computation and provides a way to check if the computation is complete, to wait for its completion, and to retrieve the result of the computation. 
While CompleteFuture is an extension for Future, which supports CompletionStage with dependent functions and actions that trigger upon its completion.

The real benefit of CompletableFuture is that it allows you to coordinate activities without writing nested callbacks.

For instance, you want to trigger an action on the completion of a future. CompleteFuture makes it easy to chain Future together.
```java
    /**
    Introduce an artificial delay
    Apply a function when previous stage is finished
    Apply a consumer when previous stage is finished
    Retrieve the finished result 
    */
    private String sleepThenReturnString() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        return "42";
    }
    
    //.....
    
    CompletableFuture.supplyAsync(() -> this::sleepThenReturnString)
        .thenApply(Integer::parseInt)
        .thenApply(x -> 2 * x)
        .thenAccept(System.out::println)
        .join();
    System.out.println("Running...");

```

  