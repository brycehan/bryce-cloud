# VSCode Markdown 预览
方法一：

Ctrl + k  v

方法二：

1) View -> Command palette...
2) 输入 Markdown
3) 选择 Markdown: Open Preview to the Side

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

### GitHub token 配置
1) 进入 Settings / Developer settings
2) 打开 Personal access tokens
3) 点击 Generate new token

### 文字生成 banner 图
[http://www.patorjk.com/software/taag/](http://www.patorjk.com/software/taag/)




