# mvn javadoc:javadoc
# mvn javadoc:jar
# mvn source:jar

version=1.7

mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=ossrh -DpomFile=avicenna-$version.pom -Dfile=target/avicenna-$version.jar
mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=ossrh -DpomFile=avicenna-$version.pom -Dfile=target/avicenna-$version-sources.jar -Dclassifier=sources
mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=ossrh -DpomFile=avicenna-$version.pom -Dfile=target/avicenna-$version-javadoc.jar -Dclassifier=javadoc
