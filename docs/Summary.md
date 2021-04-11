### Windows 10 打开设置

Win + I 快捷键

### Linux 常用命令
```
more
```

### IntelliJ IDEA Maven编译时Java目录中的XML文件要包含的配置

在 project 标签中添加：
```
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*Mapper.xml</include>
            </includes>
        </resource>
    </resources>
</build>		
```

### IntelliJ IDEA 导入Maven 项目失败问题

报错：Unable to import maven project: See logs for details

1. 查看日志

```
Help -> Show Log in Explorer
```

2. 日志内容

```
No implementation for org.apache.maven.model.path.PathTranslator was bound.
```

3. 解决方案

Maven版本太高了，IDEA识别不了，用内置的就可以了，配置为如下：

```
Settings -> Build,Execution,Deployment -> Maven -> Maven home directory: Bundled(Maven 3)
```

4. 清空缓存

```
File -> Invalidate Caches / Restart...
```

### GitHub token 配置

1) 进入 Settings / Developer settings
2) 打开 Personal access tokens
3) 点击 Generate new token

### 文字生成 banner 图
[http://www.patorjk.com/software/taag/](http://www.patorjk.com/software/taag/)




