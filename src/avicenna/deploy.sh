mvn release:prepare -P src,javadoc -Dresume=false

mkdir -p .bundle

mv pom.xml.releaseBackup .bundle
