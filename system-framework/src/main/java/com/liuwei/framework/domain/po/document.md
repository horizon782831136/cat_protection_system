### detail: 以后用于存json数据，这样设计的好处是为扩展提供了可能。

### 每个po类继承了StateLifecycle，这样可以方便的实现生命周期管理。

### 后续可以将校验规则封装到StateLifecycle中