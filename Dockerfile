FROM openjdk:1.8

EXPOSE 8080

RUN mkdir ./CurrencyGif

COPY ./build/libs/*.jar ./CurrencyGif

CMD java -jar ./CurrencyGif/*.jar