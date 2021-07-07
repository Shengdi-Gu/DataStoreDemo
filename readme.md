# A simple demo for Rxjava3 and Datastore

```java
// init key
private val testKey by myIntPreferencesKey(0)

// get value
var value = context.getValueSync(testKey)

// put value
context.putValue(testKey, 0)

// observe value change
context.obValue(testKey).subscribe({ it ->
            Log.d(TAG, "onValueChange: $it")
        }, {
            Log.e(TAG, it.message ?: "")
        })

```