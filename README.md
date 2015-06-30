Guava Optional only
===========

This project shows how to configure proguard to cut out unnecessary classes without compile time penalty. It uses proguard treeshake function and jarjar resulted classes to avoid duplication.

Include into you build as:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
	compile 'com.github.plastiv:guava-optional:5297c893a5'
}
```

Where `5297c893a5` is the latest commit SHA. 