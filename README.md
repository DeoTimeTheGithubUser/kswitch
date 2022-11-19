# kswitch
Switch in kotlin, pretty much just the same as when.

Example:
```kotlin
val value = switch(readln()) {
  case({ length > 10 }) { 2 }
  case({ length > 1 }) { 1 }
  default { 0 }
}
println("Worth $value points")
```
