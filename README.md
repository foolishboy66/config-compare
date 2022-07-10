# config-compare
[TOC]

## Introdution

config-compare是一个小工具，你可以使用它来对比配置文件。目前支持的配置文件格式有yaml、yml、properties以及json，甚至你可以对比两个类型不同的文件，比如yml和json。



## Quick Start

### 1. 下载源码，获取可执行的jar包

获取可执行jar包有两种方式

- 下载编译源码

  ```
  mvn clean install -DskipTests
  ```

- 直接下载file目录中的config-compare.jar文件



### 2. 执行jar包，对比文件

执行java命令即可

```
java -jar config-compare.jar <oldFile> <newFile> [<action>]

```

参数说明如下：

- oldFile是老文件地址，必传
- newFile是新文件地址，必传
- action是可选字段，支持diff-key（对比key）、diff-value（对比value），默认为diff-key



### 3. 执行结果说明

执行结果分以下三部分：

- added items:  输出内容为旧文件中不存在而新文件中存在的key

- deleted items: 输出内容为旧文件中存在而新文件中不存在的key

- changed items: 输出内容为新旧文件中都存在的key，但是新旧文件中该key对应的value不同，会把key、valueBefore和valueAfter都打印出来

  