# RookieDB

![The official unofficial mascot of the class projects](images/derpydb-small.jpg)

This repo contains a bare-bones database implementation, which supports
executing simple transactions in series. In the assignments of
this class, you will be adding support for
B+ tree indices, efficient join algorithms, query optimization, multigranularity
locking to support concurrent execution of transactions, and database recovery.

Specs for each of the projects will be released throughout the semester at here: [https://cs186.gitbook.io/project/](https://cs186.gitbook.io/project/)

## Overview

In this document, we explain

- how to fetch the released code
- how to fetch any updates to the released code
- how to setup a local development environment
- how to run tests using IntelliJ
- how to submit your code to turn in assignments
- the general architecture of the released code

## Fetching the released code

For each project, we will provide a GitHub Classroom link. Follow the
link to create a GitHub repository with the starter code for the project you are
working on. Use `git clone` to get a local copy of the newly
created repository.

## Fetching any updates to the released code

In a perfect world, we would never have to update the released code because
it would be perfectly free of bugs. Unfortunately, bugs do surface from time to
time, and you may have to fetch updates. We will provide further instructions
via a post on Piazza whenever fetching updates is necessary.

## Setting up your local development environment

You are free to use any text editor or IDE to complete the assignments, but **we
will build and test your code in a docker container with Maven**.

We recommend setting up a local development environment by installing Java
8 locally (the version our Docker container runs) and using an IDE such as
IntelliJ.

[Java 8 downloads](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

If you have another version of Java installed, it's probably fine to use it, as
long as you do not use any features not in Java 8. You should run tests
somewhat frequently inside the container to make sure that your code works with
our setup.

To import the project into IntelliJ, make sure that you import as a Maven
project (select the pom.xml file when importing). Make sure that you can compile
your code and run tests (it's ok if there are a lot of failed tests - you
haven't begun implementing anything yet!). You should also make sure that you
can run the debugger and step through code.

## Running tests in IntelliJ

If you are using IntelliJ, and wish to run the tests for a given assignment
follow the instructions in the following document:

[IntelliJ setup](intellij-test-setup.md)

## Submitting assignments

To submit a project, navigate to the cloned repo, and use
`git push` to push all of your changes to the remote GitHub repository created
by GitHub Classroom. Then, go to Gradescope class and click on the
project to which you want to submit your code. Select GitHub for the submission
method (if it hasn't been selected already), and select the repository and branch
with the code you want to upload and submit. If you have not done this before,
then you will have to link your GitHub account to Gradescope using the "Connect
to GitHub" button. If you are unable to find the appropriate repository, then you
might need to go to https://github.com/settings/applications, click Gradescope,
and grant access to the `berkeley-cs186-student` organization.

Note that you are only allowed to modify certain files for each assignment, and
changes to other files you are not allowed to modify will be discarded when we
run tests.

## The code

As you will be working with this codebase for the rest of the semester, it is a good idea to get familiar with it. The code is located in the `src/main/java/edu/berkeley/cs186/database` directory, while the tests are located in the `src/test/java/edu/berkeley/cs186/database directory`. The following is a brief overview of each of the major sections of the codebase.  
由于您将在本学期剩余时间使用此代码库，因此最好熟悉它。代码位于 `src/main/java/edu/berkeley/cs186/database` 目录中，而测试位于 `src/test/java/edu/berkeley/cs186/database` 目录中。以下是代码库每个主要部分的简要概述。

### cli

The cli directory contains all the logic for the database's command line interface. Running the main method of CommandLineInterface.java will create an instance of the database and create a simple text interface that you can send and review the results of queries in. **The inner workings of this section are beyond the scope of the class** (although you're free to look around), you'll just need to know how to run the Command Line Interface.  
cli 目录包含数据库命令行界面的所有逻辑。运行 `CommandLineInterface.java` 的 `main` 方法将创建一个数据库实例，并创建一个简单的文本界面，您可以在其中发送和查看查询结果。本部分的内部工作原理超出了该类的范围（尽管您可以随意看看），您只需要知道如何运行命令行界面即可。

#### cli/parser

The subdirectory cli/parser contains a lot of scary looking code! Don't be intimidated, this is all generated automatically from the file RookieParser.jjt in the root directory of the repo. The code here handles the logic to convert from user inputted queries (strings) into a tree of nodes representing the query (parse tree).  
子目录 `cli/parser` 包含很多看起来很吓人的代码！不要被吓倒，这都是从 `repo` 根目录中的文件 `RookieParser.jjt` 自动生成的。此处的代码处理用户输入的查询（字符串）转换为表示查询的节点树（解析树）的逻辑。

#### cli/visitor

The subdirectory cli/visitor contains classes that help traverse the trees created from the parser and create objects that the database can work with directly.  
子目录 `cli/visitor` 包含帮助遍历从解析器创建的树，并创建数据库可以直接使用的对象的类。

### common

The `common` directory contains bits of useful code and general interfaces that are not limited to any one part of the codebase.  
`common` 目录包含一些有用的代码和不限于代码库的任何一部分的通用接口。

### concurrency

The `concurrency` directory contains a skeleton for adding multigranularity locking to the database. You will be implementing this in Project 4.  
`concurrency` 目录包含用于向数据库添加多粒度锁的框架。您将在项目 4 中实现它。

### databox

Our database has, like most DBMS's, a type system distinct from that of the programming language used to implement the DBMS. (Our DBMS doesn't quite provide SQL types either, but it's modeled on a simplified version of SQL types).  
与大多数 DBMS 一样，我们的数据库具有与用于实现 DBMS 的编程语言不同的类型系统。（我们的 DBMS 也不完全提供 SQL 类型，但它是基于 SQL 类型的简化版本建模的）。

The `databox` directory contains classes which represents values stored in a database, as well as their types. The various `DataBox` classes represent values of certain types, whereas the `Type` class represents types used in the database.  
`databox` 目录包含表示存储在数据库中的值及其类型的类。各种 `DataBox` 类表示某些类型的值，而 `Type` 类表示数据库中使用的类型。

An example:  
一个例子：
```java
DataBox x = new IntDataBox(42); // The integer value '42'.
Type t = Type.intType();        // The type 'int'.
Type xsType = x.type();         // Get x's type, which is Type.intType().
int y = x.getInt();             // Get x's value: 42.
String s = x.getString();       // An exception is thrown, since x is not a string.
```

### index

The `index` directory contains a skeleton for implementing B+ tree indices. You will be implementing this in Project 2.  
`index` 目录包含用于实现 B+ 树索引的框架。您将在项目 2 中实现它。

### memory

The `memory` directory contains classes for managing the loading of data into and out of memory (in other words, buffer management).  
`memory` 目录包含用于管理数据加载和加载内存的类（换句话说，缓冲区管理）。

The `BufferFrame` class represents a single buffer frame (page in the buffer pool) and supports pinning/unpinning and reading/writing to the buffer frame. All reads and writes require the frame be pinned (which is often done via the `requireValidFrame` method, which reloads data from disk if necessary, and then returns a pinned frame for the page).  
`BufferFrame` 类表示单个缓冲帧（缓冲池中的页面）并支持固定/取消固定和读取/写入缓冲帧。所有读取和写入都需要固定帧（这通常通过 `requireValidFrame` 方法完成，必要时从磁盘重新加载数据，然后返回页面的固定帧）。

The `BufferManager` interface is the public interface for the buffer manager of our DBMS.  
`BufferManager` 接口是我们 DBMS 缓冲区管理器的公共接口。

The `BufferManagerImpl` class implements a buffer manager using a write-back buffer cache with configurable eviction policy. It is responsible for fetching pages (via the disk space manager) into buffer frames, and returns Page objects to allow for manipulation of data in memory.  
`BufferManagerImpl` 类使用具有可配置逐出策略的回写缓冲区缓存实现缓冲区管理器。它负责将页面（通过磁盘空间管理器）获取到缓冲区帧中，并返回页面对象以允许对内存中的数据进行操作。

The `Page` class represents a single page. When data in the page is accessed or modified, it delegates reads/writes to the underlying buffer frame containing the page.  
`Page` 类表示单个页面。当页面中的数据被访问或修改时，它将读取/写入委托给包含该页面的底层缓冲区帧。

The `EvictionPolicy` interface defines a few methods that determine how the buffer manager evicts pages from memory when necessary. Implementations of these include the `LRUEvictionPolicy` (for LRU) and `ClockEvictionPolicy` (for clock).  
`EvictionPolicy` 接口定义了一些方法，用于确定缓冲区管理器在必要时如何从内存中逐出页面。这些的实现包括 `LRUEvictionPolicy`（用于 LRU）和 `ClockEvictionPolicy`（用于时钟）。

### io

The `io` directory contains classes for managing data on-disk (in other words, disk space management).  
`io` 目录包含用于管理磁盘上数据（换句话说，磁盘空间管理）的类。

The `DiskSpaceManager` interface is the public interface for the disk space manager of our DBMS.  
`DiskSpaceManager` 接口是我们 DBMS 的磁盘空间管理器的公共接口。

The `DiskSpaceMangerImpl` class is the implementation of the disk space manager, which maps groups of pages (partitions) to OS-level files, assigns each page a virtual page number, and loads/writes these pages from/to disk.  
`DiskSpaceMangerImpl` 类是磁盘空间管理器的实现，它将页面（分区）组映射到操作系统级别的文件，为每个页面分配一个虚拟页码，并将这些页面从/写入磁盘。

### query

The `query` directory contains classes for managing and manipulating queries.  
`query` 目录包含用于管理和操作查询的类。

The various operator classes are query operators (pieces of a query), some of which you will be implementing in Project 3.  
各种运算符类是查询运算符（查询的一部分），您将在项目 3 中实现其中一些。

The `QueryPlan` class represents a plan for executing a query (which we will be covering in more detail later in the semester). It currently executes the query as given (runs things in logical order, and performs joins in the order given), but you will be implementing a query optimizer in Project 3 to run the query in a more efficient manner.  
`QueryPlan` 类表示执行查询的计划（我们将在本学期稍后详细介绍）。它当前按给定的方式执行查询（按逻辑顺序运行事物，并按给定的顺序执行连接），但您将在项目 3 中实现查询优化器以更有效的方式运行查询。

### recovery

The `recovery` directory contains a skeleton for implementing database recovery a la ARIES. You will be implementing this in Project 5.  
`recovery` 目录包含用于实现 ARIES 数据库恢复的框架。您将在项目 5 中实现它。

### table

The `table` directory contains classes representing entire tables and records.  
`table` 目录包含表示整个表和记录的类。

The `Table` class is, as the name suggests, a table in our database. See the comments at the top of this class for information on how table data is layed out on pages.  
顾名思义，`Table` 类是我们数据库中的一个表。有关表格数据如何在页面上布局的信息，请参阅此类顶部的注释。

The `Schema` class represents the _schema_ of a table (a list of column names and their types).  
`Schema` 类表示表的模式（列名及其类型的列表）。

The `Record` class represents a record of a table (a single row). Records are made up of multiple DataBoxes (one for each column of the table it belongs to).  
`Record` 类表示表的一条记录（单行）。记录由多个数据框组成（一个对应于它所属表的每一列）。

The `RecordId` class identifies a single record in a table.  
`RecordId` 类标识表中的单个记录。

The `PageDirectory` class is an implementation of a heap file that uses a page directory.  
`PageDirectory` 类是使用页面目录的堆文件的实现。

#### table/stats

The `table/stats` directory contains classes for keeping track of statistics of a table. These are used to compare the costs of different query plans, when you implement query optimization in Project 4.  
`table/stats` 目录包含用于跟踪表统计信息的类。当您在项目 4 中实现查询优化时，这些用于比较不同查询计划的成本。

### Transaction.java

The `Transaction` interface is the _public_ interface of a transaction - it contains methods that users of the database use to query and manipulate data.  
`Transaction` 接口是事务的公共接口——它包含数据库用户用来查询和操作数据的方法。

This interface is partially implemented by the `AbstractTransaction` abstract class, and fully implemented in the `Database.Transaction` inner class.  
该接口由 `AbstractTransaction` 抽象类部分实现，完全在 `Database.Transaction` 内部类实现。

### TransactionContext.java

The `TransactionContext` interface is the _internal_ interface of a transaction - it contains methods tied to the current transaction that internal methods (such as a table record fetch) may utilize.  
`TransactionContext` 接口是事务的内部接口——它包含绑定到内部方法（例如表记录提取）可能使用的当前事务的方法。

The current running transaction's transaction context is set at the beginning of a `Database.Transaction` call (and available through the static `getCurrentTransaction` method) and unset at the end of the call.  
当前运行事务的事务上下文在 `Database.Transaction` 调用开始时设置（可通过静态 `getCurrentTransaction` 方法获得）并在调用结束时取消设置。

This interface is partially implemented by the `AbstractTransactionContext` abstract class, and fully implemented in the `Database.TransactionContext` inner class.  
该接口由 `AbstractTransactionContext` 抽象类部分实现，完全在 `Database.TransactionContext` 内部类实现。

### Database.java

The `Database` class represents the entire database. It is the public interface of our database - users of our database can use it like a Java library.  
`Database` 类代表整个数据库。它是我们数据库的公共接口——我们数据库的用户可以像使用 Java 库一样使用它。

All work is done in transactions, so to use the database, a user would start a transaction with `Database#beginTransaction`, then call some of `Transaction`'s numerous methods to perform selects, inserts, and updates.  
所有工作都在事务中完成，因此要使用数据库，用户可以使用 `Database#beginTransaction` 启动事务，然后调用 `Transaction` 的众多方法中的一些来执行选择、插入和更新。

For example:
```java
Database db = new Database("database-dir");

try (Transaction t1 = db.beginTransaction()) {
    Schema s = new Schema()
            .add("id", Type.intType())
            .add("firstName", Type.stringType(10))
            .add("lastName", Type.stringType(10));

    t1.createTable(s, "table1");

    t1.insert("table1", 1, "Jane", "Doe");
    t1.insert("table1", 2, "John", "Doe");

    t1.commit();
}

try (Transaction t2 = db.beginTransaction()) {
    // .query("table1") is how you run "SELECT * FROM table1"
    Iterator<Record> iter = t2.query("table1").execute();

    System.out.println(iter.next()); // prints [1, John, Doe]
    System.out.println(iter.next()); // prints [2, Jane, Doe]

    t2.commit();
}

db.close();
```

More complex queries can be found in [`src/test/java/edu/berkeley/cs186/database/TestDatabase.java`](https://github.com/PKUFlyingPig/CS186-Rookiedb/blob/master/src/test/java/edu/berkeley/cs186/database/TestDatabase.java).  
可以在 `src/test/java/edu/berkeley/cs186/database/TestDatabase.java` 中找到更复杂的查询。
