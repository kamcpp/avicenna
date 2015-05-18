mvn javadoc:javadoc
mvn javadoc:jar
mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=ossrh -DpomFile=avicenna-1.6.pom -Dfile=avicenna-1.6.jar
mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=ossrh -DpomFile=avicenna-1.6.pom -Dfile=avicenna-1.6-sources.jar -Dclassifier=sources
mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=ossrh -DpomFile=avicenna-1.6.pom -Dfile=avicenna-1.6-javadoc.jar -Dclassifier=javadoc
