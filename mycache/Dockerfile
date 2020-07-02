#基镜像为java，标签（版本）为8
#FROM java:8
FROM registry.cn-hangzhou.aliyuncs.com/hhit/java8:min
#作者为yonyong
MAINTAINER yonyong
#将ioc-1.0.0-SNAPSHOT.jar添加到镜像中，并重命名为app.jar
ADD mycache-1.0.0-SNAPSHOT.jar app.jar
#暴露端口号8080 项目启动时访问的是哪个端口就暴露哪个端口
EXPOSE 8080
#启动时运行 java -jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#build image 最后的 . 用来指明Dockerfile的路径（当前目录下）
#docker build -t yonyong/opcl .

#run image
#docker run -d --name ioc -p 8080:20000 yonyong/opcl