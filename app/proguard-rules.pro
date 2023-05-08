-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite* {
   <fields>;
}
-dontwarn org.openjsse.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**