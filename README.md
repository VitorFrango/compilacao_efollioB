
-----------------------------------------------------------------------------------------------
					COMPILE WIZARDS
-----------------------------------------------------------------------------------------------

Este projeto utiliza o Maven para a sua estruturação e gestão de dependências.

##DEPENDÊNCIAS:
antlr4-runtime:dependência principal que é descarregada pelo Maven

##PLUGINS UTILIZADOS:
O projeto faz uso de dois plugins essenciais, que são automaticamente descarregados:
- maven-assembly-plugin: Utilizado para gerar o JAR executável.
- antlr4-maven-plugin: Responsável por gerar as classes a partir da gramática ANTLR4 especificada.

##ESTRUTURA DO PROJETO:
Os arquivos estão dispostos da seguinte forma:
- src: contém os códigos-fonte, sendo subdivididos da seguinte forma:
    - antlr4: contém a gramática ANTLR4
    - java: contém os códigos-fonte JAVA. Há dois pacotes:
    - "montpy" contém as classes gerados pelo ANTLR4 para fazer o "parse" do MontPy.g4.
    - "wizards", contém os códigos para gerar o TAC (Three Address Code) otimizado. Dentro desta pasta temos 3 classes principais:
        - A classe Main é responsável por obter o nome do arquivo ".mpy" para compilar.
        - A classe TACGenerator gera o TAC básico.
        - A classe TACOptimizer gera o otimizado.
- target: contém os arquivos gerados pelo Maven, sendo o mais importante
  "efoliob-1.0-jar-with-dependencies.jar", que é o JAR executável
- pom.xml: Configuração do Maven


##PRÉ-REQUISITOS:
- Deve ter o Maven instalado no sistema. Para verificar se o tem instalado, utilize o comando:
  mvn --version. Se não o tiver instalado, deve instalar o mesmo. Aceda a https://maven.apache.org/download.cgi e siga os passos de instalação.

##COMPILAÇÃO E EXECUÇÃO:
1)Para compilar, primeiro deve-se gerar o JAR Executável com o comando abaixo:
mvn clean compile assembly:single

2)Após esse comando, o arquivo "efoliob-1.0-jar-with-dependencies.jar" encontrar-se-á na pasta "target".

3) Após isso, basta executar o comando abaixo:
   java -jar "efoliob-1.0-jar-with-dependencies.jar" ARQUIVO_FONTE# Compilacao_EFOLIOC
