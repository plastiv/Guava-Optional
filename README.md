Guava Optional only
===========

Custom tailored Guava for your android project needs (hello 65k method count limit!). 

1. Add Guava dependency with required version
```
injar "com.google.guava:guava:18.0"
```
2. Specify what needed to be kept at Guava (with proguard syntax)
```
-keep public class com.google.common.base.Optional {
    public *;
}
```
3. JarJar resulted stripped jar to avoid conflicts
```
project.ant {
    taskdef name: 'jarjar', classname: 'com.tonicsystems.jarjar.JarJarTask', classpath: configurations.jarjar.asPath
    jarjar(jarfile: jarjarOutputFile) {
        zipfileset(src: proguardOutputFile)
        rule pattern: "com.google.common.**", result: "com.google.repacked.common.@1"
    }
}
```
4. Share resulted jar binary with JitPack.io
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
	compile 'com.github.plastiv:guava-optional:-SNAPSHOT' // or replace '-SNAPSHOT' with exact commit SHA, like '5297c893a5'
}
```
