# GraalVM native-image substitutions for Scala

This project exists to fix the following errors when running Scala applications with native-image

```
Error: com.oracle.graal.pointsto.constraints.UnsupportedFeatureException: Invoke with MethodHandle argument could not be reduced to at most a single call or single field access. The method handle must be a compile time constant, e.g., be loaded from a `static final` field. Method that contains the method handle invocation: java.lang.invoke.MethodHandle.invokeBasic()
To diagnose the issue, you can add the option --report-unsupported-elements-at-runtime. The error is then reported at run time when the invoke is executed.
Trace: 
	at parsing java.lang.invoke.LambdaForm$MH/1971838936.invoke_MT(LambdaForm$MH)
Call path from entry point to java.lang.invoke.LambdaForm$MH/1971838936.invoke_MT(Object, Object): 
	at java.lang.invoke.LambdaForm$MH/1971838936.invoke_MT(LambdaForm$MH)
	at scala.collection.immutable.VM.releaseFence(VM.java:25)
```

To use this project, add to `build.sbt`

```diff
  // build.sbt
  lazy val myNativeImageProject = project
    .settings(
+     libraryDependencies += "org.scalameta" %% "svm-subs" % "GRAALVM_VERSION" % Compile
    )
```

Replace GRAALVM_VERSION with a GraalVM version like "20.2.0". See [releases](https://github.com/scalameta/svm-subs/releases) for what GraalVM versions are supported.

That's it. The next time you generate a native image you should not get this error anymore.


Related issues:

* https://github.com/scala/bug/issues/11634
* https://github.com/scala/bug/issues/12116

